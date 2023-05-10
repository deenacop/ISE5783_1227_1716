package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    //Field represents the intensity of the light
    private final Color intensity;

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * primary constructor
     *
     * @param Ia Color intensity
     * @param Ka intensity factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        intensity = Ia.scale(Ka);
    }

    public AmbientLight(Color Ia, double Ka) {
        intensity = Ia.scale(Ka);
    }


    /**
     * default constructor sets the color to black
     */
    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    /**
     * default constructor sets the color to black
     */
//    public AmbientLight() {
//        this.intensity = Color.BLACK;
//    }


    /**
     * getter for the ambient light intensity
     *
     * @return the intensity after scale by factor
     */
    public Color getIntensity() {
        return intensity;
    }
}


