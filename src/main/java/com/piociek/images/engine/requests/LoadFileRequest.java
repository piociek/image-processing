package com.piociek.images.engine.requests;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

import static com.piociek.images.messages.ExceptionMessage.PATH_NOT_SET;

@Builder
@Getter
public class LoadFileRequest {
    private String path;
    private String fileName;

    public static LoadFileRequestBuilder builder() {
        return new LoadFileRequestBuilder() {
            @Override
            public LoadFileRequest build () {
                verifyPathSet();
                return super.build();
            }
        };
    }

    public static class LoadFileRequestBuilder {

        public LoadFileRequestBuilder fileName(String fileName) {
            verifyPathSet();
            this.fileName = fileName;
            return this;
        }

        void verifyPathSet() {
            if (Objects.isNull(path)) {
                throw new IllegalStateException(PATH_NOT_SET);
            }
        }
    }
}
