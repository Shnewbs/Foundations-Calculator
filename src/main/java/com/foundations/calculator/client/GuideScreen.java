package com.foundations.calculator.client;

import java.util.*;
import com.foundations.guide.api.*;
import com.foundations.guide.api.GuideData.*;
import com.foundations.calculator.client.guide.*;
import com.foundations.calculator.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.ChatFormatting;
import org.lwjgl.glfw.GLFW;

/**
 * Foundations book-style reader. R9 deliberately separates the guide content contract from
 * presentation: the same catalog remains consumable by Patchouli and a future master guide.
 */
public final class GuideScreen extends Screen {
    public static final String BOOK="foundations_calculator:field_guide";

    private static final int INK=0xff302d29;
    private static final int MUTED=0xff6b6257;
    private static final int LINK=0xff236878;
    private static final int PAPER=0xffeee5cf;
    private static final int PAPER_LIGHT=0xfff6efdc;
    private static final int PAPER_SHADE=0xffd5cab2;
    private static final int LEATHER=0xff85898b;
    private static final int LEATHER_LIGHT=0xffa9acae;
    private static final int LEATHER_DARK=0xff4d5153;
    private static final int SPINE=0xff5b5e60;

    private record Action(String kind,String value){}
    private record Row(int y,int height,FormattedCharSequence text,int color,ItemStack item,String image,int imageW,int imageH,List<String> cells,Action action,String style){}
    private record Hotspot(int x,int y,int w,int h,String label,boolean selected,Runnable action,String tooltip){
        boolean contains(double mx,double my){return mx>=x&&mx<x+w&&my>=y&&my<y+h;}
    }

    private final List<Row> document=new ArrayList<>();
    private final List<Hotspot> hotspots=new ArrayList<>();
    private final Map<String,Integer> recipePages=new HashMap<>();
    private final GuideNavigation navigation=GuidePreferences.navigation();

    private Target target;
    private GuideSearch index;
    private List<GuideSearch.Hit> hits=List.of();
    private EditBox search;
    private String query="",chapter="";
    private boolean allGuides,bookmarksOnly,contents;

    private int bookLeft,bookTop,bookWidth,bookHeight,bookRight,bookBottom,spineX;
    private int navX,navWidth,pageX,pageWidth,contentTop,contentBottom,navListTop;
    private int documentHeight,scroll,listOffset,age;
    private boolean dragging;
    private long catalogRevision=-1,recipeRevision=-1,configRevision=-1,valueRevision=-1,researchRevision=-1;
    private boolean waiting;
    private ItemStack hovered=ItemStack.EMPTY;
    private String tip="",message="";

    public GuideScreen(){this(new Target(BOOK,"foundations_calculator:getting_started/welcome"));}
    public GuideScreen(Target target){super(Component.literal("Foundations Field Guide"));this.target=target;navigation.visit(target);}

    public static void open(){
        var current=GuidePreferences.navigation().current().filter(t->t.guide().equals(BOOK)&&GuideApi.catalog().entry(t).isPresent()).orElse(new Target(BOOK,""));
        open(current);
    }
    public static void open(Target target){
        Runnable fallback=()->Minecraft.getInstance().setScreen(new GuideScreen(target));
        if(ClientConfig.flag(ClientConfig.GUIDE_REDIRECT))GuideApi.open(target,fallback);else fallback.run();
    }

    private boolean narrow(){return width<620||height<360;}
    private boolean listVisible(){return !narrow()||contents;}
    private boolean pageVisible(){return !narrow()||!contents;}

    private void layout(){
        if(narrow()){
            bookWidth=Math.min(520,Math.max(180,width-18));bookWidth=Math.min(bookWidth,width-8);
            bookHeight=Math.min(660,Math.max(150,height-16));bookHeight=Math.min(bookHeight,height-8);
        }else{
            // Match the compact Foundations Field Guide footprint used by PL4 instead of
            // occupying most of the desktop. The pages remain independently scrollable,
            // so reducing the shell does not discard guide content.
            bookWidth=Math.min(width-48,640);
            bookHeight=Math.min(height-36,360);
        }
        bookLeft=(width-bookWidth)/2;bookTop=(height-bookHeight)/2;bookRight=bookLeft+bookWidth;bookBottom=bookTop+bookHeight;
        if(narrow()){
            spineX=bookLeft+18;
            navX=pageX=bookLeft+28;
            navWidth=pageWidth=bookWidth-56;
            contentTop=bookTop+83;contentBottom=bookBottom-48;navListTop=bookTop+132;
        }else{
            spineX=bookLeft+bookWidth/2;
            int inner=bookWidth-68;int half=(inner-18)/2;
            navX=bookLeft+27;navWidth=half;
            pageX=spineX+18;pageWidth=half;
            contentTop=bookTop+88;contentBottom=bookBottom-49;navListTop=bookTop+132;
        }
    }

