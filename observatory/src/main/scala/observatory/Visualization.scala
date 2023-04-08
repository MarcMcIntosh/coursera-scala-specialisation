package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import math.{Pi, abs, toRadians, asin, sqrt, pow, sin, cos}
/**
  * 2nd milestone: basic visualization
  */
object Visualization extends VisualizationInterface {

  val earthRadius = 6371 // kilometers

  val width = 360
  val height = 180

  def antipodes(a: Location, b: Location): Boolean = {
    (a.lat == -b.lat) && (math.abs(a.lon - b.lon) == 180)
  }


  def circleDistance(a: Location, b: Location): Double = {
    if(a == b) 0
    else if (antipodes(a, b)) {
      earthRadius * Pi
    } else {
      val delta_lon_rad = toRadians(math.abs(a.lon - b.lon))
      val a_lat_rad = toRadians(a.lat)
      val b_lat_rad = toRadians(b.lat)
      val delta_lat = math.abs(a_lat_rad - b_lat_rad)
      val delta_sigma =   2 * asin(sqrt(pow(sin(delta_lat/2), 2) + cos(a_lat_rad) * cos(b_lat_rad) * pow(sin(delta_lon_rad / 2), 2) ))
      earthRadius * delta_sigma
    }
  }

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    val distances: Iterable[(Double, Temperature)] = temperatures.map {
      case (coords: Location, temp: Temperature) => (circleDistance(location, coords), temp)
    }

    val (min_distance: Double, temperature: Temperature) = distances.reduce((a, b) => if(a._1 < b._1) a else b)

    if (min_distance < 1) {
      temperature
    } else {
        // interpolate
        val weights = distances.map {
          case (distance, temp) => (1 / pow(distance, 6), temp)
        }
        val normalizer = weights.map(_._1).sum

        weights.map(entry => entry._1 * entry._2).sum  / normalizer
    }
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */

  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color = {

    def interpolate(below: Iterable[(Temperature, Color)], above: Iterable[(Temperature, Color)]): Color = {

      if(above.isEmpty) {
        below.maxBy(_._1)._2
      } else if (below.isEmpty) {
        above.minBy(_._1)._2
      } else {
        linear(below.maxBy(_._1), above.minBy(_._1))
      }
    }

    def linear(a: (observatory.Temperature, Color), b: (observatory.Temperature, Color)): Color = {

      val weight_a = 1 / abs(a._1 - value)
      val weight_b = 1 / abs(b._1 - value)


      def calc(color_a: Int, color_b: Int): Int = {
        ((weight_a * color_a + weight_b * color_b) / (weight_a + weight_b)).round.toInt
      }

      val red = calc(a._2.red, b._2.red)
      val green = calc(a._2.green, b._2.green)
      val blue = calc(a._2.blue, b._2.blue)

      Color(red, green, blue)
    }

    val point: Option[(Temperature, Color)] = points.find(_._1 == value)

    val (smaller: Iterable[(Temperature, Color)], bigger: Iterable[(Temperature, Color)]) = {
      points.partition { case (temp: Temperature, _: Color) => temp < value }
    }


    point match {
      case Some((_, color)) => color
      case _ => interpolate(smaller, bigger)
    }
  }



  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {

    val coords: IndexedSeq[(Int, Int)] = for (x <- 0 until width; y <- 0 until height) yield (x, y)

    val pixels = coords.par
      .map(pixelsToCoords)
      .map(predictTemperature(temperatures, _))
      .map(interpolateColor(colors, _))
      .map(color => Pixel(color.red, color.green, color.blue, 255))
      .toArray

    Image(width, height, pixels)
  }

  def pixelsToCoords(coord: (Int, Int)): Location = {
    val lon = (coord._2 - width/2) * (360 / width)
    val lat = -(coord._1 - height/2) * (180 / height)
    Location(lat, lon)
  }

}

