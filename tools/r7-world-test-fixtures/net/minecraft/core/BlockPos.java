package net.minecraft.core;
public record BlockPos(int x,int y,int z) { public BlockPos immutable(){return this;} public BlockPos below(){return new BlockPos(x,y-1,z);} public int getX(){return x;} public int getY(){return y;} public int getZ(){return z;} public long asLong(){return ((long)x & 0x3ffffff)<<38 | ((long)z & 0x3ffffff)<<12 | (y&0xfff);}}
