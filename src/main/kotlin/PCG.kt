package org.example

/**
 * PCG class which simulates a random number generator:
 * takes two 64-bit unsigned integers as input (state & inc)
 */
data class PCG(var state: ULong = 0u, var inc: ULong = 0u) {

    /**
     * block to be executed immediately after the primary constructor
     */
    init {
        this.state = 0u
        this.inc = ((54u shl 1) or 1u).toULong() // shl: Shifts this value left by the bitCount(=1) number of bits
        this.random()   // Throw a random number and discard it
        this.state += 42u
        this.random()   // Throw a random number and discard it
    }

    /**
     * Function for a PCG random number generator:
     * uses unsigned long variables
     * returns UInt
     */
    fun random(): UInt {
        val oldState = this.state
        this.state = (oldState * 6364136223846793005u + this.inc)
        val xorShifted = (((oldState shr 18) xor oldState) shr 27).toUInt() // shr: Shifts this value right by the bitCount(=27) number of bits
        val rot = (oldState shr 59).toUInt() // 32-bit variable

        // Rotation with a wrap
        return (xorShifted shr rot.toInt()) or (xorShifted shl ((-rot.toInt()) and 31))
    }

}
