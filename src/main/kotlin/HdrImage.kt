package org.example

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.stream.Stream
import kotlin.math.pow
import kotlin.math.ln
import kotlin.math.log10


fun _clamp (x: Float): Float
{
    return x / (1 + x)
}

/**
 * Function to read a PFM file
 */
fun read_pfm_image(stream: InputStream):HdrImage
{

    var magic=_read_line(stream)
    if(magic!="PF")
    {
        throw InvalidPfmFileFormat("Invalid magic in PFM File")
    }


    var img_size:String=_read_line(stream)
    var _width=_parse_img_size(img_size)[0]
    var _height=_parse_img_size(img_size)[1]

    var endianness_line:String=_read_line(stream)
    var endiannes: ByteOrder = _parse_endianness(endianness_line)

    return HdrImage(width=_width, height=_height)
}



fun _parse_img_size(str:String):Array<Int>
{
    var l = str.split(" ")

    if(l.size!=2)
    {
        throw InvalidPfmFileFormat("invalid image size specification")
    }

    var w:Int=0
    var h:Int=0

    w= l[0].toInt()
    h= l[1].toInt()

    if((w<0) or (h<0))
    {
        throw InvalidPfmFileFormat("invalid width/height")
    }


    return arrayOf<Int>(w, h)
}

fun _read_line(stream: InputStream):String {

    var res:ByteArrayOutputStream=ByteArrayOutputStream()


    while (true)
    {
        var buff:Int=stream.read()

        if ((buff == -1) or  (buff=='\n'.code))
        {
            return res.toString("ASCII")
        }

        res.write(buff)
    }

}

/**
 * Read a float 32 bit from a binary file opened in a stream object
 */
fun _read_float(stream: InputStream, endiannes:ByteOrder):Float
{
    // dichiarare un array di 4 byte perchè le righe sono tutte a 32 bit
    var b:ByteArray=ByteArray(4)

    try {
        stream.read(b)
        var value:ByteBuffer=ByteBuffer.wrap(b)
        value.order(endiannes)
        return value.float

    }catch (e:Exception)
    {
        throw InvalidPfmFileFormat("impossible to read binary data from the file")
    }


}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Create a matrix with dimensions (width, height)
 * of Color in RGB format (all in color black)
 *
 *
 */
class HdrImage(val width:Int = 0, val height:Int=0)
{
    var pixels = Array<Color>(size = width * height) {Color()}

    /**
     * Return the `Color` value for a pixel in the image
     *
     * The pixel at the top-left corner has coordinates (0, 0).
     */
    fun get_pixel(x:Int, y:Int):Color
    {

        assert(this.valid_coordinates(x,y))
        return this.pixels[this.pixel_offset(x,y)]
    }


    /**
     * Set the new color for a pixel in the image
     * The pixel at the top-left corner has coordinates (0, 0)
     */
    fun set_pixel(x:Int, y:Int, new_color:Color)
    {

        assert(this.valid_coordinates(x,y))
        this.pixels[this.pixel_offset(x,y)]=new_color
    }


    /**
     * Check if the coordinates of the matrix are inside the boundaries
     */
    fun valid_coordinates(x:Int, y:Int):Boolean
    {
        return ((x>=0) and (x<this.width) and (y>=0) and (y<this.height))
    }

    /**
     * return the index of the array implementation for the matrix of colors
     */
    fun pixel_offset(x:Int, y:Int):Int
    {
        return y*this.width+x
    }

    /**
     * Returns the average of the image luminosity
     * Using logaritmic average
     */
    fun average_luminosity (delta: Double = 1e-10): Double
    {
        var cumsum:Double = 0.0

        for (pix in this.pixels) {
            cumsum += log10(delta + pix.luminosity())
        }

        return 10.0.pow(cumsum / this.pixels.size)
    }

    /**
     * Normalizes the image through average luminosity
     * Input: the value of alpha, the luminosity and the variable color of type Color
     */
    fun normalize_image (factor: Float, luminosity: Float? = null)
    {
        val lum = luminosity ?: this.average_luminosity(delta = 1e-10)
        for(i in this.pixels.indices)
            this.pixels[i] = (this.pixels[i] * (factor / lum.toFloat()))
    }

    /**
     * Correction for light sources through "_clamp" function
     * declared outside the class HdrImage
     */
    fun clamp_image ()
    {
        for(i in this.pixels.indices) {
            this.pixels[i].r = _clamp(this.pixels[i].r)
            this.pixels[i].g = _clamp(this.pixels[i].g)
            this.pixels[i].b = _clamp(this.pixels[i].b)
        }
    }

    fun _writeFloatToStream(stream: OutputStream, value: Float, order: ByteOrder) {
        val bytes = ByteBuffer.allocate(4).putFloat(value).array() // Big endian

        if (order == ByteOrder.LITTLE_ENDIAN) {
            bytes.reverse()
        }

        stream.write(bytes)
    }

    /**
     * writes a PFM file with a given output stream
     *
     * the color matrix is written with (0,0) in the bottom left corner
     */
    fun write_pfm_image(stream: OutputStream, endiannes:ByteOrder = ByteOrder.LITTLE_ENDIAN) {
        val endiannes_str:String = if (endiannes == ByteOrder.LITTLE_ENDIAN) "-1.0" else "1.0"

        //setting the header that must be printed on the top of the file
        val header:String = "PF\n${this.width} ${this.height}\n$endiannes_str\n"
        //converting the string type to tne ASCII
        try {
            stream.write(header.toByteArray())
        }catch (e:IOException){
            println("Stream Write Error:$e")
        }
        // the (0,0) pixel starts from the bottom left
        for (i in this.height-1 downTo 0){
            for(j in 0..this.width-1){
                val color:Color = this.get_pixel(j,i)
                _writeFloatToStream(stream, color.r, endiannes)
                _writeFloatToStream(stream, color.g, endiannes)
                _writeFloatToStream(stream, color.b, endiannes)

            }
        }
    }


}

