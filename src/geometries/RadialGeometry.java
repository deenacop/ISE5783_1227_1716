package geometries;

/**
 * RadialGeometry is an abstract class that represents 3D geometries with a radial
 * property, such as spheres and tubes.
 */
public abstract class RadialGeometry implements Geometry {

    /** The radius of the geometry */
    final double radius;

    /**
     * Constructs a new radial geometry with a given radius.
     *
     * @param radius The radius of the geometry
     */
    protected RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * Returns the radius of the geometry.
     *
     * @return The radius of the geometry
     */
    public double getRadius() {
        return radius;
    }
}

