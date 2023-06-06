package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;
import static primitives.Util.isZero;

/**
 * Class represents a light source from a point
 * Extends abstract class Light
 * Implements interface LightSource
 */
public class PointLight extends Light implements LightSource {
    // Field represents the position point in which the light lies
    protected Point position;

    // Fields represent the attenuation factors
    protected double kC = 1;
    protected double kL = 0;
    protected double kQ = 0;

    private static final Random RND = new Random();

    /**
     * Constructor
     *
     * @param intensity parameter for Field intensity in super
     * @param position  parameter for field position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    /**
     * Builder pattern setter for field kC
     *
     * @param kC parameter for field kC
     * @return PointLight object
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Builder pattern setter for field kL
     *
     * @param kL parameter for field kL
     * @return PointLight object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Builder pattern setter for field kQ
     *
     * @param kQ parameter for field kQ
     * @return PointLight object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(position);
        double ds = p.distanceSquared(position);
        Color i0 = super.getIntensity();

        //intensity reduced by attenuation factors and distance according to formula:
        //i0/(kc+d*kl+d^2*kq)
        return i0.reduce(kC + kL * d + kQ * ds);
    }

    @Override
    public Vector getL(Point p) {
        if (p.equals(position)) {
            return null;
        }

        //p-position
        return p.subtract(position).normalize();
    }

    /**
     * Creates a list of vectors from the given point to random points around the light within radius r
     *
     * @param p      the given point
     * @param r      the radius
     * @param amount the amount of vectors to create
     * @return list of vectors
     */
    @Override
    public List<Vector> getLCircle(Point p, double r, int amount) {
        if (p.equals(position))
            return null;

        List<Vector> result = new LinkedList<>();

        Vector l = getL(p); //vector to the center of the point light
        result.add(l);

        if (amount < 2) {
            return result;
        }

        Vector vAcross;
        if (isZero(l.getX()) && isZero(l.getY())) { //if l is parallel to z axis, then the normal is across z on x axis
            vAcross = new Vector(-1 * l.getZ(), 0, 0).normalize();
        } else { //otherwise get the normal using x and y
            vAcross = new Vector(-1 * l.getY(), l.getX(), 0).normalize();
        }
        Vector vForward = vAcross.crossProduct(l).normalize(); //the vector to the other direction

        double cosAngle, sinAngle, moveX, moveY, d;

        for (int i = 0; i < amount; i++) {
            Point movedPoint = this.position;

            cosAngle = 2 * RND.nextDouble() - 1; //random cosine of angle between (-1,1)
            sinAngle = sqrt(1 - cosAngle * cosAngle); //sin(angle)=1-cos^2(angle)

            d = r * (2 * RND.nextDouble() - 1); //d is between (-r,r)
            if (isZero(d)) { //if we got 0 then try again, because it will just be the same as the center
                i--;
                continue;
            }

            //says how much to move across and down
            moveX = d * cosAngle;
            moveY = d * sinAngle;

            //moving the point according to the value
            if (!isZero(moveX)) {
                movedPoint = movedPoint.add(vAcross.scale(moveX));
            }
            if (!isZero(moveY)) {
                movedPoint = movedPoint.add(vForward.scale(moveY));
            }

            result.add(p.subtract(movedPoint).normalize()); //adding the vector from the new point to the light position
        }
        return result;
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
