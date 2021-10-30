import dijkstra.DirectedGraphOps.DirectedGraphOps
import InputReader.readInput
import OutputBuilder.buildOutput
import dijkstra.Helpers.mapAlphabetToIndex
import dijkstra.{EdgeWeightedDigraph, ShortestPath, ShortestPathCalc}

import scala.language.postfixOps

object Main extends App {

  val (edges, routeQuery, nearByQuery) = readInput()

  val graph =
    EdgeWeightedDigraph().addEdges(edges)
  println(graph)
  val sp: Either[String, ShortestPathCalc] =
    ShortestPath.run(graph, routeQuery.source)
  val destinationAsIndex: Int = mapAlphabetToIndex(routeQuery.destination)

  val resultPath = sp.flatMap(_.pathTo(destinationAsIndex))
  val resultDis = sp.flatMap(_.distToV(destinationAsIndex))
  val output = buildOutput(resultPath, resultDis)

  println(output.right.get)
}
