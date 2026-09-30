package com.foundations.calculator.core;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class IngredientAllocationTest {
    @Test void duplicateInputsCannotDoubleSpendOneItem(){assertNull(IngredientAllocation.allocate(new int[]{1},new int[]{1,1},(i,s)->true));}
    @Test void overlappingTagsBacktrackToTheValidSpecificAssignment(){assertArrayEquals(new int[]{1,1},IngredientAllocation.allocate(new int[]{1,1},new int[]{1,1},(i,s)->i==0||s==0));}
    @Test void countedIngredientCanSpanMultipleSlots(){assertArrayEquals(new int[]{3,4},IngredientAllocation.allocate(new int[]{3,5},new int[]{7},(i,s)->true));}
    @Test void failedAllocationDoesNotMutateInventory(){int[] input={1,2};assertNull(IngredientAllocation.allocate(input,new int[]{4},(i,s)->true));assertArrayEquals(new int[]{1,2},input);}
    @Test void unrelatedStacksAreNotConsumed(){assertArrayEquals(new int[]{0,1,0},IngredientAllocation.allocate(new int[]{64,3,64},new int[]{1},(i,s)->s==1));}
}
