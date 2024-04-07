package org.example

data class Point(var x:Float=0f, var y:Float=0f, var z:Float=0f){
    /**
     * this function converts to a string the values of the Point class
     */
    override fun toString():String{
        return "Point (x$x, y$y, z$z)"
    }
    /**
     * check if two Points are similar within a certain precision
     */
    fun are_close(a:Point):Boolean{
        return (are_similar(this.x, a.x, eps = 1e-5f) and are_similar(this.y, a.y, eps = 1e-5f) and are_similar(this.z, a.z, eps = 1e-5f))
    }





}
