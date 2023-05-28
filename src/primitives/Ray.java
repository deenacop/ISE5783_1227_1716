package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;


import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**

 Ray class represents a ray in Cartesian 3D coordinate system, composed of a starting point and a direction vector.
 */
public class Ray {

    private static final double DELTA = 0.1;
    /** The starting point of the ray */
    final Point p0;

    /** The direction vector of the ray */
    final Vector dir;

    /**

     Constructs a ray with a given starting point and direction vector, normalized.
     @param p0 The starting point of the ray
     @param dir The direction vector of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * construct a ray and move point slightly
     *
     * @param point     the point
     * @param direction direction vector
     * @param n         normal
     */
    public Ray(Point point, Vector direction, Vector n) {
        double nv = alignZero(direction.dotProduct(n));
        if (nv < 0) {
            this.p0 = point.add(n.scale(-DELTA));

        } else {
            this.p0 = point.add(n.scale(DELTA));
        }
        this.dir = direction.normalize();
    }


    /**

     Returns the starting point of the ray.
     @return The starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    public Point getP0(double d) {
        return p0.add(dir.scale(d));
    }

    /**

     Returns the direction vector of the ray.
     @return The direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }
    /**

     Checks whether the given object is equal to this ray.
     @param o The object to compare to this ray
     @return true if the given object is equal to this ray, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }
    /**

     Returns a string representation of this ray.
     @return A string representation of this ray
     */
    @Override
    public String toString() {
        return "Ray:" + p0 + ", " + dir;
    }

    public Point getPoint(double delta) {
        if (isZero(delta)) {
            return p0;
        }
        return p0.add(dir.scale(delta));
    }


    /**
     * Finds the closet Point that is intersected
     *
     * @param points the list of points in which to find the closest one
     * @return the closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closet GeoPoint that is intersected
     *
     * @param geoPointList the list of geoPoints in which to find the closest one
     * @return the closest geoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        if (geoPointList == null)
            return null;

        GeoPoint result = null;
        double distance = Double.MAX_VALUE;
        double d;
        for (var pt : geoPointList) {
            d = pt.point.distanceSquared(p0);
            if (d < distance) {
                distance = d;
                result = pt;
            }
        }
        return result;
    }
}