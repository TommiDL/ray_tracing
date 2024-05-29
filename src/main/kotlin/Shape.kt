package org.example

open class Shape(
    open val transformation: Transformation=Transformation(),
    open val material: Material=Material(),
)
{

    open fun ray_intersection(ray: Ray): HitRecord?
    {
        return null
    }

}