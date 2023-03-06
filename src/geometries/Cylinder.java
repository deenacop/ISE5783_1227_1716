package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
   final double height;

    public Cylinder(double height,Ray axisRay,double radius) {
        super(axisRay, radius);
        this.height = height;
    }
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
