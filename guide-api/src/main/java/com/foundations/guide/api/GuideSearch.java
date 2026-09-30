package com.foundations.guide.api;
import java.util.*;
import com.foundations.guide.api.GuideData.*;
/** Immutable full-text index; rebuild once per catalog revision, never from the render loop. */
public final class GuideSearch {
    public record Hit(Target target,String title,String chapter,String bookTitle){}
    private record Indexed(Hit hit,String text){}
    private final List<Indexed> index;
    public GuideSearch(Catalog catalog){
        var rows=new ArrayList<Indexed>();
        for(Book b:catalog.books().values().stream().sorted(Comparator.comparing(Book::title).thenComparing(Book::id)).toList())
            for(Entry e:b.orderedEntries())rows.add(new Indexed(new Hit(new Target(b.id(),e.id()),e.title(),b.chapters().get(e.chapter()).title(),b.title()),e.searchText()));
        index=List.copyOf(rows);
    }
    public List<Hit> search(String query,String guide,String chapter,int limit){
        String q=GuideData.text(query,256).strip().toLowerCase(Locale.ROOT);String[] words=q.split("\\s+");
        return index.stream().filter(i->(guide.isEmpty()||i.hit.target.guide().equals(guide))&&(chapter.isEmpty()||i.hit.chapter.equals(chapter)))
            .filter(i->{for(String w:words)if(!i.text.contains(w))return false;return true;}).limit(Math.clamp(limit,1,8192)).map(Indexed::hit).toList();
    }
}
