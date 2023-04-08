import coursera.hof._

import org.junit._
import org.junit.Assert.assertEquals


class hof {

  @Test def `product(sqr)(3, 7)`: Unit = {
    assertEquals(6350400, product(sqr)(3, 7))
  }

  @Test def `product(sqr)(3, 4)`: Unit = {
    assertEquals(144, product(sqr)(3, 4))
  }

  @Test def `fact(5)`: Unit = {
    assertEquals(120, fact(5))
  }
}
