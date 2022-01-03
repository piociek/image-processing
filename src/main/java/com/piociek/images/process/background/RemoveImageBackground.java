package com.piociek.images.process.background;

import java.awt.image.BufferedImage;
import java.util.*;

public class RemoveImageBackground {

    public BufferedImage getImageCopyWithoutRGBColor(BufferedImage image) {
        return getImageCopyWithoutRGBColor(image, getRgbMask(image));
    }

    public BufferedImage getImageCopyWithoutRGBColor(BufferedImage image, int rgbMask) {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) != rgbMask) {
                    output.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }

        return output;
    }

    public Integer getRgbMask(BufferedImage image) {
        Map<Integer, Integer> rgbCornersMap = new HashMap<>();
        for (int x : Arrays.asList(0, image.getWidth() - 1)) {
            for (int y : Arrays.asList(0, image.getHeight() - 1)) {
                int color = image.getRGB(x, y);
                if (rgbCornersMap.containsKey(color)) {
                    rgbCornersMap.put(color, rgbCornersMap.get(color) + 1);
                } else {
                    rgbCornersMap.put(color, 1);
                }
            }
        }

        if (rgbCornersMap.keySet().size() > 1 && new HashSet<>(rgbCornersMap.values()).size() == 1) {
            throw new IllegalStateException("Unable to find rgbMask");
        }

        List<Integer> values = new ArrayList<>(rgbCornersMap.values());
        Collections.sort(values);

        int rgbMask = 0;

        for (Integer key : rgbCornersMap.keySet()) {
            if (Objects.equals(rgbCornersMap.get(key), values.get(values.size() - 1))) {
                rgbMask = key;
            }
        }

        return rgbMask;
    }
}
