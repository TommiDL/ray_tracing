import org.example.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.PI
import kotlin.math.cos

class Vec2DTest {

    @Test
    fun test_is_close() {
        val vec1: Vec2D = Vec2D(u = 0f, v = 0f)
        val vec2: Vec2D = Vec2D(u = 0f, v = 0f)
        val vec3: Vec2D = Vec2D(u = 1f, v = 1f)

        assertTrue(vec1.is_close(vec2))
        assertFalse(vec1.is_close(vec3))
    }

}