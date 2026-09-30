package com.foundations.guide.api;
import java.util.*;
import java.util.function.Function;
import com.foundations.guide.api.GuideData.*;

/** One Jar-in-Jar-selected library, shared by publishers/readers. Call lifecycle methods on the client thread. */
public final class GuideApi {
    public static final String ARTIFACT="com.foundations:foundations-guide-api:1.0.0";
    public interface Host {
        String id();
        int priority();
        int apiVersion();
        Set<String> capabilities();
        boolean accepts(Book book);
        /** Return true only after opening the requested entry inside your own reader. */
        boolean open(Target target,Catalog catalog);
    }
    private static Catalog catalog=Catalog.empty();
    private static long revision;
    private static final Map<String,Host> hosts=new TreeMap<>();
    private static final Map<String,Function<LiveRequest,LiveResult>> providers=new HashMap<>();
    private static final Map<String,Function<RecipeRequest,RecipePage>> recipes=new HashMap<>();
    private static final ThreadLocal<Boolean> redirecting=ThreadLocal.withInitial(()->false);
    private static final ArrayDeque<String> errors=new ArrayDeque<>();
    public static Catalog catalog(){return catalog;}
    public static long revision(){return revision;}
    /** Replacing the same resource snapshot is idempotent even when multiple readers have reload listeners. */
    public static void install(Catalog next){Objects.requireNonNull(next);if(!catalog.equals(next)){catalog=next;revision++;}}
    public static void registerHost(Host host){GuideData.id(host.id());if(hosts.putIfAbsent(host.id(),host)!=null)throw new IllegalArgumentException("Host already registered: "+host.id());}
    public static void removeHost(String id){hosts.remove(id);}
    public static void registerProvider(String owner,Function<LiveRequest,LiveResult> provider){GuideData.id(owner+":provider");Objects.requireNonNull(provider);if(providers.putIfAbsent(owner,provider)!=null)throw new IllegalArgumentException("Provider already registered: "+owner);}
    public static void removeProvider(String owner){providers.remove(owner);}
    public static LiveResult live(LiveRequest request){
        Book b=catalog.books().get(request.target().guide());
        if(b==null||b.entry(request.target().entry()).isEmpty()||!b.owner().equals(request.provider()))return LiveResult.unavailable("Invalid guide/provider ownership");
        var provider=providers.get(request.provider());if(provider==null)return LiveResult.unavailable("Live provider not installed");
        try{return Objects.requireNonNull(provider.apply(request));}catch(RuntimeException e){error("Live provider "+request.provider()+" failed");return LiveResult.unavailable("Provider failed; static documentation is still available");}
    }
    public static void registerRecipes(String owner,Function<RecipeRequest,RecipePage> provider){GuideData.id(owner+":recipes");Objects.requireNonNull(provider);if(recipes.putIfAbsent(owner,provider)!=null)throw new IllegalArgumentException("Recipe provider already registered: "+owner);}
    public static void removeRecipes(String owner){recipes.remove(owner);}
    public static RecipePage recipes(RecipeRequest request){
        Book b=catalog.books().get(request.target().guide());
        if(b==null||b.entry(request.target().entry()).isEmpty()||!b.owner().equals(request.provider()))return RecipePage.unavailable("Invalid guide/recipe-provider ownership");
        var provider=recipes.get(request.provider());if(provider==null)return RecipePage.unavailable("Recipe provider not installed; static instructions remain available");
        try{return Objects.requireNonNull(provider.apply(request));}catch(RuntimeException e){error("Recipe provider "+request.provider()+" failed");return RecipePage.unavailable("Recipe data unavailable; static instructions remain available");}
    }
    public static boolean open(Target target,Runnable standalone){
        Objects.requireNonNull(standalone);Book book=catalog.books().get(target.guide());
        if(book!=null&&book.entry(target.entry()).isPresent()&&!redirecting.get()){
            redirecting.set(true);
            try{
                record Candidate(Host host,int priority,String id){}
                var candidates=new ArrayList<Candidate>();
                for(var h:List.copyOf(hosts.values()))try{candidates.add(new Candidate(h,h.priority(),h.id()));}catch(RuntimeException e){error("Host metadata failed; skipping this host");}
                candidates.sort(Comparator.comparingInt(Candidate::priority).reversed().thenComparing(Candidate::id));
                for(Candidate candidate:candidates){Host host=candidate.host();
                    try{if(host.apiVersion()==GuideData.VERSION&&host.capabilities().containsAll(book.capabilities())&&host.accepts(book)&&host.open(target,catalog))return true;}
                    catch(RuntimeException e){error("Host "+candidate.id()+" failed; keeping standalone fallback");}
                }
            }finally{redirecting.remove();}
        }
        standalone.run();return false;
    }
    private static void error(String text){if(errors.size()==32)errors.removeFirst();errors.addLast(text);}
    public static List<String> diagnostics(){return List.copyOf(errors);}
    private GuideApi(){}
}
