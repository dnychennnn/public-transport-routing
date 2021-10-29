import IOException.QueryException
import InputModels.{Edge, NearByQuery, RouteQuery}

import scala.io.StdIn.readLine

object InputReader {

  def readInput() = {
    val numberOfEdges: Int = readLine() match {
      case n => n.toInt
    }
    val edges = readEdges(numberOfEdges)
    val routeQuery = readRouteQuery()
    val nearByQuery = readNearByQuery()

    (edges, routeQuery, nearByQuery)
  }

  private def readEdges(n: Int): Seq[Edge] = {
    val edges =
      for (_ <- 1 to n)
        yield readLine().split("[ :]+").filterNot(_ == "->").toSeq match {
          case Seq(soruce, destination, time) =>
            Edge(soruce, destination, time.toInt)
        }
    edges
  }

  private def readRouteQuery() = {
    val queryType = "route"
    val routeInput = readLine().split(" ").filterNot(_ == "->")
    if (routeInput(0) == queryType) {
      routeInput.filterNot(_ == "route").toSeq match {
        case Seq(source, destination) => RouteQuery(source, destination)
      }
    } else
      throw QueryException(queryType)
  }

  private def readNearByQuery() = {
    val queryType = "nearby"
    val nearbyInput = readLine().split("[ ,]+")
    if (nearbyInput(0) == queryType) {
      nearbyInput.filterNot(_ == queryType).toSeq match {
        case Seq(destination, maxTime) =>
          NearByQuery(destination, maxTime.toInt)
      }
    } else
      throw QueryException(queryType)
  }
}
