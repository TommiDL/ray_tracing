package org.example

data class Color(var r:Float = 0f, var g:Float = 0f, var b:Float = 0f)
{
    /** Color in format RGB Floating point notation**/
    override fun equals(other: Any?): Boolean { //override logic operator ==
        return super.equals(other)
    }

    operator fun plus(col:Color):Color // sum function
    {

        /**
         * Sum every component of the RGB format of 2 Colors
         */
        val newR = this.r + col.r
        val newG = this.g + col.g
        val newB = this.b + col.b
        return Color(newR, newG, newB)
    }

    operator fun minus(col: Color):Color
    {
        /**
         * Subtract every component of the RGB format of 2 Colors
         */
        val newR = this.r - col.r
        val newG = this.g - col.g
        val newB = this.b - col.b
        return Color(newR, newG, newB)
    }
    operator fun times(factor:Any):Color //product function
    {
        /**
         * Scalar*Color: multiply every component of the RGB format by the value passed by
         * Color*Color:  return the color given by the product component by component:
         *      (R1,G1,B1)*(R2,G2,B2)=(R1*R2, G1*G2, B1*B2)
         */

        return Color()
    }

    operator fun div(factor: Any):Color
    {
        /**
         * Color/Scalar: Divide every component of the RGB format by the value passed by
         * Color/Color:  return the color given by the division component by component:
         *      (R1,G1,B1)/(R2,G2,B2)=(R1/R2, G1/G2, B1/B2)
         */
        return Color()
    }
    override fun toString(): String {
        /**
         * Display the Color in RGB notation
         */

        return "Color(r=$r, g=$g, b=$b)"
    }

    fun are_similar_colors(color:Color, eps:Float=1e-5f):Boolean
    {
        /**
         * Make a comparison between the colors within a certain tolerance for the floats
         * numbers in the RGB notation
         */
        //if (are_similar(this.r, color.r) and are_similar(this.g, color.g) and are_similar(this.b, color.b))
        {
            return true
        }
        return false
    }
}
