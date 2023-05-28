package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represents a 3D plane in Cartesian 3D coordinate system
 * implementing the Geometry interface.
 */
public class Plane extends Geometry {

    /**
     * A point on the plane
     */
    final Point q0;

    /**
     * The normal vector to the plane
     */
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
    public Plane(Point v1, Point v2, Point v3) {
        q0 = v1;
        Vector U = v3.subtract(v1);
        Vector V = v2.subtract(v1);
        this.normal = V.crossProduct(U).normalize();
    }

    /**
     * Constructs a plane with a given point on the plane and normal vector to the plane.
     *
     * @param p0     A point on the plane
     * @param vector The normal vector to the plane
     */
    public Plane(Point p0, Vector vector) {
        this.q0 = p0;
        this.normal = vector.normalize();
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
        return getNormal();
    }

    /**
     * Computes the intersections between this plane and a given ray.
     *
     * @param ray the ray to intersect with this plane
     * @return a list of points representing the intersection(s) between the ray and the plane,
     *         or null if no intersection exists
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;
        double nv = n.dotProduct((v)); //nv=n*v

        // ray parallel to plane
        if (isZero(nv)) {
            return null;
        }

        //  ray cannot start from the plane
        if (q0.equals(P0)) {
            return null;
        }

        Vector Q0_P0 = q0.subtract(P0);

        double numerator = n.dotProduct(Q0_P0); //numerator=n*Q0_P0

        //in this case P0 is on the plane, so return null
        if (isZero(numerator)) {
            return null;
        }
        double t = alignZero(numerator / nv); //t=numerator/nv

        //if t>0 the ray does point toward the plane
        if (t > 0) {
            Point point = P0.add(v.scale(t));
            double d = point.distance(P0);

            //if the distance to the intersection is smaller than maxDistance
            if (alignZero(d - maxDistance) <= 0) {
                GeoPoint P = new GeoPoint(this, P0.add(v.scale(t))); //new GeoPoint{geometry=this, point=p0+tv}
                return List.of(P);
            }
        }
        //otherwise it doesn't point toward the plane, so return null
        return null;
    }
}
