package com.foundations.calculator.client.guide;
import java.util.*;
import com.foundations.guide.api.*;
import com.foundations.guide.api.GuideData.Catalog;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.ModList;

/** Pack precedence comes from ResourceManager. Discovery happens only at resource reload. */
public final class GuideResources extends SimplePreparableReloadListener<Catalog> {
    @Override protected Catalog prepare(ResourceManager manager,ProfilerFiller profiler){
        Map<String,String> files=new TreeMap<>();List<String> ioErrors=new ArrayList<>();int total=0;
        var resources=manager.listResources("foundations_guides",p->p.getPath().endsWith(".json"));
        if(resources.size()>16384)return new Catalog(Map.of(),List.of("Too many guide resources"));
        for(var entry:resources.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()){
            try(var reader=entry.getValue().openAsReader()){
                char[] buffer=new char[4096];StringBuilder text=new StringBuilder();int count;
                while((count=reader.read(buffer))!=-1){text.append(buffer,0,count);total+=count;if(text.length()>98304||total>16777216)throw new java.io.IOException("Guide resource limit exceeded");}
                files.put(entry.getKey().toString(),text.toString());
            }catch(Exception ex){if(ioErrors.size()<64)ioErrors.add(entry.getKey()+": "+ex.getMessage());}
            if(total>16777216)return new Catalog(Map.of(),List.of("Guide resources exceed 16 Mi characters"));
        }
        var parsed=GuideParser.scan(files,mod->mod.equals("minecraft")||ModList.get().isLoaded(mod));
        var errors=new ArrayList<>(parsed.diagnostics());errors.addAll(ioErrors);
        return new Catalog(parsed.books(),errors.stream().limit(128).toList());
    }
    @Override protected void apply(Catalog catalog,ResourceManager manager,ProfilerFiller profiler){
        GuideApi.install(catalog);GuideValues.clear();
        for(String error:catalog.diagnostics())com.mojang.logging.LogUtils.getLogger().warn("[Foundations Guides] {}",error);
    }
}
