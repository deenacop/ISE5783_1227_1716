package primitives;

import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**

 Ray class represents a ray in Cartesian 3D coordinate system, composed of a starting point and a direction vector.
 */
public class Ray {

    /** The starting point of the ray */
    final Point p0;

    /** The direction vector of the ray */
    final Vector dir;

    /**

     Constructs a ray with a given starting point and direction vector, normalized.
     @param p0 The starting point of the ray
     @param dir The direction vector of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }


    /**

     Returns the starting point of the ray.
     @return The starting point of the ray
     */
    public Point getP0() {
        return p0;
    }
    /**

     Returns the direction vector of the ray.
     @return The direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }
    /**

     Checks whether the given object is equal to this ray.
     @param o The object to compare to this ray
     @return true if the given object is equal to this ray, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }
    /**

     Returns a string representation of this ray.
     @return A string representation of this ray
     */
    @Override
    public String toString() {
        return "Ray:" + p0 + ", " + dir;
    }

    public Point getPoint(double delta) {
        if (isZero(delta)) {
            return p0;
        }
        return p0.add(dir.scale(delta));
    }
}