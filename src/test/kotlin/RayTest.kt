import org.example.Point
import org.example.Ray
import org.example.Vec
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RayTest {

    @Test
    fun test_is_close() {
        val ray1: Ray = Ray(origin = Point(1f, 2f, 3f), dir= Vec(5f, 4f, -1f))
        val ray2: Ray = Ray(origin = Point(1f, 2f, 3f), dir= Vec(5f, 4f, -1f))
        val ray3: Ray = Ray(origin = Point(5f, 1f, 4f), dir= Vec(3f, 9f, 4f))

        assertTrue(ray1.is_close(ray2))
        assertFalse(ray1.is_close(ray3))
    }

    @Test
    fun test_at() {
        val ray:Ray=Ray(origin = Point(1f, 2f, 4f), dir= Vec(4f,2f, 1f))

        assertTrue(ray.at(0f).is_close(ray.origin))
        assertTrue(ray.at(1f).is_close(Point(5f, 4f, 5f)))
        assertTrue(ray.at(2f).is_close(Point(9f, 6f, 6f)))

    }

    @Test
    fun test_transform() {

    }
}