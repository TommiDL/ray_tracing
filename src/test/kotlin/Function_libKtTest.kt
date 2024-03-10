import org.example.are_similar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Function_libKtTest {

    @Test
    fun test_are_similar() {
        assert(are_similar(1f, 2f))
    }
}