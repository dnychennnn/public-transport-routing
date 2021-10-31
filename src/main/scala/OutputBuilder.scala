import InputModels.Edge
import dijkstra.NearByVertex

object OutputBuilder {
  def buildRouteOutput(
      resultPathEither: Either[String, Seq[Edge]],
      resultDistEither: Either[String, Int]
  ): String = {
    val output = for {
      resultPath <- resultPathEither
      resultDist <- resultDistEither
      resultOutput =
        if (!resultPath.isEmpty)
          resultPath(0).source + " -> " + resultPath
            .map(_.destination)
            .mkString(" -> ") + ": " + resultDist.toString
        else "destination is source, travel time = 0"
    } yield resultOutput
    output.right.get match {
      case ""     => "No route found"
      case result => result
    }
  }

  def buildSortedNearByOutput(
      resultNearByEither: Either[String, Seq[NearByVertex]]
  ) = {
    val output: Either[String, String] = for {
      resultNearBy <- resultNearByEither
      sortedResult = resultNearBy.sortBy(_.travelTime)
      resultOutput = sortedResult
        .map { nbv =>
          s"${nbv.vertex}: ${nbv.travelTime}"
        }
        .mkString(", ")
    } yield resultOutput
    output.right.get match {
      case ""     => "No nearby stations"
      case result => result
    }
  }
}
