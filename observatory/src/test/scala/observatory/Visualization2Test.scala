package observatory

import org.junit.Test

trait Visualization2Test extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("value-added information visualization", 5) _

  // Implement tests for methods of the `Visualization2` object

  @Test def `bilinearInterpolation`: Unit = {
    val point = CellPoint(0.5, 0.5)
    assert(Visualization2.bilinearInterpolation(point, 10, 20, 30, 40) == 25.0)
  }

}
