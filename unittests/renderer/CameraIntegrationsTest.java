package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationsTest {
    /**
     * Test helper function to count the intersections and compare with expected value
     *
     * @param cam      camera for the test
     * @param geo      3D body to test the integration of the camer with
     * @param expected amount of intersections
     */
    private void assertCountIntersections(int expected, Camera cam, Intersectable geo ,int rows, int columns) {
        int counter = 0;

        List<Point> points = null;

        //loop for each pixel
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                //construct a ray from the camera to the pixel, and find all the intersections of that ray with the intersectable geometries
                var intersections = geo.findIntersections(cam.constructRay(columns, rows, j, i));
                if (intersections != null) {
                    if (points == null) {
                        points = new LinkedList<>();
                    }
                    points.addAll(intersections);
                }
                counter += intersections == null ? 0 : intersections.size(); //count the number of intersections
            }
        }

        assertEquals(expected, counter, "Wrong amount of intersections");
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    public void cameraRaySphereIntegration() {
        Camera cam1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1),
                new Vector(0, -1, 0)).setViewPlaneSize(3,3).setViewPlaneDistance(1);

        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1),
                new Vector(0, -1, 0)).setViewPlaneSize(3,3).setViewPlaneDistance(1);

        // TC01: Small Sphere 2 points
        assertCountIntersections(2, cam1, new Sphere(1, new Point(0, 0, -3)),3,3);

        // TC02: Big Sphere 18 points
        assertCountIntersections(18, cam2, new Sphere(2.5, new Point(0, 0, -2.5)),3,3);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(10, cam2, new Sphere(2, new Point(0, 0, -2)),3,3);

        // TC04: Inside Sphere 9 points
        assertCountIntersections(9, cam2, new Sphere(4, new Point(0, 0, -1)),3,3);

        // TC05: Beyond Sphere 0 points
        assertCountIntersections(0, cam1, new Sphere(0.5, new Point(0, 0, 1)),3,3);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1),
                new Vector(0, -1, 0)).setViewPlaneSize(3,3).setViewPlaneDistance(1);

        // TC01: Plane against camera 9 points
        assertCountIntersections(9, cam, new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)),3,3);

        // TC02: Plane with small angle 9 points
        assertCountIntersections(9, cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)),3,3);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(6, cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)),3,3);

        // TC04: Beyond Plane 0 points
        assertCountIntersections(6, cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)),3,3);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void cameraRayTriangleIntegration() {
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1),
                new Vector(0, -1, 0)).setViewPlaneSize(3,3).setViewPlaneDistance(1);

        // TC01: Small triangle 1 point
        assertCountIntersections(1, cam, new Triangle(new Point(1, 1, -2),
                new Point(-1, 1, -2), new Point(0, -1, -2)),3,3);

        // TC02: Medium triangle 2 points
        assertCountIntersections(2, cam, new Triangle(new Point(1, 1, -2),
                new Point(-1, 1, -2), new Point(0, -20, -2)),3,3);
    }


}
