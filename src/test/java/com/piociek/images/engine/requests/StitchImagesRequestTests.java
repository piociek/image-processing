package com.piociek.images.engine.requests;

import com.piociek.images.process.stitch.HeightOffsetType;
import com.piociek.images.process.stitch.WidthOffsetType;
import org.junit.jupiter.api.Test;

import static com.piociek.images.messages.ExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class StitchImagesRequestTests {

    private final String directoryPath = "src/test/resources/load";
    private final String fileName = "file.png";
    private final String fileNameWithMask = "file%s.png";

    private final LoadFileRequest loadFileRequest = LoadFileRequest.builder()
            .path(directoryPath)
            .fileName(fileName)
            .build();
    private final LoadFilesRequest loadFilesRequest = LoadFilesRequest.builder()
            .path(directoryPath)
            .build();
    private final WriteFileRequest writeFileRequest = WriteFileRequest.builder()
            .path(directoryPath)
            .fileName(fileName)
            .build();
    private final WriteFilesRequest writeFilesRequest = WriteFilesRequest.builder()
            .path(directoryPath)
            .fileNameWithMask(fileNameWithMask)
            .startIndex(1)
            .build();

    @Test
    public void stitchImagesWithoutPalettes() {
        assertDoesNotThrow(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .output(writeFileRequest)
                .build()
        );
    }

    @Test
    public void stitchImagesWitPalettes() {
        assertDoesNotThrow(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .defaultPalette(loadFileRequest)
                .palettes(loadFilesRequest)
                .outputs(writeFilesRequest)
                .build()
        );
    }

    @Test
    public void imagesToProcessNotSet() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .output(writeFileRequest)
                .build()
        ).hasMessageContaining(IMAGES_TO_PROCESS_NOT_SET);
    }

    @Test
    public void widthOffsetNotSet() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .heightOffsetType(HeightOffsetType.DOWN)
                .output(writeFileRequest)
                .build()
        ).hasMessageContaining(WIDTH_OFFSET_NOT_SET);
    }

    @Test
    public void heightOffsetNotSet() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .output(writeFileRequest)
                .build()
        ).hasMessageContaining(HEIGHTS_OFFSET_NOT_SET);
    }

    @Test
    public void outputNotSet() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .build()
        ).hasMessageContaining(OUTPUT_NOT_SET);
    }

    @Test
    public void outputsSetWithoutPalettes() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .outputs(writeFilesRequest)
                .build()
        ).hasMessageContaining(OUTPUTS_SET);
    }

    @Test
    public void outputAndOutputsSet() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .output(writeFileRequest)
                .outputs(writeFilesRequest)
                .build()
        ).hasMessageContaining(OUTPUT_AND_OUTPUTS_SET);
    }

    @Test
    public void defaultPaletteNotSet() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .palettes(loadFilesRequest)
                .outputs(writeFilesRequest)
                .build()
        ).hasMessageContaining(PALETTES_WITHOUT_DEFAULT_PALETTE);
    }

    @Test
    public void palettesNotSet() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .defaultPalette(loadFileRequest)
                .outputs(writeFilesRequest)
                .build()
        ).hasMessageContaining(DEFAULT_PALETTE_WITHOUT_PALETTES);
    }

    @Test
    public void outputSetForPalettes() {
        assertThatThrownBy(() -> StitchImagesRequest.builder()
                .imagesToProcess(loadFilesRequest)
                .widthOffsetType(WidthOffsetType.RIGHT)
                .heightOffsetType(HeightOffsetType.DOWN)
                .defaultPalette(loadFileRequest)
                .palettes(loadFilesRequest)
                .output(writeFileRequest)
                .build()
        ).hasMessageContaining(OUTPUTS_NOT_SET);
    }
}
