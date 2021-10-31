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

### Algorithm Discussion:
1. In the graph building part, a `HashMap` of adjacency lists for all vertices are used to represent the directed graph. (O[E]) 
2. For the main routing part, three main segments are considered:
   1. Given the source vertex, an array of size(vertices) storing the last edge that leads to each destination from the source vertex `edgeTo` and another array storing the total time from source vertex to each vertices are precomputed. (O[EVlog(V)])
   2. Based on the precomputed arrays, three methods `pathTo`, `timeTo` are provided to compute the path, distance to the destination.
   3. Last but not least, `nearByTo` leverage the `timeTo` method to get the travel time from source to each destination and filter out the ones outside constraints. 

### Total Time Spent:
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
- <del>*Did not consider alphanumeric characters*: The current solution will only work when the edges are denoted in capital alphabetical order (ASCII conversion).</del>
  - <del>Could be improved using HashMap in the graph building phase (currently using ArrayBuffer with index as the vertex key) or some preprocessing for vertex mapping to index.</del>
  - **Update**: Improved using *v2ilookup* array and it now supports alphanumeric inputs.
- *Testings*: Improve the test coverage for implemented methods. The present implementation only covers e2e test with 1 provided input.
  - Input edge cases, e.g. empty edges, unmatched, etc.