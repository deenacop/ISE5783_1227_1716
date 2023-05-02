package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class GeometriesTest {
    @Test
    void testFindIntersections()
    {
        Geometries gr=new Geometries();
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here


        // ============ Boundary Value Test ==============

        //checks for an empty geometry list.
        assertNull(gr.findIntersections(new Ray(new Point(1,1,1),new Vector(1,0,0))),"empty collection");

        // checks for no intersections
        Triangle triangle=new Triangle(new Point(1,1,1), new Point(0.5, -1, 0), new Point(1.5, -1, 0));
        Sphere sphere = new Sphere( 1,new Point(1, 0, 0));
        Plane plane = new Plane(new Point(3, 0, 0),new Vector(0, 0, 1));
        Geometries gr1=new Geometries(plane, sphere, triangle);
        assertNull(gr1.findIntersections(new Ray(new Point(0,0,10),new Vector(1,0,0))));

        //checks for one expected intersection
        Geometries gr2 = new Geometries(triangle, plane, new Sphere( 3,new Point(0, 0, 3)));
        assertEquals(1,gr2.findIntersections(new Ray(new Point(0, 0, 4), new Vector(0, 0, 1))).size());

        //checks for expected more than one shape intersecting
        Geometries gr3 = new Geometries(triangle, new Plane(new Point(0, 0, 7), new Vector(0, 0, 1)), new Sphere( 2,new Point(0, 0, 3)) );
        assertEquals(3,gr3.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1))).size());

        // intersects with all the shapes
        Geometries gr4 = new Geometries(new Triangle(new Point(-1,-1,8),new Point(2,0,8), new Point(-1,1,8)), new Plane(new Point(0, 0, 7), new Vector(0, 0, 1)), new Sphere( 2,new Point(0, 0, 3)) );
        assertEquals(4,gr4.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1))).size());


    }

}
