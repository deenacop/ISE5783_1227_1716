package primitives;

import java.util.Objects;

/**
 * Represents a point in 3D space.
 * @author Chani Olshtein and Deena Glecer
 */
public class Point {

    public static final Point ZERO = new Point(0,0,0);
    final Double3 xyz;

    /**
     * Constructs a point with the given coordinates.
     *
     * @param xyz the coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructs a point with the given coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    /**
     * Checks if this point is equal to another object.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    /**
     * Calculates the hash code of this point.
     *
     * @return the hash code of this point
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * Returns a string representation of this point.
     *
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        return "Point:" + xyz ;
    }

    /**
     * Calculates the Euclidean distance between this point and another point.
     *
     * @param other the other point
     * @return the Euclidean distance between this point and the other point
     */
    public double distance(Point other)
    {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Calculates the square of the Euclidean distance between this point and another point.
     * This is more efficient than calculating the actual distance and is useful in certain situations.
     *
     * @param other the other point
     * @return the square of the Euclidean distance between this point and the other point
     */
    public double distanceSquared(Point other)
    {
        double x1 = xyz.d1;
        double y1 = xyz.d2;
        double z1 = xyz.d3;

        double x2 = other.xyz.d1;
        double y2 = other.xyz.d2;
        double z2 = other.xyz.d3;

        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
    }

    /**
     * Returns a vector that points from the specified point to this point.
     *
     * @param point the starting point of the vector
     * @return a vector that points from the specified point to this point
     */
    public Vector subtract(Point point){
        return  new Vector(xyz.subtract(point.xyz));
    }

    /**
     * Adds the specified vector to this point and returns a new point with the result.
     *
     * @param vector the vector to add
     * @return a new point that is the result of adding the specified vector to this point
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {return xyz.d3;}
}
