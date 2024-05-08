package org.example

import kotlin.math.*

data class Triangle(val A: Point = Point(1f, 0f, 0f), val B: Point = Point(0f, 1f, 0f), val C:Point = Point(0f, 0f, 1f)) {

    fun get_point(beta:Float, gamma:Float):Point{
        if((0f <= beta) && (beta <= 1f) && (0f <= gamma) && (gamma <= 1f)) {
            return (this.A + (this.B - this.A) * beta + (this.C - this.A) * gamma)
        }
        else {
            throw IllegalArgumentException("Point is outside the triangle! beta and gamma must be between 0 and 1")
        }
    }

    operator fun invoke(beta:Float, gamma: Float):Point{
        if((0f <= beta) && (beta <= 1f) && (0f <= gamma) && (gamma <= 1f)) {
            return (this.A + (this.B - this.A) * beta + (this.C - this.A) * gamma)
        }
        else {
            throw IllegalArgumentException("Point is outside the triangle! beta and gamma must be between 0 and 1")
        }
    }

}
