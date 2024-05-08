import org.example.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.PI
import kotlin.math.cos

class HitRecordTest {

    @Test
    fun test_is_close() {
        val hitrecord1: HitRecord = HitRecord(world_point = Point(1f, 2f, 3f), normal = Normal(0f, 0f, 0f), surface_point = Vec2D(5f, 4f), t = 0f, ray = Ray(Point(0f, 0f, 0f), Vec(1f, 0f, 0f)))
        val hitrecord2: HitRecord = HitRecord(world_point = Point(1f, 2f, 3f), normal = Normal(0f, 0f, 0f), surface_point = Vec2D(5f, 4f), t = 0f, ray = Ray(Point(0f, 0f, 0f), Vec(1f, 0f, 0f)))
        val hitrecord3: HitRecord = HitRecord(world_point = Point(1f, 0f, 3f), normal = Normal(1f, 0f, 0f), surface_point = Vec2D(5f, 5f), t = 1f, ray = Ray(Point(0f, 0f, 1f), Vec(1f, 1f, 0f)))

        assertTrue(hitrecord1.is_close(hitrecord2))
        assertFalse(hitrecord1.is_close(hitrecord3))
    }

}