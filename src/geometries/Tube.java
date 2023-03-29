package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class represents a 3D tube in Cartesian 3D coordinate system
 * extending the RadialGeometry abstract class.
 */
public class Tube extends RadialGeometry {

    /** The axis ray of the tube */
    final Ray axisRay;

    /**
     * Constructs a tube with a given axis ray and radius.
     *
     * @param axisRay The axis ray of the tube
     * @param radius The radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Returns the normal vector to the tube at a given point on its surface.
     * Since a tube can have an infinite number of normal vectors at any given point,
     * this method always returns null.
     *
     * @param point The point on the tube's surface to get the normal vector at
     * @return Always returns null since a tube has no single normal vector at a given point
     */
    @Override
    public Vector getNormal(Point point) {
        Vector P0_point = point.subtract(axisRay.getP0());
        Vector v = axisRay.getDir();

        double t = v.dotProduct(P0_point);  // finding scaler for the projection of point on axisRay

        Point O = axisRay.getPoint(t);   // O is the projection of point on axisRay

        Vector N=point.subtract(O);
        return N.normalize();
    }
}