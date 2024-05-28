package org.example

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * BRDF:
 */
open class BRDF(val pigment: Pigment = UniformPigment(Color(255f, 255f, 255f)))
{
    /**
     * Function that return a color corresponding to the BRDF integrated in the r/g/b band
     */
    open fun eval(normal: Normal, in_dir: Vec, out_dir: Vec, uv: Vec2D):Color
    {
        return Color(0f,0f,0f)
    }

    operator fun invoke(normal: Normal, in_dir: Vec, out_dir: Vec, uv: Vec2D):Color
    {
        return Color(0f,0f,0f)
    }

    open fun scatter_ray(
        pcg:PCG,
        incoming_dir:Vec,
        interaction_point:Point,
        normal: Normal,
        depth:Int
    ):Ray
    {
        return Ray()
    }

}

/**
 * BRDF with uniform diffusion of wave lenght: uniform reflection
 */
class DiffusiveBRDF(pigment: Pigment, val reflectance:Float=1f):BRDF(pigment)
{
    override fun eval(normal: Normal, in_dir: Vec, out_dir: Vec, uv: Vec2D):Color
    {
        return  this.pigment.get_color(uv) * (this.reflectance/ PI.toFloat())
    }

    override fun scatter_ray(
        pcg: PCG,
        incoming_dir: Vec,
        interaction_point: Point,
        normal: Normal,
        depth: Int
    ):Ray {
        val e:Array<Vec> = create_onb_from_z(normal)

        val cos_theta_sq:Float=pcg.random().toFloat()

        val cos_theta:Float= sqrt(cos_theta_sq)
        val sin_theta:Float= sqrt(1-cos_theta_sq)

        val phi:Float= 2f*PI.toFloat()*pcg.random().toFloat()

        return Ray(
            dir=e[1]* cos(phi)*cos_theta+e[2]*sin(phi)*cos_theta +e[3]*sin_theta,
            tmin = 1e-3f,
            tmax = Float.POSITIVE_INFINITY,
            depth = depth
        )
    }
}
