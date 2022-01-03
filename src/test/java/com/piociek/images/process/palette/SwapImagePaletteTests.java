package com.piociek.images.process.palette;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SwapImagePaletteTests {

    private final SwapImagePalette swapImagePalette = new SwapImagePalette();
    private static final String directoryPath = "src/test/resources/process/palette/";

    private static BufferedImage image;
    private static BufferedImage palette1;
    private static BufferedImage palette2;

    @BeforeAll
    public static void loadResources() throws IOException {
        image = ImageIO.read(new File(directoryPath + "image.png"));
        palette1 = ImageIO.read(new File(directoryPath + "palette1.png"));
        palette2 = ImageIO.read(new File(directoryPath + "palette2.png"));
    }

    @Test
    public void palettesSizeHaveToMatch() {
        assertThat(swapImagePalette.validatePaletteSize(palette1, palette2)).isTrue();
    }

    @Test
    public void palettesSizeHaveToMatch2() {
        assertThat(swapImagePalette.validatePaletteSize(palette1, image)).isFalse();
    }

    @Test
    public void swapColors_colorsChangedForCopy() {
        BufferedImage imagedWithSwappedColors =
                swapImagePalette.getImageCopyWithSwappedColors(image, palette1, palette2);

        boolean atLeastOneColorChanged = false;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int originalColor = image.getRGB(x, y);
                int swappedColor = imagedWithSwappedColors.getRGB(x, y);
                if (originalColor != swappedColor) {
                    atLeastOneColorChanged = true;
                }
            }
        }
        assertThat(atLeastOneColorChanged).isTrue();
    }

    @Test
    public void swapColors_colorsNotChangedInOriginal() throws IOException {
        swapImagePalette.getImageCopyWithSwappedColors(image, palette1, palette2);

        BufferedImage originalImage = ImageIO.read(new File(directoryPath + "image.png"));

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int imageUsedInMethod = image.getRGB(x, y);
                int reloadedImage = originalImage.getRGB(x, y);
                if (imageUsedInMethod != reloadedImage) {
                    fail("Colors were swapped");
                }
            }
        }
    }

    @Test
    public void swapColors_imageCopyHasSameSize() {
        BufferedImage imagedWithSwappedColors =
                swapImagePalette.getImageCopyWithSwappedColors(image, palette1, palette2);

        assertThat(image.getWidth()).isEqualTo(imagedWithSwappedColors.getWidth());
        assertThat(image.getHeight()).isEqualTo(imagedWithSwappedColors.getHeight());
    }
}
