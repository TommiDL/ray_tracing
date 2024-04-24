package org.example

data class Vec2D(var u:Float=0f, var v:Float=0f) {
    fun is_close(vec2d:Vec2D, eps:Float=1e-5f):Boolean
    {
        return (this.u.is_close(vec2d.u, eps=eps) and this.v.is_close(vec2d.v, eps = eps))
    }
}