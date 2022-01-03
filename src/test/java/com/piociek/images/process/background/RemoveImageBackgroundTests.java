package com.piociek.images.process.background;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class RemoveImageBackgroundTests {

    private static final RemoveImageBackground removeImageBackground = new RemoveImageBackground();
    private static final String directoryPath = "src/test/resources/process/background/";
    private static final String rgbImageFileName = "rgbMask.png";
    private final String noRgbImageFileName = "noRgbMask.png";

    private static BufferedImage image;
    private static int colorMask;
    private static BufferedImage imageWithoutBackground;

    @BeforeAll
    public static void loadResources() throws IOException {
        image = ImageIO.read(new File(directoryPath + rgbImageFileName));
        colorMask = removeImageBackground.getRgbMask(image);
        imageWithoutBackground =
                removeImageBackground.getImageCopyWithoutRGBColor(image, colorMask);
    }

    @Test
    public void findRgbMask() throws IOException {
        BufferedImage image = ImageIO.read(new File(directoryPath + rgbImageFileName));
        assertThat(removeImageBackground.getRgbMask(image))
                .isLessThan(0);
    }

    @Test
    public void throwsExceptionIfCantFindRgbMask() throws IOException {
        BufferedImage image = ImageIO.read(new File(directoryPath + noRgbImageFileName));
        assertThatThrownBy(() -> removeImageBackground.getRgbMask(image));
    }

    @Test
    public void removeBackground_maskColorRemovedFromCopy() {
        for (int x = 0; x < imageWithoutBackground.getWidth(); x++) {
            for (int y = 0; y < imageWithoutBackground.getHeight(); y++) {
                if (imageWithoutBackground.getRGB(x, y) == colorMask) {
                    fail("Mask color was not removed from image copy");
                }
            }
        }
    }

    @Test
    public void removeBackground_maskColorNotRemovedFromOriginal() {
        boolean isColorPresent = false;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) == colorMask) {
                    isColorPresent = true;
                    break;
                }
            }
        }
        assertThat(isColorPresent).isTrue();
    }

    @Test
    public void removeBackground_imageCopyHasSameSize() {
        assertThat(image.getWidth()).isEqualTo(imageWithoutBackground.getWidth());
        assertThat(image.getHeight()).isEqualTo(imageWithoutBackground.getHeight());
    }

    @Test
    public void removeBackground_maskFoundAutomatically() {
        BufferedImage imageWithoutBackground =
                removeImageBackground.getImageCopyWithoutRGBColor(image);
        for (int x = 0; x < imageWithoutBackground.getWidth(); x++) {
            for (int y = 0; y < imageWithoutBackground.getHeight(); y++) {
                if (imageWithoutBackground.getRGB(x, y) == colorMask) {
                    fail("Mask color was not removed from image copy");
                }
            }
        }
    }
}
