
import org.example.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class TriangleTest {

    @Test
    fun test_get_point() {

        val beta:Float = 0.5f
        val gamma:Float = 0.5f
        val triangle:Triangle = Triangle()
        val point:Point =  Point(0f, 0.5f, 0.5f)
        val point1:Point =  Point(1f, 1f, 1f)

        assertTrue(triangle.get_point(beta, gamma).is_close(point))
        assertFalse(triangle.get_point(beta, gamma).is_close(point1))

        assertTrue(triangle(beta, gamma).is_close(point))
        assertFalse(triangle(beta, gamma).is_close(point1))


    }

    @Test
    fun testDet() {

        val a:Triangle = Triangle()
        val b:Triangle = Triangle()

        assertTrue(are_similar(a.det(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f), 1f))
        assertFalse(are_similar(b.det(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f), 1f))
        assertTrue(are_similar(b.det(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f), 0f))

    }

    @Test
    fun testRayIntersection(){

        var origin:Point = Point(0f, 0f, 0f)
        var dir:Vec = Vec(0f, 0f, -1f)
        var ray:Ray = Ray(origin, dir)
        var triangle:Triangle = Triangle(Point(1f, 0f, 0f), Point(0f, 1f, 0f), Point(0f, 0f, 0f))

        var beta:Float = 0f
        var gamma:Float = 1f
        var t:Float = 0f
        var vec2d:Vec2D = Vec2D(beta, gamma)
        var hit_point:Point = triangle.get_point(beta, gamma)
        var normal:Normal = Normal(0f, 0f, 1f)

        var hit_record:HitRecord = HitRecord(hit_point, normal, vec2d,  t, ray)

        assertTrue(triangle.ray_intersection(ray)!!.is_close(hit_record))

        origin = Point(0f, 0f, 0f)
        dir = Vec(-1f, 0f, 0f)
        ray = Ray(origin, dir)
        triangle = Triangle(Point(0f, 0f, 0f), Point(0f, 1f, 0f), Point(0f, 0f, 1f))

        beta = 0f               // TO BE FIXED
        gamma = 1f              // TO BE FIXED
        t = 0f                  // TO BE FIXED
        vec2d = Vec2D(beta, gamma)
        hit_point = triangle.get_point(beta, gamma)
        normal = Normal(1f, 0f, 0f)

        hit_record = HitRecord(hit_point, normal, vec2d,  t, ray)

        assertTrue(triangle.ray_intersection(ray)!!.is_close(hit_record))

        origin = Point(0f, 0f, 0f)
        dir = Vec(0f, -1f, 0f)
        ray = Ray(origin, dir)
        triangle = Triangle(Point(1f, 0f, 0f), Point(0f, 0f, 0f), Point(0f, 0f, 1f))

        beta = 0f           // TO BE FIXED
        gamma = 1f          // TO BE FIXED
        t = 0f              // TO BE FIXED
        vec2d = Vec2D(beta, gamma)
        hit_point = triangle.get_point(beta, gamma)
        normal = Normal(0f, 1f, 0f)

        hit_record = HitRecord(hit_point, normal, vec2d,  t, ray)

        assertTrue(triangle.ray_intersection(ray)!!.is_close(hit_record))

    }

}