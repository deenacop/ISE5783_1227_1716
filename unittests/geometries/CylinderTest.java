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
            // TC01: There is a simple single test here (when point is on the top of the cylinder)
            Cylinder pl = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1, 1d);
            assertEquals(new Vector(0, 0, 1d), pl.getNormal(new Point(0, 0.5, 1)), "Bad normal to cylinder");
            // TC02: There is a simple single test here (when point is on the base of the cylinder)
            assertEquals(new Vector(0, 0, -1d), pl.getNormal(new Point(0, 0.5, 0)), "Bad normal to cylinder");
            // TC03: There is a simple single test here (when point is on the surface of the cylinder)
            assertEquals(new Vector(0, 1d, 0), pl.getNormal(new Point(0, 0.5, 0.5)), "Bad normal to cylinder");

            // =============== Boundary Values Tests ==================
            // TC04: There is a single boundary test here (between surface and base of cylinder)
            assertNotEquals(pl.getNormal(new Point(0, 1, 0.00001)), pl.getNormal(new Point(0, 0.99999999, 0)), "Error in boundary test with base !");
            // TC05: There is a single boundary test here (between surface and top of cylinder)
            assertNotEquals(pl.getNormal(new Point(0, 1, 0.99999)), pl.getNormal(new Point(0, 0.99999999, 1)), "Error in boundary test with top !");

    }
}