    @Override protected void init(){
        layout();
        updateIndex();refreshSearch();buildDocument();
        hotspots.clear();
        buildHotspots();
        search=new EditBox(font,navX+10,bookTop+68,Math.max(60,navWidth-20),18,Component.literal("Search field guides"));
        search.setMaxLength(256);search.setHint(Component.literal("Search the guide…"));search.setValue(query);
        search.setResponder(value->{query=value;listOffset=0;refreshSearch();});
        search.visible=listVisible();search.active=listVisible();addRenderableWidget(search);
    }

    private void buildHotspots(){
        int topY=bookTop+15;
        addHotspot(bookRight-54,topY,34,18,"Close",false,this::onClose,"Close the Field Guide");
        addHotspot(bookRight-127,topY,68,18,"Research",false,()->minecraft.setScreen(new ResearchScreen(this)),"Open research and mastery");
        if(narrow())addHotspot(bookLeft+20,topY,72,18,contents?"Read":"Contents",true,()->{contents=!contents;reinitWidgets();},contents?"Return to the page":"Open contents");
        if(listVisible()){
            int y=bookTop+96;
            addHotspot(navX+8,y,58,18,allGuides?"All guides":"This guide",allGuides,()->{allGuides=!allGuides;bookmarksOnly=false;chapter="";listOffset=0;refreshSearch();reinitWidgets();},"Choose whether search includes other compatible guides");
            addHotspot(navX+70,y,48,18,"Saved",bookmarksOnly,()->{bookmarksOnly=!bookmarksOnly;chapter="";listOffset=0;refreshSearch();reinitWidgets();},"Show saved entries");
            addHotspot(navX+122,y,42,18,"All",chapter.isEmpty()&&!bookmarksOnly,()->{chapter="";bookmarksOnly=false;allGuides=false;listOffset=0;refreshSearch();reinitWidgets();},"Show all Calculator chapters");
        }
        if(pageVisible()){
            int y=bookBottom-39,x=pageX;
            addHotspot(x,y,32,18,"<",false,()->navigation.back().ifPresent(t->navigate(t,false)),"Back");x+=36;
            addHotspot(x,y,32,18,">",false,()->navigation.forward().ifPresent(t->navigate(t,false)),"Forward");x+=36;
            addHotspot(x,y,48,18,navigation.bookmarked(target)?"Saved":"Save",navigation.bookmarked(target),()->{navigation.toggle(target);GuidePreferences.save();if(bookmarksOnly)refreshSearch();reinitWidgets();},"Save or unsave this entry");x+=52;
            addHotspot(x,y,54,18,"Refresh",false,()->{GuideValues.refresh();GuideRecipeProvider.clear();buildDocument();},"Refresh live server values");
        }
        if(!narrow())buildChapterTabs();
    }

    private void buildChapterTabs(){
        Book book=GuideApi.catalog().books().get(target.guide());if(book==null)return;
        var chapters=book.chapters().values().stream().sorted(Comparator.comparingInt(Chapter::order).thenComparing(Chapter::id)).toList();
        int tabX=bookRight-4,tabY=bookTop+54,tabH=Math.max(20,Math.min(29,(bookHeight-112)/Math.max(1,chapters.size())));
        String current=currentChapterTitle();
        for(Chapter c:chapters){
            String title=c.title();String label=tabLabel(c);
            boolean selected=title.equals(chapter)||(chapter.isEmpty()&&title.equals(current));
            addHotspot(tabX,tabY,38,tabH-2,label,selected,()->{chapter=title;bookmarksOnly=false;allGuides=false;listOffset=0;refreshSearch();reinitWidgets();},title);
            tabY+=tabH;
        }
    }

