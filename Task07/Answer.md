# Q7 – Diagnose, Redesign and Validate (13 Marks)

## Selected limitation: repeated report sorting in the pharmacy inventory
The original Q4 option can use a HashMap for fast lookup and sort all keys whenever a code-ordered report is requested. Sorting costs O(n log n) each time. If the report is requested frequently while the inventory changes only occasionally, repeatedly sorting the same keys wastes work.

## A. Diagnose the bottleneck (2 marks)
The dominant extra work is sorting n medicine codes on every sorted report. The cost is O(n log n) per report, in addition to O(n) output traversal. Use the Q4 benchmark to record report time for 100, 10,000 and 50,000 items; the raw CSV must be generated on the student's machine and attached. Do not claim an exact timing before running it.

## B. Design objective (2 marks)
Maintain the required orders as indexes so producing either report is O(n) traversal without sorting at report time. Keep average code lookup O(1). The objective is to trade extra index memory and update work for faster repeated reports.

## C. Revised solution (4 marks)
`InventoryRedesign.java` maintains:
1. HashMap for average O(1) lookup by code.
2. TreeMap for sorted-code reporting.
3. LinkedHashMap for insertion-order reporting.

An add/update operation updates all indexes together. A delete removes from all indexes. Each index stores O(n) references/entries, so total space remains O(n) with a larger constant. Insert/update costs average O(log n) due to TreeMap plus expected O(1) updates to the other maps. Each report is O(n) traversal and no longer needs O(n log n) sorting.

## D. Comparable experiment (2 marks)
Run the Q4 original implementation and Q7 redesign under the same generated data and repeat count. Record lookup, add/update, sorted report and insertion report timings. The redesigned solution should reduce repeated sorted-report cost when reports are frequent; it may be slower to update and uses more memory.

## F. Defend the final design (2 marks)
The redesign is appropriate when both report orders are frequent and writes are moderate. If the sorted report is rare and data changes constantly, one HashMap plus sorting on demand may be simpler and use less memory. The correct decision depends on measured report frequency, update frequency and the raw benchmark results. The benchmark code writes CSV; fill the report with actual machine results after execution.
