package com.piociek.images.write;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.piociek.images.messages.LogMessages.IMAGE_WRITTEN;

public class WriteImage {

    private static final Logger LOGGER = Logger.getLogger(WriteImage.class.toString());

    public void write(String path, BufferedImage image) {
        write(new File(path), image);
    }

    public void write(String path, String fileName, BufferedImage image) {
        write(new File(path, fileName), image);
    }

    private void write(File file, BufferedImage image) {
        try {
            ImageIO.write(image, "PNG", file);
            LOGGER.log(Level.INFO, String.format(IMAGE_WRITTEN, file.getPath()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
