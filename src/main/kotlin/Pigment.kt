package org.example
import kotlin.math.floor
/**
* Class Pigment, associates a Color to a Point of coordinates (u,v) of a 2D parametric surface
 * @param Color
 * @return Pigment
* */
open class Pigment() {

    open fun get_color(uv: Vec2D): Color {
        return Color()
    }
}

/**
* Class UniformPigment:  This class represents a uniform pigment that applies a consistent hue over the entire surface.
 * @param Color
 * @return Pigment
*/
class UniformPigment( color :Color): Pigment() {

    override fun get_color(uv: Vec2D): Color {
        return Color()
        }
}
/**
*class CheckeredPigment:This class represents a checkered pigment that alternates between two colors depending on the position.
 * @param color1 The first color in the checkered pattern.
 * @param color2 The second color in the checkered pattern.
 * @param n_steps The number of steps to divide the surface for the checkered effect.
 * @return Pigment
 */
class CheckeredPigment (val color1 : Color, val color2 : Color, val n_steps: Int)  : Pigment() {
    override fun get_color(uv : Vec2D) : Color {
        val u: Int = floor((uv.u * n_steps)).toInt()
        val v: Int = floor((uv.v * n_steps)).toInt()
        return if ((u % 2) == (v % 2))  color1 else color2
    }

}

/**
* Class ImagePigment: returns a textured pigment, the texture is passed through a PFM image*/
class ImagePigment(val Image : HdrImage) : Pigment() {
    override fun get_color(uv: Vec2D): Color {
        var col = (uv.u * Image.width).toInt()
        var row = (uv.v * Image.height).toInt()
        if (col >= Image.width) {
            col = Image.width -1
        }
        if (row >= Image.height) {
            row = Image.height -1
        }
        return Image.get_pixel(col,row)
    }

}