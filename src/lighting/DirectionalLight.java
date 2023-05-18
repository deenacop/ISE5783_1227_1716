package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class represents directional light in a scene
 * Extends abstract class Light and implements interface LightSource
 */
public class DirectionalLight extends Light implements LightSource {
    // Field represents the direction of the light
    private Vector direction;

    /**
     * Constructor for DirectionalLight
     * @param intensity parameter for field intensity in super
     * @param direction parameter for field direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        //intensity of directional light is the same in every point
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }
}
