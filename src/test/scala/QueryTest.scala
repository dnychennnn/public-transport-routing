import InputModels.{Edge, NearByQuery, RouteQuery}
import OutputBuilder.{buildRouteOutput, buildSortedNearByOutput}
import dijkstra.{
  EdgeWeightedDigraph,
  NearByVertex,
  ShortestPath,
  ShortestPathCalc
}
import dijkstra.Helpers.mapAlphabetToIndex
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import RouteQueryTest._
import dijkstra.EdgeWeightedDigraphOps.EdgeWeightedDigraphOps

import scala.language.postfixOps

class QueryTest extends AnyFreeSpec with Matchers {

  val graph =
    EdgeWeightedDigraph().addEdges(testEdges)
  val sp: Either[String, ShortestPathCalc] =
    ShortestPath.run(graph, testRouteQuery.source)

  "RouteQueryTest" - {
    "when search for best route for given source and destination in a graph" - {
      "should return optimal path in output format" - {
        val expectedOutput = Right("A -> C -> B: 130")

        val destinationAsIndex: Int =
          mapAlphabetToIndex(testRouteQuery.destination)
        val resultPath = sp.flatMap(_.pathTo(destinationAsIndex))
        val resultDis = sp.flatMap(_.timeToV(destinationAsIndex))

        val output = buildRouteOutput(resultPath, resultDis)

        output shouldBe expectedOutput
      }
      "should return empty path if destination is source in output format" - {
        val expectedOutput =
          Right("destination is source, travel time = 0")

        val destinationAsIndex: Int =
          mapAlphabetToIndex(testRouteQuery.source)

        val resultPath = sp.flatMap(_.pathTo(destinationAsIndex))
        val resultDis = sp.flatMap(_.timeToV(destinationAsIndex))

        val output = buildRouteOutput(resultPath, resultDis)
        output shouldBe expectedOutput
      }
    }
  }

  "NearyByQueryTest" - {
    "when search for reachable destinations from given source within certain time in a graph" - {
      "should return expected destinations" - {
        val expectedOutput = Right("C: 70, D: 120, B: 130")
        val resultNearByEither: Either[String, Seq[NearByVertex]] =
          sp.map(_.nearbyTo(130))

        val output = buildSortedNearByOutput(resultNearByEither)
        output shouldEqual expectedOutput
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
  val testNearByQuery = NearByQuery("A", 130)
}
