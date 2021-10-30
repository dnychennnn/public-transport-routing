# public-transport-routing

## Task Description


## Solution Description
This solution is highly inspired by the scala implementation of Dijkstra algorthm by [Alexey Novakov](https://medium.com/se-notes-by-alexey-novakov/algorithms-in-scala-dijkstra-shortest-path-78c4291dd8ab)

### Solved:
1. Input reading and output string construction.
2. Directed graph building using adjacency list.
3. Data modeling using `case class`: 
   - EdgeWeightedDigraph 
   - Inputs & Outputs
   - Custom exception handling
4. Routing Algorithm: leverage exisiting implementation of `Dijkstra` algorithm and adopt to our use case.
5. Testing: unit testings for `route` and `nearBy` queries using `scalaTest`.

### Total Time Spent
- Weekdays [~2] : preparatory work, research findings.
- Saturday [9 - 12] : Implementing & troubleshooting.
- Total: 5 hrs

## How To
### Requirements
   - sbt 
   - openJDK higher than 8

### Run 

```bash
sbt run
```

### Test Input
```bash
8
A -> B: 240
A -> C: 70
A -> D: 120
C -> B: 60
D -> E: 480
C -> E: 240
B -> E: 210
E -> A: 300
route A -> B
nearby A, 130
```

### Test All
```bash
sbt test
```

## Future Work
- *Did not consider alphanumeric characters*: The current solution will only work when the edges are denoted in capital alphabetical order (ASCII conversion).   
  - Could be improved using HashMap in the graph building phase (currently using ArrayBuffer with index as the vertex key) or some preprocessing for vertex mapping to index.
- *TypeSafe implementation*: Several places were still done in primitive types. It'd be better to complete these.
- *Testings*: Improve the test coverage for implemented methods. The present implementation only covers e2e test with 1 provided input.
  - Input edge cases, e.g. empty edges, unmatched, etc.s