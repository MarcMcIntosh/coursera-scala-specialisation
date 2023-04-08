import coursera.sum

import org.junit._
import org.junit.Assert.assertEquals

class SumSuite {
  @Test def `sum id`: Unit = {
    assertEquals(sum.spec(sum.id)(1, 1), sum(sum.id)(1, 1))
  }

  @Test def `sum cube`: Unit = {
    assertEquals(sum.spec(sum.cube)(3, 4), sum(sum.cube)(3, 4))
  }

  @Test def `sum fact`: Unit = {
    assertEquals(sum.spec(sum.fact)(3, 4), sum(sum.fact)(3, 4))
  }
}