# Q5 – Derive a Generic Ranking Component (7 Marks)

## Design choice
Team does not need one universal natural ordering because different clients may rank by different rules (points, goal difference, name, or other fields). Keep Team as a simple data class and inject a Comparator into `Leaderboard<T>`. This makes the component reusable for teams, players and colleges.

## Comparator chain
The sample uses points descending, then goal difference descending, then name ascending:
`Comparator.comparingInt(Team::getPoints).reversed().thenComparing(Comparator.comparingInt(Team::getGoalDiff).reversed()).thenComparing(Team::getName)`.
Avoid subtraction comparators such as `b.points - a.points`; integer overflow can produce an incorrect ordering. Use comparingInt and reversed.

## Generics and wildcards
`Leaderboard<T>` stores a type parameter and accepts `Comparator<? super T>`, allowing a comparator of T or one of its supertypes. A method accepting `List<? extends T>` can add items from a list of T or a subtype (producer extends). The ranking method returns a sorted copy, leaving the internal insertion list unchanged.

## Complexity
Adding is amortized O(1). Producing a ranking copy takes O(n) to copy and O(n log n) to sort; the copy requires O(n) extra space. Sorting three times by stable sort also takes O(n log n) per pass, or O(3n log n) = O(n log n), with repeated sorting work. A single comparator chain is clearer and generally performs one sort. A natural Comparable is useful when one canonical order exists; Comparator is more reusable when several valid rankings are needed.

## Correctness tests
The source includes ties at points and goal difference and a second test with extreme integer values to expose subtraction overflow. The final name tie-breaker ensures deterministic total ordering for different teams.
