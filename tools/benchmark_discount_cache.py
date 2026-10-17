"""Measure exact production discount arithmetic vs its bounded per-consumer cache.
Reports JVM CPU/allocation counters, not Minecraft TPS or FPS. No Minecraft fixtures.
"""
from pathlib import Path
import json,statistics,subprocess,tempfile
root=Path(__file__).resolve().parents[1]
harness=r'''
package com.foundations.calculator.core;
public final class DiscountBenchmark {
    static volatile long sink;
    static final long[] BASES={500,1000,10_000_000_000L,Long.MAX_VALUE-13};
    static long batch(boolean cached,int count){
        var cache=new EnergyOperations.DiscountCache();long sum=0;
        for(int i=0;i<count;i++){long base=BASES[(i/1000)%BASES.length];int n=1+(i/8000)%16;
            sum+=cached?cache.apply(base,n,.1):EnergyOperations.discounted(base,n,.1);}
        return sink=sum;
    }
    static void measure(boolean cached,int count){
        var bean=(com.sun.management.ThreadMXBean)java.lang.management.ManagementFactory.getThreadMXBean();bean.setThreadAllocatedMemoryEnabled(true);
        long id=Thread.currentThread().threadId(),bytes=bean.getThreadAllocatedBytes(id),cpu=bean.getCurrentThreadCpuTime();
        batch(cached,count);cpu=bean.getCurrentThreadCpuTime()-cpu;bytes=bean.getThreadAllocatedBytes(id)-bytes;
        System.out.println((cached?"cached":"direct")+" "+cpu+" "+bytes+" "+count);
    }
    public static void main(String[] args){
        var cache=new EnergyOperations.DiscountCache();var random=new java.util.Random(1734096);
        for(int i=0;i<20000;i++){long base=i%7==0?Long.MAX_VALUE:random.nextLong()>>>1;int n=random.nextInt(1025);double d=new double[]{0,.1,.25,1,100}[i%5];
            long expected=EnergyOperations.discounted(base,n,d);if(cache.apply(base,n,d)!=expected||cache.apply(base,n,d)!=expected)throw new AssertionError(i);}
        for(int i=0;i<4;i++){batch(false,50000);batch(true,50000);}
        for(int i=0;i<5;i++){measure(i%2==0,200000);measure(i%2!=0,200000);}
    }
}
'''
with tempfile.TemporaryDirectory() as temporary:
 temp=Path(temporary);source=temp/'DiscountBenchmark.java';source.write_text(harness)
 subprocess.run(['javac','--release','21','-d',str(temp),str(root/'src/main/java/com/foundations/calculator/core/EnergyOperations.java'),str(source)],check=True)
 result=subprocess.run(['java','-cp',str(temp),'com.foundations.calculator.core.DiscountBenchmark'],check=True,text=True,capture_output=True)
rows=[]
for line in result.stdout.splitlines():
 mode,cpu,allocated,count=line.split();rows.append({'mode':mode,'cpu_ns_per_op':int(cpu)/int(count),'allocated_bytes_per_op':int(allocated)/int(count)})
summary={mode:{key:statistics.median(row[key] for row in rows if row['mode']==mode) for key in ['cpu_ns_per_op','allocated_bytes_per_op']} for mode in ['direct','cached']}
report={'scope':'Production exact arithmetic; 99.9% repeated numeric inputs with regular base and upgrade changes; isolated JVM, not whole-mod TPS/FPS','equivalence_cases':20000,'samples':rows,'median':summary}
out=root/'validation/performance-r4';out.mkdir(parents=True,exist_ok=True);(out/'discount-cache-benchmark.json').write_text(json.dumps(report,indent=2)+'\n')
print(json.dumps(report,indent=2))
