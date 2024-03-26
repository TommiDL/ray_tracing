import org.example.Color
import org.example.are_similar
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

        //1,2,3 == 1,2,4
        t1=Color(r=1f,g=2f,b=3f)
        t2=Color(r=1f,g=2f,b=4f)
        print(t1)
        print(" == ")
        println(t2)
        assertFalse(t1 == t2)

        //1,2,3 == 2,2,3
        t1=Color(r=1f,g=2f,b=3f)
        t2=Color(r=2f,g=2f,b=3f)
        print(t1)
        print(" == ")
        println(t2)
        assertFalse(t1 == t2)

        //1,2,3 == 3,2,1
        t1=Color(r=1f,g=2f,b=3f)
        t2=Color(r=3f,g=2f,b=1f)
        print(t1)
        print(" == ")
        println(t2)
        assertFalse(t1 == t2)

    }

    @Test
    fun plus() {

        //(1, 2, 3) + (4, 5, 6) == (5, 7, 9)
        var t1:Color=Color(r=1f,g=2f,b=3f)
        var t2:Color=Color(r=4f,g=5f,b=6f)
        var sum:Color=Color(r=5f, g=7f, b=9f)

        assertTrue(t1 +  t2 == sum)

        //(1, 2, 3) + (4, 5, 6) == (6, 5, 10)
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=4f,g=5f,b=6f)
        sum=Color(r=6f, g=5f, b=10f)

        assertFalse(t1 +  t2 == sum)

        //(1, 2, 3) + (4, 5, 6) == (5, 7, 10)
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=4f,g=5f,b=6f)
        sum=Color(r=5f, g=7f, b=10f)

        assertFalse(t1 +  t2 == sum)

        //(1, 2, 3) + (4, 5, 6) == (6, 7, 9)
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=4f,g=5f,b=6f)
        sum=Color(r=6f, g=7f, b=9f)

        assertFalse(t1 +  t2 == sum)

        //(1, 2, 3) + (4, 5, 6) == (5, 6, 9)
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=4f,g=5f,b=6f)
        sum=Color(r=5f, g=6f, b=9f)

        assertFalse(t1 +  t2 == sum)

        //(2, 2, 3) + (4, 5, 5) == (6, 7, 8)
        t1 =Color(r=2f,g=2f,b=3f)
        t2 =Color(r=4f,g=5f,b=5f)
        sum=Color(r=6f, g=7f, b=8f)

        assertTrue(t1 +  t2 == sum)

        //(1.5, 2, 3) + (4, 5.5, 6) == (6, 7, 9)
        t1 =Color(r=1.5f,g=2f,b=3f)
        t2 =Color(r=4f,g=5.5f,b=6f)
        sum=Color(r=6.5f, g=7f, b=9f)

        assertFalse(t1 +  t2 == sum)

        //(1.5, 2, 3) + (4.5, 5, 6) == (6, 7, 9)
        t1 =Color(r=1.5f,g=2f,b=3f)
        t2 =Color(r=4.5f,g=5f,b=6f)
        sum=Color(r=6f, g=7f, b=9f)

        assertTrue(t1 +  t2 == sum)
    }

    @Test
    fun minus() {

        //(8, 10, 12) - (4, 5, 6) == (4, 5, 6)
        var t1:Color=Color(r=8f,g=10f,b=12f)
        var t2:Color=Color(r=4f,g=5f ,b=6f)
        var sub:Color=Color(r=4f, g=5f, b=6f)

        assertTrue(t1 - t2 == sub)

        //(8.5, 10, 12) - (4, 5, 6) == (4.5, 5, 6)
        t1 =Color(r=8.5f,g=10f,b=12f)
        t2 =Color(r=4f,g=5f,b=6f)
        sub=Color(r=4.5f, g=5f, b=6f)

        assertTrue(t1 - t2 == sub)

        //(10, 10, 12) - (10, 5, 6) == (0, 5, 6)
        t1 =Color(r=10f,g=10f,b=12f)
        t2 =Color(r=10f,g=5f,b=6f)
        sub=Color(r=0f, g=5f, b=5f)

        assertFalse(t1 - t2 == sub)

        //(8, 10, 12) - (4, 5, 6) == (4, 5, 5)
        t1 =Color(r=8f,g=10f,b=12f)
        t2 =Color(r=4f,g=5f,b=6f)
        sub=Color(r=4f, g=5f, b=5f)

        assertFalse(t1 - t2 == sub)

        //(8, 10, 12) - (4, 5, 6) == (5, 5, 6)
        t1 =Color(r=8f,g=10f,b=12f)
        t2 =Color(r=4f,g=5f,b=6f)
        sub=Color(r=5f, g=5f, b=5f)

        assertFalse(t1 - t2 == sub)

        //(8, 10, 12) - (4, 5, 6) == (4, 4, 6)
        t1 =Color(r=8f,g=10f,b=12f)
        t2 =Color(r=4f,g=5f,b=6f)
        sub=Color(r=4f, g=4f, b=5f)

        assertFalse(t1 - t2 == sub)

        //(8.1, 10.02, 12) - (4, 5, 6) == (4, 5, 6)
        t1 =Color(r=8.1f,g=10.02f,b=12f)
        t2 =Color(r=4f,g=5f,b=6f)
        sub=Color(r=4f, g=5f, b=5f)

        assertFalse(t1 - t2 == sub)
    }


    @Test
    fun times() {

        //2f * (1, 2, 3) == (2, 4, 6)
        var t1:Color=Color(r=1f,g=2f,b=3f)
        var factFloat:Float=2f
        var prod:Color=Color(r=2f, g=4f, b=6f)
        assertTrue(t1*factFloat==prod)

        //2 * (1, 2, 3) == (2, 4, 6)
        var factInt:Int=2
        assertTrue(t1*factInt==prod)

        //3f * (1, 2, 3) == (1, 2, 3)
        t1 =Color(r=1f,g=2f,b=3f)
        factFloat =3f
        prod =Color(r=1f, g=2f, b=3f)
        assertFalse(t1*factFloat==prod)

        //3 * (1, 2, 3) == (1, 2, 3)
        factInt =3
        assertFalse(t1*factInt==prod)

        //2.1f * (1, 2, 3) == (2, 4, 6)
        t1 =Color(r=1f,g=2f,b=3f)
        factFloat =2.1f
        prod =Color(r=2f, g=4f, b=6f)
        assertFalse(t1*factFloat==prod)

        //2 * (1, 2, 3) == (2, 4, 6)
        factInt =2
        assertTrue(t1*factInt==prod)
        //***************non sicura abbia senso******************

        //0f * (1, 2, 3) == (1, 0, 0)
        t1 =Color(r=1f,g=2f,b=3f)
        factFloat =0f
        prod =Color(r=1f, g=0f, b=0f)
        assertFalse(t1*factFloat==prod)

        //0 * (1, 2, 3) == (1, 0, 0)
        factInt =0
        assertFalse(t1*factInt==prod)

        //2f * (0, 1, 2) == (2, 2, 4)
        t1 =Color(r=0f,g=1f,b=2f)
        factFloat =2f
        prod =Color(r=2f, g=2f, b=4f)
        assertFalse(t1*factFloat==prod)

        //2 * (0, 1, 2) == (2, 2, 4)
        factInt =2
        assertFalse(t1*factInt==prod)

        //2f * (1.5, 1, 2) == (3, 2, 4)
        t1 =Color(r=1.5f,g=1f,b=2f)
        factFloat =2f
        prod =Color(r=3f, g=2f, b=4f)
        assertTrue(t1*factFloat==prod)

        //2 * (1.5, 1, 2) == (3, 2, 4)
        factInt =2
        assertTrue(t1*factInt==prod)

    }

    @Test
    fun testTimes() {

        // (1, 2, 3) * (2, 3, 4) == (2, 6, 12)
        var t1:Color=Color(r=1f,g=2f,b=3f)
        var t2:Color=Color(r=2f,g=3f,b=4f)
        var prod:Color=Color(r=2f, g=6f, b=12f)

        assertTrue(t1*t2==prod)

        // (1.5, 2, 3) * (2, 3, 4) == (3, 6, 12)
        t1 =Color(r=1.5f,g=2f,b=3f)
        t2 =Color(r=2f,g=3f,b=4f)
        prod =Color(r=3f, g=6f, b=12f)

        assertTrue(t1*t2==prod)

        // (1, 2, 3) * (2, 3, 4) == (2, 6, 10)
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=2f,g=3f,b=4f)
        prod =Color(r=2f, g=6f, b=10f)

        assertFalse(t1*t2==prod)

        // (1, 2, 3) * (2, 3, 4) == (2, 6.5, 12)
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=2f,g=3f,b=4f)
        prod =Color(r=2f, g=6.5f, b=12f)

        assertFalse(t1*t2==prod)

        // (1, 2, 3) * (2, 3, 4) == (1, 6, 12)
        t1 =Color(r=1f,g=2f,b=3f)
        t2 =Color(r=2f,g=3f,b=4f)
        prod =Color(r=1f, g=6f, b=12f)

        assertFalse(t1*t2==prod)

        // (1.5, 2, 3) * (2, 3, 4) == (2, 6, 12)
        t1 =Color(r=1.5f,g=2f,b=3f)
        t2 =Color(r=2f,g=3f,b=4f)
        prod =Color(r=2f, g=6f, b=12f)

        assertFalse(t1*t2==prod)

        // (0, 2, 3) * (2, 3, 4) == (2, 6, 12)
        t1 =Color(r=0f,g=2f,b=3f)
        t2 =Color(r=2f,g=3f,b=4f)
        prod =Color(r=2f, g=6f, b=12f)

        assertFalse(t1*t2==prod)

    }

    @Test
    fun div() {

        // (2, 4, 6) / 2f == (1, 2, 3)
        var t1:Color=Color(r=2f,g=4f,b=6f)
        var div_float:Float=2f
        var prod:Color=Color(r=1f, g=2f, b=3f)
        assertTrue(t1/div_float==prod)

        // (2, 4, 6) / 2 == (1, 2, 3)
        var div_int:Int=2
        assertTrue(t1/div_int==prod)
        //****************prima c'era scritto solo assert, dev'essere assertTrue giusto?****************

        // (2, 4, 6) / 2f == (2, 2, 3)
        t1 =Color(r=2f,g=4f,b=6f)
        div_float =2f
        prod =Color(r=2f, g=2f, b=3f)
        assertFalse(t1/div_float==prod)

        // (2, 4, 6) / 2 == (2, 2, 3)
        div_int =2
        assertFalse(t1/div_int==prod)

        // (2, 4, 6) / 2f == (1, 1, 3)
        t1 =Color(r=2f,g=4f,b=6f)
        div_float =2f
        prod =Color(r=1f, g=1f, b=3f)
        assertFalse(t1/div_float==prod)

        // (2, 4, 6) / 2 == (1, 1, 3)
        div_int =2
        assertFalse(t1/div_int==prod)

        // (2, 4, 6) / 2f == (1, 2, 2)
        t1 =Color(r=2f,g=4f,b=6f)
        div_float =2f
        prod =Color(r=1f, g=2f, b=2f)
        assertFalse(t1/div_float==prod)

        // (2, 4, 6) / 2 == (1, 2, 2)
        div_int =2
        assertFalse(t1/div_int==prod)

        // (2, 4, 6) / 0f == (1, 2, 2)
        t1 =Color(r=2f,g=4f,b=6f)
        div_float =0f
        prod =Color(r=1f, g=2f, b=2f)
        assertFalse(t1/div_float==prod)

        // (2, 4, 6) / 0 == (1, 2, 2)
        div_int =0
        assertFalse(t1/div_int==prod)

        // (2.1, 4, 6) / 2f == (1, 2, 3)
        t1 =Color(r=2.1f,g=4f,b=6f)
        div_float =2f
        prod =Color(r=1f, g=2f, b=3f)
        assertFalse(t1/div_float==prod)

        // (2.1, 4, 6) / 2 == (1, 2, 3)
        div_int =2
        assertFalse(t1/div_int==prod)
        //****************float / int == int ???*************************

    }

    @Test
    fun test_are_similar_colors() {

        // (1, 2, 3) similar (1-1e-6f, 2, 3)
        var t1:Color=Color(r=1f, g=2f, b=3f)
        var t2:Color=Color(r=1f-1e-6f, g=2f, b=3f)
        assertTrue(t1.is_close(t2))

        // (1, 2, 3) similar (1-1e-6f, 2-1e-6f, 3-1e-6f)
        t1 =Color(r=1f, g=2f, b=3f)
        t2 =Color(r=1f-1e-6f, g=2f-1e-6f, b=3f-1e-6f)
        assertTrue(t1.is_close(t2))

        // (1, 2, 3) similar (1, 2-1e-6f, 3)
        t1 =Color(r=1f, g=2f, b=3f)
        t2 =Color(r=1f, g=2f-1e-6f, b=3f)
        assertTrue(t1.is_close(t2))

        // (1, 2, 3) similar (1, 2, 3-1e-6f)
        t1 =Color(r=1f, g=2f, b=3f)
        t2 =Color(r=1f, g=2f, b=3f-1e-6f)
        assertTrue(t1.is_close(t2))

        // (1, 2, 3) similar (0, 2-1e-6f, 3)
        t1 =Color(r=1f, g=2f, b=3f)
        t2 =Color(r=0f, g=2f-1e-6f, b=3f)
        assertFalse(t1.is_close(t2))

        // (1, 2, 3) similar (0, 2-1e-6f, 3-1e-6f)
        t1 =Color(r=1f, g=2f, b=3f)
        t2 =Color(r=0f, g=2f-1e-6f, b=3f-1e-6f)
        assertFalse(t1.is_close(t2))

        // (1, 2, 3) similar (1, 2, 4)
        t1 =Color(r=1f, g=2f, b=3f)
        t2 =Color(r=1f, g=2f, b=4f)
        assertFalse(t1.is_close(t2))

        // (1, 2, 3) similar (1, 3, 2)
        t1 =Color(r=1f, g=2f, b=3f)
        t2 =Color(r=1f, g=3f, b=2f)
        assertFalse(t1.is_close(t2))

    }

    @Test
    fun luminosity() {

        val col1:Color=Color(1.0f, 2.0f, 3.0f)
        val col2:Color=Color(9.0f, 5.0f, 7.0f)

        assertTrue(are_similar(2.0f, col1.luminosity().toFloat()))
        assertTrue(are_similar(7.0f, col2.luminosity().toFloat()))
    }
}