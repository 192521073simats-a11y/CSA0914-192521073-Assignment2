import java.util.*;
class Team {
    final String name;final int points,goalDiff;
    Team(String n,int p,int g){name=n;points=p;goalDiff=g;}
    String getName(){return name;}int getPoints(){return points;}int getGoalDiff(){return goalDiff;}
    public String toString(){return name+"("+points+","+goalDiff+")";}
}
class Leaderboard<T> {
    private final List<T> items=new ArrayList<>();
    private final Comparator<? super T> order;
    Leaderboard(Comparator<? super T> order){this.order=Objects.requireNonNull(order);}
    void add(T item){items.add(Objects.requireNonNull(item));}
    void addAll(List<? extends T> source){items.addAll(source);}
    List<T> ranking(){List<T> copy=new ArrayList<>(items);copy.sort(order);return copy;}
}
public class LeaderboardDemo {
    static Comparator<Team> ORDER=Comparator.comparingInt(Team::getPoints).reversed()
        .thenComparing(Comparator.comparingInt(Team::getGoalDiff).reversed()).thenComparing(Team::getName);
    public static void main(String[] args){
        Leaderboard<Team> board=new Leaderboard<>(ORDER);
        board.addAll(Arrays.asList(new Team("Falcons",20,8),new Team("Tigers",20,8),new Team("Wolves",20,5),new Team("Eagles",18,9),new Team("Bears",18,9)));
        System.out.println("Ranking: "+board.ranking());
        Comparator<Team> unsafe=(a,b)->b.points-a.points; // intentionally unsafe: subtraction may overflow
        List<Team> extreme=new ArrayList<>(Arrays.asList(new Team("Huge",Integer.MAX_VALUE,0),new Team("Low",Integer.MIN_VALUE,0)));
        extreme.sort(unsafe);System.out.println("Unsafe comparator result (may be wrong): "+extreme);
        extreme.sort(ORDER);System.out.println("Safe comparator result: "+extreme);
    }
}