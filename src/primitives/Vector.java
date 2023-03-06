package primitives;

/**
 * The Vector class represents a mathematical vector in 3D space.
 * A vector consists of three components: x, y, and z, which are
 * represented as double-precision floating point numbers.
 *
 * <p>This class is a subclass of the Point class and inherits its x, y, and z components.
 * A Vector object can be created from three double-precision floating point numbers or a Double3 object.</p>
 *
 * <p>This class provides methods for performing vector arithmetic, such as addition, scalar multiplication,
 * dot product, cross product, and normalization.</p>
 */
public class Vector extends Point {

    /**
     * Constructs a Vector object with the specified x, y, and z components.
     *
     * @param x the x component of the vector.
     * @param y the y component of the vector.
     * @param z the z component of the vector.
     * @throws IllegalArgumentException if the x, y, and z components are all zero.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero");
        }
    }

    /**
     * Constructs a Vector object from a Double3 object.
     *
     * @param xyz the Double3 object containing the x, y, and z components of the vector.
     */
    public Vector(Double3 xyz) {
        this(xyz.d1, xyz.d2, xyz.d3);
    }

    /**
     * Returns true if this vector is equal to the specified object.
     *
     * @param o the object to compare to.
     * @return true if this vector is equal to the specified object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return a string representation of this vector.
     */
    @Override
    public String toString() {
        return "Vector:" + xyz;
    }

    /**
     * Returns the vector resulting from adding this vector to the specified vector.
     *
     * @param vector the vector to add to this vector.
     * @return the resulting vector.
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Returns the vector resulting from scaling this vector by the specified scalar.
     *
     * @param scalar the scalar to multiply this vector by.
     * @return the resulting vector.
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Returns the square of the length of this vector.
     *
     * @return the square of the length of this vector.
     */
    public double lengthSquared() {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns the dot product of this vector with the specified vector.
     *
     * @param vector The vector to compute the dot product with.
     * @return The dot product of this vector with the specified vector.
     */
    public double dotProduct(Vector vector) {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;

        double vx = vector.xyz.d1;
        double vy = vector.xyz.d2;
        double vz = vector.xyz.d3;

        return (dx * vx + dy * vy + dz * vz);
    }

    /**

     *Computes the cross product of this vector and another vector.

     *@param vector The other vector

     *@return The cross product of this vector and the other vector
     */
    public Vector crossProduct(Vector vector) {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;

        double vx = vector.xyz.d1;
        double vy = vector.xyz.d2;
        double vz = vector.xyz.d3;

        return new Vector(new Double3(
                dy * vz - dz * vy,
                dz * vx - dx * vz,
                dx * vy - dy * vx
        ));

    }

    /**

     Returns a new vector that is the normalized version of this vector.
     The normalized vector has the same direction as this vector, but a length of 1.
     @return the normalized version of this vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }
}
