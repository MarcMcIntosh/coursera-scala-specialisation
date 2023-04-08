trait Generator[+T] {
  self =>

  def generate: T

  def map[S](f:  T => S): Generator[S] = {
    new Generator[S] {
      override def generate = f(self.generate)
    }
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = {
    new Generator[S] {
      override def generate = f(self.generate).generate
    }
  }
}

val integers = new Generator[Int] {
  val rand = new java.util.Random
  override def generate = rand.nextInt()
}

val booleans = for (x <- integers) yield x > 0

trait Tree
case class Inner(left: Tree, right: Tree)
case class Leaf(x: Int)

/*
val branch = new Generator[Tree] {
  override def generate: Tree = if(booleans.generate) Inner(left = branch, right = branch) else Leaf(x = integers.generate)
}*/

def leafs: Generator[Leaf] = for {
  x <- integers
} yield Leaf(x)

def inners: Generator[Inner] = for {
  l <- trees
  r <- trees
} yield Inner(l, r)

def trees: Generator[Tree] = for { 
  isLeaf <- booleans
  tree <- if(isLeaf) leafs else inners
} yield tree

trees.generate