import InputModels.{Edge, NearByQuery, RouteQuery}
import OutputBuilder.{buildRouteOutput, buildSortedNearByOutput}
import dijkstra.{
  EdgeWeightedDigraph,
  NearByVertex,
  ShortestPath,
  ShortestPathCalc
}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import RouteQueryTest._
import dijkstra.EdgeWeightedDigraphOps.EdgeWeightedDigraphOps

import scala.language.postfixOps

class MainSpec extends AnyFreeSpec with Matchers {
  "search in a graph1" - {
    val testRouteQuery = RouteQuery("A", "B")
    val testNearByQuery = NearByQuery("A", 130)
    val graph1 =
      EdgeWeightedDigraph().addEdges(testEdges)
    val sp: Either[String, ShortestPathCalc] =
      ShortestPath.run(graph1, testRouteQuery.source)

    "should return optimal path in output format for graph" in {
      val expectedOutput = "A -> C -> B: 130"

      val destinationAsIndex: Int =
        graph1.getIndex(testRouteQuery.destination)
      val resultPath = sp.flatMap(_.pathTo(destinationAsIndex))
      val resultDis = sp.flatMap(_.timeToV(destinationAsIndex))

      val output = buildRouteOutput(resultPath, resultDis)

      output shouldBe expectedOutput
    }
    "should return empty path if destination is source in output format" in {
      val expectedOutput =
        "destination is source, travel time = 0"

      val destinationAsIndex: Int =
        graph1.getIndex(testRouteQuery.source)

      val resultPath = sp.flatMap(_.pathTo(destinationAsIndex))
      val resultDis = sp.flatMap(_.timeToV(destinationAsIndex))

      val output = buildRouteOutput(resultPath, resultDis)
      output shouldBe expectedOutput
    }

    "when search for nearby stations from given source within certain time" - {
      "should return no nearby stations if no nearby stations" in {
        val expectedOutput = "C: 70, D: 120, B: 130"
        val resultNearByEither: Either[String, Seq[NearByVertex]] =
          ShortestPath
            .run(graph1, testNearByQuery.source)
            .map(_.nearbyTo(testNearByQuery.maxTime))

        val output = buildSortedNearByOutput(resultNearByEither)
        output shouldBe expectedOutput
      }
    }

  }

  "search in a graph2" - {
    val graph2 =
      EdgeWeightedDigraph().addEdges(testEdges2)

    "return optimal path in output format for graph2" in {

      val testRouteQuery2 = RouteQuery("BRANDENBURGERTOR", "B")

      val sp2: Either[String, ShortestPathCalc] =
        ShortestPath.run(graph2, testRouteQuery2.source)

      val expectedOutput = "BRANDENBURGERTOR -> B: 240"

      val destinationAsIndex: Int =
        graph2.getIndex(testRouteQuery2.destination)
      val resultPath = sp2.flatMap(_.pathTo(destinationAsIndex))
      val resultDis = sp2.flatMap(_.timeToV(destinationAsIndex))

      val output = buildRouteOutput(resultPath, resultDis)
      output shouldBe expectedOutput
    }
    "when search for nearby stations from given source within certain time in a graph" - {
      "should return no nearby stations if no nearby stations" in {
        val testNearByQuery2 = NearByQuery("B", 130)
        val expectedOutput = "No nearby stations"
        val resultNearByEither: Either[String, Seq[NearByVertex]] =
          ShortestPath
            .run(graph2, testNearByQuery2.source)
            .map(_.nearbyTo(testNearByQuery2.maxTime))

        val output = buildSortedNearByOutput(resultNearByEither)
        output shouldBe expectedOutput
      }
      "should return all expected nearby stations" in {
        val testNearByQuery2 = NearByQuery("E", 400)
        val expectedOutput = "A: 300, C: 370"
        val resultNearByEither: Either[String, Seq[NearByVertex]] =
          ShortestPath
            .run(graph2, testNearByQuery2.source)
            .map(_.nearbyTo(testNearByQuery2.maxTime))

        val output = buildSortedNearByOutput(resultNearByEither)
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

  val testEdges2 = Seq(
    Edge("BRANDENBURGERTOR", "B", 240),
    Edge("A", "C", 70),
    Edge("A", "D", 120),
    Edge("C", "B", 60),
    Edge("D", "E", 480),
    Edge("C", "E", 240),
    Edge("B", "E", 210),
    Edge("E", "A", 300)
  )

}