    private String tabLabel(Chapter c){
        String id=c.id();int colon=id.indexOf(':');if(colon>=0)id=id.substring(colon+1);
        return switch(id){
            case "getting_started"->"START";case "calculators"->"CALC";case "circuits"->"CHIP";case "power"->"PWR";case "machines"->"MACH";
            case "multiblocks"->"BUILD";case "farming"->"FARM";case "research"->"R&D";case "troubleshooting"->"FIX";case "pack_authors"->"API";
            default->id.length()>5?id.substring(0,5).toUpperCase(Locale.ROOT):id.toUpperCase(Locale.ROOT);
        };
    }
    private String currentChapterTitle(){
        Book book=GuideApi.catalog().books().get(target.guide());if(book==null)return "";
        var entry=book.entry(target.entry());if(entry.isEmpty())return "";
        Chapter c=book.chapters().get(entry.get().chapter());return c==null?"":c.title();
    }
    private void addHotspot(int x,int y,int w,int h,String label,boolean selected,Runnable action,String tooltip){hotspots.add(new Hotspot(x,y,w,h,label,selected,action,tooltip));}
    private void reinitWidgets(){clearWidgets();init();}

    private void updateIndex(){if(index==null||catalogRevision!=GuideApi.revision()){index=new GuideSearch(GuideApi.catalog());catalogRevision=GuideApi.revision();}}
    private void refreshSearch(){
        updateIndex();hits=index.search(query,allGuides?"":target.guide(),chapter,8192);
        if(bookmarksOnly)hits=hits.stream().filter(h->navigation.bookmarked(h.target())).toList();
        listOffset=Math.clamp(listOffset,0,Math.max(0,hits.size()-listRows()));
    }
    private int listRows(){return Math.max(1,(bookBottom-46-navListTop)/28);}

    private void navigate(Target next,boolean visit){
        if(GuideApi.catalog().entry(next).isEmpty()){message="That entry is unavailable. Its guide or required mod may be missing.";return;}
        Book book=GuideApi.catalog().books().get(next.guide());target=new Target(next.guide(),next.entry().isEmpty()?book.landing():next.entry());
        if(visit)navigation.visit(target);scroll=0;message="";contents=false;chapter="";buildDocument();refreshSearch();GuidePreferences.save();reinitWidgets();
    }

    private void addText(String value,int color,boolean bold,Action action,int inset,String style){
        var text=Component.literal(value);if(bold)text=text.withStyle(ChatFormatting.BOLD);
        int width=Math.max(40,pageWidth-inset-(style.equals("callout")||style.equals("setting")?14:0));
        for(var line:font.split(text,width)){
            document.add(new Row(documentHeight,12,line,color,ItemStack.EMPTY,"",0,0,List.of(),action,style));documentHeight+=12;
        }
        documentHeight+=style.equals("heading")?7:4;
    }
    private void addText(String value,int color,boolean bold,Action action,int inset){addText(value,color,bold,action,inset,"text");}
    private void addCallout(String value){addText(value,INK,false,null,0,"callout");documentHeight+=3;}
    private void addSetting(String value){addText(value,MUTED,false,null,0,"setting");}
    private void addTableRow(String value){addText(value,INK,false,null,0,"table");}

    private void addItem(ItemStack item,String label){
        var wrapped=font.split(Component.literal(label),Math.max(40,pageWidth-36));int height=Math.max(24,wrapped.size()*12+4);int y=documentHeight;
        Action action=item.isEmpty()?null:new Action("item",BuiltInRegistries.ITEM.getKey(item.getItem()).toString());
        document.add(new Row(y,height,null,INK,item.copy(),"",0,0,List.of(),action,"item"));int lineY=y+3;
        for(var line:wrapped){document.add(new Row(lineY,12,line,INK,ItemStack.EMPTY,"text_inset",0,0,List.of(),action,"item_text"));lineY+=12;}
        documentHeight+=height+5;
    }
    private ItemStack item(String id){Identifier key=Identifier.tryParse(id);return key!=null&&BuiltInRegistries.ITEM.containsKey(key)?new ItemStack(BuiltInRegistries.ITEM.get(key)):ItemStack.EMPTY;}
    private int number(Map<String,String> values,String key,int fallback,int max){try{return Math.clamp(Integer.parseInt(values.getOrDefault(key,"")),1,max);}catch(NumberFormatException e){return fallback;}}

