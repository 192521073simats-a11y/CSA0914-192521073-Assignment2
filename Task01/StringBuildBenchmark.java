import java.util.*;
public class StringBuildBenchmark {
    static final int WARMUP=2, RUNS=5;
    static String[] fields(int count){String[] a=new String[count];for(int i=0;i<count;i++)a[i]="Field"+i+"=PassengerData";return a;}
    static String plus(String[] f){String s="";for(String x:f)s+=x+(char)10;return s;}
    static String builder(String[] f){StringBuilder b=new StringBuilder();for(String x:f)b.append(x).append((char)10);return b.toString();}
    static String buffer(String[] f){StringBuffer b=new StringBuffer();for(String x:f)b.append(x).append((char)10);return b.toString();}
    interface Work{String run(String[] f);}
    static long usedHeap(){Runtime r=Runtime.getRuntime();return r.totalMemory()-r.freeMemory();}
    static void measure(String label,int fields,int tickets,Work w){
        String[] f=fields(fields);long total=0,checksum=0,heapDelta=0;
        for(int r=-WARMUP;r<RUNS;r++){
            System.gc();long before=usedHeap();long start=System.nanoTime();
            for(int t=0;t<tickets;t++)checksum+=w.run(f).length();
            long elapsed=System.nanoTime()-start;long after=usedHeap();
            if(r>=0){total+=elapsed;heapDelta+=Math.max(0,after-before);}
        }
        long intermediate=label.equals("StringPlus")?(long)Math.max(0,fields-1)*tickets:0;
        System.out.printf(Locale.ROOT,"%s,%d,%d,%.3f,%d,%.1f,%d%n",label,fields,tickets,total/(double)RUNS/1_000_000.0,intermediate,heapDelta/(double)RUNS/1024.0,checksum);
    }
    public static void main(String[] args){
        System.out.println("method,fields,tickets,avg_ms,estimated_intermediate_strings,avg_positive_heap_delta_KB,checksum");
        int[][] workloads={{6,1},{50,10000},{100,500}};
        for(int[] w:workloads){measure("StringPlus",w[0],w[1],StringBuildBenchmark::plus);measure("StringBuilder",w[0],w[1],StringBuildBenchmark::builder);measure("StringBuffer",w[0],w[1],StringBuildBenchmark::buffer);}
    }
}