package org.example

import kotlin.math.sqrt

data class Normal(var x:Float=0f, var y:Float=0f, var z:Float=0f)
    {
    override fun toString(): String {
        return "Normal (x=$x, y=$y, z=$z)"
    }
    /**
     * Check if two normals are close
     */
    fun is_close(normal: Normal, eps:Float=1e-15f):Boolean
    {
        return (are_similar(this.x, normal.x, eps=eps) and are_similar(this.y, normal.y, eps=eps) and  are_similar(this.z, normal.z, eps=eps))
    }

    /**
     * return the opposite normal
     */
    fun neg():Normal
    {
        return Normal(x=-this.x, y=-this.y, z=-this.z)
    }

    /**
     * Product with a scalar
     */
    operator fun times(factor:Float):Normal
    {
        return Normal(x=factor * this.x, y=factor * this.y, z=factor * this.z)
    }
    operator fun div(factor:Float):Normal
    {
        return Normal(x=this.x / factor, y=this.y / factor, z=this.z / factor)
    }

    operator fun times(factor:Int):Normal
    {
        return Normal(x=factor * this.x, y=factor * this.y, z=factor * this.z)
    }
    operator fun div(factor:Int):Normal
    {
        return Normal(x=this.x / factor, y=this.y / factor, z=this.z / factor)
    }


    /**
     * Scalar product
     */
    operator fun times(vec: Normal):Float
    {
        return (this.x * vec.x + this.y * vec.y + this.z * vec.z)
    }

    /**
     * Vector product (vec x normal)
     */
    operator fun times(vec: Vec):Normal
    {
        return Normal(
            x=this.y * vec.z - this.z * vec.y,
            y=this.z * vec.x - this.x * vec.z,
            z=this.x * vec.y - this.y * vec.x,
        )
    }

    /**
     * Vector product (normal x normal)
     */
    fun prod(normal: Normal):Normal
    {
        return Normal(
            x=this.y * normal.z - this.z * normal.y,
            y=this.z * normal.x - this.x * normal.z,
            z=this.x * normal.y - this.y * normal.x,
        )
    }

    /**
     * Norm of a Normal
     */
    fun norm():Float
    {
        return sqrt(this.squared_norm())
    }

    /**
     * Squared norm
     */
    fun squared_norm():Float
    {
        return this * this
    }

    /**
     * Normalization of a Normal
     */
    fun normalize():Normal
    {

        return (this / (this.norm()))
    }

}
