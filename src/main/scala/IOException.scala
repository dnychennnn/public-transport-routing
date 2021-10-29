object IOException {
  sealed trait IOException extends Exception {
    def message: String
  }

  case class QueryException(queryType: String) extends IOException {
    def message = s"$queryType query is in wrong format."
  }
}
