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
        throw NotImplementedError("BRDF.scatter_ray is an abstract Method")
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

        // the las vec is equal to the normal
        val e:Array<Vec> = create_onb_from_z(normal)

        // bound from 0 to 1
        val cos_theta_sq:Float=pcg.random_float()


        val cos_theta:Float= sqrt(cos_theta_sq)
        val sin_theta:Float= sqrt(1f-cos_theta_sq)

        val phi:Float= 2f*PI.toFloat()*pcg.random_float()

        return Ray(
            origin = interaction_point,
            dir= e[0]* cos(phi) * cos_theta + e[1] * sin(phi) * cos_theta +e[2]*sin_theta,
            tmin = 1e-3f,
            tmax = Float.POSITIVE_INFINITY,
            depth = depth
        )
    }
}


class SpecularBRDF(pigment: Pigment):BRDF(pigment)
{
    override fun scatter_ray(pcg: PCG, incoming_dir: Vec, interaction_point: Point, normal: Normal, depth: Int): Ray {
        val ray_dir:Vec=Vec(
            x=incoming_dir.x,
            y=incoming_dir.y,
            z=incoming_dir.z
        ).normalize()

        val _norm=normal.toVec().normalize()

        return Ray(
            origin = interaction_point,
            dir = ray_dir-_norm * 2 *(_norm*ray_dir),
            tmin = 1e-3f,
            depth = depth
        )
    }
}