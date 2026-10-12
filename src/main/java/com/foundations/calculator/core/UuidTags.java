package com.foundations.calculator.core;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;

/** Preserve the legacy four-int UUID representation after CompoundTag's UUID helpers were removed. */
public final class UuidTags {
    public static void put(CompoundTag tag,String key,UUID uuid){
        long most=uuid.getMostSignificantBits(),least=uuid.getLeastSignificantBits();
        tag.putIntArray(key,new int[]{(int)(most>>>32),(int)most,(int)(least>>>32),(int)least});
    }
    public static boolean has(CompoundTag tag,String key){return tag.getIntArray(key).map(value->value.length==4).orElse(false);}
    public static UUID get(CompoundTag tag,String key){
        int[] value=tag.getIntArray(key).filter(array->array.length==4)
            .orElseThrow(()->new IllegalArgumentException("Missing or malformed UUID: "+key));
        long most=((long)value[0]<<32)|(value[1]&0xffffffffL);
        long least=((long)value[2]<<32)|(value[3]&0xffffffffL);
        return new UUID(most,least);
    }
    private UuidTags(){}
}
