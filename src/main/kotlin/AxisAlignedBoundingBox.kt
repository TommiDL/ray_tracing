package org.example

/**
 * Interface that creates an Axis-Aligned-Bounding-Box and verify the intersection with a Ray.
 */
interface AxisAlignedBoundingBox {

    /**
     * Calculate the Axis-Aligned-Bounding-Box based on the vertices of a Shape.
     * @param vertices List of vertices of the shape.
     * @return couple of Points: 1) minimum  2) maximum points of the bounding box.
     */
    fun calculateBoundingBox(vertices: List<Point>): Pair<Vec, Vec>

    /**
     * Verify if there is an intersection between a Ray and the Axis-Aligned-Bounding-Box
     */
    fun ray_intersection(ray: Ray, minPoint: Vec, maxPoint: Vec): Boolean //da sistemare

}

