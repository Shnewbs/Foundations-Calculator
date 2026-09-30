import java.util.Set;
import com.foundations.guide.api.GuideApi;
import com.foundations.guide.api.GuideData.*;
/** No Calculator or Minecraft imports. A real master mod supplies its own renderer. */
public final class ExampleMasterBridge implements GuideApi.Host {
 public interface Reader {
  Set<String> supportedBlocks();
  boolean showInsideMaster(Target target, Book book, Entry entry, Catalog allGuides);
 }
 private final Reader reader;
 public ExampleMasterBridge(Reader reader){this.reader=java.util.Objects.requireNonNull(reader);}
 public String id(){return "example_master:reader";}
 public int priority(){return 100;}
 public int apiVersion(){return 1;}
 public Set<String> capabilities(){return Set.copyOf(reader.supportedBlocks());}
 public boolean accepts(Book book){return capabilities().containsAll(book.capabilities());}
 public boolean open(Target target,Catalog allGuides){
  Book book=allGuides.books().get(target.guide());
  if(book==null)return false;
  var entry=book.entry(target.entry());
  return entry.isPresent()&&reader.showInsideMaster(target,book,entry.get(),allGuides);
 }
 public void registerDuringClientSetup(){GuideApi.registerHost(this);}
 public void unregister(){GuideApi.removeHost(id());}
}
