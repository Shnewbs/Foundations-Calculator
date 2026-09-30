package com.foundations.guide.api;
import java.util.*;
import com.foundations.guide.api.GuideData.Target;
/** Bounded preference-only navigation, independent of player progress. */
public final class GuideNavigation {
    private final ArrayList<Target> history=new ArrayList<>();private int cursor=-1;
    private final LinkedHashSet<Target> bookmarks=new LinkedHashSet<>();
    public void visit(Target target){if(cursor>=0&&history.get(cursor).equals(target))return;while(history.size()>cursor+1)history.removeLast();history.add(target);if(history.size()>128)history.removeFirst();cursor=history.size()-1;}
    public Optional<Target> back(){return cursor>0?Optional.of(history.get(--cursor)):Optional.empty();}
    public Optional<Target> forward(){return cursor+1<history.size()?Optional.of(history.get(++cursor)):Optional.empty();}
    public Optional<Target> current(){return cursor<0?Optional.empty():Optional.of(history.get(cursor));}
    public boolean toggle(Target target){if(bookmarks.remove(target))return false;if(bookmarks.size()>=128)bookmarks.removeFirst();bookmarks.add(target);return true;}
    public boolean bookmarked(Target target){return bookmarks.contains(target);}
    public List<Target> bookmarks(){return List.copyOf(bookmarks);}
    public void restoreBookmarks(Collection<Target> targets){bookmarks.clear();targets.stream().limit(128).forEach(bookmarks::add);}
    public int historySize(){return history.size();}
}
