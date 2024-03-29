package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * The Geometries class represents a collection of intersectable geometries,
 * which can be used to calculate the intersections of a ray with multiple geometries.
 */
public class Geometries extends Intersectable {

    /**
     * The list of intersectable geometries in this collection.
     */
    protected List<Intersectable> intersectablesList;

    /**
     * Constructs an empty collection of geometries.
     */
    public Geometries() {
        intersectablesList = new LinkedList<>();
    }

    /**
     * Constructs a collection of geometries from an array of intersectables.
     *
     * @param intersectables the intersectables to add to the collection
     */
    public Geometries(Intersectable... intersectables) {
        intersectablesList = new LinkedList<>();
        Collections.addAll(intersectablesList, intersectables);
    }

    /**
     * Adds one or more intersectables to this collection.
     *
     * @param intersectables the intersectables to add to the collection
     */
    public void add(Intersectable... intersectables) {
        Collections.addAll(intersectablesList, intersectables);
    }

    /**
     * Calculates the intersections between this collection of geometries and a given ray.
     *
     * @param ray the ray to intersect with the geometries
     * @return a list of points representing the intersection(s) between the ray and the geometries,
     * or null if no intersection exists
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;

        //iterate over the list of the geometries and find the intersections for each one
        //add the results to list "result"
        for (Intersectable item : intersectablesList) {
            List<GeoPoint> itemGeoPoints = item.findGeoIntersections(ray, maxDistance);
            if (itemGeoPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemGeoPoints);
            }
        }
        return result;
    }
}


