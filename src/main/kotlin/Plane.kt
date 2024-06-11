package org.example

import kotlin.math.truncate

/**
*A 3D infinite plane parallel to the x and y axis and passing through the origin
* */
class Plane(
    override val transformation:Transformation=Transformation(),
    override val material: Material=Material(),
):Shape(transformation, material) {
    /**
     * checks if a ray intersects the plane
     * @param Ray
     * @return HitRecord
     */


    override fun ray_intersection(ray: Ray): HitRecord? {
        val inv_ray:Ray = ray.transform(this.transformation.inverse())

        if (inv_ray.dir.z == 0f ) return null

        val t:Float = - ((inv_ray.origin.z)/(inv_ray.dir.z))
        if (t < inv_ray.tmin || t > inv_ray.tmax) return null  // intersection is out the limits of the ray
        val hit_point:Point=inv_ray.at(t)

        return HitRecord (
            world_point = this.transformation * hit_point,
            normal = _plane_normal(hit_point, inv_ray.dir),
            surface_point = _plane_point_to_uv(hit_point),
            t=t,
            ray = ray,
            material = this.material
        )
    }
    /**
     * Define the Normal of the plane
     * @param Point, ray_dir
     * @return Normal
     */
    fun _plane_normal(point:Point, ray_dir:Vec):Normal
    {
        val res:Normal=this.transformation*Normal(x = 0f, y = 0f, z = 1f)
        return if ( ray_dir.z < 0f) res else res*(-1)
    }
    /**
     * Return the 2D (u,v) coordinates of a pixel on the screen
     * @param Point
     * @return Vec2D
     */
    fun _plane_point_to_uv(point:Point):Vec2D {
        val u = point.x - truncate(point.x)
        val v = point.y - truncate(point.y)
        return Vec2D(u=u, v=v)
    }

    }
