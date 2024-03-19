package org.example
import java.io.*
import java.nio.*

//class HdrImage(
//    val width: Int,  // Using 'val' ensures that we cannot change the width
//    val height: Int, // or the height of the image once it's been created
//    var pixels: Array<Color> = Array(width * height) { Color(0.0F, 0.0F, 0.0F) }
//) {
//    // Here are the methods for the class…


//Python example of methods in HdrImage
//def valid_coordinates(self, x: int, y: int) -> bool:
//    return ((x >= 0) and (x < self.width) and
//            (y >= 0) and (y < self.height))
//
//def pixel_offset(self, x: int, y: int) -> int:
//    return y * self.width + x
//
//def get_pixel(self, x: int, y: int) -> Color:
//    assert self.valid_coordinates(x, y)
//    return self.pixels[self.pixel_offset(x, y)]
//
//def set_pixel(self, x: int, y: int, new_color: Color):
//    assert self.valid_coordinates(x, y)
//    self.pixels[self.pixel_offset(x, y)] = new_color
/**
 * the HdrImage class take as argument 2 int variables, (0,0) by default:
 * width (width in n° of pixels the image),
 * height (height in n° of pixels the image),
 */
/*class HdrImage (val width: Int = 0,
                val height: Int = 0,
                var pixels: Array<Color> = Array(width * height) { Color(0.0F, 0.0F, 0.0F) })
{
   // fun valid_coordinates()
}

 */
class HdrImage(val witdth:Int = 0, val height:Int=0)
{
    /**
     * Create a matrix with dimensions (width, height)
     * of Color in RGB format (all in color black)
     *
     *
     */

    var pixels = Array<Color>(size = witdth * height) { Color() }

    fun get_pixel(x:Int, y:Int):Color
    {
        /**
         * Return the `Color` value for a pixel in the image
         *
         * The pixel at the top-left corner has coordinates (0, 0).
         */
        assert(this.valid_coordinates(x,y))
        return this.pixels[this.pixel_offset(x,y)]
    }

    fun set_pixel(x:Int, y:Int, new_color:Color)
    {
        /**Set the new color for a pixel in the image
         * The pixel at the top-left corner has coordinates (0, 0)
         */
        assert(this.valid_coordinates(x,y))
        this.pixels[this.pixel_offset(x,y)]=new_color
    }

    fun valid_coordinates(x:Int, y:Int):Boolean
    {
        return ((x>=0) and (x<this.witdth) and (y>=0) and (y<this.height))
    }

    fun pixel_offset(x:Int, y:Int):Int
    {
        return y*this.witdth+x
    }

}
