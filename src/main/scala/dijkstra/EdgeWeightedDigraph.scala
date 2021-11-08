package dijkstra

import InputModels.Edge

final case class EdgeWeightedDigraph(adj: Map[String, List[Edge]] = Map.empty) {
  //TODO: this is wrong, fix it
  val v2ilookup: Seq[String] = adj.keys.toSeq

  def getIndex(vertex: String): Int = {
    if (v2ilookup.contains(vertex)) v2ilookup.indexOf(vertex)
    else throw new Exception("Given vertex doesn't exist in the graph.")
  }

  def getVertex(index: Int) = {
    v2ilookup(index)
  }
}

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