    private void section(Block b){
        switch(b.type()){
            case "heading"->addText(b.text(),INK,true,null,0,"heading");
            case "paragraph"->addText(b.text(),INK,false,null,0,"text");
            case "callout"->addCallout(b.text());
            case "list","steps"->{
                if(!b.text().isEmpty())addText(b.text(),INK,true,null,0,"heading");int n=1;
                for(var row:b.rows())addText((b.type().equals("steps")?n++ + ". ":"• ")+String.join(" — ",row),INK,false,null,10,b.type().equals("steps")?"step":"list");
            }
            case "table"->{if(!b.text().isEmpty())addText(b.text(),INK,true,null,0,"heading");for(var row:b.rows())addTableRow(String.join("  ·  ",row));documentHeight+=3;}
            case "link"->addText("› "+b.text(),LINK,false,new Action("link",b.target()),0,"link");
            case "item"->{var stack=item(b.target());if(!stack.isEmpty())stack.setCount(number(b.options(),"count",1,9999));addItem(stack,b.text().isBlank()?(stack.isEmpty()?"Missing item: "+b.target():stack.getHoverName().getString()):b.text());}
            case "recipe"->{
                addText(b.text().isEmpty()?"Recipes from this server":b.text(),INK,true,null,0,"heading");
                Book owner=GuideApi.catalog().books().get(target.guide());String provider=owner==null?"":owner.owner();String key=target.guide()+"|"+b.target()+"|"+b.options().getOrDefault("profile","");
                var view=GuideApi.recipes(new RecipeRequest(target,provider,b.target(),recipePages.getOrDefault(key,0),b.options().getOrDefault("profile","")));
                addText(view.label(),LINK,false,null,0,"recipe_label");waiting|=view.state()==State.PENDING;
                for(var part:view.blocks())section(part);
                if(view.count()>1){addText("‹ Previous recipe",LINK,false,new Action("recipe_prev",key),0,"link");addText("Next recipe ›",LINK,false,new Action("recipe_next",key),0,"link");}
            }
            case "live"->{
                addText(b.text().isBlank()?"Live server information":b.text(),INK,true,null,0,"heading");
                var value=GuideApi.live(new LiveRequest(target,b.target(),b.options().getOrDefault("key","")));waiting|=value.state()==State.PENDING;
                addText(value.label(),LINK,false,null,0,"live_label");for(String line:value.lines())addSetting(line);
            }
            case "layers"->{
                addText(b.text(),INK,true,null,0,"heading");
                for(var row:b.rows()){
                    String cells=String.join("",row);List<String> chars=cells.chars().mapToObj(c->String.valueOf((char)c)).toList();
                    if(chars.size()>32){addText("Diagram row too wide",MUTED,false,null,0);continue;}
                    int cell=Math.max(5,Math.min(14,pageWidth/Math.max(1,chars.size())));
                    document.add(new Row(documentHeight,cell,null,INK,ItemStack.EMPTY,"",cell,0,chars,null,"layers"));documentHeight+=cell;
                }
                documentHeight+=8;String legend=b.options().getOrDefault("legend","");if(!legend.isEmpty())addText(legend,MUTED,false,null,0,"caption");
            }
            case "image"->{
                addText(b.text(),MUTED,false,null,0,"caption");Identifier id=Identifier.tryParse(b.target());
                if(id==null||minecraft.getResourceManager().getResource(id).isEmpty()){addText("Illustration unavailable: "+b.target(),MUTED,false,null,0);break;}
                int tw=number(b.options(),"width",128,1024),th=number(b.options(),"height",128,1024),dw=Math.min(pageWidth,tw),dh=Math.max(1,th*dw/tw);
                if(dh>512){dw=dw*512/dh;dh=512;}
                document.add(new Row(documentHeight,dh,null,INK,ItemStack.EMPTY,b.target(),tw,th,List.of(),null,"image"));documentHeight+=dh+8;
            }
            default->addText("Unsupported content: "+b.text(),MUTED,false,null,0,"caption");
        }
    }

