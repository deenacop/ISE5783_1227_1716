package primitives;

/**
 * Class represents the material of a Geometry
 */
public class Material {
    // Field represents the
    public Double3 kD = Double3.ZERO;
    // Field represents the
    public Double3 kS = Double3.ZERO;
    // Field represents the shininess of the material
    public int nShininess = 0;

    /**
     * Builder patterns setter for field kD
     * @param kD parameter for kD
     * @return Material object
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Builder patterns setter for field kD
     * @param value parameter for kD constructor
     * @return Material object
     */
    public Material setKd(double value) {
        this.kD = new Double3(value);
        return this;
    }

    /**
     * Builder patterns setter for field kS
     * @param kS parameter for kS
     * @return Material object
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Builder patterns setter for field kS
     * @param value parameter for kS constructor
     * @return Material object
     */
    public Material setKs(double value) {
        this.kS = new Double3(value);
        return this;
    }

    /**
     * Builder patterns setter for field nShininess
     * @param nShininess parameter for nShininess
     * @return Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}

