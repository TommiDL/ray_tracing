package org.example

import kotlin.math.withSign

fun are_similar(a:Float, b:Float, eps:Float=1e-5f):Boolean
{
    /**
     * check if two float are similar within a certain precision
     */
    return (kotlin.math.abs(a-b)<eps)

}


fun are_similar(a:Double, b: Double, eps:Float=1e-5f):Boolean
{
    /**
     * check if two float are similar within a certain precision
     */
    return (kotlin.math.abs(a-b)<eps)

}



fun are_matr_close(m1:HomMatrix, m2:HomMatrix, eps:Float=1e-5f):Boolean
{
    if((m1.width!=m2.width) or (m1.height!=m2.height))
        return false

    var close:Boolean=true

    for(i in 0 until m1.width)
    {
        for(j in 0 until m1.height)
        {
            close = (close) and (are_similar(m1[i,j], m2[i,j], eps = eps) )
        }
    }

    return close
}

fun interval_intersection(xmin:Float, xmax:Float, ymin:Float, ymax:Float):Boolean
{
    val min1:Float
    val min2:Float
    val max1:Float
    val max2:Float

    if(xmin>xmax) {
        max1 = xmin
        min1 = xmax
    } else{
        max1=xmax
        min1=xmin
    }

    if(ymin>ymax) {
        max2 = ymin
        min2 = ymax
    } else{
        max2=ymax
        min2=ymin
    }


    if( (min2>max1) or (min1>max2) or (max2<min1) or (max1<min2) ) {
        return false
    }

    return true
}

/**
 * Creation of a OrtoNormalBase from Normal
 */
fun create_onb_from_z(normal: Normal):Array<Vec>
{
    val sign:Float = 1f.withSign(normal.z)

    val a:Float = -1f/(sign+normal.z)

    val b:Float = normal.x * normal.y * a

    val e1:Vec = Vec(
        x=1+sign*normal.x*normal.x*a,
        y=sign*b,
        z = -sign*normal.x,
    )

    val e2:Vec=Vec(
        x=b,
        y=sign+normal.y*normal.y*a,
        z=-normal.y
    )

    return arrayOf<Vec>(e1, e2, Vec(
        x=normal.x,
        y=normal.y,
        z=normal.z
    ))
}

/**
 * Creation of a OrtoNormalBase from Vec
 */
fun create_onb_from_z(normal: Vec):Array<Vec>
{
    val sign:Float = 1f.withSign(normal.z)

    val a:Float = -1f/(sign+normal.z)

    val b:Float = normal.x * normal.y * a

    val e1:Vec = Vec(
        x=1+sign*normal.x*normal.x*a,
        y=sign*b,
        z = -sign*normal.x,
    )

    val e2:Vec=Vec(
        x=b,
        y=sign+normal.y*normal.y*a,
        z=-normal.y
    )

    return arrayOf<Vec>(e1, e2, Vec(
        x=normal.x,
        y=normal.y,
        z=normal.z
    ))
}