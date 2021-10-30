object IOException {
  sealed trait IOException extends Exception {
    def message: String
  }

  case class QueryException(queryType: String) extends IOException {
    def message = s"$queryType query is in wrong format."
  }

  case class InputEdgesException(n: Int) extends IOException {
    def message =
      s"The input edges are either in wrong format or not aligned with the provided count $n."
  }
}
