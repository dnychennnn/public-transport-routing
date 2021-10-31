import InputReader.readInput
import OutputBuilder.{buildRouteOutput, buildSortedNearByOutput}
import dijkstra.EdgeWeightedDigraphOps.EdgeWeightedDigraphOps
import dijkstra.{EdgeWeightedDigraph, ShortestPath, ShortestPathCalc}

import scala.language.postfixOps

object Main extends App {

  val (edges, routeQuery, nearByQuery) = readInput()

  val graph =
    EdgeWeightedDigraph().addEdges(edges)
  val sp: Either[String, ShortestPathCalc] =
    ShortestPath.run(graph, routeQuery.source)

  val destinationAsIndex: Int = graph.getIndex(routeQuery.destination)
  val resultPath = sp.flatMap(_.pathTo(destinationAsIndex))
  val resultDis = sp.flatMap(_.timeToV(destinationAsIndex))
  val resultNearBy = ShortestPath
    .run(graph, nearByQuery.source)
    .map(_.nearbyTo(nearByQuery.maxTime))

  println(buildRouteOutput(resultPath, resultDis))
  println(buildSortedNearByOutput(resultNearBy))
}
