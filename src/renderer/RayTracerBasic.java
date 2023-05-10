package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {
    /**
     * Constructor
     *
     * @param scene Parameter for scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
        if (scene == null)
            return;
    }


    /**
     * returns the color of the closest point which the ray hits
     *
     * @param ray the ray to check
     * @return The color of the point
     */
    @Override
    public Color traceRay(Ray ray) {
        //get the ntersections of the ray with the scene
        List<Point> intersections = scene.geometries.findIntersections(ray);

        //if no intersections were found return the background color of the scene
        if (intersections == null)
            return scene.background;

        //find the intersection which is closest to the ray
        Point closestPoint = ray.findClosestPoint(intersections);
        //return the color at that point
        return calcColor(closestPoint);
    }

    /**
     * returns the color at a certain point based in lighting
     *
     * @param closestPoint with the geometry
     * @return Color of the background of the scene
     */
    private Color calcColor(Point closestPoint) {
        return scene.ambientLight.getIntensity();
    }
}
