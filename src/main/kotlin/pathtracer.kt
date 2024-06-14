package org.example

import kotlin.math.max

class pathtracer(
    override val world:World,
    override val background_color: Color=Color(0f,0f,0f),
    val pcg:PCG=PCG(),
    val n_ray:Int=10,
    val max_depth:Int=100,
    val russian_roulette_limit:Int=3
):Renderer(world)
{
    override operator fun invoke(ray: Ray):Color
    {

        if (ray.depth>this.max_depth) return Color(0f,0f,0f)

        val hitRecord:HitRecord?=this.world.ray_intersection(ray)

        if (hitRecord==null)
            return Color(0f,0f,0f)

        val hit_material=hitRecord.material
        var hit_color=hit_material.brdf.pigment.get_color(hitRecord.surface_point)

        val emittedRadiance=hit_material.emitted_radiance.get_color(hitRecord.surface_point)

        val hit_color_lum:Float=max(max(hit_color.r, hit_color.g), hit_color.b)

        // russian roulette
        if(ray.depth>this.russian_roulette_limit)
        {
            val q:Float = max(a=0.05f, b=(1f-hit_color_lum))

            if(this.pcg.random_float() >q)
            {
                // Keep the recursion going, but compensate for other potentially discarded rays
                hit_color*=1f/(1f-q)
            }
            else
            {
                // Terminate prematurely
                return emittedRadiance
            }

        }

        //Monte Carlo integration
        var cum_radiance=Color(0f,0f,0f)

        // Only do costly recursions if it's worth it

        if (hit_color_lum>0f)
        {
            for(ray_index in 0 until this.n_ray)
            {
                val new_ray:Ray=hit_material.brdf.scatter_ray(
                    pcg=this.pcg,
                    incoming_dir = hitRecord.ray.dir,
                    interaction_point = hitRecord.world_point,
                    normal = hitRecord.normal,
                    depth = ray.depth+1
                )

                val new_radiance=this(new_ray) //recursive call
                cum_radiance+=hit_color*new_radiance
            }
        }

        return emittedRadiance+cum_radiance*(1f/this.n_ray)
    }
}