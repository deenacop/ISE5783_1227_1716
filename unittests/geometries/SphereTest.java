package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link Sphere GetNormal}
     */
    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(1d, new Point(0, 0, 0));
        assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(2, 0, 0)),
                "to bad");
    }
}