package net.minecraft.core;
public record BlockPos(int x,int y,int z){public static final BlockPos ZERO=new BlockPos(0,0,0);public int getX(){return x;}public int getY(){return y;}public int getZ(){return z;}}
