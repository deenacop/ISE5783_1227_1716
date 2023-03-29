package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere class represents a 3D sphere in Cartesian 3D coordinate system
 * extending the RadialGeometry abstract class.
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere
     */
    final Point center;

    /**
     * Constructs a sphere with a given radius and center point.
     *
     * @param radius The radius of the sphere
     * @param center The center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector to the sphere at a given point on its surface.
     *
     * @param point The point on the sphere's surface to get the normal vector at
     * @return The normal vector to the sphere at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        Vector V = point.subtract(center);
        return V.normalize();
    }
}