    private void buildDocument(){
        if(font==null||pageWidth<=0)return;document.clear();documentHeight=0;waiting=false;
        var book=GuideApi.catalog().books().get(target.guide());var entry=book==null?Optional.<Entry>empty():book.entry(target.entry());
        if(entry.isEmpty()){
            addText("Guide unavailable",INK,true,null,0,"title");
            addCallout("The Field Guide resources could not be loaded. Reload resources with F3+T and check the diagnostics below.");
            for(String error:GuideApi.catalog().diagnostics())addText(error,MUTED,false,null,0,"caption");
        }else{
            if(target.entry().isEmpty())target=new Target(book.id(),entry.get().id());
            addText(entry.get().title(),INK,true,null,0,"title");
            for(Block b:entry.get().blocks())section(b);
        }
        scroll=Math.clamp(scroll,0,maxScroll());recipeRevision=RecipeIndex.revision();configRevision=CalculatorConfig.revision();valueRevision=GuideValues.revision();researchRevision=minecraft.level==null?0:ResearchData.revision(minecraft.level);
    }
    private int maxScroll(){return Math.max(0,documentHeight-(contentBottom-contentTop));}

    @Override public void tick(){
        age++;boolean catalogChanged=catalogRevision!=GuideApi.revision();if(catalogChanged){updateIndex();refreshSearch();}
        if(catalogChanged||recipeRevision!=RecipeIndex.revision()||configRevision!=CalculatorConfig.revision()||valueRevision!=GuideValues.revision()||(minecraft.level!=null&&researchRevision!=ResearchData.revision(minecraft.level))||waiting&&age%10==0)buildDocument();
    }

    @Override public void renderBackground(GuiGraphicsExtractor g,int x,int y,float delta){}

    private void renderBookShell(GuiGraphicsExtractor g,int accent){
        g.fill(0,0,width,height,0x8d000000);
        // soft-grey leather cover and worn edging
        g.fill(bookLeft-4,bookTop-4,bookRight+4,bookBottom+4,0xff292c2e);
        g.fill(bookLeft,bookTop,bookRight,bookBottom,LEATHER_DARK);
        g.fill(bookLeft+4,bookTop+4,bookRight-4,bookBottom-4,LEATHER);
        g.fill(bookLeft+7,bookTop+7,bookRight-7,bookTop+10,LEATHER_LIGHT);
        g.fill(bookLeft+7,bookBottom-10,bookRight-7,bookBottom-7,0xff6d7173);
        // page block
        if(narrow()){
            g.fill(bookLeft+18,bookTop+41,bookRight-18,bookBottom-18,PAPER_SHADE);
            g.fill(bookLeft+22,bookTop+37,bookRight-22,bookBottom-22,PAPER);
            g.fill(bookLeft+25,bookTop+40,bookRight-25,bookBottom-25,PAPER_LIGHT);
        }else{
            g.fill(bookLeft+16,bookTop+38,spineX-7,bookBottom-18,PAPER_SHADE);
            g.fill(bookLeft+20,bookTop+34,spineX-11,bookBottom-22,PAPER);
            g.fill(bookLeft+23,bookTop+37,spineX-14,bookBottom-25,PAPER_LIGHT);
            g.fill(spineX+7,bookTop+38,bookRight-16,bookBottom-18,PAPER_SHADE);
            g.fill(spineX+11,bookTop+34,bookRight-20,bookBottom-22,PAPER);
            g.fill(spineX+14,bookTop+37,bookRight-23,bookBottom-25,PAPER_LIGHT);
            // binding and inner-page shadows
            g.fill(spineX-10,bookTop+32,spineX+10,bookBottom-20,SPINE);
            g.fill(spineX-7,bookTop+35,spineX-3,bookBottom-23,0xff3f4345);
            g.fill(spineX+3,bookTop+35,spineX+7,bookBottom-23,0xff777b7d);
            g.fill(spineX-2,bookTop+38,spineX+2,bookBottom-26,0xff25282a);
        }
        // restrained technical accent on the cover
        g.fill(bookLeft+11,bookTop+12,bookLeft+14,bookBottom-12,accent);
        g.fill(bookRight-14,bookTop+12,bookRight-11,bookBottom-12,accent);
    }

