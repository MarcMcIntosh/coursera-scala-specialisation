import coursera.factorial

import org.junit._
import org.junit.Assert.assertEquals


class factorialSuite {

  @Test def `factorial of 4 is 24`: Unit = {
    assertEquals(factorial(4), 24)
  }
}