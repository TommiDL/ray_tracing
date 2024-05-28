package org.example

open class Pigment(val col:Color)
{
    fun get_color(uv: Vec2D): Color {
        return Color()
    }
}

class UniformPigment(col:Color):Pigment(col)