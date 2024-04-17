package org.example

data class Point(var x:Float=0f, var y:Float=0f, var z:Float=0f){
    /**
     * this function converts to a string the values of the Point class
     */
    override fun toString():String{
        return "Point (x=$x, y=$y, z=$z)"
    }
    /**
     * check if two Points are similar within a certain precision
     */
    fun is_close(a:Point, eps:Float = 1e-5f):Boolean{
        return (are_similar(this.x, a.x, eps = eps) and are_similar(this.y, a.y, eps=eps) and are_similar(this.z, a.z, eps = eps))
    }


    /**
     * Sum between a Point and a Vector, returns a Point
     */
    operator fun plus (a:Vec):Point{
        return Point(x=this.x + a.x, y=this.y + a.y, z=this.z + a.z)

    }

    /**
     * Difference between Points returns a Vec
     */
    operator fun minus(a:Point):Vec
    {
        return Vec(x=this.x - a.x, y=this.y - a.y, z=this.z - a.z)
    }

    /**
     * Difference between Point and a Vec, return a Point
     */
    operator fun minus(a:Vec):Point
    {
        return Point(x=this.x - a.x, y=this.y - a.y, z=this.z - a.z)
    }
    /**
     * Function that make a conversion from Point to Vec
     */
    fun conversion():Vec
    {
    return Vec(x = this.x, y=this.y, z = this.z)
    }
}
