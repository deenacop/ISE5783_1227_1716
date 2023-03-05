package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    public Plane(Point v1 ,Point v2 , Point v3)
    {
        normal = new Vector(1,1,1);
    }

    public Vector getNormal()
    {
        return normal;
    }

    private Vector normal;
    @Override
    public Vector getNormal(Point point)
    {
        return normal;
    }
}
