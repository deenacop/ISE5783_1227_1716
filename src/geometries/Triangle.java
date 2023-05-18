package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        //first find the intersections with the plane in which the triangle lays
        List<GeoPoint> intersections = plane.findGeoIntersections(ray);

        //if the plane has no intersections so there are no intersections, so return null
        if (intersections == null)
            return null;//there are no intersection points

        Point p0 = ray.getP0();//the start ray point
        Vector v = ray.getDir();

        Vector v1 = vertices.get(0).subtract(p0);//vector from the ray start point to the polygon vertices
        Vector v2 = vertices.get(1).subtract(p0);//vector from the ray start point to the polygon vertices
        Vector v3 = vertices.get(2).subtract(p0);//vector from the ray start point to the polygon vertices

        double s1 = v.dotProduct(v1.crossProduct(v2)); //s1 = v * (v1 X v2)
        if (isZero(s1))
            return null;//the point is out of triangle

        double s2 = v.dotProduct(v2.crossProduct(v3)); //s2 = v * (v2 X v3)
        if (isZero(s2))
            return null;//the point is out of triangle

        double s3 = v.dotProduct(v3.crossProduct(v1)); //s3 = v * (v3 X v1)
        if (isZero(s3))
            return null;//the point is out of triangle

        //update the geometry
        for (GeoPoint geoPoint : intersections) {
            geoPoint.geometry = this;
        }

        //if they all have the same sign then return the intersections , otherwise return null
        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;
    }

}
