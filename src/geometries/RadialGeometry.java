package geometries;

public abstract class RadialGeometry implements Geometry{
   final double radius;

    protected RadialGeometry(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
