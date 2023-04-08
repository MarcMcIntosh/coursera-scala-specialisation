import coursera.sqrt

import org.junit._
import org.junit.Assert.assertEquals


class SqrtSuite {

  @Test def `square root of 4 is 2`: Unit = {
    assertEquals(sqrt(4), 2, 0.001)
  }

  @Test def `square root of 9 is 3`: Unit = {
    assertEquals(sqrt(9), 3, 0.001)
  }

  @Test def `square root of 4096 is 64`: Unit = {
    assertEquals(sqrt(4096), 64, 0.001)
  }
/**
  *  Breaks stuff :)
  *  @Test def `square root of -1 is ???`: Unit = {
  *    assertEquals(Sqrt(-1), 1, 0.001)
  *  }
**/

  @Test def `square root of 1e-6 is roughly 0.001`: Unit = {
    assertEquals(sqrt(1e-6), 0.001, 0.001)
  }

}
