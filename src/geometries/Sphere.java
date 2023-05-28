package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        //if P0 is the center of the sphere, return the radius to there
        if (P0.equals(center)) {
            //center+(radius*v)
            Point point = center.add(v.scale(radius));
            double dis = point.distance(ray.getP0());
            if (alignZero(dis - maxDistance) <= 0) //if the distance is less than the maximum
                return List.of(new GeoPoint(this, point));
        }

        Vector U = center.subtract(P0); //u=center-p0

        double tm = alignZero(v.dotProduct(U)); //tm=v*u
        //d is the distance from the center to the ray
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm)); //d=squrt(|u|^2-tm^2)

        //if d is larger are equals radius, then there are no intersections
        if (d >= radius) {
            return null;
        }

        //th is the last part of the triangle between radius and d, calculate according to pythagoras
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th); //t1=tm-th
        double t2 = alignZero(tm + th); //t2=tm+th

        if (t1 > 0 && t2 > 0) { //if they are both positive then there are 2 intersections
            Point P1 = ray.getP0(t1); //p1=p0+t1*v
            Point P2 = ray.getP0(t2); //p2=p0+t2*v

            List<GeoPoint> list = null;

            double dis = P1.distance(ray.getP0());
            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                list = new LinkedList<>();
                list.add(new GeoPoint(this, P1));
            }

            dis = P2.distance(ray.getP0());
            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                if (list == null) list = new LinkedList<>();
                list.add(new GeoPoint(this, P2));

            }

            return list;
        }
        //otherwise if only one is positive so there is one intersection
        if (t1 > 0) {
            Point P1 = ray.getPoint(t1); //p1=p0+t1*v
            double dis = P1.distance(ray.getP0());
            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                return List.of(new GeoPoint(this, P1));
            }
        }
        if (t2 > 0) {
            Point P2 = ray.getPoint(t2); //p2=p0+t2*v
            double dis = P2.distance(ray.getP0());
            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                return List.of(new GeoPoint(this, P2));
            }
        }

        //if they are both negative then there are no intersections
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0 && Objects.equals(center, sphere.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }

}
