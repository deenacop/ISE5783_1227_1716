package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Implement the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase {


    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * constructor that called the constructor of RayTracerBase
     *
     * @param scene ,the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * returns the color of the closest point which the ray hits
     *
     * @param ray the ray to check
     * @return The color of the point
     */
    @Override
    public Color traceRay(Ray ray) {
        //get the closest intersection of the ray with the scene
        GeoPoint intersection = findClosestIntersection(ray);

        //if no intersections were found return the background color of the scene
        if (intersection == null)
            return scene.background;

        //return the color at that point
        return calcColor(intersection, ray);
    }


    /**
     * Returns the color at a certain point
     *
     * @param geoPoint  the point with the geometry
     * @param ray the ray from the viewer
     * @return Color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        // Calculating the color at a point according to Phong Reflection Model
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).
                add(scene.ambientLight.getIntensity());
    }

    /**
     * recursive function to calculate the color at a certain point
     *
     * @param intersection the point
     * @param ray          the ray from the viewer
     * @param level        level of recursion
     * @param k            the kR or kT factor at this point
     * @return Color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        // Calculating the color at a point according to Phong Reflection Model

        //calculated light contribution from all light sources
        Color color = calcLocalEffect(intersection, ray.getDir(), k);

        if (level == 1) {
            return color; //end recursion
        }

        //+calculated light contribution from global effect (kR*ir+kT*it recursively)
        return color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }


    /**
     * Calculate the local effect of light sources on a point
     *
     * @param intersection the point
     * @param v            the direction of the ray from the viewer
     * @param k            the kR or kT factor at this point
     * @return the color
     */
    private Color calcLocalEffect(GeoPoint intersection, Vector v, Double3 k) {
        Vector n = intersection.geometry.getNormal(intersection.point);

        double nv = alignZero(n.dotProduct(v)); //nv=n*v
        if (isZero(nv)) {
            return Color.BLACK;
        }

        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;

        Color color = intersection.geometry.getEmission(); //base color

        //for each light source in the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point); //the direction from the light source to the point
            double nl = alignZero(n.dotProduct(l)); //nl=n*l

            //if sign(nl) == sign(nv) (if the light hits the point add it, otherwise don't add this light)
            if (nl * nv > 0) {
                //ktr is the level of shade on the point (according to transparency of material)
                Double3 ktr = transparency(intersection, lightSource, l, n, nv);
                if (!(ktr.product(k)).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }



    /**
     * recursive function calculates the global effects of objects on a certain point
     *
     * @param intersection the point
     * @param inRay        direction of ray from the camera to point
     * @param level        level of recursion
     * @param k            the level of light
     * @return the color
     */
    private Color calcGlobalEffects(GeoPoint intersection, Vector inRay, int level, Double3 k) {
        Color color = Color.BLACK; //base color
        Vector n = intersection.geometry.getNormal(intersection.point); //normal

        //reflection attenuation of the material
        Double3 kr = intersection.geometry.getMaterial().kR;
        //reflection level as affected by k
        Double3 kkr = kr.product(k);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) { //if the reflection level is not lower than the minimum
            //construct a reflection  ray from the point
            Ray reflectedRay = constructReflectedRay(n, inRay, intersection.point);

            //add this color to the point by recursively calling calcGlobalEffect
            color = calcGlobalEffect(reflectedRay, level, kr, kkr);
        }


        //transparency  attenuation factor of the material
        Double3 kt = intersection.geometry.getMaterial().kT;
        //transparency level
        Double3 kkt = kt.product(k);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {//if the transparency level is not lower than the minimum
            //construct a refracted ray from the point
            Ray refractedRay = constructRefractedRay(n, inRay, intersection.point);

            //add to the color to the point by recursively calling calcGlobalEffect
            color = color.add(calcGlobalEffect(refractedRay, level, kt, kkt));
        }

        return color;
    }

    /**
     * calculates global effects recursively
     *
     * @param ray   the ray from the viewer
     * @param level level of recursion
     * @param kx    k attenuation
     * @param kkx   k times attenuation
     * @return the color
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);

        if (gp == null) {
            return scene.background;
        }

        return calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Calculate the diffuse light effect on the point
     *
     * @param kd             diffuse attenuation factor
     * @param l              the direction of the light
     * @param n              normal from the point
     * @param lightIntensity the intensity of the light source at this point
     * @return the color
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(abs(l.dotProduct(n))); //ln=|l*n|
        return lightIntensity.scale(kd.scale(ln)); //Kd * |l * n| * Il
    }

    /**
     * Calculate the specular light at this point
     *
     * @param ks             specular attenuation factor
     * @param l              the direction of the light
     * @param n              normal from the point
     * @param v              direction of the viewer
     * @param nShininess     shininess factor of the material at the point
     * @param lightIntensity the intensity of the light source at the point
     * @return the color of the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n)); //ln=l*n
        Vector r = l.subtract(n.scale(2 * ln)).normalize(); //r=l-2*(l*n)*n
        double vr = alignZero(v.dotProduct(r)); //vr=v*r
        double vrnsh = pow(max(0, -vr), nShininess); //vrnsh=max(0,-vr)^nshininess
        return lightIntensity.scale(ks.scale(vrnsh)); //Ks * (max(0, - v * r) ^ Nsh) * Il
    }


    /**
     * Returns wether a certain point is shaded by other objects
     *
     * @param gp          the point
     * @param l           the direction of the light
     * @param n           normal to the point
     * @param lightSource the loght source
     * @return true if the point is shaded
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nv) {
        Vector lightDirection = l.scale(-1); //vector from the point to the light source

        Vector deltaVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point p = gp.point.add(deltaVector);
        Ray lightRay = new Ray(p, lightDirection);

        double lightDistance = lightSource.getDistance(gp.point);
        //finding only points that are closer to the point than the light
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        //if there are no intersections return true (there is no shadow)
        if (intersections == null) {
            return true;
        }

        //for each intersection
        for (GeoPoint intersection : intersections) {
            //if the material is not transparent return false
            if (intersection.geometry.getMaterial().kT == Double3.ZERO) {
                return false;
            }

        }
        return true;
    }




    /**
     * calculates the transparency level at a certain point
     *
     * @param gp the point
     * @param ls the light source
     * @param l  direction of the light
     * @param n  normal to the point
     * @param nv n*v
     * @return transparency level
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1); //vector from the point to the light source

        Ray lightRay;

        lightRay = new Ray(gp.point, lightDirection, n);

        double lightDistance = ls.getDistance(gp.point);
        //finding only points that are closer to the point than the light
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);

        if (intersections == null) {
            return Double3.ONE;
        }

        Double3 ktr = Double3.ONE;
        //for each intersection
        for (GeoPoint intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kT);

            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO; //end recursion
            }
        }

        return ktr;
    }

    /**
     * Constructs a reflection ray from an intersection
     *
     * @param n normal to the point
     * @param v direction of the ray to the point
     * @param p the [point
     * @return new Ray
     */
    private Ray constructReflectedRay(Vector n, Vector v, Point p) {
        double vn = alignZero(v.dotProduct(n)); //v*n

        if (isZero(vn)) {
            return null;
        }

        Vector vnn = n.scale(2 * vn);// n*2*vn
        Ray r = new Ray(p, v.subtract(vnn), n); //new Ray{point,v-2*(v*n)*n}
        return r;
    }

    /**
     * Construct a refractive ray from a point
     *
     * @param n normal to the point
     * @param v direction of ray to the point
     * @param p point
     * @return new Ray
     */
    private Ray constructRefractedRay(Vector n, Vector v, Point p) {
        return new Ray(p, v, n);
    }

    /**
     * Finds the closest intersection in the scene to a given ray
     *
     * @param ray the ray
     * @return the closest intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        //get the intersections of the ray with the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }

        //find the closest GeoPoint among the intersections
        GeoPoint result = ray.findClosestGeoPoint(intersections);

        return result;
    }
}
