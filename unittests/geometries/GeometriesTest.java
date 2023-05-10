package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GeometriesTest {
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries();
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here


        // ============ Boundary Value Test ==============

        //checks for an empty geometry list.
        assertNull(geometries.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 0, 0))), "empty collection");

        // checks for no intersections
        Triangle triangle = new Triangle(new Point(1, 1, 1), new Point(0.5, -1, 0), new Point(1.5, -1, 0));
        Sphere sphere = new Sphere(1, new Point(1, 0, 0));
        Plane plane = new Plane(new Point(3, 0, 0), new Vector(0, 0, 1));

        geometries = new Geometries(plane, sphere, triangle);
        assertNull(geometries.findIntersections(new Ray(new Point(0, 0, 10), new Vector(1, 0, 0))));

        //checks for one expected intersection
        geometries = new Geometries(triangle, plane, new Sphere(3, new Point(0, 0, 3)));
        assertEquals(1, geometries.findIntersections(new Ray(new Point(0, 0, 4), new Vector(0, 0, 1))).size());

        //checks for expected more than one shape intersecting
        geometries = new Geometries(triangle, new Plane(new Point(0, 0, 7), new Vector(0, 0, 1)), new Sphere(2, new Point(0, 0, 3)));
        assertEquals(3, geometries.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1))).size());

        // intersects with all the shapes
        geometries = new Geometries(new Triangle(new Point(-1, -1, 8), new Point(2, 0, 8), new Point(-1, 1, 8)), new Plane(new Point(0, 0, 7), new Vector(0, 0, 1)), new Sphere(2, new Point(0, 0, 3)));
        assertEquals(4, geometries.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1))).size());


    }

}
