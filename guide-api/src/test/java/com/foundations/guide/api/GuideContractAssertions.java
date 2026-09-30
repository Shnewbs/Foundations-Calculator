package com.foundations.guide.api;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.foundations.guide.api.GuideData.*;
/** Executes actual API and authored books without Minecraft, stubs, reflection or copied production classes. */
public final class GuideContractAssertions {
 private static int checks;
 private static void check(boolean pass,String message){checks++;if(!pass)throw new AssertionError(message);}
 private static void reject(Runnable r){boolean bad=false;try{r.run();}catch(IllegalArgumentException e){bad=true;}check(bad,"Expected rejected input");}
 public static Map<String,String> resources(Path assets)throws Exception{
  var map=new TreeMap<String,String>();try(var files=Files.walk(assets)){for(Path p:files.filter(f->f.toString().endsWith(".json")&&f.toString().contains("foundations_guides")).toList()){String s=assets.relativize(p).toString().replace('\\','/');map.put(s.substring(0,s.indexOf('/'))+":"+s.substring(s.indexOf('/')+1),Files.readString(p));}}return map;
 }
 public static Catalog corpus(Path assets)throws Exception{
  var map=resources(assets);Catalog c=GuideParser.scan(map,x->true);check(c.diagnostics().isEmpty(),"Guide errors "+c.diagnostics());check(c.books().size()==1,"Calculator book count");
  var book=c.books().get("foundations_calculator:field_guide");check(book!=null,"Book published");check(book.chapters().size()==10,"Chapter count");check(book.entries().size()>=107,"Complete entries");
  check(book.entries().values().stream().filter(e->e.id().contains(":machines/")).count()==45,"Every machine has own entry");
  for(var entry:book.entries().values()){
   check(book.chapters().containsKey(entry.chapter()),"Chapter exists");check(!entry.blocks().isEmpty(),"Nonempty entry");
   for(var block:entry.blocks()){
    check(GuideData.BLOCK_TYPES.contains(block.type()),"Supported block type");
    if(block.type().equals("link"))check(c.entry(Target.parse(block.target(),book.id())).isPresent(),"Unresolved link "+entry.id()+" -> "+block.target());
    if(block.type().equals("layers")){check(!block.rows().isEmpty(),"Layer rows");int w=block.rows().getFirst().getFirst().length();for(var row:block.rows())check(row.size()==1&&row.getFirst().length()==w,"Rectangular diagram "+entry.id());}
   }
  }
  var index=new GuideSearch(c);check(!index.search("dirty chip","","",10).isEmpty(),"Dirty-chip troubleshooting searchable");check(!index.search("EU","","",10).isEmpty(),"Power indexed");
  check(index.search("unlikelyphraseajwqm","","",10).isEmpty(),"Search has no false wildcard");
  return c;
 }
 public static void core(){
  check(GuideJson.parse("{\"a\":[true,false,null,1.25]}") instanceof Map,"JSON types");
  for(String raw:List.of("{\"a\":1,\"a\":2}","{\"a\":1,}","[1,]","{","true false","01","1e","\"\\uD800\"","\"\\uDC00\"","\"bad\ntext\"","[".repeat(30)+"]".repeat(30)," ".repeat(98305)))reject(()->GuideJson.parse(raw));
  reject(()->new Target("Invalid Namespace:book",""));reject(()->Target.parse("a:b#c:d#e:f","a:b"));reject(()->GuideData.id("a:../b"));
  var nav=new GuideNavigation();for(int i=0;i<300;i++){nav.visit(new Target("a:b","a:p"+i));nav.toggle(new Target("a:b","a:p"+i));}
  check(nav.historySize()==128,"History bounded");check(nav.bookmarks().size()==128,"Bookmarks bounded");
  Target b=nav.back().orElseThrow();check(nav.forward().orElseThrow().entry().equals("a:p299"),"Forward survives back");nav.back();nav.visit(new Target("a:b","a:new"));check(nav.forward().isEmpty(),"Branch prunes forward");
  check(!nav.toggle(new Target("a:b","a:p299")),"Toggle removes saved entry");nav.restoreBookmarks(List.of(b,b));check(nav.bookmarks().size()==1,"Bookmarks de-duplicated");
 }
 public static void negotiation(Catalog original){
  // A genuinely separate namespace publishes content; consumer imports only the public API.
  Map<String,String> demo=new TreeMap<>();String root="guide_demo:foundations_guides/demo/";
  demo.put(root+"book.json","{\"schema\":1,\"id\":\"guide_demo:field_guide\",\"owner\":\"guide_demo\",\"title\":\"Second Guide\",\"icon\":\"minecraft:book\",\"landing\":\"guide_demo:hello\",\"chapters\":[\"chapter.json\"],\"entries\":[\"entry.json\"]}");
  demo.put(root+"chapter.json","{\"id\":\"guide_demo:start\",\"title\":\"Start\"}");
  demo.put(root+"entry.json","{\"id\":\"guide_demo:hello\",\"chapter\":\"guide_demo:start\",\"title\":\"Hello\",\"blocks\":[{\"type\":\"paragraph\",\"text\":\"A second publisher without Calculator imports.\"}]}");
  Catalog second=GuideParser.scan(demo,x->true);check(second.diagnostics().isEmpty(),"Second publisher parse");check(GuideParser.scan(demo,x->false).books().isEmpty(),"Absent owner hidden");
  Map<String,Book> books=new TreeMap<>(original.books());books.putAll(second.books());Catalog both=new Catalog(books,List.of());GuideApi.install(both);long rev=GuideApi.revision();GuideApi.install(both);check(rev==GuideApi.revision(),"Identical reload idempotent");
  Target calc=new Target("foundations_calculator:field_guide","");Target other=new Target("guide_demo:field_guide","");AtomicInteger fallback=new AtomicInteger();
  check(!GuideApi.open(calc,fallback::incrementAndGet)&&fallback.get()==1,"Standalone without master");
  final class Reader implements GuideApi.Host {
   final String id;final int priority,version;final Set<String> caps;int read;boolean fail,decline;
   Reader(String id,int priority,int version,Set<String> caps){this.id=id;this.priority=priority;this.version=version;this.caps=caps;}
   public String id(){return id;}public int priority(){return priority;}public int apiVersion(){return version;}public Set<String> capabilities(){return caps;}public boolean accepts(Book b){return !decline;}
   public boolean open(Target t,Catalog catalog){if(fail)throw new IllegalStateException();var b=catalog.books().get(t.guide());check(b==books.get(t.guide()),"Consumer receives same immutable book, not copy");for(var e:b.orderedEntries())for(var part:e.blocks()){read++;check(caps.contains(part.type()),"Consumer capability negotiated");}return true;}
  }
  Reader old=new Reader("demo:old",100,99,GuideData.BLOCK_TYPES);GuideApi.registerHost(old);check(!GuideApi.open(calc,fallback::incrementAndGet),"Incompatible API fallback");GuideApi.removeHost(old.id());
  Reader text=new Reader("demo:text",200,1,Set.of("paragraph"));GuideApi.registerHost(text);check(!GuideApi.open(calc,fallback::incrementAndGet),"Missing capability fallback");check(GuideApi.open(other,fallback::incrementAndGet),"Simple reader accepts second guide");GuideApi.removeHost(text.id());
  Reader host=new Reader("demo:master",20,1,GuideData.BLOCK_TYPES);GuideApi.registerHost(host);check(GuideApi.open(calc,fallback::incrementAndGet)&&host.read>300,"Independent reader consumes Calculator model");check(GuideApi.open(other,fallback::incrementAndGet),"Same host consumes second publisher");
  host.decline=true;check(!GuideApi.open(calc,fallback::incrementAndGet),"Declining host fallback");host.decline=false;host.fail=true;check(!GuideApi.open(calc,fallback::incrementAndGet),"Host exception fallback");GuideApi.removeHost(host.id());check(!GuideApi.open(calc,fallback::incrementAndGet),"Removed master fallback");
  GuideApi.registerProvider("guide_demo",r->new LiveResult(State.READY,"test snapshot",List.of("approved data")));
  check(GuideApi.live(new LiveRequest(other,"guide_demo","demo")).state()==State.READY,"Own live resolver");check(GuideApi.live(new LiveRequest(calc,"guide_demo","demo")).state()==State.UNAVAILABLE,"Foreign provider denied");GuideApi.removeProvider("guide_demo");check(GuideApi.live(new LiveRequest(other,"guide_demo","demo")).state()==State.UNAVAILABLE,"Provider removed");
  GuideApi.registerRecipes("guide_demo",r->new RecipePage(State.READY,"resolved recipe",1,0,List.of(Block.paragraph("One stone makes one example result"))));
  check(GuideApi.recipes(new RecipeRequest(other,"guide_demo","item:minecraft:stone",0,"")).blocks().size()==1,"Public recipe resolver produces portable blocks");
  check(GuideApi.recipes(new RecipeRequest(calc,"guide_demo","anything",0,"")).state()==State.UNAVAILABLE,"Foreign recipe provider refused");
  GuideApi.removeRecipes("guide_demo");check(GuideApi.recipes(new RecipeRequest(other,"guide_demo","anything",0,"")).state()==State.UNAVAILABLE,"Absent recipe resolver safe");
  reject(()->new RecipePage(State.READY,"recursive",1,0,List.of(new Block("recipe","","x",List.of(),Map.of()))));
  String originalEntry=demo.get(root+"entry.json");demo.put(root+"entry.json",originalEntry.replace("\"paragraph\"","\"future_block\""));check(!GuideParser.scan(demo,x->true).diagnostics().isEmpty(),"Unknown type refused without fallback");demo.put(root+"entry.json",originalEntry.replace("\"paragraph\"","\"future_block\",\"fallback\":\"readable alternative\""));check(GuideParser.scan(demo,x->true).books().size()==1,"Unknown extension text fallback");
  demo.put(root+"entry.json",originalEntry);demo.put(root+"book.json",demo.get(root+"book.json").replace("\"entry.json\"","\"../entry.json\""));check(!GuideParser.scan(demo,x->true).diagnostics().isEmpty(),"Path traversal refused");
  GuideApi.install(original);
 }

