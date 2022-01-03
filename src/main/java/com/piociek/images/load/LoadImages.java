package com.piociek.images.load;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.piociek.images.messages.LogMessages.IMAGE_LOADED;

public class LoadImages {

    private static final Logger LOGGER = Logger.getLogger(LoadImages.class.toString());

    public BufferedImage loadImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            LOGGER.log(Level.INFO, String.format(IMAGE_LOADED, file.getName()));
            return image;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<BufferedImage> loadImages(List<File> files) {
        return files.stream()
                .map(this::loadImage)
                .collect(Collectors.toList());
    }
}
