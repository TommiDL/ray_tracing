import org.example.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

class HomMatrixTest {

    @Test
    fun Test_are_matr_close() {
        assertTrue(are_matr_close(HomMatrix(), HomMatrix(ID.copyOf())))

        println(HomMatrix())

        val a: HomMatrix = HomMatrix(
            floatArrayOf(
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

        val a: HomMatrix = HomMatrix()

        for (i in 0 until a.height) {
            assertTrue(are_similar(a[i, i], 1f))

            //if(i<a.height-1) println( a[i,i+1] )

            if (i < a.height - 1) assertTrue(are_similar(a[i, i + 1], 0f))

            a[i, 0] = 2f + i
            assertTrue(are_similar(a[i, 0], 2f + i))
        }

        assertTrue(are_similar(a[0, 0], 2f))

    }


    @Test
    fun testTimes() {

        val m1: HomMatrix = HomMatrix(
            floatArrayOf(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 9f, 8f, 7f,
                6f, 5f, 4f, 1f,
            )
        )

        val m2: HomMatrix = HomMatrix(
            floatArrayOf(
                3.0f, 5.0f, 2.0f, 4.0f,
                4.0f, 1.0f, 0.0f, 5.0f,
                6.0f, 3.0f, 2.0f, 0.0f,
                1.0f, 4.0f, 2.0f, 1.0f,
            )
        )

        val expected: HomMatrix = HomMatrix(
            floatArrayOf(
                33.0f, 32.0f, 16.0f, 18.0f,
                89.0f, 84.0f, 40.0f, 58.0f,
                118.0f, 106.0f, 48.0f, 88.0f,
                63.0f, 51.0f, 22.0f, 50.0f,
            )
        )

        assertTrue(are_matr_close(expected, (m1 * m2)))

        //matrix*matrix
        val a: HomMatrix = HomMatrix(
            floatArrayOf(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f,
            )
        )

        val b: HomMatrix = HomMatrix(
            floatArrayOf(
                17f, 18f, 19f, 20f,
                21f, 22f, 23f, 24f,
                25f, 26f, 27f, 28f,
                29f, 30f, 31f, 32f,
            )
        )

        val res1: HomMatrix = HomMatrix(
            floatArrayOf(
                1f * 17f + 2f * 21f + 3f * 25f + 4f * 29f,
                1f * 18f + 2f * 22f + 3f * 26f + 4f * 30f,
                1f * 19f + 2f * 23f + 3f * 27f + 4f * 31f,
                1f * 20f + 2f * 24f + 3f * 28f + 4f * 32f,

                5f * 17f + 6f * 21f + 7f * 25f + 8f * 29f,
                5f * 18f + 6f * 22f + 7f * 26f + 8f * 30f,
                5f * 19f + 6f * 23f + 7f * 27f + 8f * 31f,
                5f * 20f + 6f * 24f + 7f * 28f + 8f * 32f,

                9f * 17f + 10f * 21f + 11f * 25f + 12f * 29f,
                9f * 18f + 10f * 22f + 11f * 26f + 12f * 30f,
                9f * 19f + 10f * 23f + 11f * 27f + 12f * 31f,
                9f * 20f + 10f * 24f + 11f * 28f + 12f * 32f,

                13f * 17f + 14f * 21f + 15f * 25f + 16f * 29f,
                13f * 18f + 14f * 22f + 15f * 26f + 16f * 30f,
                13f * 19f + 14f * 23f + 15f * 27f + 16f * 31f,
                13f * 20f + 14f * 24f + 15f * 28f + 16f * 32f,
            )
        )

        assertTrue(are_matr_close(a * b, res1))


        //matrix*scalar
        assertTrue(
            are_matr_close(
                a * 2f, HomMatrix(
                    floatArrayOf(
                        2f, 4f, 6f, 8f,
                        10f, 12f, 14f, 16f,
                        18f, 20f, 22f, 24f,
                        26f, 28f, 30f, 32f,
                    )
                )
            )
        )


        //matrix*vec
        assertTrue(
            (a * Vec(1f, 2f, 3f)).is_close(
                Vec(
                    1f * 1f + 2f * 2f + 3f * 3f,
                    5f * 1f + 6f * 2f + 7f * 3f,
                    9f * 1f + 10f * 2f + 11f * 3f
                )
            )
        )

        //matrix*normal
//        assertTrue((a*Normal(1f, 2f,3f)).is_close(Normal(1f*1f + 2f*2f + 3f*3f, 5f*1f + 6f*2f + 7f*3f, 9f*1f + 10f*2f + 11f*3f)))

        //matrix*Point
        assertTrue(
            (a * Point(1f, 2f, 3f)).is_close(
                Point(
                    (1f * 1f + 2f * 2f + 3f * 3f + 4f) / (13f * 1f + 14f * 2f + 15f * 3f + 16f),
                    (5f * 1f + 6f * 2f + 7f * 3f + 8f) / (13f * 1f + 14f * 2f + 15f * 3f + 16f),
                    (9f * 1f + 10f * 2f + 11f * 3f + 12f) / (13f * 1f + 14f * 2f + 15f * 3f + 16f)
                )
            )
        )


    }

    @Test
    fun testDet() {

        val a: HomMatrix = HomMatrix(
            floatArrayOf(
                1f, 0f, 0f,
                0f, 1f, 0f,
                0f, 0f, 1f,
            ),
            3,
            3
        )

        val b: HomMatrix = HomMatrix(
            floatArrayOf(
                1f, 1f, 1f,
                1f, 1f, 1f,
                1f, 1f, 1f,
            ),
            3,
            3
        )

        val det: Float = 1f

        assertTrue(are_similar(a.det(), det))
        assertFalse(are_similar(b.det(), det))
        assertTrue(are_similar(b.det(), 0f))

    }
}