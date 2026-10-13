package com.foundations.calculator.core;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class SnapshotAssemblyTest {
    @Test void publishesOnlyCompleteOrderedSnapshot(){var a=new SnapshotAssembly<String>();assertTrue(a.accept(1,1,2,List.of("b")).isEmpty());assertEquals(List.of("a","b"),a.accept(1,0,2,List.of("a")).orElseThrow());assertTrue(a.accept(1,0,2,List.of("a")).isEmpty());}
    @Test void newerEpochDiscardsIncompleteOlderData(){var a=new SnapshotAssembly<String>();a.accept(1,0,2,List.of("old"));assertTrue(a.accept(2,1,2,List.of("new-b")).isEmpty());assertTrue(a.accept(1,1,2,List.of("old-b")).isEmpty());assertEquals(List.of("new-a","new-b"),a.accept(2,0,2,List.of("new-a")).orElseThrow());}
    @Test void rejectsInconsistentAndConflictingChunks(){var a=new SnapshotAssembly<String>();a.accept(1,0,2,List.of("a"));assertThrows(IllegalArgumentException.class,()->a.accept(1,1,3,List.of("b")));assertThrows(IllegalArgumentException.class,()->a.accept(1,0,2,List.of("changed")));assertEquals(List.of("a","b"),a.accept(1,1,2,List.of("b")).orElseThrow());}
    @Test void boundsRejectMalformedSizesAndIndexes(){assertThrows(IllegalArgumentException.class,()->SnapshotAssembly.validate(1,0,0,0));assertThrows(IllegalArgumentException.class,()->SnapshotAssembly.validate(1,2,2,1));assertThrows(IllegalArgumentException.class,()->SnapshotAssembly.validate(1,0,1,65));assertThrows(IllegalArgumentException.class,()->SnapshotAssembly.validate(1,0,4097,1));assertThrows(IllegalArgumentException.class,()->SnapshotAssembly.validate(-1,0,1,1));}
    @Test void emptySnapshotsAndReconnectResetAreSupported(){var a=new SnapshotAssembly<String>();assertEquals(List.of(),a.accept(9,0,1,List.of()).orElseThrow());a.clear();assertEquals(List.of("reconnected"),a.accept(1,0,1,List.of("reconnected")).orElseThrow());}
}
