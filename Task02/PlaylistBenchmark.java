import java.util.*;
public class PlaylistBenchmark {
    static volatile long sink;
    static long time(Runnable r,int repeats){long start=System.nanoTime();for(int i=0;i<repeats;i++)r.run();return (System.nanoTime()-start)/repeats;}
    static void run(int n){
        ArrayList<Integer> a=new ArrayList<>();LinkedList<Integer> l=new LinkedList<>();
        for(int i=0;i<n;i++){a.add(i);l.add(i);}
        int reps=Math.max(100,100000/n);
        long aAppend=time(()->{a.add(-1);a.remove(a.size()-1);},reps);
        long lAppend=time(()->{l.add(-1);l.remove(l.size()-1);},reps);
        long aFront=time(()->{a.add(0,-1);a.remove(0);},reps);
        long lFront=time(()->{l.add(0,-1);l.remove(0);},reps);
        long aMiddle=time(()->{int p=a.size()/2;a.add(p,-1);a.remove(p);},reps);
        long lMiddle=time(()->{int p=l.size()/2;l.add(p,-1);l.remove(p);},reps);
        long aGet=time(()->sink+=a.get(n/2),reps);
        long lGet=time(()->sink+=l.get(n/2),reps);
        long aIter=time(()->{long s=0;for(int x:a)s+=x;sink=s;},Math.max(3,reps/10));
        long lIter=time(()->{long s=0;for(int x:l)s+=x;sink=s;},Math.max(3,reps/10));
        System.out.printf(Locale.ROOT,"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d%n",n,aAppend,lAppend,aFront,lFront,aMiddle,lMiddle,aGet,lGet,aIter,lIter);
    }
    public static void main(String[] args){System.out.println("size,arrayAppend,linkedAppend,arrayFront,linkedFront,arrayMiddle,linkedMiddle,arrayGet,linkedGet,arrayIter,linkedIter (ns/op)");for(int n:new int[]{1000,10000,50000})run(n);}
}