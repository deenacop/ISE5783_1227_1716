package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

import static primitives.Util.alignZero;

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

    /**
     * calculates number of intersections of ray with a sphere
     * @param ray pointing towards the graphic object
     * @return list of points
     */
    @Override
    public List<Point> findIntersections(Ray ray)
    {

        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        //if the ray is starting from precisely the center of the sphere
        // than we know the intersection point will be scaling the point by the radius
        if (P0.equals(center))
        {
            return List.of(center.add(v.scale(radius)));
        }

        Vector U = center.subtract(P0); // The vector from p0 to the center of the sphere
        double tm =alignZero(v.dotProduct(U)); // the scalar for the projection of v on u
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm)); // the distance of the center to the ray

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d)); // distance from p1 to intersection with d
        double t1 = alignZero(tm - th); // from p0 to p1
        double t2 = alignZero(tm + th);// from p0 to p2

        if (t1 > 0 && t2 > 0) // take only t > 0 (going in the right direction)
        {
//            Point P1 = P0.add(v.scale(t1));
//            Point P2 = P0.add(v.scale(t2));
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(P1, P2);
        }
        if (t1 > 0) {
//            Point P1 = P0.add(v.scale(t1));
            Point P1 =ray.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
//            Point P2 = P0.add(v.scale(t2));
            Point P2 =ray.getPoint(t2);
            return List.of(P2);
        }
        return null;

    }
}
