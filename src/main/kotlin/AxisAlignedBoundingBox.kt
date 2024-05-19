package org.example

/**
 * Interface that creates an Axis-Aligned-Bounding-Box and verify the intersection with a Ray.
 */
interface AxisAlignedBoundingBox {

    var xmin:Float
    var xmax:Float

    var ymin:Float
    var ymax:Float

    var zmin:Float
    var zmax:Float
    /**
     * Calculate the Axis-Aligned-Bounding-Box based on the vertices of a Shape.
     * @param vertices List of vertices of the shape.
     * @return couple of Points: 1) minimum  2) maximum points of the bounding box.
     *
     * to override in child class
     *
     */
    fun calculateBoundingBox(vertices: List<Point>): Pair<Vec, Vec>

    /**
     * Verify if there is an intersection between a Ray and the Axis-Aligned-Bounding-Box
     */
    fun ray_intersection(ray: Ray, minPoint: Vec, maxPoint: Vec): Boolean
    {

        // x intersection
        val txmin = (xmin-ray.origin.x)/ray.dir.x
        val txmax = (xmax-ray.origin.x)/ray.dir.x

        // y intersection
        val tymin = (ymin-ray.origin.y)/ray.dir.y
        val tymax = (ymax-ray.origin.y)/ray.dir.y

        // x intersection
        val tzmin = (zmin-ray.origin.z)/ray.dir.z
        val tzmax = (zmax-ray.origin.z)/ray.dir.z


        return (
                interval_intersection(txmin, txmax, tymin, tymax) and
                interval_intersection(txmin, txmax, tzmin, tzmax) and
                interval_intersection(tymin, tymax, tzmin, tzmax)
                )
    }

}

