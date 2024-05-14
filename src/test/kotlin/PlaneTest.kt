

import org.example.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.math.sqrt



class PlaneTest {

    @Test
    fun test_plane() {
        val plane: Plane = Plane()

        var ray1: Ray = Ray(
            origin = Point(0f, 0f, 1f),
            dir = Vec(0f, 0f, 1f) * (-1),
        )

        assertTrue(
            plane.ray_intersection(ray1)?.is_close(
                HitRecord(
                    world_point = Point(0f, 0f, 0f),
                    normal = Normal(0f, 0f, 1f),
                    surface_point = plane._plane_point_to_uv(Point(0f, 0f, 0f)),
                    t = 1f,
                    ray = ray1,
                )
            ) ?: true
        )

        var ray2 : Ray = Ray(
            origin = Point(0f, 0f, 1f ),
            dir = Vec(0f, 0f, 1f )

        )
        assertNull(plane.ray_intersection((ray2)))

        var ray3 : Ray = Ray(
            origin = Point(1f, 1f, 1f ),
            dir = Vec(1f, 0f, 0f )

        )
        assertNull(plane.ray_intersection((ray3)))

        var ray4 : Ray = Ray(
            origin = Point(1f, 1f, 1f ),
            dir = Vec(0f, 1f, 0f )
        )
        assertNull(plane.ray_intersection((ray4)))

    }

    @Test
    fun test_plane_normal() {
     
    }

}