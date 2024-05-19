package org.example


/**
 * Generic Camera interface
 */
interface Camera {
    fun fire_ray (u:Float, v:Float):Ray
}

/**
 * Camera specification type
 * Fire all ray from x direction to obtain an orthogonal perspective image
 *
 * Input:
 *      @aspect_ratio = ratio of the screen
 *      @transformation = transformation to place the camera inside the scene
 */
class OrthogonalCamera(
        val aspect_ratio:Float=1.0f,
        val transformation: Transformation = Transformation(),
    ):Camera
{
    override fun fire_ray (u:Float, v:Float):Ray{
        val origin:Point = Point(-1.0f, (1.0f - 2 * u) * this.aspect_ratio, 2 * v - 1)
        val direction:Vec = Vec(1f, 0f, 0f)
        return Ray(origin = origin, dir = direction, tmin = 1.0e-5f).transform(this.transformation)
    }


}

/**
 * Camera specification type
 * Fire all ray from a point to obtain perspective image
 *
 * Input:
 *      @distance = distance between the observer and the screen
 *      @aspect_ratio = ratio of the screen
 *      @transformation = transformation to place the camera inside the scene
 */
class PerspectiveCamera(
        val distance:Float=1.0f,
        val aspect_ratio:Float=1.0f,
        val transformation: Transformation = Transformation(),
    ):Camera
{
    override fun fire_ray (u:Float, v:Float):Ray{
        val origin:Point = Point(-this.distance, 0.0f, 0.0f)
        val direction:Vec = Vec(this.distance, (1.0f - 2 * u) * this.aspect_ratio, 2 * v - 1)
        return Ray(origin = origin, dir = direction, tmin = 1.0e-5f).transform(this.transformation)
    }



}