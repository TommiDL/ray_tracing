import org.example.Point
import org.example.Vec
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PointTest {
    @Test
    fun test_points()
    {
        val a: Point = Point(1.0f, 2.0f, 3.0f)
        val b: Point = Point(4.0f, 6.0f, 8.0f)
        assertTrue (a.is_close(a))
        assertFalse (a.is_close(b))
    }

    @Test
    fun testToString() {
        val p:Point = Point(x=0f, y=11f, z=6f)
        assertTrue(p.toString() == "Point (x=0.0, y=11.0, z=6.0)")

    }

    @Test
    fun test_is_close(){
        //the epsilon of acceptance is 1e-5f = 0.00001
        val p:Point = Point(1.0f, 2.0f, 3.0f)

        //test with a point that is close and other points that are not
        val a:Point = Point(1.000001f, 2.000001f, 3.000001f)
        assertTrue(p.is_close(a))

        val b:Point = Point(1.01f, 2.000001f, 3.000001f)
        assertFalse(p.is_close(b))
        val c:Point = Point(1.000001f, 2.01f, 3.000001f)
        assertFalse(p.is_close(c))
        val d:Point = Point(1.000001f, 2.000001f, 3.01f)
        assertFalse(p.is_close(d))

        val e:Point = Point(1.01f, 2.01f, 3.000001f)
        assertFalse(p.is_close(e))
        val f:Point = Point(1.01f, 2.000001f, 3.01f)
        assertFalse(p.is_close(f))
        val g:Point = Point(1.000001f, 2.01f, 3.01f)
        assertFalse(p.is_close(g))

        val h:Point = Point(1.01f, 2.01f, 3.01f)
        assertFalse(p.is_close(h))
    }
    @Test
    fun test_point_operations () {
        val v = Vec(1.0f, 2.0f, 3.0f)
        val p = Point(2.0f, 2.0f, 2.0f)
        val q = Point(1.0f, 1.0f, 1.0f)

        assertTrue( (p + v).is_close(Point(3.0f, 4.0f, 5.0f)) )
        assertTrue( (p - q).is_close(Vec(1.0f, 1.0f, 1.0f)) )
        assertTrue( (p - v).is_close(Point(1.0f, 1.0f, 1.0f )) )
    }

    }