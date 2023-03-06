package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Plane class represents a 3D plane in Cartesian 3D coordinate system
 * implementing the Geometry interface.
 */
public class Plane implements Geometry {

    /** A point on the plane */
    final Point q0;

    /** The normal vector to the plane */
    final Vector normal;

    /**
     * Constructs a plane from three points on the plane.
     * The normal vector to the plane is calculated as the cross product of the two vectors
     * formed by subtracting the first point from the second and third points, respectively.
     *
     * @param v1 A point on the plane
     * @param v2 A point on the plane
     * @param v3 A point on the plane
     */
    public Plane(Point v1, Point v2, Point v3){
        q0 = v1;
        Vector U = v2.subtract(v1);
        Vector V = v3.subtract(v1);
        Vector N = U.crossProduct(V);
        N.normalize();
        normal = N;
    }

    /**
     * Constructs a plane with a given point on the plane and normal vector to the plane.
     *
     * @param q0 A point on the plane
     * @param normal The normal vector to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Returns the normal vector to the plane at a given point on the plane.
     * Since the normal vector to a plane is the same at all points on the plane,
     * this method always returns the normal vector to the plane.
     *
     * @param point A point on the plane
     * @return The normal vector to the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
