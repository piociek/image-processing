package com.piociek.images.write;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WriteImageTests {

    private final WriteImage writeImage = new WriteImage();
    private final String fileName = "output.png";

    @Test
    public void writeImage_path(@TempDir File tempDir) {

        writeImage.write(
                Paths.get(tempDir.toPath().toString(), fileName).toString(), new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        assertThat(Files.exists(Paths.get(tempDir.getPath(), fileName))).isTrue();
    }

    @Test
    public void writeImage_pathAndFileName(@TempDir File tempDir) {
        writeImage.write(
                Paths.get(tempDir.toPath().toString(), fileName).toString(),
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        assertThat(Files.exists(Paths.get(tempDir.getPath(), fileName))).isTrue();
    }

    @Test
    public void writeImage_pathDontExist(@TempDir File tempDir) {
        assertThatThrownBy(() -> writeImage.write(
                tempDir.toPath() + "1",
                fileName,
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB))
        ).hasCauseInstanceOf(IOException.class);
    }
}
