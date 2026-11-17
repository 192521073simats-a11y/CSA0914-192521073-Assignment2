# Q6 – Priority, Order and Fairness in a Queue (8 Marks)

## Why a plain FIFO queue is insufficient
A normal Queue serves the earliest arrival regardless of severity. The requirement says the lowest severity number is treated first, with arrival order used only for equal severity. A PriorityQueue with a comparator expresses both rules.

## Priority order
Comparator sorts severity ascending, then arrival sequence ascending. `offer/add` is O(log n), `poll` is O(log n), `peek` is O(1), and storage is O(n). Printing the PriorityQueue directly does not guarantee sorted iteration; only repeated poll (or copying and polling) reveals treatment order.

## Hand verification
The source admits eight patients with mixed severity and arrival order. Expected order is severity 1 first; then severity 2 patients in arrival order; then severity 3; then severity 4; finally severity 5. The program prints the poll order for comparison.

## Growth investigation
The benchmark tests 1,000, 10,000 and 100,000 patients and records average insertion and poll costs. Heap-based priority queue operations grow logarithmically. Preserve raw CSV and use repeated runs.

## Starvation and fairness
If critical cases continue to arrive, a severity-5 patient can remain waiting indefinitely. A fair policy can promote a waiting patient one severity level after a configured wait threshold, with a maximum promotion to severity 1. Because PriorityQueue does not automatically reorder an object whose priority changes in place, remove/reinsert the patient or rebuild the heap after recalculating effective priorities. The policy must never delay a truly critical patient.

## Alternatives
A sorted LinkedList takes O(n) to insert at the correct position and O(1) to remove the head; it uses O(n) space. Five FIFO queues, one per severity, offer O(1) enqueue/dequeue and O(5) (constant) severity selection, with O(n) space; they are efficient when severity is a small fixed range and FIFO within each level is sufficient. The PriorityQueue is flexible when priorities are more complex.
