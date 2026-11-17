# Q2 – Playlist Data Structure: ArrayList vs LinkedList (6 Marks)

## Prediction
- ArrayList: append is amortized O(1); indexed access is O(1); iteration is O(n); inserting/removing at the front or middle is O(n) because elements must shift.
- LinkedList: append is O(1) when adding at the tail; indexed access is O(n); iteration is O(n); inserting/removing at a known node is O(1), but finding the position by index is O(n).
- For a playlist that often jumps to the k-th song and iterates, ArrayList is the reasonable default. A LinkedList is only attractive when edits are made through an already-held iterator/node and indexed access is rare.

## Experimental design
`PlaylistBenchmark.java` tests three playlist sizes (1,000; 10,000; 50,000) and measures append, front insert/remove, middle insert/remove, indexed access and full iteration. It warms up, repeats operations and reports average nanoseconds per operation. Run each size on the same machine/JDK. Do not compare a single measurement; use repeated averages and keep the generated CSV.

## Engineering boundary
Choose ArrayList while random access and iteration dominate and middle edits are occasional. Stop recommending it when a workload continuously inserts/removes near the front and shifting dominates. Even then, LinkedList is not automatically best: locating an arbitrary middle position remains linear and linked nodes use more memory and have poorer cache locality.

## Changed condition: Up Next queue
For a queue where songs are frequently added at both ends and removed from the front, use `ArrayDeque<Song>`. `addFirst`, `addLast`, `removeFirst` and `removeLast` are amortized O(1); it avoids LinkedList node allocation and is designed as a double-ended queue. ArrayDeque does not provide O(1) indexed access and does not permit null elements.
