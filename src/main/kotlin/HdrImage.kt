package org.example

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.stream.Stream

class HdrImage(val width:Int = 0, val height:Int=0)
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
        //if(magic!="PF")
        //{
        //}
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
        

        return ByteOrder.BIG_ENDIAN
    }

    fun _parse_img_size():Array<Int>
    {

        return  Array<Int>(1){0}
    }

    fun _read_line(stream: InputStream):ByteArray {
       ByteBuffer.
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
            print("Lore succhia i cazzzi al limone")
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

}