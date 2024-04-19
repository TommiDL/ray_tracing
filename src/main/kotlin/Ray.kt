package org.example

import kotlin.Float.Companion.POSITIVE_INFINITY

/**
 * Ray class for the simulation of a light ray
 */
data class Ray(
    val origin:Point=Point(),
    val dir:Vec=Vec(),
    val tmin:Float=1e-5f,
    val tmax:Float=POSITIVE_INFINITY,
    val depth:Int=0,
    )
{
    /**
     * Comparison between 2 ray objects
     */
    fun is_close(other:Ray, eps:Float=1e-5f):Boolean
    {
        return (
                this.origin.is_close(other.origin, eps=eps) and
                this.dir.is_close(other.dir, eps = eps)
                )
    }

    /**
     * fot a given length return the point of the ray of that length
     */
    fun at(t:Float):Point
    {
        return this.origin + this.dir * t
    }

    /**
     * Return the ray evolved with the transformation given
     */
    fun transform(transformation: Transformation):Ray
    {
        return Ray(
            origin=transformation*this.origin,
            dir=transformation*this.dir,
            tmin=this.tmin,
            tmax = tmax,
            depth=this.depth,
        )
    }
}