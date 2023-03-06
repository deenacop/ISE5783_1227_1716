package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    final Point center;

    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }


    @Override
    public Vector getNormal(Point point) {
        Vector V = point.subtract(center);
        return V.normalize();
    }
}
