package com.foundations.guide.api;
import java.util.*;
import java.util.function.Consumer;
import com.foundations.guide.api.GuideData.*;
/** Optional publisher-side cache; caller supplies a monotonic nanosecond clock and its own transport. Client thread only. */
public final class GuideSnapshotCache {
 public record Query(long token,String key){}
 private record Pending(long token,long at){}
 private final Map<String,LiveResult> values=new LinkedHashMap<>();
 private final Map<String,Pending> pending=new HashMap<>();
 private Object session;private long sequence,revision,lastSend;private boolean sent;
 public void session(Object identity){if(identity!=session){clear();session=identity;}}
 public void clear(){values.clear();pending.clear();session=null;sent=false;revision++;}
 public void refresh(){values.clear();pending.clear();revision++;}
 public long revision(){return revision;}
 public int size(){return values.size()+pending.size();}
 public LiveResult request(String key,long now,Consumer<Query> send){
  GuideData.text(key,240);Objects.requireNonNull(send);
  if(values.containsKey(key))return values.get(key);
  var p=pending.get(key);
  if(p!=null){if(now-p.at()>8_000_000_000L){pending.remove(key);var result=LiveResult.unavailable("Server did not answer. Press Refresh to retry.");values.put(key,result);revision++;return result;}return new LiveResult(State.PENDING,"Requesting server values…",List.of());}
  if(sent&&now-lastSend<1_100_000_000L)return new LiveResult(State.PENDING,"Waiting for request budget…",List.of());
  if(size()>=128){values.clear();pending.clear();revision++;}
  var query=new Query(++sequence,key);pending.put(key,new Pending(query.token(),now));lastSend=now;sent=true;
  try{send.accept(query);}catch(RuntimeException e){pending.remove(key);var result=LiveResult.unavailable("Snapshot request failed; static documentation remains available.");values.put(key,result);revision++;return result;}
  return new LiveResult(State.PENDING,"Requesting server values…",List.of());
 }
 public boolean receive(long token,String key,LiveResult result){
  var p=pending.get(key);if(p==null||p.token()!=token)return false;
  pending.remove(key);values.put(key,Objects.requireNonNull(result));revision++;return true;
 }
}
