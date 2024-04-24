package org.example

class Sphere(val trasformation:Transformation=Transformation()):Shape {

    override fun ray_intersection(ray: Ray): HitRecord? {

        val inv_ray:Ray = ray.transform(this.trasformation.inverse())
        val ray1 =
        if (false) return null
        else return HitRecord()
    }



}