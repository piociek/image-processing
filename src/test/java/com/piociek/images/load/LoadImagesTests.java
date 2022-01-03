package com.piociek.images.load;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoadImagesTests {

    private final LoadImages loadImages = new LoadImages();
    private final String directoryPath = "src/test/resources/load/";
    private final List<String> imageFileNames =
            Arrays.asList("file_1.png", "file_01.png");

    @Test
    public void loadImage() {
        BufferedImage bufferedImage = loadImages.loadImage(new File(directoryPath + imageFileNames.get(0)));
        assertThat(bufferedImage.getWidth()).isGreaterThan(0);
        assertThat(bufferedImage.getHeight()).isGreaterThan(0);
    }

    @Test
    public void loadImages() {
        loadImages.loadImages(imageFileNames.stream()
                .map(name -> new File(directoryPath + name))
                .collect(Collectors.toList())
        ).forEach(bufferedImage -> {
            assertThat(bufferedImage.getWidth()).isGreaterThan(0);
            assertThat(bufferedImage.getHeight()).isGreaterThan(0);
        });

    }

    @Test
    public void loadNonExistingImage() {
        assertThatThrownBy(() -> loadImages.loadImage(new File(directoryPath + "any.png")));
    }
}
