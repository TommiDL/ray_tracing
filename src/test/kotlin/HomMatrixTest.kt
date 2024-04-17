import org.example.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

class HomMatrixTest {

    @Test
    fun Test_are_matr_close() {
        assertTrue( are_matr_close(HomMatrix(), HomMatrix(ID.copyOf())))

        println(HomMatrix())

        val a:HomMatrix=HomMatrix(floatArrayOf(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),

                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),

                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),

                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
            )
        )

        assertTrue(are_matr_close(a, a))
    }

    @Test
    fun test_pivot() {

        val a:HomMatrix=HomMatrix()
        println(a)

        for(i in 0 until a.height)
        {
            assertTrue(are_similar(a[i, i]  , 1f))

            //if(i<a.height-1) println( a[i,i+1] )

            if(i<a.height-1) assertTrue(are_similar(a[i, i+1], 0f))

            a[i,0]=2f + i
            assertTrue(are_similar(a[i,0], 2f + i))
        }

        assertTrue(are_similar(a[0,0], 2f))

    }


    @Test
    fun testTimes() {
        //matrix*matrix
        val a:HomMatrix=HomMatrix(floatArrayOf(
            1f,  2f,  3f,  4f,
            5f,  6f,  7f,  8f,
            9f,  10f, 11f, 12f,
            13f, 14f, 15f, 16f,
        ))

        val b:HomMatrix=HomMatrix(floatArrayOf(
            17f, 18f, 19f, 20f,
            21f, 22f, 23f, 24f,
            25f, 26f, 27f, 28f,
            29f, 30f, 31f, 32f,
        ))

        val res1:HomMatrix=HomMatrix(floatArrayOf(
            1f*17f + 2f*18f + 3f*19f + 4f*20f,
            1f*21f + 2f*22f + 3f*23f + 4f*24f,
            1f*25f + 2f*26f + 3f*27f + 4f*28f,
            1f*29f + 2f*30f + 3f*31f + 4f*32f,

            5f*17f + 6f*18f + 7f*19f + 8f*20f,
            5f*21f + 6f*22f + 7f*23f + 8f*24f,
            5f*25f + 6f*26f + 7f*27f + 8f*28f,
            5f*29f + 6f*30f + 7f*31f + 8f*32f,

            9f*17f + 10f*18f + 11f*19f + 12f*20f,
            9f*21f + 10f*22f + 11f*23f + 12f*24f,
            9f*25f + 10f*26f + 11f*27f + 12f*28f,
            9f*29f + 10f*30f + 11f*31f + 12f*32f,

            13f*17f + 14f*18f + 15f*19f + 16f*20f,
            13f*21f + 14f*22f + 15f*23f + 16f*24f,
            13f*25f + 14f*26f + 15f*27f + 16f*28f,
            13f*29f + 14f*30f + 15f*31f + 16f*32f,
        ))

        val res2:HomMatrix=HomMatrix(floatArrayOf(
            17f*1f + 18f*2f + 19f*3f +  20f*4f,
            17f*5f + 18f*6f + 19f*7f +  20f*8f,
            17f*9f + 18f*10f + 19f*11f +  20f*12f,
            17f*13f + 18f*14f + 19f*15f +  20f*16f,

            21f*1f + 22f*2f + 23f*3f + 24f*4f,
            21f*5f + 22f*6f + 23f*7f + 24f*8f,
            21f*9f + 22f*10f + 23f*11f + 24f*12f,
            21f*13f + 22f*14f + 23f*15f + 24f*16f,

            25f*1f + 26f*2f + 27f*3f + 28f*4f,
            25f*5f + 26f*6f + 27f*7f + 28f*8f,
            25f*9f + 26f*10f + 27f*11f + 28f*12f,
            25f*13f + 26f*14f + 27f*15f + 28f*16f,

            29f*1f + 30f*2f + 31f*3f + 32f*4f,
            29f*5f + 30f*6f + 31f*7f + 32f*8f,
            29f*9f + 30f*10f + 31f*11f + 32f*12f,
            29f*13f + 30f*14f + 31f*15f + 32f*16f,
        ))


        assertTrue(are_matr_close(a*b, res1))
        assertTrue(are_matr_close(b*a, res2))


        //matrix*scalar
/*        assertTrue(are_matr_close(a*2f, HomMatrix(floatArrayOf(
            2f, 4f, 6f, 8f,
            10f, 12f, 14f, 16f,
            18f, 20f, 22f, 24f,
            26f, 28f, 30f, 32f,
        ))))
*/
        //matrix*vec
        assertTrue((a* Vec(1f, 2f, 3f)).is_close(Vec(1f*1f + 2f*2f + 3f*3f, 5f*1f + 6f*2f + 7f*3f, 9f*1f + 10f*2f + 11f*3f)))

        //matrix*normal
        //matrix*Point
    }

    @Test
    fun getMatrix() {

    }

    @Test
    fun setMatrix() {
    }

    @Test
    fun component1() {
    }

    @Test
    fun copy() {
    }

    @Test
    fun testToString() {
    }

    @Test
    fun testHashCode() {
    }

    @Test
    fun testEquals() {
    }
}