package org.example

import java.awt.image.BufferedImage
import java.io.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.imageio.ImageIO
import kotlin.math.log10
import kotlin.math.pow


fun _clamp (x: Float): Float
{
    return x / (1 + x)
}

/**
 * Function to read a PFM file
 */
fun read_pfm_image(stream: InputStream):HdrImage
{

    val magic=_read_line(stream)
    if(magic!="PF")
    {
        throw InvalidPfmFileFormat("Invalid magic in PFM File")
    }


    val img_size:String=_read_line(stream)
    val _width=_parse_img_size(img_size)[0]
    val _height=_parse_img_size(img_size)[1]

    val endianness_line:String=_read_line(stream)
    val endiannes: ByteOrder = _parse_endianness(endianness_line)

    val img:HdrImage = HdrImage(width=_width, height=_height)

    for (i in img.height-1 downTo 0){
        for(j in 0..img.width-1){
            val r:Float= _read_float(stream, endiannes)
            val g:Float= _read_float(stream, endiannes)
            val b:Float= _read_float(stream, endiannes)

            img.set_pixel(j,i, Color(r,g,b))

        }
    }


    return img


}

/**
 * read the endianness from PFM file
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
    val l = str.split(" ")

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

    val res:ByteArrayOutputStream=ByteArrayOutputStream()


    while (true)
    {
        val buff:Int=stream.read()

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
    val b:ByteArray=ByteArray(4)

    try {
        stream.read(b)
        val value:ByteBuffer=ByteBuffer.wrap(b)
        value.order(endiannes)
        return value.float

    }catch (e:Exception)
    {
        throw InvalidPfmFileFormat("impossible to read binary data from the file")
    }


}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun pfm_from_png(stream: InputStream, path:String="conversion.pfm", return_img:Boolean=false, parameters: Parameters= Parameters()):HdrImage?
{
    val png=ImageIO.read(stream)


    val img:HdrImage=HdrImage()

    for(col in 0 until png.width)
    {
        for (row in 0 downTo  png.height-1)
        {
            val color=png.getRGB(col, row)

            val blue: Float = (color and 0xff).toFloat()
            val green: Float = ((color and 0xff00) shr 8).toFloat()
            val red: Float = ((color and 0xff0000) shr 16).toFloat()
            img.set_pixel(col, row, Color(r=red, g=green, b=blue))
        }
    }

    img.pixels.forEach { it ->
        it.r *=img.average_luminosity().toFloat()/parameters.factor
        it.g *=img.average_luminosity().toFloat()/parameters.factor
        it.b *=img.average_luminosity().toFloat()/parameters.factor

        it.r*=1/255f
        it.g*=1/255f
        it.b*=1/255f

        it.r=it.r.pow(parameters.gamma)
        it.g=it.g.pow(parameters.gamma)
        it.b=it.b.pow(parameters.gamma)

    }


    img.write_pfm_image(stream=FileOutputStream(path))

    if(return_img)
        return img
    return null
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

    constructor(width: Int, height: Int, pixels:Array<Color>):this(width = width, height=height)
    {
        this.pixels=pixels
    }

    /**
     * Return the `Color` value for a pixel in the image
     *
     * The pixel in the top-left corner has coordinates (0, 0).
     */
    fun get_pixel(x:Int, y:Int):Color
    {

        assert(this.valid_coordinates(x,y))
        return this.pixels[this.pixel_offset(x,y)]
    }


    /**
     * Set the new color for a pixel in the image
     * The pixel in the top-left corner has coordinates (0, 0)
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

        println("Writing pfm image")
        print("[")

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
            if (i%10==0) print("#")
        }
        print("]")
        println()
    }

    /**
     * generate a ldr image from a Stream of Output
     * with the selected format
     */
    fun write_ldr_image(stream: OutputStream, format:String, gamma:Float=1.0f)
    {
        println("Writing LDR image in format $format")
        print("[")
        val img:BufferedImage = BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB)

        for (y in 0 until this.height) {
            for (x in 0 until this.width) {
                // 0xFF0000 corresponds to sRGB(255, 0, 0):
                val color:Color=this.pixels[pixel_offset(x,y)]


                val conv:Int = (255*(color.r.pow(1/gamma))).toInt() * 65536 + (255*(color.g.pow(1/gamma))).toInt() * 256 + (255*(color.b.pow(1/gamma))).toInt()
                img.setRGB(x, y, conv)
            }
            if (y%10==0) print("#")
        }
        ImageIO.write(img, format, stream)
        print("]")
        println()
    }




}







