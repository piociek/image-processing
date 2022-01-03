package com.piociek.images.process.stitch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StitchImagesTests {

    private final StitchImages stitchImages = new StitchImages();

    private static final String directoryPath = "src/test/resources/process/stitch/";

    private static BufferedImage image1;
    private static BufferedImage image2;
    private static List<BufferedImage> images;

    @BeforeAll
    public static void loadResources() throws IOException {
        image1 = ImageIO.read(new File(directoryPath + "file_1.png"));
        image2 = ImageIO.read(new File(directoryPath + "file_2.png"));
        images = Arrays.asList(image1, image2);
    }

    @Test
    public void checkStitchSize() {
        BufferedImage output = stitchImages.stitch(images, WidthOffsetType.LEFT, HeightOffsetType.TOP);

        assertThat(output.getWidth())
                .isEqualTo(2 * (Math.max(image1.getWidth(), image2.getWidth())));
        assertThat(output.getHeight())
                .isEqualTo(Math.max(image1.getHeight(), image2.getHeight()));
    }

    @Test
    public void checkStitch_offsetLeftTop() {
        BufferedImage output = stitchImages.stitch(images, WidthOffsetType.LEFT, HeightOffsetType.TOP);

        int image1Color = image1.getRGB(0, 0);
        int image2Color = image2.getRGB(0, 0);

        int image2WidthStart = output.getWidth() / 2;

        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                if (x < image1.getWidth() && y < image1.getHeight()) {
                    assertThat(output.getRGB(x, y)).isEqualTo(image1Color);
                } else if (x >= image2WidthStart
                        && (x < image2WidthStart + image2.getWidth())
                        && y < image2.getHeight()) {
                    assertThat(output.getRGB(x, y)).isEqualTo(image2Color);
                } else {
                    assertThat(output.getRGB(x, y)).isNotEqualTo(image1Color);
                    assertThat(output.getRGB(x, y)).isNotEqualTo(image2Color);
                }
            }
        }
    }

    @Test
    public void checkStitch_offsetCenterCenter() {
        BufferedImage output = stitchImages.stitch(images, WidthOffsetType.CENTER, HeightOffsetType.CENTER);
        int image1Color = image1.getRGB(0, 0);
        int image2Color = image2.getRGB(0, 0);

        int image1WidthOffset = ((output.getWidth() / 2) - image1.getWidth()) / 2;
        int image1HeightOffset = (output.getHeight() - image1.getHeight()) / 2;

        int image2WidthOffset = (output.getWidth() / 2) + ((output.getWidth() / 2) - image2.getWidth()) / 2;
        int image2HeightOffset = (output.getHeight() - image2.getHeight()) / 2;

        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                if (x >= image1WidthOffset
                        && (x < image1WidthOffset + image1.getWidth())
                        && y >= image1HeightOffset
                        && y < image1HeightOffset + image1.getHeight()) {
                    assertThat(output.getRGB(x, y)).isEqualTo(image1Color);
                } else if (x >= image2WidthOffset
                        && (x < image2WidthOffset + image2.getWidth())
                        && y >= image2HeightOffset
                        && y < image2HeightOffset + image2.getHeight()) {
                    assertThat(output.getRGB(x, y)).isEqualTo(image2Color);
                } else {
                    assertThat(output.getRGB(x, y)).isNotEqualTo(image1Color);
                    assertThat(output.getRGB(x, y)).isNotEqualTo(image2Color);
                }
            }
        }
    }

    @Test
    public void checkStitch_offsetRightDown() {
        BufferedImage output = stitchImages.stitch(images, WidthOffsetType.RIGHT, HeightOffsetType.DOWN);

        int image1Color = image1.getRGB(0, 0);
        int image2Color = image2.getRGB(0, 0);

        int image1WidthOffset = (output.getWidth() / 2) - image1.getWidth();
        int image1HeightOffset = output.getHeight() - image1.getHeight();

        int image2WidthOffset = (output.getWidth() / 2) + (output.getWidth() / 2) - image2.getWidth();
        int image2HeightOffset = output.getHeight() - image2.getHeight();

        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                if (x >= image1WidthOffset
                        && (x < image1WidthOffset + image1.getWidth())
                        && y >= image1HeightOffset
                        && y < image1HeightOffset + image1.getHeight()) {
                    assertThat(output.getRGB(x, y)).isEqualTo(image1Color);
                } else if (x >= image2WidthOffset
                        && (x < image2WidthOffset + image2.getWidth())
                        && y >= image2HeightOffset
                        && y < image2HeightOffset + image2.getHeight()) {
                    assertThat(output.getRGB(x, y)).isEqualTo(image2Color);
                } else {
                    assertThat(output.getRGB(x, y)).isNotEqualTo(image1Color);
                    assertThat(output.getRGB(x, y)).isNotEqualTo(image2Color);
                }
            }
        }
    }
}
