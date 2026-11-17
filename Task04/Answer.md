# Q4 – Choosing a Map for a Pharmacy Inventory (6 Marks)

## Requirements
The pharmacy needs fast medicine-code lookups, a report sorted by code, and a report in insertion order.

## Map comparison
| Structure | Lookup | Insert | Iteration order | Space |
|---|---|---|---|---|
| HashMap | Average O(1), worst O(n) | Average O(1) | No guaranteed order | O(n) |
| TreeMap | O(log n) | O(log n) | Sorted by key | O(n) |
| LinkedHashMap | Average O(1), worst O(n) | Average O(1) | Insertion order | O(n) |
| Hashtable | Average O(1) expected | Average O(1) expected | No guaranteed order | O(n) |

A single map cannot naturally provide both sorted-key and insertion order. A practical design can maintain a HashMap for lookup plus a TreeMap and LinkedHashMap for the two reports, or choose one primary map and create/sort a report copy when needed. The benchmark source compares these choices at 100, 10,000 and 50,000 medicines, including 100,000 lookups and report generation. It writes raw measurements to CSV.

## Acceptability criterion
For this exercise, define an acceptable lookup as an average of less than 1 millisecond per lookup over a batch of 100,000 lookups on the test machine. This is a stated engineering target, not a universal pharmacy requirement. Compare measured results against the criterion and report the machine/JDK.

## Deployment decision
Use HashMap as the primary index when code lookup dominates. Use LinkedHashMap when insertion-order reporting is important and a second sorted report is infrequent. Use TreeMap when sorted traversal is frequent and predictable O(log n) operations are acceptable. If both reports are frequent, maintain separate indexes and update them together so reports do not repeatedly sort. Hashtable is legacy synchronized collection; modern code normally uses HashMap with explicit synchronization/concurrent collections where needed.

## Alternative: sorted array + Arrays.binarySearch
Lookup is O(log n), insertion is O(n) because later elements shift, code-order report is O(n), and storage is O(n). HashMap offers average O(1) lookup and average O(1) insertion but no order. TreeMap offers O(log n) lookup/insertion and sorted O(n) traversal. A sorted array can be compact and cache-friendly for read-mostly data but is less suitable when stock changes frequently.
