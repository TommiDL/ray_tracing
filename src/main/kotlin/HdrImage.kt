package org.example

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.stream.Stream

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

/**
 * read the endianness from
 */
fun _parse_endianness(line:String):ByteOrder
{
    var value:Float=0f
    try {
        value=line.toFloat()
    }catch (e:Exception)
    {
        throw InvalidPfmFileFormat("missing endianness specification")
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
        throw InvalidPfmFileFormat("invalid endianness specification, it cannot be zero")
    }
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


    fun pixel_offset(x:Int, y:Int):Int
    {
        return y*this.width+x
    }

}
