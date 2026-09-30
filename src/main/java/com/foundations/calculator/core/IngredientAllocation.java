package com.foundations.calculator.core;

import java.util.Arrays;
import java.util.function.BiPredicate;

/** Integral max-flow allocates overlapping tags and repeated ingredients without exponential search. */
public final class IngredientAllocation {
    public static int[] allocate(int[] available,int[] required,BiPredicate<Integer,Integer> accepts){
        int target=0;for(int n:required){if(n<0)throw new IllegalArgumentException("negative count");target=Math.addExact(target,n);}
        for(int n:available)if(n<0)throw new IllegalArgumentException("negative count");
        int slots=available.length,ingredients=required.length,sink=slots+ingredients+1,size=sink+1;
        int[][] residual=new int[size][size];
        for(int s=0;s<slots;s++){
            residual[0][s+1]=Math.min(available[s],target);
            for(int i=0;i<ingredients;i++)if(accepts.test(i,s))residual[s+1][1+slots+i]=Math.min(available[s],required[i]);
        }
        for(int i=0;i<ingredients;i++)residual[1+slots+i][sink]=required[i];
        int total=0;
        while(total<target){
            int[] parent=new int[size];Arrays.fill(parent,-1);parent[0]=0;
            int[] queue=new int[size];int read=0,write=1;
            while(read<write&&parent[sink]<0){int node=queue[read++];for(int n=1;n<size;n++)if(parent[n]<0&&residual[node][n]>0){parent[n]=node;queue[write++]=n;}}
            if(parent[sink]<0)return null;
            int flow=target-total;
            for(int n=sink;n!=0;n=parent[n])flow=Math.min(flow,residual[parent[n]][n]);
            for(int n=sink;n!=0;n=parent[n]){residual[parent[n]][n]-=flow;residual[n][parent[n]]+=flow;}
            total+=flow;
        }
        int[] used=new int[slots];for(int s=0;s<slots;s++)used[s]=Math.min(available[s],target)-residual[0][s+1];return used;
    }
    private IngredientAllocation(){}
}
