package org.example

class HitRecord(val world_point:Point, val normal:Normal, val surface_point:Vec2D, val t:Float, val ray:Ray) {

    fun createHitRecord(var inv_ray:Ray,
                        var first_hit_t:Float,
                        var transformation:Transformation,
                        var ray:Ray):HitRecord
    {

        val hit_point:Point = inv_ray.at(first_hit_t)

        return HitRecord(world_point = this.transformation * hit_point,
            normal = this.transformation * _sphere_normal(hit_point, ray.dir),
            surface_point = _sphere_point_to_uv(hit_point),
            t = first_hit_t ,
            ray = ray,
            )
    }

    fun is_close(hit_record:HitRecord, eps:Float=1e-5f):Boolean
    {
        return (this.world_point.is_close(hit_record.world_point, eps=eps) and
                this.normal.is_close(hit_record.dir, eps = eps) and
                this.surface_point.is_close(hit_record.surface_point, eps = eps)
                )
    }

}