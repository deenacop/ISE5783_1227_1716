package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * abstract class
 */
public abstract class RayTracerBase {
    /**
     * Field represents a scene to trace
     */
    protected Scene scene;

    /**
     * Constructor
     *
     * @param scene Parameter for scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Declaration of abstract function
     *
     * @param ray ray to check the color
     * @return Color of the point
     */
    public abstract Color traceRay(Ray ray);

    /**
     * Declaration of abstract function
     *
     * @param rays rays to check the color
     * @return Average color of all rays at the pixel
     */
    public abstract Color traceRays(List<Ray> rays);

    /**
     * Declaration of abstract function
     * @param rays rays to check the color
     * @return Average color of some rays at the pixel, using adaptive super-sampling
     */
    public abstract Color adaptiveTraceRays(List<Ray> rays);
}
