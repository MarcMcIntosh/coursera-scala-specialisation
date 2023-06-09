
object nQueens {
  def apply(n: Int): Set[List[Int]] = {

    def isSafe(col: Int, queens: List[Int]): Boolean = {
      val row = queens.length
      val queensWithRow = (row - 1 to 0 by -1).zip(queens)
      queensWithRow.forall {
        case (r, c) => col != c && math.abs(col - c) != row - r
      }
    }

    def placeQueens(k: Int): Set[List[Int]] = {
      if(k == 0) Set(List())
      else {
        for {
          queens <- placeQueens(k - 1)
          col <- (0 until n)
          if isSafe(col, queens)
        } yield col :: queens
      }
    }
  }
}

nQueens(4)