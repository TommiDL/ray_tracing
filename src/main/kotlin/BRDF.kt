package org.example

import kotlin.math.PI

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
}
