package org.example

/**
 * Vec2D class which returns a 2D vector:
 * useful for declaring intersection points
 */
data class Vec2D(var u:Float=0f, var v:Float=0f) {

    /**
     * Comparison between 2 Vec2D objects
     */
    fun is_close(vec2d:Vec2D, eps:Float=1e-5f):Boolean
    {
        return (are_similar(this.u, vec2d.u, eps=eps) and are_similar(this.v, vec2d.v, eps=eps))
    }

}