package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder class represents a 3D cylinder in Cartesian 3D coordinate system,
 * extending the Tube class.
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder
     */
    final double height;

    /**
     * Fields represent the bottom base of the cylinder
     */
    protected final Plane bottomCap;
    /**
     * Fields represent the top base of the cylinder
     */
    protected final Plane topCap;



    /**
     * Constructor for cylinder
     *
     * @param axisRay parameter for axisRay field
     * @param radius  parameter for radius field
     * @param height  parameter for height field
     */
    public Cylinder(Ray axisRay, double height, double radius) {
        super(axisRay, radius);

        if (height <= 0) {
            throw new IllegalArgumentException("The height should be greater then 0");
        }
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }

        this.height = height;
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);
        bottomCap = new Plane(p0, axisRay.getDir().scale(-1) /* Sets the normal directed outside of the cylinder */);
        topCap = new Plane(p1, axisRay.getDir());
    }

    public double getHeight() {
        return height;
    }

    /**
     * Returns the normal vector to the cylinder at a given point on the cylinder.
     * This method always returns null because the normal vector to a cylinder is
     * undefined at any point on the cylinder's surface.
     *
     * @param point A point on the cylinder
     * @return null
     */
    @Override
    public Vector getNormal(Point point) {
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();

        //if p=p0, then (p-p0) is zero vector
        //returns the vector of the base as a normal
        if (point.equals(p0)) {
            return v.scale(-1);
        }

        double t = v.dotProduct(point.subtract(p0)); //t = v * (p - p0)
        //check if the point on the bottom
        if (isZero(t)) {
            return v.scale(-1);
        }
        //check if the point on the top
        if (isZero(t - height)) {
            return v;
        }

        Point o = p0.add(v.scale(t));  // o = p0 + t * v
        return point.subtract(o).normalize();
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);
        List<GeoPoint> result = null;

        // Find the infinite tube's intersections
        List<GeoPoint> tubePoints = super.findGeoIntersectionsHelper(ray, maxDistance);

        if (tubePoints != null) {
            if (tubePoints.size() == 2) {
                // Checks if the intersection points are on the cylinder
                GeoPoint q0 = tubePoints.get(0);
                GeoPoint q1 = tubePoints.get(1);
                boolean q0Intersects = isBetweenCaps(q0.point);
                boolean q1Intersects = isBetweenCaps(q1.point);

                if (q0Intersects && q1Intersects) {
                    return List.of(new GeoPoint(this, q0.point), new GeoPoint(this, q1.point));
                }

                if (q0Intersects) {
                    result = new LinkedList<>();
                    result.add(new GeoPoint(this, q0.point));
                } else if (q1Intersects) {
                    result = new LinkedList<>();
                    result.add(new GeoPoint(this, q1.point));
                }
            }

            if (tubePoints.size() == 1) {
                // Checks if the intersection point is on the cylinder
                GeoPoint q = tubePoints.get(0);
                if (isBetweenCaps(q.point)) {
                    result = new LinkedList<>();
                    result.add(new GeoPoint(this, q.point));
                }
            }
        }

        // Finds the bottom cap's intersections
        List<GeoPoint> cap0Point = bottomCap.findGeoIntersections(ray);
        if (cap0Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap0Point.get(0);
            if (gp.point.distanceSquared(p0) < radius * radius) {
                if (result == null) {
                    result = new LinkedList<>();
                }

                result.add(new GeoPoint(this, gp.point));
                if (result.size() == 2) {
                    return result;
                }
            }
        }

        // Finds the top cap's intersections
        List<GeoPoint> cap1Point = topCap.findGeoIntersections(ray);
        if (cap1Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap1Point.get(0);
            if (gp.point.distanceSquared(p1) < radius * radius) {
                if (result == null) {
                    return List.of(new GeoPoint(this, gp.point));
                }

                result.add(new GeoPoint(this, gp.point));
            }
        }

        return result;
    }

    /**
     * Helper function that checks if a points is between the two caps (not on them, even on the edge)
     *
     * @param p The point that will be checked.
     * @return True if it is between the caps. Otherwise, false.
     */
    private boolean isBetweenCaps(Point p) {
        Vector v0 = axisRay.getDir();
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);

        // Checks against zero vector...
        if (p.equals(p0) || p.equals(p1)) {
            return false;
        }

        return v0.dotProduct(p.subtract(p0)) > 0 &&
                v0.dotProduct(p.subtract(p1)) < 0;
    }

}
