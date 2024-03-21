package org.example

import kotlin.math.abs

fun are_similar(a:Float, b:Float, eps:Float=1e-5f):Boolean
{
    /**
     * check if two float are similar within a certain precision
     */
    return (kotlin.math.abs(a-b)<eps)

}


fun are_similar(a:Double, b:Double, eps:Float=1e-5f):Boolean
{
    /**
     * check if two float are similar within a certain precision
     */
    return (kotlin.math.abs(a-b)<eps)

}

