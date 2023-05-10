package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

import java.util.Objects;

public class Scene {
    //Field represents the name of the scene
    public String name;
    //Field represents background color of the scene
    public Color background;
    //Field represents the ambient lighting of the scene (default-black)
    public AmbientLight ambientLight = AmbientLight.NONE;
    //Field represents the geometry models in the scene
    public Geometries geometries;

    /**
     * Constructor for Scene
     *
     * @param name the name of the scene
     */
    /**
     * Constructor for Scene
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * Plain Data Structure
     *
     * @param builder is the scene builder
     */
    public Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }
    public static class SceneBuilder {

        private final String name;
        public Color background = Color.BLACK;
        public AmbientLight ambientLight = new AmbientLight();
        public Geometries geometries = new Geometries();

        public SceneBuilder(String name) {
            this.name = name;
        }

        //chaining methods:

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public Scene build() {
            return new Scene(this);
        }
    }

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
