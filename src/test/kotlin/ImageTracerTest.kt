import org.example.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ImageTracerTest
{
    //setup
    val image = HdrImage(width = 4, height = 2)
    val camera = PerspectiveCamera(aspect_ratio=2.0f)
    val imageTracer = ImageTracer(image, camera)
    @Test
    fun test_Orientation() {
        //Fire a ray against top-left corner of the screen
        val top_left_ray = imageTracer.fire_ray(0, 0, u_pixel = 0f, v_pixel = 0f)
        assert(Point(0f, 2f, 1f).is_close(top_left_ray.at(1f)))

        //Fire a ray against bottom-right corner of the screen
        val bottom_right_ray = imageTracer.fire_ray(3, 1, u_pixel = 1f, v_pixel = 1f)
        assert(Point(0f, -2f, -1f).is_close(bottom_right_ray.at(1f)))

    }
    @Test
    fun test_uv_sub_mapping(){
        //  Here we're cheating: we are asking `ImageTracer.fire_ray` to fire one ray *outside*
        //  the pixel we're specifying
        val ray1 = imageTracer.fire_ray(0, 0, u_pixel = 2.5f, v_pixel = 1.5f)
        val ray2 = imageTracer.fire_ray(2, 1, u_pixel = 0.5f, v_pixel = 0.5f)
        assert(ray1.is_close(ray2))
    }

    @Test
    fun test_image_coverage(){

    val colorLambda: (Ray) -> Color = {ray: Ray -> Color(1f, 2f, 3f)  }
        //set all pixels with constant Color(1.0f,2.0f,3.0f)
        imageTracer.fire_all_ray(colorLambda)
        for (row in 0 until image.height){
            for (col in 0 until image.width){
                assert(image.get_pixel(col, row) == Color(1f, 2f ,3f))
            }
        }

    }

}