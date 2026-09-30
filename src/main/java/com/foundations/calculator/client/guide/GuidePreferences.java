package com.foundations.calculator.client.guide;
import java.util.*;
import java.nio.file.*;
import com.foundations.guide.api.*;
import com.foundations.guide.api.GuideData.Target;
import net.neoforged.fml.loading.FMLPaths;

/** Bookmarks contain public entry IDs only. Never stores UUIDs, research, server addresses or live values. */
public final class GuidePreferences {
    private static final GuideNavigation navigation=new GuideNavigation();private static boolean loaded;
    private static Path path(){return FMLPaths.CONFIGDIR.get().resolve("foundations/guide_preferences_v1.json");}
    public static GuideNavigation navigation(){
        if(!loaded){loaded=true;Path p=path();try{
            if(Files.isRegularFile(p)&&Files.size(p)<=65536){
                Object parsed=GuideJson.parse(Files.readString(p));if(parsed instanceof Map<?,?> m){
                    var bookmarks=new ArrayList<Target>();if(m.get("bookmarks") instanceof List<?> rows)for(Object row:rows.stream().limit(128).toList())try{bookmarks.add(decode(row));}catch(RuntimeException ignored){}
                    navigation.restoreBookmarks(bookmarks);if(m.get("last") instanceof String last&&!last.isEmpty())try{navigation.visit(decode(last));}catch(RuntimeException ignored){}
                }
            }
        }catch(Exception ex){com.mojang.logging.LogUtils.getLogger().warn("Guide preferences could not be read; using empty preferences",ex);}}
        return navigation;
    }
    private static Target decode(Object row){String text=(String)row;String[] s=text.split("#",-1);if(s.length!=2)throw new IllegalArgumentException();return new Target(s[0],s[1]);}
    public static void save(){
        if(!loaded)return;
        String last=navigation.current().map(Target::key).orElse("");
        String rows=navigation.bookmarks().stream().map(t->"\""+t.key()+"\"").collect(java.util.stream.Collectors.joining(","));
        String json="{\"schema\":1,\"last\":\""+last+"\",\"bookmarks\":["+rows+"]}\n";
        Path p=path(),temp=p.resolveSibling(p.getFileName()+".tmp");
        try{Files.createDirectories(p.getParent());Files.writeString(temp,json);try{Files.move(temp,p,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}catch(AtomicMoveNotSupportedException e){Files.move(temp,p,StandardCopyOption.REPLACE_EXISTING);}}
        catch(Exception e){com.mojang.logging.LogUtils.getLogger().warn("Guide bookmarks could not be saved",e);}
    }
    private GuidePreferences(){}
}
