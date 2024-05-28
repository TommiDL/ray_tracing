import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.PCG

class PCGTest(){
    @Test
    fun testRandom() {
        val pcg:PCG = PCG()

        assertTrue(pcg.state == 1753877967969059832u)
        assertTrue(pcg.inc == 109u.toULong())

        val expectedValues = listOf(
            2707161783u, 2068313097u,
            3122475824u, 2211639955u,
            3215226955u, 3421331566u
        )

        for (expected in expectedValues) {
            assertEquals(expected, pcg.random())
        }
    }
}