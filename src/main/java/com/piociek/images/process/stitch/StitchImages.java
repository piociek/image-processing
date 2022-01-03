package com.piociek.images.process.stitch;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.IntStream;

public class StitchImages {

    public BufferedImage stitch(List<BufferedImage> images, WidthOffsetType widthOffsetType, HeightOffsetType heightOffsetType) {
        MaxDimensions maxDimensions = getMaxImageDimensions(images);
        BufferedImage output = createOutputImage(maxDimensions, images.size());
        Graphics graphics = output.getGraphics();

        IntStream.range(0, images.size())
                .boxed()
                .forEach(i ->
                        addImageToGraphics(graphics, images.get(i), i, maxDimensions, widthOffsetType, heightOffsetType)
                );

        return output;
    }

    private MaxDimensions getMaxImageDimensions(List<BufferedImage> images) {
        int maxHeight = 0;
        int maxWidth = 0;

        for (BufferedImage image : images) {
            if (image.getHeight() > maxHeight) {
                maxHeight = image.getHeight();
            }
            if (image.getWidth() > maxWidth) {
                maxWidth = image.getWidth();
            }
        }

        return new MaxDimensions(maxWidth, maxHeight);
    }

    private BufferedImage createOutputImage(MaxDimensions maxDimensions, int imagesCount) {
        int targetHeight = maxDimensions.getHeight();
        int targetWidth = imagesCount * maxDimensions.getWidth();

        return new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
    }

    private void addImageToGraphics(Graphics graphics, BufferedImage image, int index, MaxDimensions maxDimensions,
                                    WidthOffsetType widthOffsetType, HeightOffsetType heightOffsetType) {

        int width = index * maxDimensions.getWidth() + getWidthOffset(widthOffsetType, image, maxDimensions.getWidth());
        int height = getHeightOffset(heightOffsetType, image, maxDimensions.getHeight());
        graphics.drawImage(image, width, height, null);
    }

    private int getWidthOffset(WidthOffsetType widthOffsetType, BufferedImage image, int maxWidth) {
        return switch (widthOffsetType) {
            case LEFT -> 0;
            case CENTER -> (maxWidth - image.getWidth()) / 2;
            case RIGHT -> maxWidth - image.getWidth();
        };
    }

    private int getHeightOffset(HeightOffsetType heightOffsetType, BufferedImage image, int maxHeight) {
        return switch (heightOffsetType) {
            case TOP -> 0;
            case CENTER -> (maxHeight - image.getHeight()) / 2;
            case DOWN -> maxHeight - image.getHeight();
        };
    }
}
