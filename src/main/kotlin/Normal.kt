package org.example

data class Normal(var x:Float, var y:Float, var z:Float)
{
    override fun toString(): String {
        return "Normal (x=$x, y=$y, z=$z)"
    }
}
