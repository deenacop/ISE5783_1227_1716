package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries();

        // =============== Boundary Values Tests ==================
        //TC01: empty geometries list
        assertNull(geometries.findIntersections(new Ray(new Point(0.0, 1.0, 0.0), new Vector(1.0, 0.0, 5.0))));

        geometries.add(new Plane(new Point(1.0, 1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
        geometries.add(new Triangle(new Point(1.0, 0.0, 0.0), new Point(0.0, 1.0, 0.0), new Point(0.0, 0.0, 1.0)));
        geometries.add(new Sphere(1.0, new Point(1.0, 0.0, 0.0)));

        //TC02: each geometry doesn't have intersection points
        assertNull(geometries.findIntersections(new Ray(new Point(0.0, 0.0, 2.0), new Vector(0.0, -1.0, 0.0))));

        //TC03: just one geometry has intersections point
        assertEquals(1, geometries.findIntersections(new Ray(new Point(0.0, 5.0, -1.0), new Vector(0.0, 0.0, 1.0))).size());

        //TC04: All the geometries have intersection points
        Geometries geo2 = new Geometries();
        geo2.add(new Sphere(5, new Point(4, 0, 0)));
        geo2.add(new Triangle(new Point(1, 4, 0), new Point(1, 2, 0), new Point(5, 2, 0)));
        geo2.add(new Plane(new Point(1, 2, 0), new Point(0, 7, 0), new Point(1, 0, 0)));
        List<Point> pointList = geo2.findIntersections(new Ray(new Point(2, 5, 4), new Vector(0, -2, -4)));
        assertEquals(4, pointList.size(), "Wrong number of points");


        // ============ Equivalence Partitions Tests ==============
        //TC11: part of the geometries has intersection points
        assertEquals(2, geometries.findIntersections(new Ray(new Point(1.0, 0.0, -1.0), new Vector(0.0, 0.0, 1.0))).size());

    }
}