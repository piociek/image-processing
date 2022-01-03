package com.piociek.images.messages;

public abstract class ExceptionMessage {

    public static final String FOLDER_DOES_NOT_EXIST = "Folder '%s' does not exist";
    public static final String FILE_DOES_NOT_EXIST = "File '%s' does not exist";

    public static final String FILE_WITH_MASK_WITHOUT_MASK = "'fileNameWithMask' should contain '%s'";

    public static final String PATH_NOT_SET = "'path' not set";
    public static final String FILE_WITH_MASK_NOT_SET = "'fileNameWithMask' not set";
    public static final String START_INDEX_NOT_SET = "'startIndex' not set";
    public static final String END_INDEX_NOT_SET = "'endIndex' not set";
    public static final String IMAGES_TO_PROCESS_NOT_SET = "'imagesToProcess' not set";
    public static final String HEIGHTS_OFFSET_NOT_SET = "'heightOffsetType' is not set";
    public static final String WIDTH_OFFSET_NOT_SET = "'widthOffsetType' is not set";
    public static final String OUTPUT_NOT_SET = "'output' is not set";

    public static final String OUTPUTS_NOT_SET = "'outputs' have to be set for palette transformations";
    public static final String OUTPUTS_SET = "'outputs' can only be set for palette transformations";

    public static final String EXTENSION_AND_FILE_WITH_MASK_SET =
            "Cannot set 'extension' and 'fileNameWithMask'";
    public static final String START_INDEX_WITHOUT_FILE_WITH_MASK =
            "'startIndex' should be set only when 'fileNameWithMask' is set";
    public static final String END_INDEX_WITHOUT_FILE_WITH_MASK =
            "'endIndex' should be set only when 'fileNameWithMask' is set";
    public static final String START_INDEX_NEGATIVE =
            "Cannot set 'startIndex' lower than 0";
    public static final String END_INDEX_NEGATIVE =
            "Cannot set 'endIndex' lower than 0";
    public static final String END_INDEX_LOWER_THAN_START_INDEX =
            "Cannot set 'endIndex' before 'startIndex'";
    public static final String FILL_NUMBER_UP_TO_LOWER_THAN_START_INDEX =
            "Cannot set 'fillNumberUpTo' lower than 'startIndex'";
    public static final String FILL_NUMBER_UP_TO_WITHOUT_FILE_WITH_MASK =
            "'fillNumberUpTo' should be set only when 'fileNameWithMask' is set";
    public static final String DEFAULT_PALETTE_WITHOUT_PALETTES =
            "Cannot set 'defaultPalette' if 'palettes' are not set";
    public static final String PALETTES_WITHOUT_DEFAULT_PALETTE =
            "Cannot set 'palettes' if 'defaultPalette' is not set";
    public static final String OUTPUT_AND_OUTPUTS_SET =
            "Cannot set 'output' and 'outputs'";
}
