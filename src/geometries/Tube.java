package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Tube class represents a 3D tube in Cartesian 3D coordinate system
 * extending the RadialGeometry abstract class.
 */
public class Tube extends RadialGeometry {

    /**
     * The axis ray of the tube
     */
    final Ray axisRay;

    /**
     * Constructs a tube with a given axis ray and radius.
     *
     * @param axisRay The axis ray of the tube
     * @param radius  The radius of the tube
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

        Vector N = point.subtract(O);
        return N.normalize();
    }

    /**

     Finds the intersection points between a given ray and a tube (i.e., a cylinder with open ends).
     The tube is defined by an axis ray and a radius, and the method uses a quadratic equation to solve for the intersection points.
     If there are no intersection points, the method returns null.
     @param ray The input ray to find intersections with
     @return A list of intersection points between the ray and the tube, or null if there are no intersections
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector vAxis = axisRay.getDir(); //direction of the axis ray
        Vector v = ray.getDir(); //direction of ray
        Point p0 = ray.getP0(); //point of ray

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis)); //vVa=v*vAxis
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) { // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        } else {
            vVaVa = vAxis.scale(vVa); //vVAVA=vVa*vAxis
            try {
                vMinusVVaVa = v.subtract(vVaVa); //v-vVaVa
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0()); //deltaP=p0-pAxis
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            // the ray is orthogonal to Axis, only one intersection on edge
            if (vVa == 0) {
                return List.of(new GeoPoint(this, ray.getPoint(radius))); //return paxis+radius*vaxis
            }

            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis)); //dpvaxis=deltap*vaxis
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0) {
            dPMinusdPVaVa = deltaP;
        } else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return t == 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        else // t2 is behind the head
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
    }
}
