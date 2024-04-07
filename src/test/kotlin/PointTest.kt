import org.example.Point
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PointTest {
    @Test
    fun test_points()
    {
        val a: Point = Point(1.0f, 2.0f, 3.0f)
        val b: Point = Point(4.0f, 6.0f, 8.0f)
        //assertTrue (a.is_close(a))
        //assertFalse (a.is_close(b))
    }

}