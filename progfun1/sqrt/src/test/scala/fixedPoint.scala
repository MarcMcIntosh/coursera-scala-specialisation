import coursera.fixedPoint

import org.junit._
import org.junit.Assert.assertEquals




class fixedPoint {


  @Test def `fixedPoint(x => 1 + x/2)(1)`: Unit = {
    assertEquals(1.999755859375, fixedPoint(x => 1 + x/2)(1), 1e-12)
  }

  @Test def `sqrt`: Unit = {
    assertEquals(1.4142135623746899, fixedPoint.sqrt(2), 1e-12)
  }
}
