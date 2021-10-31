package dijkstra

import InputModels.Edge

final case class EdgeWeightedDigraph(adj: Map[String, List[Edge]] = Map.empty)

object EdgeWeightedDigraphOps {
  implicit class EdgeWeightedDigraphOps(g: EdgeWeightedDigraph) {
    def addEdges(
        edges: Seq[Edge]
    ): EdgeWeightedDigraph = {
      var list = List.empty[Edge]
      var adj = Map.empty[String, List[Edge]]
      edges.foreach { e =>
        list = adj.getOrElse(e.source, List.empty)
        adj = adj + (e.source -> (list :+ e))
      }
      EdgeWeightedDigraph(adj)
    }
  }
}
