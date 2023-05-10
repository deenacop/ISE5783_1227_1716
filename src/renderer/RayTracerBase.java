package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    //Field represents a scene to trace
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
}
