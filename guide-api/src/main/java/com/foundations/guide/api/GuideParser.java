package com.foundations.guide.api;
import java.util.*;
import java.util.function.Predicate;
import com.foundations.guide.api.GuideData.*;

/** Resource paths are namespaced, e.g. mod:foundations_guides/book/book.json. */
public final class GuideParser {
    @SuppressWarnings("unchecked") private static Map<String,Object> object(Object v){if(!(v instanceof Map<?,?>))throw new IllegalArgumentException("Expected object");return (Map<String,Object>)v;}
    private static List<?> array(Object v){if(!(v instanceof List<?> l))throw new IllegalArgumentException("Expected array");return l;}
    private static String string(Object v){if(!(v instanceof String s))throw new IllegalArgumentException("Expected string");return s;}
    private static String str(Map<String,Object> o,String k,String fallback){return o.containsKey(k)?string(o.get(k)):fallback;}
    private static String required(Map<String,Object> o,String k){return string(o.get(k));}
    private static int num(Map<String,Object> o,String k,int fallback){if(!o.containsKey(k))return fallback;try{return ((java.math.BigDecimal)o.get(k)).intValueExact();}catch(Exception e){throw new IllegalArgumentException("Expected integer "+k);}}
    private static List<String> strings(Map<String,Object> o,String k){if(!o.containsKey(k))return List.of();return array(o.get(k)).stream().map(GuideParser::string).toList();}
    private static String child(String root,String relative){if(relative.isEmpty()||relative.length()>200||relative.contains("..")||relative.contains(":")||relative.startsWith("/")||!relative.matches("[a-z0-9_./-]+\\.json"))throw new IllegalArgumentException("Unsafe child resource");return root+relative;}
    private static Map<String,Object> read(Map<String,String> files,String path){String s=files.get(path);if(s==null)throw new IllegalArgumentException("Missing resource "+path);return object(GuideJson.parse(s));}
    private static boolean available(Map<String,Object> o,Predicate<String> loaded){for(String mod:strings(o,"required_mods"))if(!loaded.test(mod))return false;return true;}
    public static Catalog scan(Map<String,String> resources,Predicate<String> loaded){
        if(resources.size()>16384) return new Catalog(Map.of(),List.of("Guide resource count exceeds 16384"));
        long size=0;for(String data:resources.values()){size+=data.length();if(size>16777216)return new Catalog(Map.of(),List.of("Guide resources exceed 16 Mi characters"));}
        Map<String,Book> books=new TreeMap<>();Set<String> duplicates=new HashSet<>();List<String> errors=new ArrayList<>();
        var paths=resources.keySet().stream().filter(p->p.contains(":foundations_guides/")&&p.endsWith("/book.json")).sorted().toList();
        for(String path:paths){
            if(books.size()>=128){errors.add("Guide limit reached (128)");break;}
            try{
                var o=read(resources,path);int schema=num(o,"schema",-1);if(schema!=1)throw new IllegalArgumentException("Unsupported schema "+schema+"; reader supports 1");
                String owner=required(o,"owner");if(!path.startsWith(owner+":"))throw new IllegalArgumentException("Resource namespace does not match owner");
                if(!loaded.test(owner)||!available(o,loaded))continue;
                String id=GuideData.id(required(o,"id"));String root=path.substring(0,path.lastIndexOf('/')+1);
                Map<String,Chapter> chapters=new LinkedHashMap<>();Map<String,Entry> entries=new LinkedHashMap<>();Set<String> caps=new HashSet<>();
                var chapterFiles=strings(o,"chapters");var entryFiles=strings(o,"entries");if(chapterFiles.size()>64||entryFiles.size()>512)throw new IllegalArgumentException("Book limits");
                for(String f:chapterFiles){var c=read(resources,child(root,f));String cid=GuideData.id(required(c,"id"));if(!cid.startsWith(owner+":"))throw new IllegalArgumentException("Foreign chapter namespace");if(chapters.putIfAbsent(cid,new Chapter(cid,required(c,"title"),num(c,"order",0)))!=null)throw new IllegalArgumentException("Duplicate chapter "+cid);}
                for(String f:entryFiles){
                    var e=read(resources,child(root,f));if(!available(e,loaded))continue;
                    String eid=GuideData.id(required(e,"id"));if(!eid.startsWith(owner+":"))throw new IllegalArgumentException("Foreign entry namespace");
                    List<Block> blocks=new ArrayList<>();
                    for(Object b:array(e.get("blocks"))){
                        var data=object(b);String type=required(data,"type"),text=str(data,"text",""),target=str(data,"target","");
                        Map<String,String> options=new LinkedHashMap<>();if(data.containsKey("options"))for(var opt:object(data.get("options")).entrySet())options.put(opt.getKey(),string(opt.getValue()));
                        List<List<String>> rows=new ArrayList<>();if(data.containsKey("rows"))for(Object row:array(data.get("rows")))rows.add(array(row).stream().map(GuideParser::string).toList());
                        if(!GuideData.BLOCK_TYPES.contains(type)){
                            if(!data.containsKey("fallback"))throw new IllegalArgumentException("Unknown block "+type+" without text fallback");
                            type="callout";text="Unsupported page extension: "+required(data,"fallback");target="";rows=List.of();options=Map.of();
                        }
                        if(type.equals("live")&&!target.equals(owner))throw new IllegalArgumentException("Live provider must be book owner");
                        if(type.equals("link"))Target.parse(target,id);
                        if(type.equals("item")||type.equals("image"))GuideData.id(target);
                        caps.add(type);blocks.add(new Block(type,text,target,rows,options));
                    }
                    var entry=new Entry(eid,required(e,"chapter"),required(e,"title"),num(e,"order",0),strings(e,"items"),strings(e,"keywords"),blocks);
                    if(entries.putIfAbsent(eid,entry)!=null)throw new IllegalArgumentException("Duplicate entry "+eid);
                }
                var book=new Book(id,owner,schema,required(o,"title"),required(o,"icon"),str(o,"revision","0"),required(o,"landing"),chapters,entries,caps);
                if(books.containsKey(id)||duplicates.contains(id)){books.remove(id);duplicates.add(id);throw new IllegalArgumentException("Ambiguous duplicate book "+id+"; neither copy accepted");}
                books.put(id,book);
            }catch(RuntimeException ex){if(errors.size()<128)errors.add(GuideData.text((path+": "+ex.getMessage()).substring(0,Math.min(1000,(path+": "+ex.getMessage()).length())),1024));}
        }
        return new Catalog(books,errors);
    }
    private GuideParser(){}
}
