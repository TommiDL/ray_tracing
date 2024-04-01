package org.example

data class HomMatrix(var width:Int=2, var height:Int=2)
{
    var matrix:Array<Float?> = arrayOfNulls (size=this.width*this.height)

    /**
     * Get access to pivot (x,y)
     */
    fun get(x:Int, y:Int):Float?
    {
        return matrix.get(y*this.width+x)
    }

    /**
     * Set pivot (x,y)
     */
    fun set(x:Int, y:Int, float: Float)
    {
        matrix.set(y * this.width + x, float)
    }

    /**
     * Set the matrix with the array of pivots
     *
     * the order is given by concatenate rows
     */
    fun set(array: Array<Float>)
    {
        if (array.size==matrix.size)
        {
            for(i in 0 until array.size)
                matrix[i]=array[i]
        }
        else
        {
            throw ArrayIndexOutOfBoundsException("The size of the array is not compatible with the matrix dimension\n${array.toString()} ")
        }
    }

    /**
     * Get row of the matrix
     */
    fun row(x:Int): Array<Float?> {
        if(x<this.height)
            return matrix.sliceArray(x*this.width..<(x+1)*this.width)
        else
            throw IndexOutOfBoundsException("The index $x is outside the matrix height ${this.height}")
    }

    /**
     * Get column of the matrix
     */
    fun col(x:Int): Array<Float?> {
        if(x<this.width)
            val ind:Array<Int> = Array<Int>(size = this.height)
        return matrix.sliceArray(x*this.width..<(x+1)*this.width)
        else
            throw IndexOutOfBoundsException("The index $x is outside the matrix height ${this.height}")
    }


    /**
     * Product matrix-vector
     */
    operator fun times(vec: Array<Float>): Array<Float?>
    {
        if(vec.size==this.height)
        {
            var res:Array<Float?> = arrayOfNulls<Float?>(this.height)

            for(i in 0 until this.height)
            {
                for(j in 0 until this.width)
                {
                    res[i]= matrix[i*width+j]?.times(vec[i]) ?: throw NullPointerException("The matrix is empty")
                }
            }
            return res
        } else
        {
            throw UnsupportedOperationException("The size of the array is not compatible with the matrix")
        }
    }

    operator fun times(p:Point):Point
    {

    }
}

data class Transformation(var matrix:HomMatrix, var invmatrix:HomMatrix)
