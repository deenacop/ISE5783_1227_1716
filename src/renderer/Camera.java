package renderer;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Camera producing rays through a view plane
 */
public class Camera {

    private  Point p0;          // camera eye
    private  Vector vUp;        // vector pointing upwards : Y axis
    private  Vector vTo;        // vector pointing towards the scene
    private  Vector vRight;     // vector pointing towards the right : X axis

    private double distance;    // cameras distance from ViewPlane
    private double width;       // ViewPlane width
    private double height;      // ViewPlane height

    /**
     *
     * @param p0 origin  point in 3D space
     * @param vUp vechu
     * @param vTo vechulei
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo))){
            throw  new IllegalArgumentException("vTo and vUp should be orthogonal");
        }

        this.p0 = p0;

        //normalizing the positional vectors
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        this.vRight = this.vTo.crossProduct(this.vUp);

    }

    public Point getP0() {
        return this.p0;
    }

    public Vector getvTo() {
        return this.vTo;
    }

    public Vector getvUp() {
        return this.vUp;
    }

    public Vector getvRight() {
        return this.vRight;
    }

    public double getDistance() {
        return this.distance;
    }

    // chaining methods

    /**
     * set distance between the camera and it's view plane
     * @param distance the  distance for the view plane
     * @return instance of Camera for chaining
     */
    public Camera setViewPlaneDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * setting View Plane size
     * @param width     "physical" width
     * @param height    "physical" height
     * @return instance of Camera for chaining
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Constructing a ray through a pixel
     *
     * @param Nx
     * @param Ny
     * @param j
     * @param i
     * @return ray form the camera to Pixel[i,j]
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        //Image center
        Point Pc = p0.add(vTo.scale(distance));

        //Ratio (pixel width & height)
        double Ry =height/ Ny;
        double Rx = width/Nx;

        //Pixel[i,j] center
        Point Pij = Pc;

        //delta values for going to Pixel[i,j]  from Pc

        double yI =  -(i - (Ny -1)/2d)* Ry;
        double xJ =  (j - (Nx -1)/2d)* Rx;

        if (! isZero(xJ) ){
            Pij = Pij.add(vRight.scale(xJ));
        }
        if (! isZero(yI)) {
            Pij = Pij.add(vUp.scale(yI));
        }

        return new Ray(p0, Pij.subtract(p0));
    }
}