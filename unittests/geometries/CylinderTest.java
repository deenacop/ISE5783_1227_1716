package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.*;

class CylinderTest {

    /**
     * Test method for {@link Cylinder GetNormal}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Cylinder cy = new Cylinder(
                new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)),
                2,
                3);
        // test for point on first disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 1, 1)));

        // test for point on second disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 1, 4)));


        //test for point on body
        assertEquals(new Vector(0, 1, 0), cy.getNormal(new Point(0, 2, 2)));

        // ============ Boundary Value Tests ==============
        // test for center point on second disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 0, 1)));

        // test for center point on second disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 0, 4)));
    }
}