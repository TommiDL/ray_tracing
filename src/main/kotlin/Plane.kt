package org.example

import kotlin.math.sqrt
import kotlin.math.truncate

/**
*A 3D infinite plane parallel to the x and y axis and passing through the origin
* */
class Plane(val trasformation:Transformation=Transformation()):Shape {
    /**
     * checks if a ray intersects the plane
     * @param Ray
     * @return HitRecord
     */
    override fun ray_intersection(ray: Ray): HitRecord? {
        val inv_ray:Ray = ray.transform(this.trasformation.inverse())
        if (inv_ray.dir.z == 0f ) return null
        val t:Float = - ((inv_ray.origin.z)/(inv_ray.dir.z))

        val hit_point:Point=inv_ray.at(t)

        return HitRecord (
            world_point = this.trasformation * hit_point,
            normal = _plane_normal(hit_point, inv_ray.dir),
            surface_point = _plane_point_to_uv(hit_point),
            t=t,
            ray = ray,
        )
    }
    /**
     * Define the Normal of the plane
     * @param Point, ray_dir
     * @return Normal
     */
    fun _plane_normal(point:Point, ray_dir:Vec):Normal
    {
        val res:Normal=this.trasformation*Normal(x = 0f, y = 0f, z = 1f)
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
        return Vec2D(u, v )
    }

    }
