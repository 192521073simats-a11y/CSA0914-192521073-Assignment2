import java.util.*;
public class InventoryRedesign {
    private final Map<String,Integer> lookup=new HashMap<>();
    private final NavigableMap<String,Integer> sorted=new TreeMap<>();
    private final LinkedHashMap<String,Integer> insertion=new LinkedHashMap<>();
    public void put(String code,int quantity){String key=Objects.requireNonNull(code).trim().toUpperCase();lookup.put(key,quantity);sorted.put(key,quantity);insertion.put(key,quantity);}
    public Integer get(String code){return lookup.get(code.trim().toUpperCase());}
    public boolean remove(String code){String k=code.trim().toUpperCase();boolean existed=lookup.containsKey(k);lookup.remove(k);sorted.remove(k);insertion.remove(k);return existed;}
    public void printSorted(){sorted.forEach((k,v)->System.out.println(k+"="+v));}
    public void printInsertionOrder(){insertion.forEach((k,v)->System.out.println(k+"="+v));}
    public static void main(String[] args){InventoryRedesign i=new InventoryRedesign();i.put("MED003",12);i.put("MED001",30);i.put("MED002",7);System.out.println("Lookup MED002: "+i.get("MED002"));System.out.println("Sorted report:");i.printSorted();System.out.println("Insertion report:");i.printInsertionOrder();i.remove("MED001");System.out.println("After removal: "+i.get("MED001"));}
}