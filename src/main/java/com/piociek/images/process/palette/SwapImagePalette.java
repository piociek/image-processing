package com.piociek.images.process.palette;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SwapImagePalette {

    public boolean validatePaletteSize(BufferedImage paletteDefault, BufferedImage palette) {
        return (paletteDefault.getHeight() == palette.getHeight()) && (paletteDefault.getHeight() == palette.getHeight());
    }

    public BufferedImage getImageCopyWithSwappedColors(BufferedImage image, BufferedImage paletteDefault, BufferedImage palette) {
        Map<Integer, Integer> swapMap = createSwapMap(paletteDefault, palette);

        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int originalRgb = image.getRGB(x, y);
                output.setRGB(x, y, swapMap.getOrDefault(originalRgb, originalRgb));
            }
        }

        return output;
    }

    private Map<Integer, Integer> createSwapMap(BufferedImage paletteDefault, BufferedImage palette) {
        Map<Integer, Integer> swapMap = new HashMap<>();

        for (int x = 0; x < paletteDefault.getWidth(); x++) {
            for (int y = 0; y < paletteDefault.getHeight(); y++) {
                swapMap.put(paletteDefault.getRGB(x, y), palette.getRGB(x, y));
            }
        }

        return swapMap;
    }
}
