package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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
}
