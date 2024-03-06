package org.example

data class Color(var r:Float = 0f, var g:Float = 0f, var b:Float = 0f)
{
    /** Color in format RGB Floating point notation**/
    override fun equals(other: Any?): Boolean { //override operatore logico ==
        return super.equals(other)
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
        return Color()
    }
    operator fun times(factor:Any):Color //funzione prodotto
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

        return super.toString()
    }

    fun are_similar_colors(color:Color, eps:Float):Boolean
    {
        /**
         * Make a comparison between the colors within a certain tolerance for the floats
         * numbers in the RGB notation
         */
        return true
    }
}
>>>>>>> 6e3570e1d20a195b7e954020784783f1b775c480