    private void renderHotspots(GuiGraphicsExtractor g,int mx,int my,int accent){
        for(Hotspot h:hotspots){
            boolean hover=h.contains(mx,my);int bg=h.selected()?0xff355f68:hover?0xffb7b0a2:0xff716f6b;int fg=h.selected()?0xfff5f0df:0xfff1e8d3;
            g.fill(h.x(),h.y(),h.x()+h.w(),h.y()+h.h(),0xff343638);
            g.fill(h.x()+1,h.y()+1,h.x()+h.w()-1,h.y()+h.h()-1,bg);
            if(h.selected())g.fill(h.x()+1,h.y()+h.h()-3,h.x()+h.w()-1,h.y()+h.h()-1,accent);
            String label=font.plainSubstrByWidth(h.label(),Math.max(1,h.w()-6));int tx=h.x()+(h.w()-font.width(label))/2,ty=h.y()+(h.h()-8)/2;
            g.drawString(font,label,tx,ty,fg,false);if(hover&&tip.isEmpty())tip=h.tooltip();
        }
    }

    private void renderContents(GuiGraphicsExtractor g,int mx,int my,int accent){
        g.drawString(font,"CALCULATOR FIELD GUIDE",navX+8,bookTop+45,INK,false);
        ItemStack guide=item("foundations_calculator:info_calculator");if(!guide.isEmpty())g.renderItem(guide,navX+navWidth-28,bookTop+42);
        g.fill(navX+8,bookTop+61,navX+navWidth-8,bookTop+62,0xffbdb19a);
        String filter=bookmarksOnly?"Saved entries":chapter.isEmpty()?"All chapters":chapter;
        g.drawString(font,font.plainSubstrByWidth(filter,navWidth-20),navX+9,navListTop-13,MUTED,false);
        for(int i=0;i<listRows()&&i+listOffset<hits.size();i++){
            var hit=hits.get(i+listOffset);int y=navListTop+28*i;boolean selected=hit.target().equals(target),hover=mx>=navX+6&&mx<navX+navWidth-7&&my>=y-1&&my<y+26;
            if(selected||hover){g.fill(navX+6,y-1,navX+navWidth-7,y+25,selected?0xffd0dcda:0xffebe3d1);g.fill(navX+6,y-1,navX+9,y+25,selected?accent:0xffa59b87);}
            g.drawString(font,font.plainSubstrByWidth(hit.title(),navWidth-24),navX+12,y+2,INK,false);
            g.drawString(font,font.plainSubstrByWidth(allGuides?hit.bookTitle():hit.chapter(),navWidth-24),navX+12,y+14,MUTED,false);
            if(hover)tip=hit.title()+" · "+hit.chapter();
        }
        String count=(hits.isEmpty()?"0":(Math.min(listOffset+1,hits.size())+"–"+Math.min(hits.size(),listOffset+listRows())))+" / "+hits.size();
        g.drawString(font,count,navX+9,bookBottom-39,MUTED,false);
        if(hits.size()>listRows()){
            int track=bookBottom-48-navListTop,thumb=Math.max(12,track*listRows()/hits.size());int y=navListTop+(track-thumb)*listOffset/Math.max(1,hits.size()-listRows());
            g.fill(navX+navWidth-10,navListTop,navX+navWidth-8,bookBottom-48,0xffc6baa4);g.fill(navX+navWidth-11,y,navX+navWidth-7,y+thumb,accent);
        }
    }

