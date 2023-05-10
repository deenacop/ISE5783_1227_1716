package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter}
     */
    @Test
    void testWriteToImage() {
        int nX=800;
        int nY =500;

        Color redColor = new Color(java.awt.Color.red);
        Color yelloColor = new Color(java.awt.Color.yellow);

        ImageWriter imageWriter = new ImageWriter("testbyello",800,500);

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                // 800/16 = 50// 500/10 = 50
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, redColor);
                }

                else {
                    imageWriter.writePixel(i, j,yelloColor);
                }
            }
        }
        imageWriter.writeToImage();
    }
}