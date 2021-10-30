package dijkstra

import Helpers.{mapAlphabetToIndex, mapIndexToAlphabet}
import InputModels.Edge

import scala.collection.mutable

//
// reference: https://medium.com/se-notes-by-alexey-novakov/algorithms-in-scala-dijkstra-shortest-path-78c4291dd8ab
//

object ShortestPath {
  def run(
      g: EdgeWeightedDigraph,
      sourceV: String
  ): Either[String, ShortestPathCalc] = {
    val size = g.adj.size

    val sourceVIndex = mapAlphabetToIndex(sourceV)

    if (sourceVIndex > size)
      Left(s"Source vertex doesn't exist in the graph")
    else {
      val edgeTo = mutable.ArrayBuffer.fill[Option[Edge]](size)(None)
      val distTo = mutable.ArrayBuffer.fill(size)(Int.MaxValue)

      //init source distance and add to the queue
      distTo(sourceVIndex) = 0
      val sourceDist = (sourceVIndex, distTo(sourceVIndex))
      val sortByWeight: Ordering[(Int, Int)] = (a, b) => a._2.compareTo(b._2)
      val queue = mutable.PriorityQueue[(Int, Int)](sourceDist)(sortByWeight)

      while (queue.nonEmpty) {
        val (minDestV, _) = queue.dequeue()
        val minDestVAsAlphabet = mapIndexToAlphabet(minDestV)
        val edges = g.adj.getOrElse(minDestVAsAlphabet, List.empty)

        edges.foreach { e =>
          {
            val destinationAsIndex = mapAlphabetToIndex(e.destination)
            val sourceAsIndex = mapAlphabetToIndex(e.source)
            if (distTo(destinationAsIndex) > distTo(sourceAsIndex) + e.time) {
              distTo(destinationAsIndex) = distTo(sourceAsIndex) + e.time
              edgeTo(destinationAsIndex) = Some(e)
              if (!queue.exists(_._1 == destinationAsIndex))
                queue.enqueue((destinationAsIndex, distTo(destinationAsIndex)))
            }
          }

        }
      }

      Right(new ShortestPathCalc(edgeTo.toSeq, distTo.toSeq))
    }
  }
}

import scala.annotation.tailrec

class ShortestPathCalc(edgeTo: Seq[Option[Edge]], distTo: Seq[Int]) {

  /** @param v vertex to get the path for
    * @return returns error when v is invalid or sequence of edges
    * which form the path from source vertex to v vertex
    */
  def pathTo(v: Int): Either[String, Seq[Edge]] = {

    @tailrec
    def go(list: List[Edge], vv: Int): List[Edge] =
      edgeTo(vv) match {
        case Some(e) => go(e +: list, mapAlphabetToIndex(e.source))
        case None    => list
      }

    hasPath(v).map(b => if (!b) Seq() else go(List(), v))
  }

  /** @param v vertex to check whether path from source vertex to v vertex exists
    * @return returns error when v is invalid or Boolean
    * when some path from source vertex to vertex v exists
    */
  def hasPath(v: Int): Either[String, Boolean] =
    distTo
      .lift(v)
      .map(_ < Double.PositiveInfinity)
      .toRight(s"Vertex $v does not exist")

  /** @param v vertex to get the distance for
    * @return returns error when v is invalid or
    * the Double distance which is a sum of weights
    */
  def distToV(v: Int): Either[String, Int] =
    distTo.lift(v).toRight(s"Vertex $v does not exist")
}