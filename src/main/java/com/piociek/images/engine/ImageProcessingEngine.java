package com.piociek.images.engine;

import com.piociek.images.engine.requests.*;
import com.piociek.images.load.LoadFiles;
import com.piociek.images.load.LoadImages;
import com.piociek.images.process.background.RemoveImageBackground;
import com.piociek.images.process.palette.SwapImagePalette;
import com.piociek.images.process.stitch.StitchImages;
import com.piociek.images.util.NumberUtil;
import com.piociek.images.write.WriteImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.piociek.images.messages.LogMessages.PALETTE_DOES_NOT_MATCH_DEFAULT;

public class ImageProcessingEngine {

    private final static Logger LOGGER = Logger.getLogger(ImageProcessingEngine.class.toString());

    private final LoadFiles loadFiles = new LoadFiles();
    private final LoadImages loadImages = new LoadImages();
    private final RemoveImageBackground removeImageBackground = new RemoveImageBackground();
    private final SwapImagePalette swapImagePalette = new SwapImagePalette();
    private final StitchImages stitchImages = new StitchImages();
    private final WriteImage writeImage = new WriteImage();

    public void process(StitchImagesRequest request) {
        List<File> filesToProcess = loadFiles(request.getImagesToProcess());
        List<BufferedImage> imagesToProcess = loadImages.loadImages(filesToProcess);

        if (request.isRemoveBackground()) {
            imagesToProcess = imagesToProcess.stream()
                    .map(removeImageBackground::getImageCopyWithoutRGBColor)
                    .collect(Collectors.toList());
        }

        BufferedImage stitchedImage = stitchImages
                .stitch(imagesToProcess, request.getWidthOffsetType(), request.getHeightOffsetType());

        if (request.paletteSwap()) {
            File paletteDefaultFile = loadFile(request.getDefaultPalette());
            BufferedImage paletteDefault = loadImages.loadImage(paletteDefaultFile);
            List<File> paletteFiles = loadFiles(request.getPalettes());
            List<BufferedImage> palettes = loadImages.loadImages(paletteFiles);

            List<BufferedImage> swapPaletteImages = palettes.stream()
                    .filter(palette -> {
                        boolean match = swapImagePalette.validatePaletteSize(paletteDefault, palette);
                        if (!match) {
                            LOGGER.severe(PALETTE_DOES_NOT_MATCH_DEFAULT);
                        }
                        return match;
                    })
                    .map(palette -> swapImagePalette.getImageCopyWithSwappedColors(stitchedImage, paletteDefault, palette))
                    .collect(Collectors.toList());

            List<BufferedImage> allImages = new ArrayList<>();
            allImages.add(stitchedImage);
            allImages.addAll(swapPaletteImages);

            writeImagesToFiles(request.getOutputs(), allImages);
        } else {
            writeImageToFile(request.getOutput(), stitchedImage);
        }
    }

    private File loadFile(LoadFileRequest request) {
        if (Objects.isNull(request.getFileName())) {
            return loadFiles.loadFile(request.getPath());
        } else {
            return loadFiles.loadFile(request.getPath(), request.getFileName());
        }
    }

    private List<File> loadFiles(LoadFilesRequest request) {
        if (Objects.nonNull(request.getExtension())) {
            return loadFiles.loadFilesFromDirectoryWithExtensionFilter(
                    request.getPath(),
                    request.getExtension()
            );
        } else if (Objects.nonNull(request.getFileNameWithMask())) {
            if (Objects.isNull(request.getFillNumberUpTo())) {
                return loadFiles.loadFilesFromDirectoryWithCounterMask(
                        request.getPath(),
                        request.getFileNameWithMask(),
                        request.getStartIndex(),
                        request.getEndIndex()
                );
            } else {
                return loadFiles.loadFilesFromDirectoryWithCounterMask(
                        request.getPath(),
                        request.getFileNameWithMask(),
                        request.getStartIndex(),
                        request.getEndIndex(),
                        request.getFillNumberUpTo()
                );
            }
        } else {
            return loadFiles.loadFilesFromDirectory(request.getPath());
        }
    }

    private void writeImageToFile(WriteFileRequest request, BufferedImage image) {
        if (Objects.isNull(request.getFileName())) {
            writeImage.write(request.getPath(), image);
        } else {
            writeImage.write(request.getPath(), request.getFileName(), image);
        }
    }

    private void writeImagesToFiles(WriteFilesRequest request, List<BufferedImage> image) {
        for (int i = 0; i < image.size(); i++) {
            String fileId;
            if (Objects.nonNull(request.getFillNumberUpTo())) {
                fileId = NumberUtil.fillWithZeros(request.getStartIndex() + i, request.getFillNumberUpTo());
            } else {
                fileId = String.valueOf(request.getStartIndex() + i);
            }
            String fileName = String.format(request.getFileNameWithMask(), fileId);
            String stringPath = Paths.get(request.getPath(), fileName).toString();
            writeImage.write(stringPath, image.get(i));
        }
    }
}
