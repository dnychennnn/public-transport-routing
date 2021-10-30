import InputModels.{Edge, RouteQuery}
import OutputBuilder.buildOutput
import dijkstra.DirectedGraphOps.DirectedGraphOps
import dijkstra.{EdgeWeightedDigraph, ShortestPath, ShortestPathCalc}
import dijkstra.Helpers.mapAlphabetToIndex
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import RouteQueryTest._

import scala.language.postfixOps

class RouteQueryTest extends AnyFreeSpec with Matchers {
  "RouteQueryTest" - {
    "when search for best routes for given edges" - {
      "should return optimal path in output format" - {
        val expectedOutput = Right("A -> C -> B: 130")

        val graph =
          EdgeWeightedDigraph().addEdges(testEdges)

        val sp: Either[String, ShortestPathCalc] =
          ShortestPath.run(graph, testRouteQuery.source)
        val destinationAsIndex: Int =
          mapAlphabetToIndex(testRouteQuery.destination)
        val resultPath = sp.flatMap(_.pathTo(destinationAsIndex))
        val resultDis = sp.flatMap(_.distToV(destinationAsIndex))
        val output = buildOutput(resultPath, resultDis)

        output shouldBe expectedOutput
      }
    }
  }

}

object RouteQueryTest {
  val testEdges = Seq(
    Edge("A", "B", 240),
    Edge("A", "C", 70),
    Edge("A", "D", 120),
    Edge("C", "B", 60),
    Edge("D", "E", 480),
    Edge("C", "E", 240),
    Edge("B", "E", 210),
    Edge("E", "A", 300)
  )
  val testRouteQuery = RouteQuery("A", "B")
}
