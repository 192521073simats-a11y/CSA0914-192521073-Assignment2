import java.util.*;
public class TriageBenchmark {
    static volatile long sink;
    static void run(int n){long total=0;int repeats=5;for(int r=0;r<repeats;r++){PriorityQueue<Integer> q=new PriorityQueue<>();long start=System.nanoTime();for(int i=0;i<n;i++)q.offer((i*7919)%1000003);while(!q.isEmpty())sink+=q.poll();total+=System.nanoTime()-start;}System.out.printf(Locale.ROOT,"%d,%.3f%n",n,total/(double)repeats/1e6);}
    public static void main(String[] args){System.out.println("patients,avg_insert_and_poll_ms");for(int n:new int[]{1000,10000,100000})run(n);}
}