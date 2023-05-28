package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   private final int           size;

   /** Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }

   @Override
   public Vector getNormal(Point point) { return plane.getNormal(); }


   /**

   * Finds the intersection points between a given ray and this polygon. Uses the plane
   * containing the polygon to find the initial intersection point, and then checks whether
   * this point lies inside the polygon's boundaries by performing cross products of the
   * ray's direction vector with vectors connecting the ray's origin to the polygon's vertices.
   * @param ray the ray to intersect with the polygo
   * @return a list of intersection points, or null if the ray doesn't intersect with the polygon
    */
   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
      //first, find the intersections for the plane in which the polygon is
      List<GeoPoint> result = plane.findGeoIntersections(ray, maxDistance);

      if (result == null) { //if the intersections with the plane returned null so return null
         return null;
      }

      Point P0 = ray.getP0();
      Vector v = ray.getDir();

      //get the first and second vertices
      Point P1 = vertices.get(1);
      Point P2 = vertices.get(0);

      Vector v1 = P1.subtract(P0); //v1=p1-p0
      Vector v2 = P2.subtract(P0); //v2=p2-p0

      //check the sign of the vectors
      double sign = alignZero(v.dotProduct(v1.crossProduct(v2)));

      if (isZero(sign)) { //if it was 0 so return null
         return null;
      }

      boolean positive = sign > 0; //True if the sign is positive (which means they both have the same sign)

      //iterate through all vertices of the polygon
      for (int i = vertices.size() - 1; i > 0; --i) {
         //now check for the rest if the vertices
         v1 = v2;
         v2 = vertices.get(i).subtract(P0);

         sign = alignZero(v.dotProduct(v1.crossProduct(v2)));

         //if one of them is 0, return null
         if (isZero(sign)) {
            return null;
         }

         //if they don't have the same sign return null
         if (positive != (sign > 0)) {
            return null;
         }
      }

      //update the geometry
      for (GeoPoint gp : result) {
         gp.geometry = this;
      }

      return result;
   }

}
