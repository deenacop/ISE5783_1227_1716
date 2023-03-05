package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y ,z);
        if(xyz.equals(Double3.ZERO))
        {
            throw new IllegalArgumentException("Vector zero ");
        }
    }

    public Vector(Double3 xyz) {
        this(xyz.d1, xyz.d2, xyz.d3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    @Override
    public String toString() {
        return "Vector:" + xyz ;
    }

    public Vector add(Vector vector)
    {
        return new Vector(xyz.add(vector.xyz));
    }

    public Vector scale(double scalar)
    {
        return new Vector(xyz.scale(scalar));
    }

    public double lengthSquared() {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;
        return dx*dx + dy*dy + dz*dz ;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double dotProduct(Vector vector) {
        return (length());
    }

    public Vector crossProduct(Vector v2) {
        return new Vector(1,1,1);//מכפלה וקטורית
    }

    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }
}
