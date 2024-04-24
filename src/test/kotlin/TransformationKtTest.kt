import org.example.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.PI

class TransformationKtTest {



    @Test
    fun Test_times()
    {
        assertTrue( (Transformation()* Vec(1f,2f,3f)).is_close(Vec(1f,2f,3f)) )
        assertTrue( (Transformation()* Normal(1f,2f,3f)).is_close(Normal(1f,2f,3f)) )
        assertTrue( (Transformation()* Point(1f,2f,3f)).is_close(Point(1f,2f,3f)) )

        val a:Transformation= Transformation()

        for(i in 0 until a.matrix.width)
        {
            a.matrix[i,i]=2f
        }

        println(a)

        assertFalse(a.is_consistent())


        assertTrue( (a* Vec(1f,2f,3f)).is_close(Vec(2f,4f,6f)) )
        assertFalse( (a*Normal(1f,2f,3f)).is_close(Normal(2f,4f,6f)) )

        //println(a*Normal(1f,2f,3f))

        for(i in 0 until a.matrix.width)
        {
            a.invmatrix[i,i]=1/2f
        }

        assertTrue( (a*Normal(1f,2f,3f)).is_close(Normal(1/2f,1f,1.5f)) )


        assertTrue( (a* Point(1f,2f,3f)).is_close(Point(1f,2f,3f)) )



    }

    @Test
    fun Test_traslation() {
        val trasl:Transformation= traslation(Vec(1f,2f,3f))

        //vector product
        assertTrue( (trasl*Vec(4f,5f,6f)).is_close(Vec(4f,5f,6f)) )             //traslation of a not null vector --> vector cannot be traslated
        assertTrue( (traslation() * Vec(1f,2f,3f)).is_close(Vec(1f,2f,3f)) )    //traslation of a null vector

        //normal product
        assertTrue( (trasl*Normal(4f,5f,6f)).is_close(Normal(4f,5f,6f)) )             //traslation of a not null normal --> normal cannot be traslated
        assertTrue( (traslation() * Normal(1f,2f,3f)).is_close(Normal(1f,2f,3f)) )    //traslation of a null normal

        //point product
        assertTrue( (trasl*Point(4f,5f,6f)).is_close(Point(5f,7f,9f)) )             //traslation of a not null Point
        assertTrue( (traslation() * Point(1f,2f,3f)).is_close(Point(1f,2f,3f)) )    //traslation of a null Point

    }

    @Test
    fun Test_rotation_matrix() {

        val rot_idx:FloatArray = _rotation_matrix(Vec(1f, 0f,0f), (2*PI).toFloat())

        for(i in 0 until rot_idx.size)
        {
            assertTrue(are_similar(rot_idx[i], ID[i]))
        }

        val rot_idy:FloatArray = _rotation_matrix(Vec(0f, 1f,0f), (2*PI).toFloat())

        for(i in 0 until rot_idy.size)
        {
            assertTrue(are_similar(rot_idy[i], ID[i]))
        }

        val rot_idz:FloatArray = _rotation_matrix(Vec(0f, 0f,1f), (2*PI).toFloat())

        for(i in 0 until rot_idz.size)
        {
            assertTrue(are_similar(rot_idz[i], ID[i]))
        }


        //assertTrue(_rotation_matrix(Vec(1f, 0f,0f), (2*PI).toFloat()).contentEquals(ID))


    }

    @Test
    fun Test_rotation() {
        val rotx:Transformation= rotation(Vec(1f,0f,0f), (PI).toFloat() )
        val roty:Transformation= rotation(Vec(0f,1f,0f), (PI).toFloat() )
        val rotz:Transformation= rotation(Vec(0f,0f,1f), (PI).toFloat() )

        val rot_IDz:Transformation= rotation(Vec(0f,0f,1f), theta = (2*PI).toFloat())

        //rotation x PI * null vector
        assertTrue((rotx*Vec()).is_close(Vec()))

        //rotation x PI * (4,9,11) == (4-9,-11)
        assertTrue((rotx*Vec(4f, 9f, 11f)).is_close(Vec(4f, -9f, -11f), eps = 1e-4f))
        println("rotx(PI)*${Vec(4f, 9f, 11f)}")
        println(rotx*Vec(4f, 9f, 11f))

        //rotation y PI * null vector
        assertTrue((roty*Vec()).is_close(Vec()))

        assertTrue((roty*Vec(7f, 21f, 7f)).is_close(Vec(-7f, 21f, -7f), eps = 1e-4f))


        assertTrue((rotz*Vec()).is_close(Vec()))

        assertTrue((rotz*Vec(3f, 7f, 4f)).is_close(Vec(-3f, -7f, 4f), eps = 1e-4f))

        //rotation 2pi
        assertTrue((rot_IDz*Vec(6f, 1f, 9f)).is_close(Vec(6f, 1f, 9f), eps = 1e-4f))

    }

    @Test
    fun Test_scalar_transformation() {
        val sc:Transformation= scalar_transformation(2f,3f,4f)
        assertTrue((sc*Vec(5f, 7f, 11f)).is_close(Vec(10f, 21f, 44f)))
    }
}