package com.foundations.calculator.platform;

import com.foundations.calculator.core.UuidTags;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class UuidTagsTest {
    @Test void readsExistingFourIntWorldData(){
        var tag=new CompoundTag();tag.putIntArray("Owner",new int[]{0x01234567,0x89abcdef,0xfedcba98,0x76543210});
        assertTrue(UuidTags.has(tag,"Owner"));
        assertEquals(UUID.fromString("01234567-89ab-cdef-fedc-ba9876543210"),UuidTags.get(tag,"Owner"));
    }
    @Test void roundTripPreservesSignedBitsAndExistingKeys(){
        for(var uuid:new UUID[]{new UUID(0,0),new UUID(-1,-1),new UUID(Long.MIN_VALUE,Long.MAX_VALUE),UUID.fromString("01234567-89ab-cdef-fedc-ba9876543210")}){
            var tag=new CompoundTag();tag.putLong("Energy",3_000_000_000L);UuidTags.put(tag,"Player",uuid);
            assertEquals(uuid,UuidTags.get(tag,"Player"));assertEquals(3_000_000_000L,tag.getLongOr("Energy",0));
            assertEquals(4,tag.getIntArray("Player").orElseThrow().length);
        }
    }
    @Test void invalidOrMissingOwnersCannotBecomeValidUuids(){
        var tag=new CompoundTag();assertFalse(UuidTags.has(tag,"Owner"));
        tag.putIntArray("Owner",new int[]{1,2,3});assertFalse(UuidTags.has(tag,"Owner"));
        assertThrows(IllegalArgumentException.class,()->UuidTags.get(tag,"Owner"));
        tag.putString("Owner","invalid");assertFalse(UuidTags.has(tag,"Owner"));
    }
}
