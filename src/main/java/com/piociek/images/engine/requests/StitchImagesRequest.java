package com.piociek.images.engine.requests;

import com.piociek.images.process.stitch.HeightOffsetType;
import com.piociek.images.process.stitch.WidthOffsetType;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

import static com.piociek.images.messages.ExceptionMessage.*;

@Builder
@Getter
public class StitchImagesRequest {
    private LoadFilesRequest imagesToProcess;
    private boolean removeBackground;
    private LoadFileRequest defaultPalette;
    private LoadFilesRequest palettes;
    private HeightOffsetType heightOffsetType;
    private WidthOffsetType widthOffsetType;
    private WriteFileRequest output;
    private WriteFilesRequest outputs;

    public boolean paletteSwap() {
        return Objects.nonNull(defaultPalette) && Objects.nonNull(palettes);
    }

    public static StitchImagesRequestBuilder builder() {
        return new StitchImagesRequestBuilder() {
            @Override
            public StitchImagesRequest build() {
                validateImagesToProcess();
                validateOffsets();
                verifyPalettes();
                validateWriteOutput();
                validateCorrectWriteOutput();
                return super.build();
            }
        };
    }

    public static class StitchImagesRequestBuilder {

        public StitchImagesRequestBuilder defaultPalette(LoadFileRequest defaultPalette) {
            this.defaultPalette = defaultPalette;
            return this;
        }

        public StitchImagesRequestBuilder palettes(LoadFilesRequest palettes) {
            this.palettes = palettes;
            verifyDefaultPalettes();
            return this;
        }

        void validateImagesToProcess() {
            if (Objects.isNull(imagesToProcess)) {
                throw new IllegalStateException(IMAGES_TO_PROCESS_NOT_SET);
            }
        }

        void verifyPalettes() {
            if (Objects.nonNull(defaultPalette) && Objects.isNull(palettes)) {
                throw new IllegalStateException(DEFAULT_PALETTE_WITHOUT_PALETTES);
            }
        }

        private void verifyDefaultPalettes() {
            if (Objects.isNull(defaultPalette) && Objects.nonNull(palettes)) {
                throw new IllegalStateException(PALETTES_WITHOUT_DEFAULT_PALETTE);
            }
        }

        void validateOffsets() {
            if (Objects.isNull(heightOffsetType)) {
                throw new IllegalStateException(HEIGHTS_OFFSET_NOT_SET);
            }
            if (Objects.isNull(widthOffsetType)) {
                throw new IllegalStateException(WIDTH_OFFSET_NOT_SET);
            }
        }

        void validateWriteOutput() {
            if (Objects.isNull(output) && Objects.isNull(outputs)) {
                throw new IllegalStateException(OUTPUT_NOT_SET);
            }
            if (Objects.nonNull(output) && Objects.nonNull(outputs)) {
                throw new IllegalStateException(OUTPUT_AND_OUTPUTS_SET);
            }
        }

        void validateCorrectWriteOutput() {
            if (Objects.nonNull(defaultPalette) && Objects.nonNull(palettes) && Objects.nonNull(output)) {
                throw new IllegalStateException(OUTPUTS_NOT_SET);
            }
            if (Objects.isNull(defaultPalette) && Objects.isNull(palettes) && Objects.nonNull(outputs)) {
                throw new IllegalStateException(OUTPUTS_SET);
            }
        }
    }
}
