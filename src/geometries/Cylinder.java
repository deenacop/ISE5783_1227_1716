package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Cylinder class represents a 3D cylinder in Cartesian 3D coordinate system,
 * extending the Tube class.
 */
public class Cylinder extends Tube {

    /** The height of the cylinder */
    final double height;

    /**
     * Constructs a cylinder with a given height, axis ray, and radius.
     *
     * @param height The height of the cylinder
     * @param axisRay The axis ray of the cylinder
     * @param radius The radius of the cylinder
     */
    public Cylinder(Ray axisRay ,double height,  double radius) {
        super(axisRay, radius);
        this.height = height;
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
}
