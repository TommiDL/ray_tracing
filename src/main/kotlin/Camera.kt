package org.example


interface Camera {
    fun fire_ray (u:Float, v:Float):Ray
}

class OrthogonalCamera(val aspect_ratio:Float=1.0f,
                       val transformation: Transformation = Transformation(),
    ) : Camera
{
    override fun fire_ray (u:Float, v:Float):Ray{
        val origin:Point = Point(-1.0f, (1.0f - 2 * u) * this.aspect_ratio, 2 * (v - 1))
        val direction:Vec = Vec(1f, 0f, 0f)
        return Ray(origin = origin, dir = direction, tmin = 1.0e-5f).transform(this.transformation)
    }

}

class PerspectiveCamera(val distance:Float=1.0f,
                        val aspect_ratio:Float=1.0f,
                        val transformation: Transformation = Transformation(),
    ) : Camera
{
    override fun fire_ray (u:Float, v:Float):Ray{
        val origin:Point = Point(-this.distance, 0.0f, 0.0f)
        val direction:Vec = Vec(this.distance, (1.0f - 2 * u) * this.aspect_ratio, 2 * (v - 1))
        return Ray(origin = origin, dir = direction, tmin = 1.0e-5f).transform(this.transformation)
    }

}