    private void renderPage(GuiGraphicsExtractor g,int mx,int my,int accent){
        Book book=GuideApi.catalog().books().get(target.guide());String title=book==null?"Calculator Field Guide":book.title();
        var entry=book==null?Optional.<Entry>empty():book.entry(target.entry());String chapterTitle=entry.isEmpty()?"Contents":Optional.ofNullable(book.chapters().get(entry.get().chapter())).map(Chapter::title).orElse("Field notes");
        g.drawString(font,font.plainSubstrByWidth(title,pageWidth),pageX,bookTop+47,LINK,false);
        g.drawString(font,font.plainSubstrByWidth(chapterTitle,pageWidth),pageX,bookTop+61,MUTED,false);
        g.fill(pageX,bookTop+75,pageX+pageWidth,bookTop+76,0xffb7aa92);
        g.enableScissor(pageX-2,contentTop,pageX+pageWidth+2,contentBottom);
        int tableIndex=0;
        for(Row row:document){
            int y=contentTop+row.y()-scroll;if(y+row.height()<contentTop||y>=contentBottom)continue;
            if(row.style().equals("callout")){
                g.fill(pageX-2,y-1,pageX+pageWidth,y+row.height()+1,0xffd9e4e0);g.fill(pageX-2,y-1,pageX+2,y+row.height()+1,accent);
            }else if(row.style().equals("setting")){
                g.fill(pageX-1,y-1,pageX+pageWidth-1,y+row.height()+1,0xffe2ddd1);
            }else if(row.style().equals("table")){
                if((tableIndex++&1)==0)g.fill(pageX-1,y-1,pageX+pageWidth-1,y+row.height()+1,0xffe8e0cd);
            }else if(row.style().equals("heading")){
                g.fill(pageX,y+10,pageX+Math.min(pageWidth,Math.max(28,font.width(row.text())+4)),y+11,0xffb8aa91);
            }
            if(row.text()!=null){int inset=row.image().equals("text_inset")?28:(row.style().equals("callout")||row.style().equals("setting")?7:row.style().equals("step")||row.style().equals("list")?5:0);g.drawString(font,row.text(),pageX+inset,y,row.color(),false);}
            if(!row.item().isEmpty()){
                g.fill(pageX-2,y-1,pageX+23,y+22,0xffd8cfbc);g.renderItem(row.item(),pageX+2,y+2);if(mx>=pageX&&mx<pageX+24&&my>=y&&my<y+23)hovered=row.item();
            }
            if(!row.cells().isEmpty()){
                int cell=row.imageW();for(int i=0;i<row.cells().size();i++){String c=row.cells().get(i);int x=pageX+i*cell;int color=switch(c){case "S","L"->0xff7f8e88;case "G"->0xffa2cacf;case "C"->accent;case "W"->0xff83aedd;case "P","T"->0xffb29e72;default->0xffe2d8bf;};g.fill(x,y,x+cell-1,y+cell-1,color);if(cell>=9)g.drawString(font,c,x+(cell-font.width(c))/2,y+(cell-8)/2,INK,false);}
            }
            if(!row.image().isEmpty()&&!row.image().equals("text_inset")){
                int dw=Math.min(pageWidth,row.imageW());if(row.imageH()*dw/row.imageW()>512)dw=dw*512/(row.imageH()*dw/row.imageW());
                g.pose().pushPose();g.pose().translate(pageX,y,0);g.pose().scale((float)dw/row.imageW(),(float)row.height()/row.imageH(),1);g.blit(Identifier.parse(row.image()),0,0,0,0,row.imageW(),row.imageH(),row.imageW(),row.imageH());g.pose().popPose();
            }
            if(row.action()!=null&&mx>=pageX&&mx<pageX+pageWidth&&my>=Math.max(y,contentTop)&&my<Math.min(y+row.height(),contentBottom)&&tip.isEmpty())tip="Open";
        }
        g.disableScissor();
        if(maxScroll()>0){int track=contentBottom-contentTop,thumb=Math.max(18,track*track/Math.max(track,documentHeight));int sy=contentTop+(track-thumb)*scroll/Math.max(1,maxScroll());g.fill(pageX+pageWidth+4,contentTop,pageX+pageWidth+6,contentBottom,0xffc7baa2);g.fill(pageX+pageWidth+3,sy,pageX+pageWidth+7,sy+thumb,accent);}
    }

    @Override public void render(GuiGraphicsExtractor g,int mx,int my,float delta){
        hovered=ItemStack.EMPTY;tip="";int accent=0xff000000|ClientConfig.color(ClientConfig.ACCENT,0x47acb9);
        renderBookShell(g,accent);
        if(listVisible())renderContents(g,mx,my,accent);
        if(pageVisible())renderPage(g,mx,my,accent);
        renderHotspots(g,mx,my,accent);
        super.render(g,mx,my,delta); // only the search EditBox renders here; book content stays crisp.
        if(!message.isEmpty())g.renderTooltip(font,Component.literal(message),Math.min(mx,width-20),Math.min(my,height-20));
        else if(!hovered.isEmpty())g.renderTooltip(font,hovered,mx,my);
        else if(!tip.isEmpty())g.renderTooltip(font,Component.literal(tip),mx,my);
    }

