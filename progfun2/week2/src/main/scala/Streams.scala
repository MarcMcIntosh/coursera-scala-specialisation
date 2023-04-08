import scala.annotation.tailrec

object StreamRange {
  def apply(lo: Int, hi: Int): LazyList[Int] = {
    if(lo > hi) LazyList.empty
    else LazyList(lo) #:: StreamRange(lo + 1, hi)
  }
}

