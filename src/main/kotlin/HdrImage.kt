package org.example

class HdrImage(val witdth:Int = 0, val height:Int=0)
{
    /**
     * Create a matrix with dimensions (width, height)
     * of Color in RGB format (all in color black)
     *
     *
     */

    val pixels = Array<Color>(size = witdth * height) { Color() }

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