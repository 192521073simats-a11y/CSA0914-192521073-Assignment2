import java.util.*;
public class StringBuildBenchmark {
    static final int WARMUP=3, RUNS=7;
    static String[] fields(int count){String[] a=new String[count];for(int i=0;i<count;i++)a[i]="Field"+i+"=PassengerData";return a;}
    static String plus(String[] f){String s="";for(String x:f)s+=x+"\n";return s;}
    static String builder(String[] f){StringBuilder b=new StringBuilder();for(String x:f)b.append(x).append('\n');return b.toString();}
    static String buffer(String[] f){StringBuffer b=new StringBuffer();for(String x:f)b.append(x).append('\n');return b.toString();}
    interface Work{String run(String[] f);}
    static void measure(String label,int fields,int tickets,Work w){
        String[] f=fields(fields);long total=0;long checksum=0;
        for(int r=-WARMUP;r<RUNS;r++){long start=System.nanoTime();for(int t=0;t<tickets;t++)checksum+=w.run(f).length();long elapsed=System.nanoTime()-start;if(r>=0)total+=elapsed;}
        System.out.printf(Locale.ROOT,"%s,%d,%d,%.3f,%d%n",label,fields,tickets,total/(double)RUNS/1_000_000.0,checksum);
    }
    public static void main(String[] args){
        System.out.println("method,fields,tickets,avg_ms,checksum");
        int[][] workloads={{6,1},{50,10000},{100,20000}};
        for(int[] w:workloads){measure("StringPlus",w[0],w[1],StringBuildBenchmark::plus);measure("StringBuilder",w[0],w[1],StringBuildBenchmark::builder);measure("StringBuffer",w[0],w[1],StringBuildBenchmark::buffer);}
    }
}