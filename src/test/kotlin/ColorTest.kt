import org.example.Color
import org.junit.jupiter.api.Test


import org.junit.jupiter.api.Assertions.*

class ColorTest {

    @Test
    fun testEquals() {

        //1,2,3 == 1,2,3
        var t1:Color=Color(r=1f,g=2f,b=3f)
        var t2:Color=Color(r=1f,g=2f,b=3f)
        print(t1)
        print(" == ")
        println(t2)
        assertTrue(t1 == t2)

        //4,7,6 == 1,6,3
        t1=Color(r=4f,g=7f,b=6f)
        t2=Color(r=1f,g=6f,b=3f)
        print(t1)
        print(" == ")
        println(t2)
        assertFalse(t1 == t2)


        //1.3,2,3 == 1.29,2,3
        t1=Color(r=1.3f,g=2f,b=3f)
        t2=Color(r=1.29f,g=2f,b=3f)
        print(t1)
        print(" == ")
        println(t2)
        assertFalse(t1 == t2)

        //farne nuovi

    }

    @Test
    fun plus() {
        var t1:Color=Color(r=1f,g=2f,b=3f)
        var t2:Color=Color(r=4f,g=5f,b=6f)
        var sum:Color=Color(r=5f, g=7f, b=9f)

        assertTrue(t1 +  t2 == sum)

        // (, ,)+(, ,)==(, ,)
        //da completare
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=4f,g=5f,b=6f)
        sum=Color(r=5f, g=7f, b=9f)

        assertTrue(t1 +  t2 == sum)


        //farne nuovi
    }

    @Test
    fun minus() {

        var t1:Color=Color(r=8f,g=10f,b=12f)
        var t2:Color=Color(r=4f,g=5f ,b=6f)
        var sub:Color=Color(r=4f, g=5f, b=6f)

        //da cambiare
        t1 =Color(r=8f,g=10f,b=12f)
        t2 =Color(r=4f,g=5f,b=6f)
        sub=Color(r=4f, g=5f, b=6f)
        assertTrue(t1 - t2 == sub)


        //farne nuovi
    }


    @Test
    fun times() {

        var t1:Color=Color(r=1f,g=2f,b=3f)
        var fact_float:Float=2f
        var prod:Color=Color(r=2f, g=4f, b=6f)
        assertTrue(t1*fact_float==prod)

        var fact_int:Int=2
        assertTrue(t1*fact_int==prod)

        //farne nuovi
    }

    @Test
    fun testTimes() {
        var t1:Color=Color(r=1f,g=2f,b=3f)
        var t2:Color=Color(r=2f,g=3f,b=4f)
        var prod:Color=Color(r=2f, g=6f, b=12f)

        assertTrue(t1*t2==prod)

        //da farne nuovi
    }

    @Test
    fun div() {
        var t1:Color=Color(r=2f,g=4f,b=6f)
        var div_float:Float=2f
        var prod:Color=Color(r=1f, g=2f, b=3f)
        assertTrue(t1/div_float==prod)

        var div_int:Int=2
        assert(t1/div_int==prod)

        //da farne nuovi
    }

    @Test
    fun test_are_similar_colors() {

        var t1:Color=Color(r=1f, g=2f, b=3f)
        var t2:Color=Color(r=1f-1e-6f, g=2f, b=3f)
        assertTrue(t1.are_similar_colors(t2))

        //da crearne nuovi

    }
}