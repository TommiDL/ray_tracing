import org.example.Triangle
import org.example.Point
import org.example.Ray
import org.example.Normal
import org.example.Vec2D
import org.example.Vec
import org.example.HitRecord
import org.example.are_similar
import org.junit.jupiter.api.Assertions.*
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

        val origin:Point = Point(0f, 0f, 0f)
        val dir:Vec = Vec(1f, 0f, 0f)
        val ray:Ray = Ray(origin, dir)
        val triangle:Triangle = Triangle()

        val beta:Float = 0f
        val gamma:Float = 0f
        val t:Float = 1f
        val vec2d:Vec2D = Vec2D(beta, gamma)
        val hit_point:Point = Point(1f, 0f, 0f)
        val normal:Normal = triangle._triangle_normal(hit_point, ray.dir)

        val hit_record:HitRecord = HitRecord(hit_point, normal, vec2d,  t, ray)

        assertFalse(triangle.ray_intersection(ray)!!.is_close(hit_record))

    }

}