package com.foundations.calculator.core;
import java.util.*;
/** Publishes one complete snapshot, tolerating chunk order and rejecting inconsistent bounds. */
public final class SnapshotAssembly<T> {
    public static final int CHUNK_SIZE=64,MAX_CHUNKS=4096;
    private long epoch=-1,completed=-1;private int expected;
    private final Map<Integer,List<T>> pending=new HashMap<>();
    public static void validate(long epoch,int index,int chunks,int size){if(epoch<0||chunks<1||chunks>MAX_CHUNKS||index<0||index>=chunks||size<0||size>CHUNK_SIZE)throw new IllegalArgumentException("Snapshot bounds");}
    public Optional<List<T>> accept(long next,int index,int chunks,List<T> values){
        validate(next,index,chunks,values.size());
        if(next<epoch||next==completed)return Optional.empty();
        if(next>epoch){epoch=next;expected=chunks;pending.clear();}
        if(chunks!=expected)throw new IllegalArgumentException("Inconsistent snapshot chunk count");
        List<T> copy=List.copyOf(values),previous=pending.get(index);
        if(previous!=null&&!previous.equals(copy))throw new IllegalArgumentException("Conflicting snapshot chunk");
        pending.put(index,copy);if(pending.size()!=expected)return Optional.empty();
        List<T> result=new ArrayList<>();for(int i=0;i<expected;i++)result.addAll(pending.get(i));
        completed=epoch;pending.clear();return Optional.of(List.copyOf(result));
    }
    public void clear(){epoch=-1;completed=-1;expected=0;pending.clear();}
}
