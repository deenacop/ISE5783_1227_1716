package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    final Point q0;
    final Vector normal;



    public Plane(Point v1, Point v2, Point v3){
        q0 = v1;
        Vector U = v2.subtract(v1);
        Vector V = v3.subtract(v1);
        Vector N = U.crossProduct(V);
        N.normalize();
        normal = N;
    }

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }


    public Vector getNormal()
    {
        return normal;
    }

    @Override
    public Vector getNormal(Point point)
    {
        return normal;
    }
}
