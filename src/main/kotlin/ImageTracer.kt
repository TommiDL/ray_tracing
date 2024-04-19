package org.example

/**
 * Interface between camera and screen
 */
class ImageTracer(val image:HdrImage, val camera: Camera)
{
    /**
     * fire a ray in a given pixel specifying the target's position in the pixel
     */
    fun fire_ray(col:Int, row:Int, u_pixel:Float=0.5f, v_pixel:Float=0.5f):Ray
    {
        val u:Float=(col+u_pixel)/this.image.width-1
        val v:Float=(row+v_pixel)/this.image.height-1

        return this.camera.fire_ray(u=u, v=v)
    }

    /**
     * fire a ray in every pixel of the image with a given function for the rendering of the ray
     */
    fun fire_all_ray(func:(Ray)->Color)
    {
        for(row in 0 until this.image.height)
        {
            for(col in  0 until this.image.width)
            {
                val ray:Ray=this.fire_ray(col, row)
                val color:Color=func(ray)

                this.image.set_pixel(col, row, color)
            }
        }
    }
}