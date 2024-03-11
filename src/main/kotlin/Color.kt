package org.example

//data class  --> override operatori
data class Color(var r:Float = 0f, var g:Float = 0f, var b:Float = 0f)
{
    /** Color in format RGB Floating point notation**/
    override fun equals(other: Any?): Boolean { //override operatore logico ==
        /**
         * override == function
         */

        if (other is Color) {
            return ((this.r == other.r) and (this.g == other.g) and (this.b == other.b))

        }

        return false
    }

    operator fun plus(col:Color):Color //funzione somma
    {
        /**
         * Sum every component of the RGB format of 2 Colors
         */
        return Color()
    }

    operator fun minus(col: Color):Color
    {
        /**
         * Minus: (R1,G1, B1) - (R2,G2,B2) = (R1-R2, G1-G2, B1-B2)
         */
        return Color(r=this.r-col.r, g=this.g-col.g, b=this.b-col.b)
    }
    operator fun times(factor:Float):Color //funzione prodotto scalare
    {
        /**
         * Scalar*Color: multiply every component of the RGB format by the value passed by
         */
        return Color(r = factor * this.r, g = factor * this.g, b = factor * this.b)
    }

    operator fun times(factor:Int):Color //funzione prodotto scalare
    {
        /**
         * Scalar*Color: multiply every component of the RGB format by the value passed by
         */
        return Color(r = factor * this.r, g = factor * this.g, b = factor * this.b)
    }


    operator fun times(factor: Color):Color
    {
        /**
         * Color*Color:  return the color given by the product component by component:
         *      (R1,G1,B1)*(R2,G2,B2)=(R1*R2, G1*G2, B1*B2)
        */
        // tensor product
        return Color(r=factor.r*this.r, g=factor.g*this.g, b=factor.b*this.b,)
    }

    operator fun div(factor: Float):Color
    {
        /**
         * Color/Scalar: Divide every component of the RGB format by the value passed by
         */
        return Color(r=this.r/factor, g=this.g/factor, b=this.b/factor)
    }


    operator fun div(factor: Int):Color
    {
        /**
         * Color/Scalar: Divide every component of the RGB format by the value passed by
         */
        return Color(r=this.r/factor, g=this.g/factor, b=this.b/factor)
    }

    /*    operator fun div(factor: Color):Color
    {
        /**
         * Color/Color:  return the color given by the division component by component:
         *      (R1,G1,B1)/(R2,G2,B2)=(R1/R2, G1/G2, B1/B2)
         */
        if (factor.r or)

    }
*/
    override fun toString(): String {
        /**
         * Display the Color in RGB notation
         */

        return "(${this.r}, ${this.g}, ${this.b})"
    }


    fun are_similar_colors(color:Color, eps:Float=1e-5f):Boolean
    {
        /**
         * Make a comparison between the colors within a certain tolerance for the floats
         * numbers in the RGB notation
         */


        return true
    }
}

