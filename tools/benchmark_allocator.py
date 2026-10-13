"""Compare production allocators with a pinned Git baseline in an isolated JVM.

Needs javac/java (17+), Git history containing the baseline, and no Minecraft stubs.
Reports CPU time and JVM thread allocation; this is not a whole-mod TPS benchmark.
"""
import json
from pathlib import Path
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[1]
BASELINE = '0a56675493cf836b4ce035fc1a8e7653e9648b64'
SOURCE = 'src/main/java/com/foundations/calculator/core/IngredientAllocation.java'
HARNESS = r'''
package com.foundations.calculator.core;
import java.util.*;
import java.util.function.BiPredicate;
public final class AllocationBenchmark {
    static volatile long consumed;
    interface Allocator {int[] run(int[] available,int[] required,BiPredicate<Integer,Integer> accepts);}
    static final Allocator OLD=BaselineAllocation::allocate,NEW=IngredientAllocation::allocate;
    static final int[][] INPUTS={{3,5,0},{1},{64,3,64},{1,1},{0,0,0}};
    static final int[][] REQUIRED={{7},{4},{1},{1,1},{1}};
    static final List<BiPredicate<Integer,Integer>> ACCEPTS=List.of((i,s)->true,(i,s)->true,(i,s)->s==1,(i,s)->i==0||s==0,(i,s)->true);
    static void batch(Allocator allocator,int count){
        long sum=0;
        for(int n=0;n<count;n++){
            int i=n%INPUTS.length;int[] used=allocator.run(INPUTS[i],REQUIRED[i],ACCEPTS.get(i));
            if(used!=null)for(int value:used)sum+=value;
        }
        consumed=sum;
    }
    static void measure(String label,Allocator allocator,int count){
        var bean=(com.sun.management.ThreadMXBean)java.lang.management.ManagementFactory.getThreadMXBean();
        if(!bean.isThreadAllocatedMemorySupported())throw new IllegalStateException("JVM allocation counter unavailable");
        bean.setThreadAllocatedMemoryEnabled(true);
        long thread=Thread.currentThread().getId(),before=bean.getThreadAllocatedBytes(thread),start=System.nanoTime();
        batch(allocator,count);
        long elapsed=System.nanoTime()-start,bytes=bean.getThreadAllocatedBytes(thread)-before;
        System.out.println(label+" "+elapsed+" "+bytes+" "+count);
    }
    public static void main(String[] args){
        var random=new Random(1734096);
        for(int trial=0;trial<100000;trial++){
            int[] available=new int[random.nextInt(7)],required=new int[random.nextInt(5)];
            for(int s=0;s<available.length;s++)available[s]=random.nextInt(8);
            for(int i=0;i<required.length;i++)required[i]=random.nextInt(8);
            boolean[][] matches=new boolean[required.length][available.length];
            for(var row:matches)for(int s=0;s<row.length;s++)row[s]=random.nextBoolean();
            BiPredicate<Integer,Integer> accepts=(i,s)->matches[i][s];
            if(!Arrays.equals(OLD.run(available,required,accepts),NEW.run(available,required,accepts)))throw new AssertionError("Differential trial "+trial);
        }
        System.out.println("equivalent 100000");
        for(int warm=0;warm<5;warm++){batch(OLD,100000);batch(NEW,100000);}
        for(int sample=0;sample<5;sample++){
            if(sample%2==0){measure("before",OLD,1000000);measure("after",NEW,1000000);}
            else{measure("after",NEW,1000000);measure("before",OLD,1000000);}
        }
    }
}
'''

baseline = subprocess.check_output(['git', 'show', BASELINE + ':' + SOURCE], cwd=ROOT, text=True)
with tempfile.TemporaryDirectory(prefix='calculator-allocator-') as temporary:
    directory = Path(temporary)
    (directory / 'BaselineAllocation.java').write_text(baseline.replace('IngredientAllocation', 'BaselineAllocation'))
    (directory / 'IngredientAllocation.java').write_text((ROOT / SOURCE).read_text())
    (directory / 'AllocationBenchmark.java').write_text(HARNESS)
    subprocess.run(['javac', '-d', temporary, *map(str, directory.glob('*.java'))], check=True)
    output = subprocess.check_output(['java', '-Xms256m', '-Xmx256m', '-cp', temporary,
                                     'com.foundations.calculator.core.AllocationBenchmark'], text=True)
samples = {'before': [], 'after': []}
for line in output.splitlines():
    fields = line.split()
    if fields[0] in samples:
        samples[fields[0]].append({'ns_per_call': int(fields[1])/int(fields[3]),
                                   'allocated_bytes_per_call': int(fields[2])/int(fields[3])})
report = {'baseline_commit': BASELINE, 'differential_cases': 100000,
          'java': subprocess.run(['java', '-version'], text=True, capture_output=True).stderr.strip(),
          'scope': 'Mixed single-ingredient, insufficient-count and overlapping-ingredient allocator calls; not world TPS.',
          'samples': samples}
destination = ROOT / 'validation/performance/allocator-benchmark.json'
destination.parent.mkdir(parents=True, exist_ok=True)
destination.write_text(json.dumps(report, indent=2) + '\n')
print(json.dumps(report, indent=2))
