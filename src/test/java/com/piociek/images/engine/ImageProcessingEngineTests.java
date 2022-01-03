package com.piociek.images.engine;

import com.piociek.images.engine.requests.*;
import com.piociek.images.process.stitch.HeightOffsetType;
import com.piociek.images.process.stitch.WidthOffsetType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageProcessingEngineTests {

    private final ImageProcessingEngine imageProcessingEngine = new ImageProcessingEngine();

    private final String loadDirectoryPath = "src/test/resources/e2e";
    private final String loadFileNameMask = "file_%s.png";
    private final String imageExtension = "PNG";
    private final int startIndex = 1;
    private final int endIndex = 2;
    private final int fillUpTo1 = 1;
    private final int fillUpTo10 = 10;
    private final String writeFileName = "output.png";
    private final String writeFileNameWithMask = "output_%s.png";
    private final String defaultPalette = "default.png";
    private final String paletteFileNameMask = "palette_%s.png";
    private final String paletteDirectoryPath = loadDirectoryPath + "/palettes";

    private final LoadFilesRequest imagesToProcess_path = LoadFilesRequest.builder()
            .path(loadDirectoryPath)
            .build();
    private final LoadFilesRequest imagesToProcess_extension = LoadFilesRequest.builder()
            .path(loadDirectoryPath)
            .extension(imageExtension)
            .build();
    private final LoadFilesRequest imagesToProcess_fileNameStartEndFillUpTo = LoadFilesRequest.builder()
            .path(loadDirectoryPath)
            .fileNameWithMask(loadFileNameMask)
            .startIndex(startIndex)
            .endIndex(endIndex)
            .fillNumberUpTo(fillUpTo10)
            .build();
    private final WidthOffsetType widthOffsetType = WidthOffsetType.RIGHT;
    private final HeightOffsetType heightOffsetType = HeightOffsetType.DOWN;
    private final LoadFileRequest defaultPlatte_path = LoadFileRequest.builder()
            .path(Paths.get(paletteDirectoryPath, defaultPalette).toString())
            .build();
    private final LoadFileRequest defaultPlatte_pathFileName = LoadFileRequest.builder()
            .path(paletteDirectoryPath)
            .fileName(defaultPalette)
            .build();
    private final LoadFilesRequest palettes_fileNameStartEnd = LoadFilesRequest.builder()
            .path(paletteDirectoryPath)
            .fileNameWithMask(paletteFileNameMask)
            .startIndex(startIndex)
            .endIndex(startIndex)
            .build();
    private final LoadFilesRequest palettes_fileNameStartEndFillUpTo = LoadFilesRequest.builder()
            .path(paletteDirectoryPath)
            .fileNameWithMask(paletteFileNameMask)
            .startIndex(startIndex)
            .endIndex(endIndex)
            .fillNumberUpTo(fillUpTo1)
            .build();

    @Test
    public void stitchImages_withRemoveBackground_withoutPalettes(@TempDir File tempDir) {
        StitchImagesRequest stitchImagesRequest = StitchImagesRequest.builder()
                .imagesToProcess(imagesToProcess_path)
                .widthOffsetType(widthOffsetType)
                .heightOffsetType(heightOffsetType)
                .output(WriteFileRequest.builder()
                        .path(tempDir.getPath())
                        .fileName(writeFileName)
                        .build())
                .build();

        imageProcessingEngine.process(stitchImagesRequest);

        assertThat(Files.exists(Paths.get(tempDir.getPath(), writeFileName))).isTrue();
    }

    @Test
    public void stitchImages_withoutRemoveBackground_withoutPalettes(@TempDir File tempDir) {
        StitchImagesRequest stitchImagesRequest = StitchImagesRequest.builder()
                .imagesToProcess(imagesToProcess_extension)
                .widthOffsetType(widthOffsetType)
                .heightOffsetType(heightOffsetType)
                .removeBackground(true)
                .output(WriteFileRequest.builder()
                        .path(Paths.get(tempDir.getPath(), writeFileName).toString())
                        .build())
                .build();

        imageProcessingEngine.process(stitchImagesRequest);

        assertThat(Files.exists(Paths.get(tempDir.getPath(), writeFileName))).isTrue();
    }

    @Test
    public void stitchImages_withPalettes(@TempDir File tempDir) {
        StitchImagesRequest stitchImagesRequest = StitchImagesRequest.builder()
                .imagesToProcess(imagesToProcess_fileNameStartEndFillUpTo)
                .widthOffsetType(widthOffsetType)
                .heightOffsetType(heightOffsetType)
                .defaultPalette(defaultPlatte_path)
                .palettes(palettes_fileNameStartEnd)
                .outputs(WriteFilesRequest.builder()
                        .path(tempDir.getPath())
                        .fileNameWithMask(writeFileNameWithMask)
                        .startIndex(startIndex)
                        .fillNumberUpTo(fillUpTo10)
                        .build())
                .build();

        imageProcessingEngine.process(stitchImagesRequest);

        assertThat(Files.exists(Paths.get(tempDir.getPath(), String.format(writeFileNameWithMask, "01")))).isTrue();
        assertThat(Files.exists(Paths.get(tempDir.getPath(), String.format(writeFileNameWithMask, "02")))).isTrue();
    }

    @Test
    public void stitchImages_withPalettes2(@TempDir File tempDir) {
        StitchImagesRequest stitchImagesRequest = StitchImagesRequest.builder()
                .imagesToProcess(imagesToProcess_extension)
                .widthOffsetType(widthOffsetType)
                .heightOffsetType(heightOffsetType)
                .defaultPalette(defaultPlatte_pathFileName)
                .palettes(palettes_fileNameStartEndFillUpTo)
                .outputs(WriteFilesRequest.builder()
                        .path(tempDir.getPath())
                        .fileNameWithMask(writeFileNameWithMask)
                        .startIndex(startIndex)
                        .build())
                .build();

        imageProcessingEngine.process(stitchImagesRequest);

        assertThat(Files.exists(Paths.get(tempDir.getPath(), String.format(writeFileNameWithMask, "1")))).isTrue();
        assertThat(Files.exists(Paths.get(tempDir.getPath(), String.format(writeFileNameWithMask, "2")))).isTrue();
    }
}
