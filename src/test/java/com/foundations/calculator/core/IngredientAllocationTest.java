package com.foundations.calculator.core;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class IngredientAllocationTest {
    @Test void duplicateInputsCannotDoubleSpendOneItem(){assertNull(IngredientAllocation.allocate(new int[]{1},new int[]{1,1},(i,s)->true));}
    @Test void overlappingTagsBacktrackToTheValidSpecificAssignment(){assertArrayEquals(new int[]{1,1},IngredientAllocation.allocate(new int[]{1,1},new int[]{1,1},(i,s)->i==0||s==0));}
    @Test void countedIngredientCanSpanMultipleSlots(){assertArrayEquals(new int[]{3,4},IngredientAllocation.allocate(new int[]{3,5},new int[]{7},(i,s)->true));}
    @Test void failedAllocationDoesNotMutateInventory(){int[] input={1,2};assertNull(IngredientAllocation.allocate(input,new int[]{4},(i,s)->true));assertArrayEquals(new int[]{1,2},input);}
    @Test void unrelatedStacksAreNotConsumed(){assertArrayEquals(new int[]{0,1,0},IngredientAllocation.allocate(new int[]{64,3,64},new int[]{1},(i,s)->s==1));}
    @Test void randomAllocationsAgreeWithExhaustiveAssignments(){
        var random=new java.util.Random(1734096);
        for(int trial=0;trial<3000;trial++){
            int[] available=new int[1+random.nextInt(4)],required=new int[random.nextInt(4)];
            for(int slot=0;slot<available.length;slot++)available[slot]=random.nextInt(4);
            for(int ingredient=0;ingredient<required.length;ingredient++)required[ingredient]=random.nextInt(4);
            boolean[][] accepts=new boolean[required.length][available.length];
            for(var row:accepts)for(int slot=0;slot<row.length;slot++)row[slot]=random.nextBoolean();
            int[] before=available.clone();
            var used=IngredientAllocation.allocate(available,required,(i,slot)->accepts[i][slot]);
            boolean possible=assign(available.clone(),required,accepts,0,0,required.length==0?0:required[0]);
            assertEquals(possible,used!=null,"trial "+trial);
            assertArrayEquals(before,available);
            if(used!=null){
                assertEquals(java.util.Arrays.stream(required).sum(),java.util.Arrays.stream(used).sum());
                for(int slot=0;slot<used.length;slot++)assertTrue(used[slot]>=0&&used[slot]<=available[slot]);
                assertTrue(assign(used.clone(),required,accepts,0,0,required.length==0?0:required[0]));
            }
        }
    }
    @Test void countsCannotOverflowTheSupplyCheck(){
        assertArrayEquals(new int[]{Integer.MAX_VALUE,0},IngredientAllocation.allocate(
            new int[]{Integer.MAX_VALUE,Integer.MAX_VALUE},new int[]{Integer.MAX_VALUE},(i,slot)->true));
        assertThrows(IllegalArgumentException.class,()->IngredientAllocation.allocate(new int[]{-1},new int[]{0},(i,slot)->true));
        assertThrows(ArithmeticException.class,()->IngredientAllocation.allocate(new int[]{1},new int[]{Integer.MAX_VALUE,1},(i,slot)->true));
    }
    private static boolean assign(int[] supply,int[] required,boolean[][] accepts,int ingredient,int slot,int left){
        if(ingredient==required.length)return true;
        if(left==0)return assign(supply,required,accepts,ingredient+1,0,ingredient+1==required.length?0:required[ingredient+1]);
        if(slot==supply.length)return false;
        int maximum=accepts[ingredient][slot]?Math.min(supply[slot],left):0;
        for(int count=0;count<=maximum;count++){
            supply[slot]-=count;
            boolean possible=assign(supply,required,accepts,ingredient,slot+1,left-count);
            supply[slot]+=count;
            if(possible)return true;
        }
        return false;
    }
    @Test void playerSizedInventoryPreservesExactSlotAllocation(){
        int[] available=new int[63],required=new int[14],expected=new int[63];
        java.util.Arrays.fill(available,1);java.util.Arrays.fill(required,2);
        for(int slot=0;slot<28;slot++)expected[slot]=1;
        assertArrayEquals(expected,IngredientAllocation.allocate(available,required,(ingredient,slot)->slot==ingredient||slot==ingredient+14));
        assertEquals(63,java.util.Arrays.stream(available).sum());
    }
}
