import org.example.Color
import org.junit.jupiter.api.Test


import org.junit.jupiter.api.Assertions.*

class ColorTest {

    @Test
    fun testEquals() {
        print(Color(r=1f,g=2f,b=3f))
        print(" == ")
        println(Color(r=1f,g=2f,b=3f))

        assert(Color(r=1f,g=2f,b=3f) == Color(r=1f,g=2f,b=3f))

     /*   print(Color(r=1f,g=2f,b=3f))
        print(" == ")
        println(Color(r=4f,g=5f,b=6f))
        assert(Color(r=1f,g=2f,b=3f) == Color(r=4f,g=5f,b=6f))
    */
        assert(Color(r=1f,g=2f,b=3f) == Color(r=4f,g=5f,b=6f))
        assert(Color(r=1f,g=2f,b=3f) == Color(r=4f,g=5f,b=6f))
        assert(Color(r=1f,g=2f,b=3f) == Color(r=4f,g=5f,b=6f))
        assert(Color(r=1f,g=2f,b=3f) == Color(r=4f,g=5f,b=6f))
        assert(Color(r=1f,g=2f,b=3f) == Color(r=4f,g=5f,b=6f))

    }

    @Test
    fun plus() {
        assert(Color(r=1f,g=2f,b=3f) + Color(r=4f,g=5f,b=6f) == Color(r=5f, g=7f, b=9f))
        assert(Color(r=1f,g=2f,b=3f) + Color(r=4f,g=5f,b=6f) == Color(r=5f, g=7f, b=9f))
        assert(Color(r=1f,g=2f,b=3f) + Color(r=4f,g=5f,b=6f) == Color(r=5f, g=7f, b=9f))
        assert(Color(r=1f,g=2f,b=3f) + Color(r=4f,g=5f,b=6f) == Color(r=5f, g=7f, b=9f))
        assert(Color(r=1f,g=2f,b=3f) + Color(r=4f,g=5f,b=6f) == Color(r=5f, g=7f, b=9f))

    }

    @Test
    fun minus() {
        assert(Color(r=8f,g=10f,b=12f) - Color(r=4f,g=5f,b=6f) == Color(r=4f, g=5f, b=6f))
        assert(Color(r=8f,g=10f,b=12f) - Color(r=4f,g=5f,b=6f) == Color(r=4f, g=5f, b=6f))
        assert(Color(r=8f,g=10f,b=12f) - Color(r=4f,g=5f,b=6f) == Color(r=4f, g=5f, b=6f))
        assert(Color(r=8f,g=10f,b=12f) - Color(r=4f,g=5f,b=6f) == Color(r=4f, g=5f, b=6f))
        assert(Color(r=8f,g=10f,b=12f) - Color(r=4f,g=5f,b=6f) == Color(r=4f, g=5f, b=6f))

    }


    @Test
    fun times() {
        assert(Color(r=1f,g=2f,b=3f)*2f==Color(r=2f, g=4f, b=6f))
        assert(Color(r=1f,g=2f,b=3f)*2==Color(r=2f, g=4f, b=6f))
    }

    @Test
    fun testTimes() {
        assert(Color(r=1f,g=2f,b=3f)*Color(r=2f,g=3f,b=4f)==Color(r=2f, g=6f, b=12f))

    }

    @Test
    fun div() {
        assert(Color(r=2f,g=4f,b=6f)/2f==Color(r=1f, g=2f, b=3f))
        assert(Color(r=2f,g=4f,b=6f)/2==Color(r=1f, g=2f, b=3f))
    }

    @Test
    fun test_are_similar_colors() {

        assert(Color(r=1f, g=2f, b=3f).are_similar_colors(Color(r=0.9f, g=2f, b=3f)))

    }
}