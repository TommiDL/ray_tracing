import org.example.Color
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ImageTracerTest
{
    @Test
    fun test_ImageTracer() {
        // Setup
        val image = HdrImage(width = 4, height = 4)
        val camera = PerspectiveCamera(aspect_ratio=2.0f)
        val imageTracer = ImageTracer(image, camera)


        val ray1 = imageTracer.fire_ray(0,0, u_pixel = 2.5, v_pixel = 1.5)
        val ray2 = imageTracer.fire_ray(2, 1, u_pixel = 0.5, v_pixel = 0.5)
        assert(ray1.is_close(ray2))

        tracer.fire_all_ray( {ray:Ray -> Color(1f,2f,3f) } )
        for (row in 0 until image.height) {
            for (col in 0 until image.width) {
                assert(image.getPixel(col, row) == Color(1f, 2f, 3f))
            }
        }
    }

    }

}