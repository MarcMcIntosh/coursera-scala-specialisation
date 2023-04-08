import scala.collection.parallel.CollectionConverters._

def initializeArray(xs: Array[Int])(v: Int): Unit = {
  for(i <- xs.indices.par) {
    xs(i) = v
  }
}
