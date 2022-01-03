package com.piociek.images.load;

import com.piociek.images.util.NumberUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.piociek.images.messages.ExceptionMessage.FILE_DOES_NOT_EXIST;
import static com.piociek.images.messages.ExceptionMessage.FOLDER_DOES_NOT_EXIST;
import static com.piociek.images.messages.LogMessages.FILES_FROM_DIRECTORY_LOADED;
import static com.piociek.images.messages.LogMessages.FILE_LOADED;

public class LoadFiles {

    private static final Logger LOGGER = Logger.getLogger(LoadFiles.class.toString());

    public File loadFile(String stringPath) {
        return loadFile(Paths.get(stringPath));
    }

    public File loadFile(String stringPath, String fileName) {
        return loadFile(Paths.get(stringPath, fileName));
    }

    public List<File> loadFilesFromDirectory(String stringPath) {
        File directory = validateAndGetDirectory(stringPath);
        List<File> files = Stream.of(Objects.requireNonNull(directory.listFiles()))
                .filter(File::isFile)
                .collect(Collectors.toList());
        return logFileNamesAndReturn(stringPath, files);
    }

    public List<File> loadFilesFromDirectoryWithExtensionFilter(String stringPath, String extension) {
        File directory = validateAndGetDirectory(stringPath);
        List<File> files = Stream.of(Objects.requireNonNull(directory.listFiles()))
                .filter(f -> f.getName().endsWith(extension.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        return logFileNamesAndReturn(stringPath, files);
    }

    public List<File> loadFilesFromDirectoryWithCounterMask(String stringPath, String fileNameWithMask, int startIndex, int endIndex, int fillNumberUpTo) {
        validateAndGetDirectory(stringPath);
        List<File> files = IntStream.rangeClosed(startIndex, endIndex).boxed()
                .map(i -> NumberUtil.fillWithZeros(i, fillNumberUpTo))
                .map(i -> String.format(fileNameWithMask, i))
                .map(name -> new File(Paths.get(stringPath, name).toString()))
                .filter(file -> {
                    boolean exists = file.exists();
                    if (!exists) {
                        LOGGER.log(Level.SEVERE, String.format(FILE_DOES_NOT_EXIST, file));
                    }
                    return exists;
                }).collect(Collectors.toList());
        return logFileNamesAndReturn(stringPath, files);
    }

    public List<File> loadFilesFromDirectoryWithCounterMask(String stringPath, String fileNameWithMask, int startIndex, int endIndex) {
        return loadFilesFromDirectoryWithCounterMask(stringPath, fileNameWithMask, startIndex, endIndex, 1);
    }

    private File loadFile(Path path) {
        File file = new File(path.toString());
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format(FILE_DOES_NOT_EXIST, path));
        } else {
            LOGGER.log(Level.INFO, String.format(FILE_LOADED, file.getPath()));
            return file;
        }
    }

    private File validateAndGetDirectory(String stringPath) {
        File directory = new File(Paths.get(stringPath).toUri());
        if (!directory.exists()) {
            throw new IllegalArgumentException(String.format(FOLDER_DOES_NOT_EXIST, stringPath));
        }
        return directory;
    }

    private List<File> logFileNamesAndReturn(String stringPath, List<File> files) {
        String loadedFileNames = files.stream()
                .map(File::getName)
                .collect(Collectors.joining(", "));
        LOGGER.log(Level.INFO, String.format(FILES_FROM_DIRECTORY_LOADED, loadedFileNames, stringPath));
        return files;
    }
}
