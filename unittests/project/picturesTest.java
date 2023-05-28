package project;

import scene.Scene;


import static java.awt.Color.*;

import geometries.*;
import lighting.LightSource;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class picturesTest {
    private Scene scene = new Scene("Test scene");
    @Test
    public void beautifulPictureScene() {
        Camera camera = new Camera(
                new Point(0, 0, 800), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(1000, 1000).setViewPlaneDistance(1000);

        Scene scene = new Scene("beautiful_picture_scene");
        scene.setBackground(new Color(135, 206, 235)); // Sky blue background

        // Ambient light
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        // Ground
        Geometry ground = new Plane(
                new Point(0, -200, 0), new Vector(0, 1, 0))
                .setEmission(new Color(50, 205, 50)) // Green ground
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
        scene.geometries.add(ground);

        // Sun
        Geometry sun = new Sphere(100, new Point(500, 500, -800))
                .setEmission(new Color(255, 255, 0)) // Yellow sun
                .setMaterial(new Material().setKd(0).setKs(0).setShininess(100));
        scene.geometries.add(sun);


        // Spotlight from the sun

        scene.lights.add(new PointLight(new Color(white), new Point(400, 400, -800)).setKl(0.00001).setKq(0.000005));

        // Clouds
        int numClouds = 20;
        int cloudRadius = 80;
        int cloudSpread = 200;

        for (int i = 0; i < numClouds; i++) {
            double x = -500 + (Math.random() * cloudSpread);
            double y = 250 + (Math.random() * cloudSpread);
            double z = -600 + (Math.random() * cloudSpread);

            Geometry cloud = new Sphere(cloudRadius, new Point(x, y, z))
                    .setEmission(new Color(255, 255, 255)) // White clouds
                    .setMaterial(new Material().setKd(0).setKs(0).setShininess(100));
            scene.geometries.add(cloud);
        }


        // House
        scene.geometries.add(new Polygon(new Point(-200, -200, -400),
                new Point(200, -200, -400),
                new Point(200, 200, -400),
                new Point(-200, 200, -400))
                .setEmission(new Color(128, 0, 0)) // Maroon house
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100)));

        // Add door to the house
        Geometry door = new Polygon(
                new Point(-50, -200, -399),
                new Point(50, -200, -399),
                new Point(50, 0, -399),
                new Point(-50, 0, -399))
                .setEmission(new Color(139, 69, 19)) // Brown door
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
        scene.geometries.add(door);

        // Add window to the house
        Geometry window = new Polygon(
                new Point(-150, 50, -399),
                new Point(-50, 50, -399),
                new Point(-50, 150, -399),
                new Point(-150, 150, -399))
                .setEmission(new Color(135, 206, 250)) // Light blue window
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
        scene.geometries.add(window);


        scene.geometries.add(new Triangle(new Point(-300, 200, -400),
                new Point(0, 400, -400),
                new Point(300, 200, -400))
                .setEmission(new Color(139, 69, 19)) // Brown roof
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100)));


        ImageWriter imageWriter = new ImageWriter("beautiful_picture_scene", 1000, 1000);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage();
        camera.writeToImage();
    }

    @Test
    public void initial_image_7() {
        Camera camera = new Camera(new Point(0, -2000, 500), new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setViewPlaneSize(700, 700).setViewPlaneDistance(650);

        Scene scene = new Scene("room");

        scene.geometries.add(
                //left wall
                new Polygon(new Point(-575, 0, 100), new Point(-575, 0, 1150), new Point(-500, -2000, 850), new Point(-500, -2000, -150))
                        .setEmission(new Color(100, 100, 100))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(300)),

                //right wall
                new Polygon(new Point(575, 0, 100), new Point(575, 0, 1150), new Point(500, -2000, 850), new Point(500, -2000, -150))
                        .setEmission(new Color(240, 240, 240))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(300)),

                //back wall
                new Polygon(new Point(-575, 0, 100), new Point(-575, 0, 1150), new Point(575, 0, 1150), new Point(575, 0, 100))
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),

                //ceiling
                new Polygon(new Point(-575, 0, 1150), new Point(-115, 0, 1150), new Point(-100, -2000, 850), new Point(-500, -2000, 850))
                        .setEmission(new Color(255, 204, 238))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Polygon(new Point(115, 0, 1150), new Point(575, 0, 1150), new Point(500, -2000, 850), new Point(100, -2000, 850))
                        .setEmission(new Color(255, 204, 238))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),

                new Polygon(new Point(-115, 0, 1150), new Point(115, 0, 1150), new Point(111.25, -500, 1075), new Point(-111.25, -500, 1075))
                        .setEmission(new Color(255, 204, 238))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Polygon(new Point(-100, -2000, 850), new Point(100, -2000, 850), new Point(109, -800, 1030), new Point(-109, -800, 1030))
                        .setEmission(new Color(255, 204, 238))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Polygon(new Point(-111.25, -500, 1065), new Point(111.25, -500, 1065), new Point(109, -800, 1020), new Point(-109, -800, 1020))
                        .setEmission(new Color(white))
                        .setMaterial(new Material().setKt(0.7)),

                //floor
                new Polygon(new Point(-575, 0, 100), new Point(-500, -2000, -150), new Point(500, -2000, -150), new Point(575, 0, 100))
                        .setEmission(new Color(200, 200, 200))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                //spheres
                new Sphere(230, new Point(-55, -650, 250))
                        .setEmission(new Color(black)) //
                        .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30).setKr(0.8))


        );

        scene.lights.add(new PointLight(new Color(white), new Point(0, -650, 1053)).setKl(0.00001).setKq(0.000002));


        ImageWriter imageWriter = new ImageWriter("room", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

}
