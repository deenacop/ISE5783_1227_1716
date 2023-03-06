package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public Cylinder(double height, Ray axisRay, double radius) {
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
        return null;
    }
}
