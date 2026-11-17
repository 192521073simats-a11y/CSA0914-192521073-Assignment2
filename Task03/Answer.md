# Q3 – Engineer the hashCode–equals Contract (6 Marks)

## Why duplicates appear
The supplied Student class overrides `equals()` using registration number but inherits `Object.hashCode()`. Two Student objects with the same registration number can therefore have different hash codes. HashSet first uses hashCode to choose a bucket and then equals to compare candidates in that bucket. If equal objects land in different buckets, equals may never be called and both can be stored.

## Contract
If `a.equals(b)` is true, `a.hashCode()` must equal `b.hashCode()`. Unequal objects may share a hash code. Both methods must use the same identity fields. This solution treats normalized registration number as the identity; name is descriptive and does not affect equality.

## Mutation hazard
If a field used by hashCode is changed after insertion, the object may remain in the bucket selected by its old hash. A later contains/remove call computes the new hash and may search a different bucket. Make identity fields final and avoid mutating them while stored in a hash collection.

## Verification tests
`StudentSetDemo.java` runs three cases: same registration with same name, same registration with a different name, and different registrations. The corrected class should keep exactly two unique students in the supplied expected scenario. The program also includes a deliberately broken variant to show why the contract matters. Record output before and after the fix when you execute it.

## Alternatives
- HashSet: average O(1) add/contains, worst-case O(n), O(n) space.
- TreeSet with comparator on registration number: O(log n) add/contains, O(n) space; maintains sorted order. Comparator equality (compare == 0) defines uniqueness.
- HashMap keyed by normalized registration number: average O(1) lookup/put, worst-case O(n), O(n) space; values can hold Student details. This is often clearest when registration number is the natural key.

The chosen implementation uses immutable normalized registration numbers and a matching hashCode/equals implementation.
