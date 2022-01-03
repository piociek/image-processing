package com.piociek.images.engine.requests;

import org.junit.jupiter.api.Test;

import static com.piociek.images.messages.ExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class WriteFilesRequestTests {

    private final String directoryPath = "src/test/resources/load";
    private final String fileNameWithMask = "file%s.png";

    @Test
    public void nothingSet() {
        assertThatThrownBy(() -> WriteFilesRequest.builder().build())
                .hasMessageContaining(PATH_NOT_SET);
    }

    @Test
    public void onlyPathSet() {
        assertThatThrownBy(() -> WriteFilesRequest.builder().path(directoryPath).build())
                .hasMessageContaining(FILE_WITH_MASK_NOT_SET);
    }

    @Test
    public void onlyFileNameSet() {
        assertThatThrownBy(() -> WriteFilesRequest.builder().fileNameWithMask(fileNameWithMask).build())
                .hasMessageContaining(PATH_NOT_SET);
    }

    @Test
    public void fileNameWithoutMask() {
        assertThatThrownBy(() -> WriteFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask("image.png")
                .startIndex(1)
                .build()
        ).hasMessageContaining(FILE_WITH_MASK_WITHOUT_MASK);
    }

    @Test
    public void startIndexNotSet() {
        assertThatThrownBy(() -> WriteFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileNameWithMask)
                .build()
        ).hasMessageContaining(START_INDEX_NOT_SET);
    }

    @Test
    public void startIndexNegative() {
        assertThatThrownBy(() -> WriteFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileNameWithMask)
                .startIndex(-10)
                .build()
        ).hasMessageContaining(START_INDEX_NEGATIVE);
    }

    @Test
    public void fillNumbersUpToLowerThanStartIndex() {
        assertThatThrownBy(() -> WriteFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileNameWithMask)
                .startIndex(100)
                .fillNumberUpTo(10)
                .build()
        ).hasMessageContaining(FILL_NUMBER_UP_TO_LOWER_THAN_START_INDEX);
    }

    @Test
    public void correctRequest() {
        assertDoesNotThrow(() -> WriteFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileNameWithMask)
                .startIndex(1)
                .build());
    }

    @Test
    public void correctRequestWithFillNumberUpTo() {
        assertDoesNotThrow(() -> WriteFilesRequest.builder()
                .path(directoryPath)
                .fileNameWithMask(fileNameWithMask)
                .startIndex(1)
                .fillNumberUpTo(10)
                .build());
    }
}
