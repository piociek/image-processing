package com.piociek.images.engine.requests;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

import static com.piociek.images.messages.ExceptionMessage.*;

@Builder
@Getter
public class LoadFilesRequest {
    private String path;
    private String extension;
    private String fileNameWithMask;
    private Integer startIndex;
    private Integer endIndex;
    private Integer fillNumberUpTo;

    public static LoadFilesRequestBuilder builder() {
        return new LoadFilesRequestBuilder() {
            @Override
            public LoadFilesRequest build() {
                verifyPathSet();
                verifyFileNameAndIndexes();
                return super.build();
            }
        };
    }

    public static class LoadFilesRequestBuilder {

        public LoadFilesRequestBuilder extension(String extension) {
            verifyPathSet();
            this.extension = extension;
            verifyExtensionOrFileName();
            return this;
        }

        public LoadFilesRequestBuilder fileNameWithMask(String fileNameWithMask) {
            verifyPathSet();
            this.fileNameWithMask = fileNameWithMask;
            verifyExtensionOrFileName();
            verifyFileName();
            return this;
        }

        public LoadFilesRequestBuilder startIndex(Integer startIndex) {
            this.startIndex = startIndex;
            return this;
        }

        public LoadFilesRequestBuilder endIndex(Integer endIndex) {
            this.endIndex = endIndex;
            return this;
        }

        public LoadFilesRequestBuilder fillNumberUpTo(Integer fillNumberUpTo) {
            this.fillNumberUpTo = fillNumberUpTo;
            verifyFillNumberUpTo();
            return this;
        }

        void verifyPathSet() {
            if (Objects.isNull(path)) {
                throw new IllegalStateException(PATH_NOT_SET);
            }
        }

        private void verifyExtensionOrFileName() {
            if (Objects.nonNull(extension) && Objects.nonNull(fileNameWithMask)) {
                throw new IllegalStateException(EXTENSION_AND_FILE_WITH_MASK_SET);
            }
        }

        private void verifyFileName() {
            if (Objects.nonNull(fileNameWithMask) && !fileNameWithMask.contains("%s")) {
                throw new IllegalStateException(FILE_WITH_MASK_WITHOUT_MASK);
            }
        }

        void verifyFileNameAndIndexes() {
            if (Objects.nonNull(fileNameWithMask)) {
                verifyStartAndEndIndex();
            } else {
                if (Objects.nonNull(startIndex)) {
                    throw new IllegalStateException(START_INDEX_WITHOUT_FILE_WITH_MASK);
                }
                if (Objects.nonNull(endIndex)) {
                    throw new IllegalStateException(END_INDEX_WITHOUT_FILE_WITH_MASK);
                }
            }
        }

        private void verifyStartAndEndIndex() {
            if (Objects.isNull(startIndex)) {
                throw new IllegalStateException(START_INDEX_NOT_SET);
            }
            if (Objects.isNull(endIndex)) {
                throw new IllegalStateException(END_INDEX_NOT_SET);
            }
            if (startIndex < 0) {
                throw new IllegalStateException(START_INDEX_NEGATIVE);
            }
            if (endIndex < 0) {
                throw new IllegalStateException(END_INDEX_NEGATIVE);
            }
            if (endIndex < startIndex) {
                throw new IllegalStateException(END_INDEX_LOWER_THAN_START_INDEX);
            }
        }

        private void verifyFillNumberUpTo() {
            if (Objects.isNull(fileNameWithMask)) {
                throw new IllegalStateException(FILL_NUMBER_UP_TO_WITHOUT_FILE_WITH_MASK);
            }
            if (fillNumberUpTo < startIndex) {
                throw new IllegalStateException(FILL_NUMBER_UP_TO_LOWER_THAN_START_INDEX);
            }
        }
    }
}
