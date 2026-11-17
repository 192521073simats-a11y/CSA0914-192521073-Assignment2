import java.util.*;
public class InventoryBenchmark {
    static volatile int sink;
    static void run(int n){
        Map<String,Integer> hash=new HashMap<>();Map<String,Integer> tree=new TreeMap<>();Map<String,Integer> linked=new LinkedHashMap<>();
        for(int i=0;i<n;i++){String k=String.format(Locale.ROOT,"MED%07d",i);hash.put(k,i);tree.put(k,i);linked.put(k,i);}
        String[] keys=hash.keySet().toArray(new String[0]);Random rnd=new Random(42);int lookups=100000;
        long start=System.nanoTime();for(int i=0;i<lookups;i++)sink+=hash.get(keys[rnd.nextInt(n)]);long hashLookup=System.nanoTime()-start;
        rnd=new Random(42);start=System.nanoTime();for(int i=0;i<lookups;i++)sink+=tree.get(keys[rnd.nextInt(n)]);long treeLookup=System.nanoTime()-start;
        start=System.nanoTime();for(String k:tree.keySet())sink+=k.length();long sortedReport=System.nanoTime()-start;
        start=System.nanoTime();for(String k:linked.keySet())sink+=k.length();long insertionReport=System.nanoTime()-start;
        start=System.nanoTime();List<String> sorted=new ArrayList<>(hash.keySet());Collections.sort(sorted);for(String k:sorted)sink+=k.length();long sortOnDemand=System.nanoTime()-start;
        System.out.printf(Locale.ROOT,"%d,%d,%d,%d,%d,%d%n",n,hashLookup,treeLookup,sortedReport,insertionReport,sortOnDemand);
    }
    public static void main(String[] args){System.out.println("size,hash_100k_lookup_ns,tree_100k_lookup_ns,tree_sorted_report_ns,linked_insertion_report_ns,hash_sort_report_ns");for(int n:new int[]{100,10000,50000})run(n);}
}