 public static void snapshots(){
  var c=new GuideSnapshotCache();var sent=new ArrayList<GuideSnapshotCache.Query>();Object one=new Object(),two=new Object();c.session(one);
  var ready=new LiveResult(State.READY,"snapshot",List.of("4 FE/EU"));
  check(c.request("power",-9_000_000_000L,sent::add).state()==State.PENDING&&sent.size()==1,"Negative monotonic clock allowed");
  c.request("power",-8_900_000_000L,sent::add);check(sent.size()==1,"Duplicate request not sent");c.request("other",-8_800_000_000L,sent::add);check(sent.size()==1,"Global transport budget");
  var q=sent.getFirst();check(!c.receive(q.token()+1,q.key(),ready),"Wrong token refused");check(!c.receive(q.token(),"other",ready),"Wrong key refused");check(c.receive(q.token(),q.key(),ready),"Matching snapshot accepted");check(c.request("power",-7_000_000_000L,sent::add).state()==State.READY,"Cached snapshot reused");
  c.refresh();c.request("power",0,sent::add);var old=sent.getLast();c.session(two);check(!c.receive(old.token(),old.key(),ready),"Previous session response refused");
  c.request("power",0,sent::add);var current=sent.getLast();check(current.token()>old.token(),"Tokens never reset on session change");check(c.request("power",8_000_000_001L,sent::add).state()==State.UNAVAILABLE,"Pending timeout explicit");check(!c.receive(current.token(),current.key(),ready),"Late timed-out response refused");
  c.refresh();for(int i=0;i<300;i++){c.request("key"+i,20_000_000_000L+i*1_100_000_001L,sent::add);var x=sent.getLast();c.receive(x.token(),x.key(),ready);check(c.size()<=128,"Snapshot cache bounded");}
  c.clear();check(c.size()==0,"Logout clears snapshot state");check(c.request("throws",0,x->{throw new IllegalStateException();}).state()==State.UNAVAILABLE,"Transport failure safe");
 }
 public static int run(Path assets)throws Exception{checks=0;core();snapshots();Catalog c=corpus(assets);negotiation(c);return checks;}
 public static void main(String[] args)throws Exception{System.out.println("PASS "+run(Path.of(args[0]))+" real API/catalog/independent-consumer assertions");}
}
