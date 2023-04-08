package quickcheck

import common._
import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

import scala.annotation.tailrec

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    elem <- arbitrary[Int]
    heap <- oneOf(const(empty), genHeap)
  } yield insert(elem, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)


  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  lazy val genEmptyHeap: Gen[H] =  for { heap <- const(empty) } yield heap

  property("isEmpty") = forAll(genEmptyHeap) { (heap: H) =>
    isEmpty(empty)
  }

  property("If you insert any two elements into an empty heap, finding the minimum of the resulting heap should get the smallest of the two elements back.") = {
    forAll { (x: Int, y: Int) =>
      val heap = insert(x, insert(y, empty))
      findMin(heap) == Math.min(x, y)
    }
  }

  property("If you insert an element into an empty heap, then delete the minimum, the resulting heap should be empty.") = {
    forAll { (x: Int) =>
      val heap = deleteMin(insert(x, empty))
      isEmpty(heap)
    }
  }

  lazy val genNonEmptyHeap: Gen[H] = for {
    heap <- arbitrary[H]
    elem <- arbitrary[Int]
  } yield if (isEmpty(heap)) insert(elem, heap) else heap

  property("genNonEmptyHeap") = forAll(genNonEmptyHeap) { heap: H => !isEmpty(heap) }

  property("Given any heap, you should get a sorted sequence of elements when continually finding and deleting minima. (Hint: recursion and helper functions are your friends.)") = {
    forAll(genNonEmptyHeap) {
      (heap: H) => {
        def iter(h: H, acc: List[Int]): List[Int] = if(isEmpty(h)) acc else findMin(h) :: iter(deleteMin(h), acc)

        val xs = iter(heap, Nil)
        xs == xs.sorted
      }
    }
  }

  property("Finding a minimum of the melding of any two heaps should return a minimum of one or the other.") = {
    forAll(genNonEmptyHeap, genNonEmptyHeap) {
      (heap1, heap2) => {
        val min1 = findMin(heap1)
        val min2 = findMin(heap2)
        val min = if(min1 < min2) min1 else min2
        val heap = meld(heap1, heap2)
        findMin(heap) == min
      }
    }
  }

  property("the melding of two heaps with the same content should always be the say regardless of which heap the content was in.") = {
    forAll(genNonEmptyHeap, genNonEmptyHeap) {
      (heap1, heap2) => {
        def iter(h: H, acc: List[Int]): List[Int] = {
          if(isEmpty(h)) acc
          else findMin(h) :: iter(deleteMin(h), acc)
        }
        val merge1 = meld(heap1, heap2)
        val min = findMin(heap1)
        val xs1 = iter(merge1, Nil)
        val xs2 = iter(meld(deleteMin(heap1), insert(min, heap2)), Nil)
        xs1 == xs2
      }
    }
  }


}
