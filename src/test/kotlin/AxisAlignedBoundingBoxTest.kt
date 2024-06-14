import org.example.*
import java.io.FileInputStream
import kotlin.test.Test
import kotlin.test.assertTrue

class AxisAlignedBoundingBoxTest
{
    val mesh:Mesh=Mesh(FileInputStream("tetrahedron.obj"))
    @Test
    fun constructorTest()
    {


        val box: AxisAlignedBoundingBox =AxisAlignedBoundingBox(this.mesh)

        assertTrue(box.xmin==1f)
        assertTrue(box.xmax==2f)

        assertTrue(box.ymin==1f)
        println(box.ymax)
        assertTrue(box.ymax==2f)

        assertTrue(box.zmin==1f)
        assertTrue(box.zmax==2f)



    }

    @Test
    fun ray_intersectionTest()
    {
        val box:AxisAlignedBoundingBox=AxisAlignedBoundingBox(this.mesh)

        val ray1:Ray= Ray(
            origin = Point(0f, 0f, 0f),
            dir = Vec(x=1.5f, y=1.5f, 1.5f),
        )

        assertTrue(box.ray_intersection(ray1))

        val ray2:Ray= Ray(
            origin = Point(0f, 1.5f, 1.5f),
            dir = Vec(x=1f),
        )
        val ray3:Ray= Ray(
            origin = Point(1.5f, 0f, 1.5f),
            dir = Vec(y=1f),
        )
        val ray4:Ray= Ray(
            origin = Point(1.5f, 1.5f, 0f),
            dir = Vec(z=1f),
        )


        assertTrue(box.ray_intersection(ray2))
        assertTrue(box.ray_intersection(ray3))
        assertTrue(box.ray_intersection(ray4))


    }
}