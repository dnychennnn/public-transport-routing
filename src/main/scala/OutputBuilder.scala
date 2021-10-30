import InputModels.Edge
import dijkstra.Helpers.mapIndexToAlphabet
import dijkstra.NearByVertex

object OutputBuilder {
  def buildRouteOutput(
      resultPathEither: Either[String, Seq[Edge]],
      resultDistEither: Either[String, Int]
  ): Either[String, String] = {
    for {
      resultPath <- resultPathEither
      resultDist <- resultDistEither
      resultOutput =
        if (!resultPath.isEmpty)
          resultPath(0).source + " -> " + resultPath
            .map(_.destination)
            .mkString(" -> ") + ": " + resultDist.toString
        else "destination is source, travel time = 0"
    } yield resultOutput
  }

  def buildSortedNearByOutput(
      resultNearByEither: Either[String, Seq[NearByVertex]]
  ) = {
    for {
      resultNearBy <- resultNearByEither
      sortedResult = resultNearBy.sortBy(_.travelTime)
      resultOutput = sortedResult
        .map { nbv =>
          s"${mapIndexToAlphabet(nbv.vertex)}: ${nbv.travelTime}"
        }
        .mkString(", ")
    } yield resultOutput
  }
}
