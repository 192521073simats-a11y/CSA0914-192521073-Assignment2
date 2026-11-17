import java.util.*;
class Student {
    private final String regNo;
    private final String name;
    Student(String regNo,String name){this.regNo=Objects.requireNonNull(regNo).trim().toUpperCase();this.name=Objects.requireNonNull(name);}
    @Override public boolean equals(Object o){if(this==o)return true;if(!(o instanceof Student))return false;Student s=(Student)o;return regNo.equals(s.regNo);}
    @Override public int hashCode(){return Objects.hash(regNo);}
    @Override public String toString(){return regNo+"-"+name;}
}
class BrokenStudent {
    final String regNo;final String name;
    BrokenStudent(String r,String n){regNo=r;name=n;}
    @Override public boolean equals(Object o){return o instanceof BrokenStudent && regNo.equals(((BrokenStudent)o).regNo);}
    // Intentionally inherits Object.hashCode to demonstrate the contract violation.
}
public class StudentSetDemo {
    public static void main(String[] args){
        Set<BrokenStudent> broken=new HashSet<>();broken.add(new BrokenStudent("192521073","Maya"));broken.add(new BrokenStudent("192521073","Maya"));System.out.println("Broken set size (may be 2): "+broken.size());
        Set<Student> fixed=new HashSet<>();fixed.add(new Student("192521073","Maya"));fixed.add(new Student("192521073","Cassandra"));fixed.add(new Student("192521074","Alex"));System.out.println("Unique students: "+fixed.size());
        Set<Student> case2=new HashSet<>();case2.add(new Student("A001","Sam"));case2.add(new Student("A001","Sam"));System.out.println("Same record duplicate test: "+case2.size());
        Set<Student> case3=new HashSet<>();case3.add(new Student("A001","Sam"));case3.add(new Student("A002","Sam"));System.out.println("Different registration test: "+case3.size());
    }
}