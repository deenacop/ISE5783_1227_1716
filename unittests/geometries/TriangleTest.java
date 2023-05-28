package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle t = new Triangle(new Point(1.0, 3.0, 5.0), new Point(5.0, 3.0, 1.0), new Point(0.0, 3.0, 1.0));

        // ============ Equivalence Partitions Tests ==============

        //case 1- ray intersects with triangle
        Ray r = new Ray(new Point(1.0, -5.0, 4.0), new Vector(0.0, 3.0, 0.0));
        List<Point> l = t.findIntersections(r);
        assertEquals(new Point(1.0, 3.0, 4.0), l.get(0));

        //case 2- ray intersects with plane but outside the triangle against edge
        r = new Ray(new Point(1.0, -5.0, 4.0), new Vector(3.0, 0.0, -1.0));
        l = t.findIntersections(r);
        assertEquals(null, l);

        //case 3- ray intersects with plane but outside the triangle against vertex
        r = new Ray(new Point(1.0, -5.0, 4.0), new Vector(1.0, 3.0, 6.0));
        l = t.findIntersections(r);
        assertEquals(null, l);

        // =============== Boundary Values Tests ==================

        //case 1- the ray begins before the plane on the edge of triangle
        r = new Ray(new Point(4.0, 2.0, 1.0), new Vector(0.0, 1.0, 0.0));
        l = t.findIntersections(r);
        assertEquals(null, l);

        //case 2- the ray begins before the plane on vertex
        r = new Ray(new Point(1.0, 2.0, 5.0), new Vector(0.0, 1.0, 0.0));
        l = t.findIntersections(r);
        assertEquals(null, l);

        //case 3- the ray begins before the plane on edge's continuation
        r = new Ray(new Point(8.0, 2.0, 1.0), new Vector(0.0, 1.0, 0.0));
        l = t.findIntersections(r);
        assertEquals(null, l);
    }
}