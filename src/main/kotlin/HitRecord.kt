package org.example

class HitRecord(
    val world_point:Point,
    val normal:Normal,
    val surface_point:Vec2D,
    val t:Float,
    val ray:Ray,
    ) {

    fun is_close(hit_record:HitRecord, eps:Float=1e-5f):Boolean
    {
        return (this.world_point.is_close(hit_record.world_point, eps=eps) and
                this.normal.is_close(hit_record.normal, eps = eps) and
                this.surface_point.is_close(hit_record.surface_point, eps = eps) and
                are_similar(this.t, hit_record.t, eps = eps) and
                ray.is_close(hit_record.ray, eps=eps)
                )
    }

}