import org.example.Camera
import org.example.are_similar
import org.example.Normal
import org.example.Point
import org.example.Vec
import org.example.Ray
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CameraTest {

    @Test
    fun testOrthogonalCamera():CameraTest {

        var cam = OrthogonalCamera(aspect_ratio=2.0f)

        var ray1 = cam.fire_ray(0.0f, 0.0f)
        var ray2 = cam.fire_ray(1.0f, 0.0f)
        var ray3 = cam.fire_ray(0.0f, 1.0f)
        var ray4 = cam.fire_ray(1.0f, 1.0f)

        //Verify that the rays are parallel by verifying that cross-products vanish
        assertTrue(are_close(0.0f, ray1.dir.prod(ray2.dir).squared_norm()))
        assertTrue(are_close(0.0f, ray1.dir.prod(ray3.dir).squared_norm()))
        assertTrue(are_close(0.0f, ray1.dir.prod(ray4.dir).squared_norm()))

        //Verify that the ray hitting the corners have the right coordinates
        assertTrue(ray1.at(1.0f).is_close(Point(0.0f, 2.0f, -1.0f)))
        assertTrue(ray2.at(1.0f).is_close(Point(0.0f, -2.0f, -1.0f)))
        assertTrue(ray3.at(1.0f).is_close(Point(0.0f, 2.0f, 1.0f)))
        assertTrue(ray4.at(1.0f).is_close(Point(0.0f, -2.0f, 1.0f)))

    }

    @Test
    fun testOrthogonalCamera_transform():CameraTest {

        var cam = OrthogonalCamera(transformation=translation(Vec(0f,-1f,0f) * 2.0f) * rotation(Vec(0f,0f,1f), 90))

        var ray = cam.fire_ray(0.5f, 0.5f)
        asserTrue(ray.at(1.0f).is_close(Point(0.0f, -2.0f, 0.0f)))

    }

    @Test
    fun testPerspectiveCamera():CameraTest {

    var cam = PerspectiveCamera(screen_distance=1.0f, aspect_ratio=2.0f)

    var ray1 = cam.fire_ray(0.0f, 0.0f)
    var ray2 = cam.fire_ray(1.0f, 0.0f)
    var ray3 = cam.fire_ray(0.0f, 1.0f)
    var ray4 = cam.fire_ray(1.0f, 1.0f)

    //Verify that all the rays depart from the same point
    assertTrue(ray1.origin.is_close(ray2.origin))
    assertTrue(ray1.origin.is_close(ray3.origin))
    assertTrue(ray1.origin.is_close(ray4.origin))

    //Verify that the ray hitting the corners have the right coordinates
    assertTrue(ray1.at(1.0f).is_close(Point(0.0f, 2.0f, -1.0f)))
    assertTrue(ray2.at(1.0f).is_close(Point(0.0f, -2.0f, -1.0f)))
    assertTrue(ray3.at(1.0f).is_close(Point(0.0f, 2.0f, 1.0f)))
    assertTrue(ray4.at(1.0f).is_close(Point(0.0f, -2.0f, 1.0f))

    }

    @Test
    fun testPerspectiveCamera_transform():CameraTest {

        var cam = PerspectiveCamera(transformation=translation(Vec(0f,-1f,0f) * 2.0f) * rotation(Vec(0f,0f,1f), 90))

        var ray = cam.fire_ray(0.5f, 0.5f)
        asserTrue(ray.at(1.0f).is_close(Point(0.0f, -2.0f, 0.0f)))

    }

}