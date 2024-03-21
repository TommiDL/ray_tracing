import org.example.Color
import org.example.HdrImage
import org.example._clamp
import org.example.are_similar
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class HdrImageTest {

    @Test
    fun img_creation()
    {
        val img:HdrImage=HdrImage(7,4)

        assertTrue(img.width==7)
        assertTrue(img.height==4)
    }

    @Test
    fun getPixels() {
    }


    @Test
    fun get_set_pixel() {
        val img: HdrImage = HdrImage(7,4)
        val reference_color:Color = Color(1.0f, 2.0f, 3.0f)
        img.set_pixel(3, 2, reference_color)

        assertTrue(reference_color.are_similar_colors(img.get_pixel(3, 2)))
    }

    @Test
    fun valid_coordinates() {
        val img: HdrImage = HdrImage(7,4)

        assertTrue(img.valid_coordinates(0, 0))
        assertTrue(img.valid_coordinates(6, 3))
        assertFalse(img.valid_coordinates(-1, 0))
        assertFalse(img.valid_coordinates(0, -1))
        assertFalse(img.valid_coordinates(7, 0))
        assertFalse(img.valid_coordinates(0, 4))
    }

    @Test
    fun pixel_offset() {
        val img: HdrImage = HdrImage(7,4)

        assertTrue(img.pixel_offset(3,2) == 17)
        assertTrue(img.pixel_offset(6,3) == 7*4-1)
    }

    @Test
    fun _clamp() {
        val x:Float=10.7f
        assertTrue((_clamp(x)<=1) and (_clamp(x)>=0))
    }
    @Test
    fun average_luminosity(){
        val img:HdrImage= HdrImage(2,1)
        img.set_pixel(0,0, Color(5.0f, 10.0f, 15.0f)) //lum = 10.0
        img.set_pixel(1,0, Color(500.0f, 1000.0f, 1500.0f)) //lum = 100.0

        println(img.average_luminosity(delta = 0.0))

        assertTrue(are_similar(100.0f, img.average_luminosity(delta = 0.0).toFloat()))
    }

    @Test
    fun normalize_image() {
        val img:HdrImage=HdrImage(2,1)

        img.set_pixel(0,0,Color(5.0f, 10.0f, 15.0f))
        img.set_pixel(1,0,Color(500.0f, 1000.0f, 1500.0f))

        img.normalize_image(factor = 1000.0f, luminosity = 100.0f)

        assertTrue(img.get_pixel(0,0).are_similar_colors(Color(0.5e2f, 1.0e2f, 1.5e2f)))
        assertTrue(img.get_pixel(1,0).are_similar_colors(Color(0.5e4f, 1.0e4f, 1.5e4f)))
    }

    @Test
    fun clamp_image() {
        val img:HdrImage=HdrImage(2,1)

        img.set_pixel(0,0, Color(0.5e1f, 1.0e1f, 1.5e1f))
        img.set_pixel(1,0, Color(0.5e3f, 1.0e3f, 1.5e3f))

        img.clamp_image()

        for( pix in img.pixels)
        {
            assertTrue((pix.r>=0) and (pix.r<=1))
            assertTrue((pix.g>=0) and (pix.g<=1))
            assertTrue((pix.b>=0) and (pix.b<=1))
        }

    }
}