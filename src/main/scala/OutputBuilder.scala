import InputModels.Edge

object OutputBuilder {
  def buildOutput(
      resultPathEither: Either[String, Seq[Edge]],
      resultDistEither: Either[String, Int]
  ): Either[String, String] = {
    for {
      resultPath <- resultPathEither
      resultDist <- resultDistEither
      resultOutput = resultPath(0).source + " -> " + resultPath
        .map(_.destination)
        .mkString(" -> ") + ": " + resultDist.toString
    } yield resultOutput
  }

}
