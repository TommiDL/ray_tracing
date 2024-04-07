import org.example.Normal
import org.example.Point
import org.example.Vec
import org.example.are_similar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.pow

class VecTest {

    @Test
    fun testToString() {
        val V:Vec = Vec(x=0f, y=11f, z=6f)
        assertTrue(V.toString() == "Vector (x=0.0, y=11.0, z=6.0)")
    }

    @Test
    fun is_close() {
        val a:Vec = Vec(1.0f, 2.0f, 3.0f)
        val b:Vec = Vec(4.0f, 6.0f, 8.0f)
        assertTrue(a.is_close(a))
        assertFalse(a.is_close(b))
    }

    @Test
    fun Test_vector_operations()
    {
        val a = Vec(1.0f, 2.0f, 3.0f)
        val b = Vec(4.0f, 6.0f, 8.0f)

        //vec-vec operations
        assertTrue( (a + b).is_close(Vec(5.0f, 8.0f, 11.0f)) )
        assertTrue( (b - a).is_close(Vec(3.0f, 4.0f, 5.0f )) )
        assertTrue( (a * 2).is_close(Vec(2.0f, 4.0f, 6.0f )) )
        assertTrue((b/2f).is_close(Vec(2f,3f,4f)))

        //scalar product
        assertTrue( are_similar(40.0f, a*b))

        //cross prod
        assertTrue( a.prod(b).is_close(Vec(-2.0f,  4.0f, -2.0f)))
        assertTrue( b.prod(a).is_close(Vec( 2.0f, -4.0f,  2.0f)))

        //norm
        assertTrue( are_similar(14.0f , a.squared_norm()))
        assertTrue(are_similar(14.0f , (a.norm()).pow(2) ))

        //neg
        assertTrue(a.neg().is_close(Vec(-1f, -2f, -3f)))

        //conversion
        assertTrue(a.conversion() is Normal)

        //vec + point
        //assertTrue(a+Point(1f, 2f, 3f).is_close(Point(2f, 4f, 6f)))

    }
}