package observatory

import com.sksamuel.scrimage.{Image, Pixel}

/**
  * 5th milestone: value-added information visualization
  */
object Visualization2 extends Visualization2Interface {

  /**
    * @param point (x, y) coordinates of a point in the grid cell
    * @param d00 Top-left value
    * @param d01 Bottom-left value
    * @param d10 Top-right value
    * @param d11 Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values, using bilinear interpolation
    *         See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(
    point: CellPoint,
    d00: Temperature,
    d01: Temperature,
    d10: Temperature,
    d11: Temperature
  ): Temperature = {
    val x = point.x
    val y = point.y

    d00 * (1 - x) * (1 - y) + d10 * x * (1 - y) + d01 * (1 - x) * y + d11 * x  * y
  }

  def pixelLocations(offsetX: Int, offsetY: Int, zoom: Int, imageWidth: Int, imageHeight: Int): IndexedSeq[(Int, Location)] = {
    for{
      xPixel <- 0 until imageWidth
      yPixel <- 0 until imageHeight
    } yield {
      val tile = Tile(xPixel / imageWidth + offsetX, yPixel / imageHeight + offsetY, zoom)
      val location = Interaction.tileLocation(tile)
      xPixel + yPixel * imageWidth -> location
    }
  }

  def interpolate(grid: GridLocation => Temperature,
                  loc: Location): Temperature = {
    val lat = loc.lat.toInt
    val lon = loc.lon.toInt
    val pt00 = GridLocation(lat, lon)
    val pt01 = GridLocation(lat + 1, lon)
    val pt10 = GridLocation(lat, lon + 1)
    val pt11 = GridLocation(lat + 1, lon + 1)
    val point = CellPoint(loc.lon - lon, loc.lat - lat)
    bilinearInterpolation(point, grid(pt00), grid(pt01), grid(pt10), grid(pt11))
  }


  val power = 8
  val height = 256
  val width = 256
  val alpha = 256


  /**
    * @param grid Grid to visualize
    * @param colors Color scale to use
    * @param tile Tile coordinates to visualize
    * @return The image of the tile at (x, y, zoom) showing the grid using the given color scale
    */
  def visualizeGrid(
    grid: GridLocation => Temperature,
    colors: Iterable[(Temperature, Color)],
    tile: Tile
  ): Image = {

    val offX: Int = (tile.x * math.pow(2, power)).toInt
    val offY: Int = (tile.y * math.pow(2, power)).toInt
    val offZ = tile.zoom
    val coords = for {
      i <- 0 until height
      j <- 0 until width
    } yield (i, j)

    val pixels = coords.par
      .map({case (y, x) => Tile(x + offX, y + offY, power + offZ)})
      .map(Interaction.tileLocation)
      .map(interpolate(grid, _))
      .map(Visualization.interpolateColor(colors, _))
      .map(col => Pixel(col.red, col.green, col.blue, alpha))
      .toArray

    Image(width, height, pixels)

  }

}
