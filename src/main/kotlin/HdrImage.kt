package org.example

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
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

class HdrImage(var width:Int = 0, var height:Int=0)
{
    /**
     * Create a matrix with dimensions (width, height)
     * of Color in RGB format (all in color black)
     *
     *
     */

    var pixels = Array<Color>(size = width * height) {Color()}

    // costruttore da file
    constructor(filename:String):this()
    {
        /**
         * upload image from a PFM file
         */
        var stream:InputStream=FileInputStream(File(filename))

        read_pfm_image(stream)

    }

    //costruttore da stream
    constructor(stream:InputStream):this()
    {
        read_pfm_image(stream)

    }

    fun read_pfm_image(stream: InputStream)
    {
        var magic=_read_line(stream)
        if(magic!="PF")
        {
            //throw
        }

        var img_size:String=_read_line(stream)
        var _width=_parse_img_size(img_size)[0]
        var _height=_parse_img_size(img_size)[1]

        var endianness_line:String=_read_line(stream)
        var endiannes: ByteOrder = _parse_endianness(endianness_line)

        //this=HdrImage(width=_width, height=_height)
    }

    fun _parse_endianness(line:String):ByteOrder
    {
        var value:Float=0f
        try {
            value=line.toFloat()
        }catch (e:Error)
        {

        }
        if(value>0)
        {
            return ByteOrder.BIG_ENDIAN
        }
        else if (value<0)
        {
            return ByteOrder.LITTLE_ENDIAN
        }
        else
        {
            //raise InvalidPfmFileFormat
        }

        // default
        return ByteOrder.BIG_ENDIAN
    }

    fun _parse_img_size(str:String):Array<Int>
    {
        var l = str.split(" ")

        if(l.size!=2)
        {
            //raise
        }

        var w:Int=0
        var h:Int=0
        try {
             w= l[0].toInt()
             h= l[1].toInt()

            if((w<0) or (h<0))
            {
                //throw
            }

        } catch (e: Exception) {}


        return arrayOf<Int>(w, h)
    }

    fun _read_line(stream: InputStream):String {

        var res:ByteBuffer=ByteBuffer.wrap("".toByteArray())

        var buff:ByteArray= ByteArray(1)
        while (true)
        {
            stream.read(buff)

            if ((buff == "".toByteArray()) or  (buff=="\n".toByteArray()))
            {
                return res.array().decodeToString()
            }

            res.put(buff[0].toByte())

        }

    }

    fun _read_float(stream: InputStream, endiannes:ByteOrder):Float
    {
        // dichiarare un array di 4 byte perchè le righe sono tutte a 32 bit
        var b:ByteArray=ByteArray(4)

        stream.read(b)

        var value:ByteBuffer=ByteBuffer.wrap(b)

        var res:Float=0f

        try {
            value.order(endiannes)
            res=value.float
        }catch (e:Exception)
        {
            print("")
        }
        return res

    }


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
        return ((x>=0) and (x<this.width) and (y>=0) and (y<this.height))
    }

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

}

