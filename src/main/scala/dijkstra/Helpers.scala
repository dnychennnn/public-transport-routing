package dijkstra

object Helpers {

  def mapAlphabetToIndex(alphabet: String) = alphabet.charAt(0) - 'A'

  def mapIndexToAlphabet(index: Int): String = if (index >= 0 && index < 26)
    String.valueOf((index + 'A').asInstanceOf[Char])
  else null

}
