package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Scene {
    //Field represents the name of the scene
    public String name;
    //Field represents background color of the scene
    public Color background  = Color.BLACK;;
    //Field represents the ambient lighting of the scene (default-black)
    public AmbientLight ambientLight = AmbientLight.NONE;
    //Field represents the geometry models in the scene
    public Geometries geometries;
    // Field represents the lights sources in the scene
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructor for Scene
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public List<LightSource> getLights() {
        return lights;
    }



//    public static class SceneBuilder {
//
//        private final String name;
//        public Color background = Color.BLACK;
//        public AmbientLight ambientLight = new AmbientLight();
//        public Geometries geometries = new Geometries();
//
//        public SceneBuilder(String name) {
//            this.name = name;
//        }
//
//        //chaining methods:
//
//        public SceneBuilder setBackground(Color background) {
//            this.background = background;
//            return this;
//        }
//
//        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
//            this.ambientLight = ambientLight;
//            return this;
//        }
//
//        public SceneBuilder setGeometries(Geometries geometries) {
//            this.geometries = geometries;
//            return this;
//        }
//
//        public Scene build() {
//            return new Scene(this);
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scene scene = (Scene) o;
        return Objects.equals(name, scene.name) && Objects.equals(background, scene.background) && Objects.equals(ambientLight, scene.ambientLight) && Objects.equals(geometries, scene.geometries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, background, ambientLight, geometries);
    }

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", background=" + background +
                ", ambientLight=" + ambientLight +
                ", geometries=" + geometries +
                '}';
    }
}
