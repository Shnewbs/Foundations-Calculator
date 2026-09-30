package com.foundations.guide.api;

import java.util.*;
import java.util.regex.Pattern;

/** Version 1 portable guide model. No game, loader, renderer, or JSON-library dependency. */
public final class GuideData {
    public static final int VERSION = 1;
    public static final Set<String> BLOCK_TYPES = Set.of("heading","paragraph","steps","list","callout","table","item","recipe","image","link","layers","live");
    private static final Pattern ID = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");
    public static String id(String value) {
        if(value == null || value.length()>240 || !ID.matcher(value).matches() || value.contains("..") || value.contains("//"))
            throw new IllegalArgumentException("Invalid guide identifier: " + value);
        return value;
    }
    public static String text(String value,int limit) {
        Objects.requireNonNull(value,"text");
        if(value.length()>limit || value.indexOf('\0')>=0) throw new IllegalArgumentException("Text exceeds limit or contains NUL");
        return value;
    }
    public record Target(String guide, String entry) {
        public Target { id(guide); if(!entry.isEmpty()) id(entry); }
        public static Target parse(String value, String currentGuide) {
            String[] split=value.split("#",-1);
            if(split.length==1) return new Target(currentGuide, id(value));
            if(split.length!=2) throw new IllegalArgumentException("Expected guide#entry");
            return new Target(split[0],split[1]);
        }
        public String key(){return guide+"#"+entry;}
    }
    public record Block(String type, String text, String target, List<List<String>> rows, Map<String,String> options) {
        public Block {
            GuideData.text(type,64); GuideData.text(text,8192); GuideData.text(target,512);
            if(rows.size()>128 || options.size()>24) throw new IllegalArgumentException("Block limits");
            rows=rows.stream().map(row->{if(row.size()>32)throw new IllegalArgumentException("Row width");return row.stream().map(s->GuideData.text(s,1024)).toList();}).toList();
            options=Map.copyOf(options); for(var e:options.entrySet()){GuideData.text(e.getKey(),64);GuideData.text(e.getValue(),2048);}
        }
        public static Block paragraph(String text){return new Block("paragraph",text,"",List.of(),Map.of());}
    }
    public record Chapter(String id,String title,int order){public Chapter{GuideData.id(id);GuideData.text(title,160);}}
    public record Entry(String id,String chapter,String title,int order,List<String> items,List<String> keywords,List<Block> blocks) {
        public Entry {
            GuideData.id(id);GuideData.id(chapter);GuideData.text(title,160);
            if(items.size()>128||keywords.size()>64||blocks.size()>128)throw new IllegalArgumentException("Entry limits");
            items=items.stream().map(GuideData::id).toList();keywords=keywords.stream().map(s->GuideData.text(s,128)).toList();blocks=List.copyOf(blocks);
        }
        public String searchText(){
            StringBuilder s=new StringBuilder(title).append(' ').append(id).append(' ').append(String.join(" ",items)).append(' ').append(String.join(" ",keywords));
            for(Block b:blocks){s.append(' ').append(b.text);for(var row:b.rows)s.append(' ').append(String.join(" ",row));}
            return s.toString().toLowerCase(Locale.ROOT);
        }
    }
    public record Book(String id,String owner,int schema,String title,String icon,String revision,String landing,
                       Map<String,Chapter> chapters,Map<String,Entry> entries,Set<String> capabilities) {
        public Book {
            GuideData.id(id);GuideData.text(owner,128);GuideData.text(title,160);GuideData.id(icon);GuideData.text(revision,128);GuideData.id(landing);
            if(schema!=VERSION||chapters.size()>64||entries.size()>512||entries.isEmpty())throw new IllegalArgumentException("Unsupported schema or book limits");
            if(!id.startsWith(owner+":"))throw new IllegalArgumentException("Book owner/namespace mismatch");
            chapters=Map.copyOf(chapters);entries=Map.copyOf(entries);capabilities=Set.copyOf(capabilities);
            var used=new HashSet<String>();for(var entry:entries.values())for(var block:entry.blocks())used.add(block.type());
            if(!BLOCK_TYPES.containsAll(used)||!capabilities.equals(used))throw new IllegalArgumentException("Capability declaration does not match content");
            if(!entries.containsKey(landing))throw new IllegalArgumentException("Missing landing entry");
            for(Entry e:entries.values())if(!chapters.containsKey(e.chapter))throw new IllegalArgumentException("Missing chapter: "+e.chapter);
        }
        public Optional<Entry> entry(String key){return Optional.ofNullable(entries.get(key.isEmpty()?landing:key));}
        public List<Entry> orderedEntries(){return entries.values().stream().sorted(Comparator.comparingInt((Entry e)->chapters.get(e.chapter).order()).thenComparingInt(Entry::order).thenComparing(Entry::id)).toList();}
    }
    public record Catalog(Map<String,Book> books,List<String> diagnostics){
        public Catalog{if(books.size()>128)throw new IllegalArgumentException("Too many books");books=Map.copyOf(books);diagnostics=diagnostics.stream().limit(128).map(s->GuideData.text(s,1024)).toList();}
        public static Catalog empty(){return new Catalog(Map.of(),List.of());}
        public Optional<Entry> entry(Target target){var b=books.get(target.guide);return b==null?Optional.empty():b.entry(target.entry);}
    }
    public enum State { READY, PENDING, UNAVAILABLE }
    /** Plain public values, not arbitrary objects. Snapshot labels must identify freshness/source. */
    public record LiveResult(State state,String label,List<String> lines){
        public LiveResult{Objects.requireNonNull(state);GuideData.text(label,256);if(lines.size()>96)throw new IllegalArgumentException("Live result limit");lines=lines.stream().map(s->GuideData.text(s,512)).toList();}
        public static LiveResult unavailable(String why){return new LiveResult(State.UNAVAILABLE,why,List.of());}
    }
    public record LiveRequest(Target target,String provider,String key){
        public LiveRequest{Objects.requireNonNull(target);GuideData.text(provider,128);GuideData.text(key,240);}
    }
    /** Recipe queries resolve into ordinary portable blocks: no ItemStack or private recipe object crosses the API. */
    public record RecipeRequest(Target target,String provider,String query,int page,String profile){
        public RecipeRequest{Objects.requireNonNull(target);GuideData.text(provider,128);GuideData.text(query,512);GuideData.text(profile,240);}
    }
    public record RecipePage(State state,String label,int count,int page,List<Block> blocks){
        public RecipePage{Objects.requireNonNull(state);GuideData.text(label,256);if(count<0||count>2048||page<0||page>=Math.max(1,count)||blocks.size()>256)throw new IllegalArgumentException("Recipe page bounds");blocks=List.copyOf(blocks);for(var b:blocks)if(!BLOCK_TYPES.contains(b.type())||b.type().equals("recipe")||b.type().equals("live"))throw new IllegalArgumentException("Recipe results cannot recurse into providers");}
        public static RecipePage unavailable(String reason){return new RecipePage(State.UNAVAILABLE,reason,0,0,List.of());}
    }
    private GuideData(){}
}
