package com.piociek.images.engine.requests;

import org.junit.jupiter.api.Test;

import static com.piociek.images.messages.ExceptionMessage.PATH_NOT_SET;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LoadFileRequestTests {

    private final String directoryPath = "src/test/resources/load";
    private final String fileName = "file.png";

    @Test
    public void onlyPathSet() {
        assertDoesNotThrow(() -> LoadFileRequest.builder().path(directoryPath).build());
    }

    @Test
    public void pathAndFileNameSet() {
        assertDoesNotThrow(() -> LoadFileRequest.builder().path(directoryPath).fileName(fileName).build());
    }

    @Test
    public void nothingSet() {
        assertThatThrownBy(() -> LoadFileRequest.builder().build())
                .hasMessageContaining(PATH_NOT_SET);
    }

    @Test
    public void pathCantBeNull() {
        assertThatThrownBy(() -> LoadFileRequest.builder().fileName(fileName).build())
                .hasMessageContaining(PATH_NOT_SET);
    }
}
