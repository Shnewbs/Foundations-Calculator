package com.foundations.calculator.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.AABB;

/** Measures rendered vertices once per resource reload, including articulated mesh transforms. */
final class ItemMeshBounds implements VertexConsumer {
    private double minX=Double.POSITIVE_INFINITY,minY=minX,minZ=minX;
    private double maxX=Double.NEGATIVE_INFINITY,maxY=maxX,maxZ=maxX;
    public VertexConsumer addVertex(float x,float y,float z){minX=Math.min(minX,x);minY=Math.min(minY,y);minZ=Math.min(minZ,z);maxX=Math.max(maxX,x);maxY=Math.max(maxY,y);maxZ=Math.max(maxZ,z);return this;}
    public VertexConsumer setColor(int r,int g,int b,int a){return this;}
    public VertexConsumer setUv(float u,float v){return this;}
    public VertexConsumer setUv1(int u,int v){return this;}
    public VertexConsumer setUv2(int u,int v){return this;}
    public VertexConsumer setNormal(float x,float y,float z){return this;}
    AABB bounds(){return Double.isFinite(minX)?new AABB(minX,minY,minZ,maxX,maxY,maxZ):new AABB(0,0,0,1,1,1);}
}
