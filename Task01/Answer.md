# Q1 – Building Text Under Load: String, StringBuilder or StringBuffer? (6 Marks)

## Prediction before implementation
- `String` is immutable. Repeated `text = text + field` creates new strings and copies the accumulated characters repeatedly. The total copying for n similarly sized fields grows quadratically in the final output size model.
- `StringBuilder` is mutable and appends into a resizable character buffer. It is normally the fastest choice for building one ticket inside one thread.
- `StringBuffer` provides synchronized methods. It is useful when multiple threads truly share the same buffer, but synchronization adds overhead. It is not necessary for separate per-ticket builders.

## Workloads
The program in `StringBuildBenchmark.java` measures three workloads: a short ticket (6 fields, 1 ticket), a batch (50 fields, 10,000 tickets), and a larger workload (100 fields, 20,000 tickets). It repeats each approach, uses `System.nanoTime()`, reports the average time, estimates intermediate string counts for the concatenation loop, and samples used heap before/after. Run on the same JDK and machine and retain the generated CSV as raw evidence.

## Expected analysis
| Approach | Approximate build time for n fields | Extra space |
|---|---|---|
| Repeated + in loop | O(L²) character copying for total output length L | O(L²) cumulative temporary allocation over the build; O(L) live result |
| StringBuilder | O(L) amortized append | O(L) buffer plus final String |
| StringBuffer | O(L) amortized append, with synchronization overhead | O(L) buffer plus final String |

The precise measured time depends on JIT warm-up, heap state, hardware and field sizes. Do not treat one run as proof; compare repeated averages and preserve the CSV produced by the program.

## Intermediate objects and memory
For k fields, repeated concatenation in a loop can create approximately k-1 intermediate result strings (excluding field strings and implementation/JIT optimizations). StringBuilder typically creates one builder and one final String, with occasional internal buffer growth. StringBuffer has similar allocation behaviour to StringBuilder but synchronizes its methods. Heap-before/after readings are noisy and are not a reliable allocation profiler; use them as supporting observations only.

## Design decision
Use a local `StringBuilder` for each e-ticket because each ticket is built independently. Use a shared `StringBuffer` only if threads must append to the same mutable log buffer and synchronized append is the intended coordination mechanism. In a real booking server, a logging framework or a thread-safe queue with a single log-writer is usually safer than allowing many threads to mutate one long-lived string buffer. A `String` is appropriate for the completed immutable ticket.

## Alternative: StringJoiner / String.join / String.format
A list of fields can be passed to `String.join("\\n", fields)` or appended using `StringJoiner`. These avoid manually repeated immutable concatenation and take O(L) time and O(L) output space. `String.format` is convenient for formatted templates but has formatting/parser overhead; it is not generally the fastest option for high-volume ticket generation.