    private void action(Action a){
        switch(a.kind()){
            case "link"->{try{navigate(Target.parse(a.value(),target.guide()),true);}catch(RuntimeException e){message="Invalid entry link";}}
            case "item"->{var match=index.search(a.value(),"","",8192).stream().filter(h->GuideApi.catalog().entry(h.target()).map(e->e.items().contains(a.value())).orElse(false)).findFirst();if(match.isPresent())navigate(match.get().target(),true);else{query=a.value();allGuides=true;chapter="";contents=true;reinitWidgets();}}
            case "recipe_prev","recipe_next"->{if(recipePages.size()>=256&&!recipePages.containsKey(a.value()))recipePages.clear();recipePages.merge(a.value(),a.kind().equals("recipe_prev")?-1:1,Integer::sum);int old=scroll;buildDocument();scroll=Math.min(old,maxScroll());}
        }
    }

    @Override public boolean mouseClicked(double x,double y,int button){
        message="";if(super.mouseClicked(x,y,button))return true;if(button!=0)return false;
        for(Hotspot h:hotspots)if(h.contains(x,y)){h.action().run();return true;}
        if(pageVisible()&&maxScroll()>0&&x>=pageX+pageWidth&&x<=pageX+pageWidth+10&&y>=contentTop&&y<contentBottom){dragging=true;drag(y);return true;}
        if(listVisible()&&x>=navX+4&&x<navX+navWidth&&y>=navListTop&&y<navListTop+listRows()*28){int i=listOffset+(int)(y-navListTop)/28;if(i<hits.size()){navigate(hits.get(i).target(),true);return true;}}
        if(pageVisible()&&x>=pageX&&x<pageX+pageWidth&&y>=contentTop&&y<contentBottom){int dy=(int)y-contentTop+scroll;for(Row r:document)if(r.action()!=null&&dy>=r.y()&&dy<r.y()+r.height()){action(r.action());return true;}}
        return false;
    }
    private void drag(double y){scroll=(int)Math.clamp((y-contentTop)/(double)Math.max(1,contentBottom-contentTop)*maxScroll(),0,maxScroll());}
    @Override public boolean mouseDragged(double x,double y,int button,double dx,double dy){if(dragging){drag(y);return true;}return super.mouseDragged(x,y,button,dx,dy);}
    @Override public boolean mouseReleased(double x,double y,int button){dragging=false;return super.mouseReleased(x,y,button);}
    @Override public boolean mouseScrolled(double x,double y,double dx,double dy){if(listVisible()&&(narrow()||x<spineX)){listOffset=Math.clamp(listOffset-(int)(dy*3),0,Math.max(0,hits.size()-listRows()));return true;}scroll=Math.clamp(scroll-(int)(dy*28),0,maxScroll());return true;}

    @Override public boolean keyPressed(int key,int scan,int mods){
        if(hasControlDown()&&key==GLFW.GLFW_KEY_F){if(narrow()&&!contents){contents=true;reinitWidgets();}setFocused(search);search.setFocused(true);return true;}
        if(search!=null&&search.isFocused()){if(key==GLFW.GLFW_KEY_ENTER&&!hits.isEmpty()){navigate(hits.getFirst().target(),true);return true;}return super.keyPressed(key,scan,mods);}
        if(hasAltDown()&&key==GLFW.GLFW_KEY_LEFT){navigation.back().ifPresent(t->navigate(t,false));return true;}
        if(hasAltDown()&&key==GLFW.GLFW_KEY_RIGHT){navigation.forward().ifPresent(t->navigate(t,false));return true;}
        int step=key==GLFW.GLFW_KEY_PAGE_DOWN?contentBottom-contentTop-12:key==GLFW.GLFW_KEY_PAGE_UP?-(contentBottom-contentTop-12):key==GLFW.GLFW_KEY_DOWN?24:key==GLFW.GLFW_KEY_UP?-24:0;
        if(step!=0){scroll=Math.clamp(scroll+step,0,maxScroll());return true;}if(key==GLFW.GLFW_KEY_HOME){scroll=0;return true;}if(key==GLFW.GLFW_KEY_END){scroll=maxScroll();return true;}
        return super.keyPressed(key,scan,mods);
    }

    @Override public void onClose(){GuidePreferences.save();GuideRecipeProvider.clear();super.onClose();}
    @Override public void removed(){GuidePreferences.save();}
    @Override public boolean isPauseScreen(){return false;}
}
