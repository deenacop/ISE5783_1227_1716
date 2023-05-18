package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * abstract class for intersectable geomotries to implement
 */
public abstract class Intersectable {
    /**
     * @param ray the ray {@link Ray} that intersect with the graphic object
     * @return immutable List of all those points {}
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * find intersection of ray with geometries
     * @param ray the ray that intersects
     * @return list of GeoPoint
     */
    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * helper function for finding intersections
     * @param ray the ray that intersects
     * @return list of GeoPoint
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Inner class that represents a point that intersect with geometries
     */
    public static class GeoPoint {
        // Field represents a geometry that is intersected
        public Geometry geometry;
        // Field represents the intersected point
        public Point point;

        /**
         * Constructor for inner class GeoPoint
         * @param geometry parameter for field geometry
         * @param point parameter for field point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            else return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
