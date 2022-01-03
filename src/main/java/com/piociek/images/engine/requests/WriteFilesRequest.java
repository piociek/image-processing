package com.piociek.images.engine.requests;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

import static com.piociek.images.messages.ExceptionMessage.*;

@Builder
@Getter
public class WriteFilesRequest {
    private String path;
    private String fileNameWithMask;
    private Integer startIndex;
    private Integer fillNumberUpTo;

    public static WriteFilesRequestBuilder builder() {
        return new WriteFilesRequestBuilder() {
            @Override
            public WriteFilesRequest build () {
                verifyPathSet();
                verifyFileName();
                verifyStartIndex();
                return super.build();
            }
        };
    }

    public static class WriteFilesRequestBuilder {

        public WriteFilesRequestBuilder fileNameWithMask(String fileNameWithMask) {
            verifyPathSet();
            this.fileNameWithMask = fileNameWithMask;
            verifyFileName();
            return this;
        }

        public WriteFilesRequestBuilder startIndex(Integer startIndex) {
            this.startIndex = startIndex;
            verifyStartIndex();
            return this;
        }

        public WriteFilesRequestBuilder fillNumberUpTo(Integer fillNumberUpTo) {
            this.fillNumberUpTo = fillNumberUpTo;
            verifyStartIndex();
            verifyFillNumberUpTo();
            return this;
        }

        void verifyPathSet() {
            if (Objects.isNull(path)) {
                throw new IllegalStateException(PATH_NOT_SET);
            }
        }

        void verifyFileName() {
            if (Objects.isNull(fileNameWithMask)) {
                throw new IllegalStateException(FILE_WITH_MASK_NOT_SET);
            }
            if (!fileNameWithMask.contains("%s")) {
                throw new IllegalStateException(FILE_WITH_MASK_WITHOUT_MASK);
            }
        }

        void verifyStartIndex() {
            if (Objects.isNull(startIndex)) {
                throw new IllegalStateException(START_INDEX_NOT_SET);
            }
            if (startIndex < 0) {
                throw new IllegalStateException(START_INDEX_NEGATIVE);
            }
        }

        private void verifyFillNumberUpTo() {
            if (fillNumberUpTo < startIndex) {
                throw new IllegalStateException(FILL_NUMBER_UP_TO_LOWER_THAN_START_INDEX);
            }
        }
    }
}
