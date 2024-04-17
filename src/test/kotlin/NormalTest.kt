import org.example.Normal
import org.example.Point
import org.example.Vec
import org.example.are_similar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.pow

class NormalTest {


    @Test
    fun testToString() {
        val V: Normal = Normal(x=0f, y=11f, z=6f)
        assertTrue(V.toString() == "Normal (x=0.0, y=11.0, z=6.0)")
    }

    @Test
    fun test_normal()
    {
        val a: Normal = Normal(1.0f, 2.0f, 3.0f)
        val b: Normal = Normal(4.0f, 6.0f, 8.0f)
        assertTrue (a.is_close(a))
        assertFalse (a.is_close(b))
    }

    @Test
    fun Test_vector_operations()
    {
        val a = Normal(1.0f, 2.0f, 3.0f)
        val b = Normal(4.0f, 6.0f, 8.0f)

        //vec-vec operations
        assertTrue( (a * 2).is_close(Normal(2.0f, 4.0f, 6.0f )) )

        //scalar product
        assertTrue( are_similar(40.0f, a*b))

        //cross prod
        assertTrue( a.prod(b).is_close(Normal(-2.0f,  4.0f, -2.0f)))
        assertTrue( b.prod(a).is_close(Normal( 2.0f, -4.0f,  2.0f)))

        //normal vec va calcolata tutta la sbatta...
        //assertTrue( a*Vec(1f,2f,3f).is_close(Normal()) )

        //norm
        assertTrue( are_similar(14.0f , a.squared_norm()))
        assertTrue(are_similar(14.0f , (a.norm()).pow(2) ))

        //neg
        assertTrue(a.neg().is_close(Normal(-1f, -2f, -3f)))

    }


}