package observatory

import org.junit.Assert._
import org.junit.Test

trait VisualizationTest extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("raw data display", 2) _

  // Implement tests for the methods of the `Visualization` object

  @Test def `antipodes test`: Unit = {
    val a = Location(5, 0)
    val b = Location(-5, 180)
    val c = Location(-5, -180)

    assert(Visualization.antipodes(a, b), "a and b are antipodes")
    assert(Visualization.antipodes(a, c), "a and c are antipodes")
    assert(Visualization.antipodes(b, c) == false, "b and c are not antipodes")
  }

  @Test def `circleDistance test`: Unit = {
    val a = Location(8, 8)
    val b = Location(64, 130)
    assert(Visualization.circleDistance(a, a) == 0, "Should be equal")
    assert(Visualization.circleDistance(a, b) > 0, "should be more than 0")
  }

  @Test def `predictTemperatures test`: Unit = {
    val temps = List[(Location, Temperature)]((Location(1, 1), 0),(Location(-1, 1), 2))

    assert(Visualization.predictTemperature(temps, Location(1, 1)) == 0)
    assert(Visualization.predictTemperature(temps, Location(0, 1)) == 1)
    assert(Visualization.predictTemperature(temps, Location(-1, 1)) > 1)
  }

  @Test def `interpolateColor test`: Unit = {

    val colours_1 = List[(Temperature, Color)]((1, Color(2, 2, 2)))
    val value_1 = 2

    assert(Visualization.interpolateColor(colours_1, value_1) == Color(2,2,2))

    val colours_2 = List[(Temperature, Color)]((1, Color(2, 2, 2)), (-1, Color(4, 4, 4)))
    val value_2 = 1

    assert(Visualization.interpolateColor(colours_2, value_2) == Color(2,2,2))

    val value_3 = 0
    assert(Visualization.interpolateColor(colours_2, value_3) == Color(3,3,3))


    /* [Observed Error]
    Incorrect predicted color: Color(128,0,128).
    Expected: Color(191,0,64)
    (scale = List((-1.0,Color(255,0,0)), (0.0,Color(0,0,255))), value = -0.75)
     */
    val test_1 = Visualization.interpolateColor(List((-1.0,Color(255,0,0)), (0.0,Color(0,0,255))), value = -0.75)
    val expected = Color(191, 0, 64)
    assert(test_1 == expected, s"epxect: $expected\trecived: $test_1")
  }
}
