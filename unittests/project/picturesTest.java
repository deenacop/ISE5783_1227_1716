package project;

import lighting.*;
import scene.Scene;


import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.*;

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
    public Scene beautifulPictureScene(String name) {
        Scene scene = new Scene(name);
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
        Geometry sun = new Sphere(150, new Point(400, 500, -800))
                .setEmission(new Color(255, 255, 0)) // Yellow sun ray
                .setMaterial(new Material().setKd(0).setKs(0).setShininess(100));
        scene.geometries.add(sun);


        // Sun rays
        int numRays = 16;
        double rayLength = 400;

        for (int i = 0; i < numRays; i++) {
            double angle = (2 * Math.PI * i) / numRays;

            Geometry ray = new Polygon(
                    new Point(400, 500, -800),
                    new Point(400 + rayLength * Math.cos(angle), 500 + rayLength * Math.sin(angle), -800),
                    new Point(400 + rayLength * Math.cos(angle + Math.PI / 16), 500 + rayLength * Math.sin(angle + Math.PI / 16), -800))
                    .setEmission(new Color(255, 255, 0)) // Yellow sun ray
                    .setMaterial(new Material().setKd(0).setKs(0).setShininess(100));
            scene.geometries.add(ray);
        }

        // Chimney for the house
        scene.geometries.add(new Polygon(
                new Point(100, 100, -600),
                new Point(100, 200, -600),
                new Point(100, 200, -500),
                new Point(100, 100, -500))
                .setEmission(new Color(128, 0, 0)) // Maroon chimney
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100)));


        // Spotlight from the sun

        scene.lights.add(new PointLight(new Color(white), new Point(200, 350, -700)).setKl(0.00001).setKq(0.000003));
        scene.lights.add(new DirectionalLight(new Color(0, 0, 0), new Vector(1, 1, -0.5)));
        scene.lights.add(new SpotLight(new Color(0, 0, 0), new Vector(1, 1, -0.5), new Vector(1, 1, -0.5))
                .setKl(0.001).setKq(0.0001));
        scene.lights.add(new DirectionalLight(new Color(GREEN), new Vector(0, 1, 5)));


        // Clouds
        int numClouds = 20;
        int cloudRadius = 80;
        int cloudSpread = 200;

        for (int j = 0; j < numClouds; j++) {
            double x = -500 + (Math.random() * cloudSpread);
            double y = 250 + (Math.random() * cloudSpread);
            double z = -600 + (Math.random() * cloudSpread);

            Geometry cloud = new Sphere(cloudRadius, new Point(x, y, z))
                    .setEmission(new Color(255, 255, 255)) // White clouds
                    .setMaterial(new Material().setKd(0).setKs(0).setShininess(300));
            scene.geometries.add(cloud);
        }

        // House
        scene.geometries.add(new Polygon(
                new Point(-200, -200, -400),
                new Point(200, -200, -400),
                new Point(200, 200, -400),
                new Point(-200, 200, -400))
                .setEmission(new Color(181, 101, 29)) // light brown house
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));

        // Back wall
        scene.geometries.add(new Polygon(
                new Point(-200, -200, -800),
                new Point(200, -200, -800),
                new Point(200, 200, -800),
                new Point(-200, 200, -800))
                .setEmission(new Color(181, 101, 29)) // light brown house
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));

        // Left wall
        scene.geometries.add(new Polygon(
                new Point(-200, -200, -400),
                new Point(-200, -200, -800),
                new Point(-200, 200, -800),
                new Point(-200, 200, -400))
                .setEmission(new Color(181, 101, 29)) // light brown house
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));

        // Right wall
        scene.geometries.add(new Polygon(
                new Point(200, -200, -400),
                new Point(200, -200, -800),
                new Point(200, 200, -800),
                new Point(200, 200, -400))
                .setEmission(new Color(181, 101, 29)) // light brown house
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));

        // Add door to the house
        Geometry door = new Polygon(
                new Point(-50, -200, -399),
                new Point(50, -200, -399),
                new Point(50, 0, -399),
                new Point(-50, 0, -399))
                .setEmission(new Color(100, 100, 100)) // Brown door
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
        scene.geometries.add(door);

        // Add door handle for the door
        Geometry doorHandle = new Sphere(7, new Point(-20, -100, -398))
                .setEmission(new Color(0, 0, 0)) // Black door handle
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
        scene.geometries.add(doorHandle);


        // Add window to the house
        Geometry window = new Polygon(
                new Point(-150, 50, -399),
                new Point(-50, 50, -399),
                new Point(-50, 150, -399),
                new Point(-150, 150, -399))
                .setEmission(new Color(135, 206, 250)) // Light blue window
                .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30));
        scene.geometries.add(window);

        // Roof
        scene.geometries.add(new Triangle(
                new Point(-250, 180, -350),
                new Point(250, 180, -350),
                new Point(0, 430, -550))
                .setEmission(new Color(128, 0, 0)) // Gray roof
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));

        scene.geometries.add(new Triangle(
                new Point(-250, 180, -350),
                new Point(0, 430, -550),
                new Point(-250, 180, -850))
                .setEmission(new Color(128, 0, 0)) // Gray roof
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));

        scene.geometries.add(new Triangle(
                new Point(250, 180, -350),
                new Point(250, 180, -850),
                new Point(0, 430, -550))
                .setEmission(new Color(128, 0, 0)) // Gray roof
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));

        scene.geometries.add(new Triangle(
                new Point(0, 430, -550),
                new Point(-250, 180, -850),
                new Point(250, 180, -850))
                .setEmission(new Color(128, 0, 0)) // Gray roof
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(300)));


        // Fence
        int fenceHeight = 160;
        int fenceWidth = 20;
        int fenceSpacing = 50;
        int numFencePosts = 20;

        // Create horizontal fence rails
        for (int i = 0; i < numFencePosts; i++) {
            double x = -200 + (fenceSpacing * i);
            double railZ = -400 - (fenceWidth / 2); // Adjust the Z-coordinate of the rails

            Point rail1Center = new Point(x + (fenceWidth / 2), -300 + (fenceHeight / 2), railZ);
            Vector rail1Dir = new Vector(0, 1, 0); // Direction along the length of the rail
            Ray rail1AxisRay = new Ray(rail1Center, rail1Dir);
            Geometry rail1 = new Cylinder(rail1AxisRay, fenceHeight, fenceWidth / 2)
                    .setEmission(new Color(139, 69, 19)) // Brown fence rail
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
            scene.geometries.add(rail1);

            Point rail2Center = new Point(-(x + (fenceWidth / 2)), -300 + (fenceHeight / 2), railZ);
            Vector rail2Dir = new Vector(0, 1, 0); // Direction along the length of the rail
            Ray rail2AxisRay = new Ray(rail2Center, rail2Dir);
            Geometry rail2 = new Cylinder(rail2AxisRay, fenceHeight, fenceWidth / 2)
                    .setEmission(new Color(139, 69, 19)) // Brown fence rail
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
            scene.geometries.add(rail2);
        }


        // Path
        int pathWidth = 200;
        int pathLength = 800;
        int numStones = 3000;
        int stoneRadius = 5;

        // Create path geometry
        Geometry path = new Polygon(
                new Point(-pathWidth / 2, -199, -pathLength / 2),
                new Point(-pathWidth / 2, -199, pathLength / 2),
                new Point(pathWidth / 2, -199, pathLength / 2),
                new Point(pathWidth / 2, -199, -pathLength / 2))
                .setEmission(new Color(139, 69, 19)) // Ground brown path
                .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30));
        scene.geometries.add(path);

        // Create stone spheres along the sides of the path
        for (int i = 0; i < numStones; i++) {
            double t = (double) i / (numStones - 1); // Parameter to interpolate between path points

            double x = -pathWidth / 2 + (t * pathWidth);
            double z = -pathLength / 2 + (Math.random() * pathLength);

            // Add stones only on the sides of the path
            if (x <= -pathWidth / 2 + stoneRadius || x >= pathWidth / 2 - stoneRadius) {
                Geometry stone = new Sphere(stoneRadius, new Point(x, -199 + stoneRadius, z))
                        .setEmission(new Color(105, 105, 105)) // Dark gray stone
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
                scene.geometries.add(stone);
            }
        }

        // Tree trunk
        Geometry trunk = new Cylinder(new Ray(new Point(500, -200, -600), new Vector(0, 1, 0)),
                300, 40)
                .setEmission(new Color(139, 69, 19)) // Brown trunk
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100));
        scene.geometries.add(trunk);

        // Tree leaves
        int numLeaves = 50;
        int leafRadius = 50;

        for (int k = 0; k < numLeaves; k++) {
            double x = 400 + (Math.random() * 200);
            double y = 0 + (Math.random() * 200);
            double z = -600 + (Math.random() * 200);

            Geometry leaf = new Sphere(leafRadius, new Point(x, y, z))
                    .setEmission(new Color(34, 139, 34)) // Green leaves
                    .setMaterial(new Material().setKd(0).setKs(0).setShininess(300));
            scene.geometries.add(leaf);
        }


        return scene;
    }

    @Test
    public void test_imageMove() {
        Camera camera = new Camera(
                new Point(0, 0, 800), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(1000, 1000)
                .setViewPlaneDistance(1000);

        Scene scene = this.beautifulPictureScene("test");

        int frames = 10;
        double angle = 360d / frames;
        double angleRadians = 2 * Math.PI / frames;

        double radius = camera.getP0().subtract(Point.ZERO).length();

        for (int i = 0; i < frames; i++) {
            camera.rotate(0, angle, 0);
            camera.setP0(
                    Math.sin(angleRadians * (i + 1)) * radius,
                    0,
                    Math.cos(angleRadians * (i + 1)) * radius
            );

            camera.setImageWriter(new ImageWriter("moveTest" + (i + 1), 1000, 1000))
                    .setRayTracer(new RayTracerBasic(scene))
                    .renderImage()
                    .writeToImage();
            ;
        }
    }


    @Test
    public void test_image() {
        Camera camera = new Camera(
                new Point(0, 0, 800), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(1000, 1000)
                .setViewPlaneDistance(800);


        Scene scene = this.beautifulPictureScene("TestImage");


        double angle = 360d /10;
        double angleRadians = 2 * Math.PI/10 ;

        double radius = camera.getP0().subtract(Point.ZERO).length();


        camera.rotate(0, angle, 0);
        camera.setP0(
                Math.sin(angleRadians * (2)) * radius,
                0,
                Math.cos(angleRadians * (2)) * radius);

        camera.setImageWriter(new ImageWriter("TestImage", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
        ;
    }


    @Test
    public void test_image_AA() {
        Camera camera = (new Camera(new Point(300, 0, 800),
                new Vector(0.0D, 0.0D, -1.0D), new Vector(0.0D, 1.0D, 0.0D)))
                .setViewPlaneSize(1000, 1000)
                .setViewPlaneDistance(800)
                .useAntiAliasing(true) //set AA
                .setNumOfAARays(70)
                .useAdaptive(true) //use adaptive
                .setMultithreading(5); //use multithreading

        Scene scene = this.beautifulPictureScene("test_AA");

        double angle = 360d /10;
        double angleRadians = 2 * Math.PI/10 ;

        double radius = camera.getP0().subtract(Point.ZERO).length();


        camera.rotate(0, angle, 0);
        camera.setP0(
                Math.sin(angleRadians * (2)) * radius,
                0,
                Math.cos(angleRadians * (2)) * radius);

        ImageWriter imageWriter = new ImageWriter("testAAFinal", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage();
        camera.writeToImage();
    }

    @Test
    public void test_image_SS() {
        Camera camera = (new Camera(new Point(0, 0, 800),
                new Vector(0.0D, 0.0D, -1.0D), new Vector(0.0D, 1.0D, 0.0D)))
                .setViewPlaneSize(1000, 1000)
                .setViewPlaneDistance(150);//.setMultithreading(0);

        Scene scene = this.beautifulPictureScene("test_SS");

        double angle = 360d /10;
        double angleRadians = 2 * Math.PI/10 ;

        double radius = camera.getP0().subtract(Point.ZERO).length();


        camera.rotate(0, angle, 0);
        camera.setP0(
                Math.sin(angleRadians * (2)) * radius,
                0,
                Math.cos(angleRadians * (2)) * radius);

        camera.setImageWriter(new ImageWriter("TestSS", 600, 600))
                .setRayTracer((new RayTracerBasic(scene))
                        .useSoftShadow(true).setNumOfSSRays(100).setRadiusBeamSS(50.0D))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void test_Glossy() {
        Camera camera = (new Camera(new Point(0.0D, 0.0D, 700.0D),
                new Vector(0.0D, 0.0D, -1.0D), new Vector(0.0D, 1.0D, 0.0D))).
                setViewPlaneSize(1000, 1000)
                .setViewPlaneDistance(700.0D).setMultithreading(4);

        Scene scene = this.beautifulPictureScene("test_G");

        double angle = 360d /10;
        double angleRadians = 2 * Math.PI/10 ;
        double radius = camera.getP0().subtract(Point.ZERO).length();

        camera.rotate(0, angle, 0);
        camera.setP0(
                Math.sin(angleRadians * (2)) * radius,
                0,
                Math.cos(angleRadians * (2)) * radius);

        camera.setImageWriter(new ImageWriter("TestGlossyFinal", 700, 700))
                .setRayTracer((new RayTracerBasic(scene))
                        .useGlossiness(true).setNumOfGlossinessRays(100))
                .renderImage()
                .writeToImage();
    }
    
    @Test
    public void PR09() {
        Camera camera = (new Camera(new Point(300, 0, 800),
                new Vector(0.0D, 0.0D, -1.0D), new Vector(0.0D, 1.0D, 0.0D)))
                .setViewPlaneSize(1000, 1000)
                .setViewPlaneDistance(800)
                .useAntiAliasing(true) //set AA
                .setNumOfAARays(70)
                .useAdaptive(true) //use adaptive
                .setMultithreading(5); //use multithreading

        Scene scene = this.beautifulPictureScene("final");

        double angle = 360d /10;
        double angleRadians = 2 * Math.PI/10 ;

        double radius = camera.getP0().subtract(Point.ZERO).length();


        camera.rotate(0, angle, 0);
        camera.setP0(
                Math.sin(angleRadians * (2)) * radius,
                0,
                Math.cos(angleRadians * (2)) * radius);

        camera.setImageWriter(new ImageWriter("final", 600, 600))
                .setRayTracer(new RayTracerBasic(scene)
                        .useSoftShadow(true).setNumOfSSRays(100).setRadiusBeamSS(50.0D) //use soft shadow
                        .useGlossiness(true).setNumOfGlossinessRays(100)) //use glossiness
                .renderImage()
                .writeToImage();
    }

    @Test
    public void PR081() {
        Camera camera = new Camera(new Point(0, -2000, 500),
                new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setViewPlaneSize(700, 700).setViewPlaneDistance(650);

        Scene scene = this.initial_image_7("test_PR08");

        camera.setImageWriter(new ImageWriter("PR08", 600, 600))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();


    }

    @Test
    public void PR08image() {
        Camera camera = new Camera(new Point(0, -2000, 500),
                new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setViewPlaneSize(200, 200).setViewPlaneDistance(200)
                .setNumOfAARays(30)
                .setMultithreading(5);

        Scene scene = this.initial_image_7("test_PR08");

        camera.setImageWriter(new ImageWriter("PR08image", 600, 600))
                .setRayTracer(new RayTracerBasic(scene)
                .useSoftShadow(true).setNumOfSSRays(30).setRadiusBeamSS(30) //use soft shadow
                .useGlossiness(true).setNumOfGlossinessRays(30)) //use glossiness
                .renderImage()
                .writeToImage();


    }


    @Test
    public Scene initial_image_7(String name) {


        Scene scene = new Scene(name);

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
                new Sphere(150, new Point(-295, -550, 250))
                        .setEmission(new Color(black)) //
                        .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30).setKr(0.8).setKg(0.8)),
                new Sphere(180, new Point(200, -850, 255))
                        .setEmission(new Color(black)) //
                        .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30).setKt(0.8).setKg(0.8))


        );

        scene.lights.add(new PointLight(new Color(white), new Point(0, -650, 1053)).setKl(0.00001).setKq(0.000002));
        scene.lights.add(new PointLight(new Color(white), new Point(200, -650, 1053)).setKl(0.00001).setKq(0.000002));

        return scene;
    }


}

