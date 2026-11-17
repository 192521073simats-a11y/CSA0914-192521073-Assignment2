import java.util.*;
class Patient {
    final String name;final int severity;final long arrival;
    Patient(String n,int s,long a){if(s<1||s>5)throw new IllegalArgumentException("Severity must be 1..5");name=n;severity=s;arrival=a;}
    public String toString(){return name+"(S"+severity+",#"+arrival+")";}
}
public class Triage {
    private long counter=0;
    private final PriorityQueue<Patient> queue=new PriorityQueue<>(
        Comparator.comparingInt((Patient p)->p.severity).thenComparingLong(p->p.arrival));
    void admit(String name,int severity){queue.add(new Patient(name,severity,counter++));}
    Patient next(){return queue.poll();}
    public static void main(String[] args){
        Triage t=new Triage();t.admit("Asha",3);t.admit("Bala",1);t.admit("Charu",2);t.admit("Deepak",2);t.admit("Esha",5);t.admit("Farah",1);t.admit("Gokul",4);t.admit("Hari",3);
        System.out.println("Treatment order (poll):");Patient p;while((p=t.next())!=null)System.out.println(p);
    }
}