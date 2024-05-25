package org.example

/**
 * Definition of a triangle through 3 points
 */
data class Triangle(val A: Point = Point(1f, 0f, 0f), val B: Point = Point(0f, 1f, 0f), val C:Point = Point(0f, 0f, 1f)):Shape {

    /**
     * Returns a point inside the triangle
     */
    fun get_point(beta:Float, gamma:Float):Point?{
        if((0f <= beta) && (beta <= 1f) && (0f <= gamma) && (gamma <= 1f))
        {
            return (this.A + (this.B - this.A) * beta + (this.C - this.A) * gamma)
        }
/*        else {
            throw IllegalArgumentException("Point is outside the triangle! beta and gamma must be between 0 and 1")
        }
*/
        else
            return null
    }

    /**
     * Operator which turns instances of the class into callable objects
     */
    operator fun invoke(beta:Float, gamma: Float):Point{
        if((0f <= beta) && (beta <= 1f) && (0f <= gamma) && (gamma <= 1f)) {
            return (this.A + (this.B - this.A) * beta + (this.C - this.A) * gamma)
        }
        else {
            throw IllegalArgumentException("Point is outside the triangle! beta and gamma must be between 0 and 1")
        }
    }

    /**
     * Determinant of a 3x3 matrix
     */
    fun det(a:Float, b:Float, c:Float, d:Float, e:Float, f:Float, g:Float, h:Float, i:Float):Float
    {
        val res:Float = a * (( e * i) - ( h * f )) - d * (( b * i ) - ( h * c )) + g * (( b * f ) - ( e * c ))

        return res
    }

    /**
     * Checks for ray intersection with triangle
     * @return hit record
     */
    override fun ray_intersection(ray: Ray): HitRecord? {

        val M:Float = this.det(this.B.x-this.A.x, this.C.x-this.A.x, ray.dir.x, this.B.y-this.A.y, this.C.y-this.A.y, ray.dir.y, this.B.z-this.A.z, this.C.z-this.A.z, ray.dir.z)
        val beta:Float = this.det(ray.origin.x-this.A.x, this.C.x-this.A.x, ray.dir.x, ray.origin.y-this.A.y, this.C.y-this.A.y, ray.dir.y, ray.origin.z-this.A.z, this.C.z-this.A.z, ray.dir.z)/M
        val gamma:Float = this.det(this.B.x-this.A.x, ray.origin.x-this.A.x, ray.dir.x, this.B.y-this.A.y, ray.origin.y-this.A.y, ray.dir.y, this.B.z-this.A.z, ray.origin.z-this.A.z, ray.dir.z)/M
        val t:Float = this.det(this.B.x-this.A.x, this.C.x-this.A.x, ray.origin.x-this.A.x, this.B.y-this.A.y, this.C.y-this.A.y, ray.origin.y-this.A.y, this.B.z-this.A.z, this.C.z-this.A.z, ray.origin.z-this.A.z)/M

        val hit_point:Point? = this.get_point(beta, gamma)

        if (hit_point!=null)
            return HitRecord(
                world_point = hit_point,
                normal = _triangle_normal(hit_point, ray.dir),
                surface_point = Vec2D(beta, gamma),
                t = t,
                ray = ray,
            )
        else
            return null
    }

    /**
     * Calculates the normal to a point into the triangle
     */
    fun _triangle_normal(point:Point, ray_dir:Vec):Normal
    {
        val diff1:Vec = this.B - this.A
        val diff2:Vec = this.C - this.A

        val norm:Vec = Vec(diff1.x, diff1.y, diff1.z).prod(diff2)

        return if (point.conversion() * ray_dir < 0f) norm.conversion() else norm.conversion()*(-1)
    }

}
