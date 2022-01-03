package com.piociek.images.engine.requests;

import org.junit.jupiter.api.Test;

import static com.piociek.images.messages.ExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LoadFilesRequestTests {

    private final String directoryPath = "src/test/resources/load";
    private final String extension = "png";
    private final String fileMask = "file_%s.png";

    @Test
    public void onlyPathSet() {
        assertDoesNotThrow(() -> LoadFilesRequest.builder().path(directoryPath).build());
    }

    @Test
    public void pathAndExtensionSet() {
        assertDoesNotThrow(() -> LoadFilesRequest.builder().path(directoryPath).extension(extension).build());
    }

    @Test
    public void pathAndFileNameSet_noFill() {
        assertDoesNotThrow(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .startIndex(1)
                .endIndex(2)
                .build());
    }

    @Test
    public void pathAndFileNameSet_withFill() {
        assertDoesNotThrow(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .startIndex(1)
                .endIndex(2)
                .fillNumberUpTo(10)
                .build());
    }

    @Test
    public void nothingSet() {
        assertThatThrownBy(() -> LoadFilesRequest.builder().build())
                .hasMessageContaining(PATH_NOT_SET);
    }

    @Test
    public void pathIsMandatoryField() {
        assertThatThrownBy(() -> LoadFilesRequest.builder().extension(extension).build())
                .hasMessageContaining(PATH_NOT_SET);
    }

    @Test
    public void canSetEitherExtensionOrFileNameMask() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .extension(extension)
                .fileNameWithMask(fileMask)
                .build()
        ).hasMessageContaining(EXTENSION_AND_FILE_WITH_MASK_SET);
    }

    @Test
    public void endIndexMustBeHigherThanStartIndex() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .startIndex(10)
                .endIndex(1)
                .build()
        ).hasMessageContaining(END_INDEX_LOWER_THAN_START_INDEX);
    }

    @Test
    public void startIndexNotSet() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .endIndex(10)
                .build()
        ).hasMessageContaining(START_INDEX_NOT_SET);
    }

    @Test
    public void startIndexMustBePositive() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .startIndex(-1)
                .endIndex(1)
                .build()
        ).hasMessageContaining(START_INDEX_NEGATIVE);
    }

    @Test
    public void endIndexNotSet() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .startIndex(10)
                .build()
        ).hasMessageContaining(END_INDEX_NOT_SET);
    }

    @Test
    public void endIndexMustBePositive() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .startIndex(1)
                .endIndex(-1)
                .build()
        ).hasMessageContaining(END_INDEX_NEGATIVE);
    }

    @Test
    public void fileNameDoesntHaveMask() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask("fileName.png")
                .build()
        ).hasMessageContaining(FILE_WITH_MASK_WITHOUT_MASK);
    }

    @Test
    public void startIndexSetWithoutFileName() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .startIndex(1)
                .build()
        ).hasMessageContaining(START_INDEX_WITHOUT_FILE_WITH_MASK);
    }

    @Test
    public void endIndexSetWithoutFileName() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .endIndex(1)
                .build()
        ).hasMessageContaining(END_INDEX_WITHOUT_FILE_WITH_MASK);
    }

    @Test
    public void fillNumberUpToSetWithoutFileName() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fillNumberUpTo(1)
                .build()
        ).hasMessageContaining(FILL_NUMBER_UP_TO_WITHOUT_FILE_WITH_MASK);
    }

    @Test
    public void fillNumberUpToMustBeLowerThanStartIndex() {
        assertThatThrownBy(() -> LoadFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileMask)
                .startIndex(10)
                .endIndex(20)
                .fillNumberUpTo(1)
                .build()
        ).hasMessageContaining(FILL_NUMBER_UP_TO_LOWER_THAN_START_INDEX);
    }
}
