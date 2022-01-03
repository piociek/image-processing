package com.piociek.images.load;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoadFilesTests {

    private final LoadFiles loadFiles = new LoadFiles();
    private final String directoryPath = "src/test/resources/load";
    private final List<String> allFileNames =
            Arrays.asList("file_1.png", "file_01.txt", "file_01.png", "file_02.png", "file_10.png");
    private final List<String> allImageFileNames =
            Arrays.asList("file_1.png", "file_01.png", "file_02.png", "file_10.png");

    @Test
    public void loadFile_withoutFileName() {
        String fileName = allFileNames.get(0);
        String path = directoryPath + "/" + fileName;
        assertThat(loadFiles.loadFile(path).getName())
                .endsWith(fileName);
    }

    @Test
    public void loadFile_withFileName() {
        String fileName = allFileNames.get(0);
        assertThat(loadFiles.loadFile(directoryPath, fileName).getName())
                .endsWith(fileName);
    }

    @Test
    public void loadFileThatDoesNotExist() {
        assertThatThrownBy(() -> loadFiles.loadFile(directoryPath, "png"));
    }

    @Test
    public void loadDirectory_withoutSlashAtTheEnd() {
        assertThat(getFileNames(loadFiles.loadFilesFromDirectory(directoryPath)))
                .hasSameElementsAs(allFileNames);
    }

    @Test
    public void loadDirectory_withSlashAtTheEnd() {
        assertThat(getFileNames(loadFiles.loadFilesFromDirectory(directoryPath + "/")))
                .hasSameElementsAs(allFileNames);
    }

    @Test
    public void loadFromNonExistingDirectory() {
        assertThatThrownBy(() -> loadFiles.loadFilesFromDirectory(directoryPath + "1"));
    }

    @Test
    public void loadFromDirectoryWithExtensionFilter() {
        assertThat(getFileNames(loadFiles.loadFilesFromDirectoryWithExtensionFilter(directoryPath, "png")))
                .hasSameElementsAs(allImageFileNames);
    }

    @Test
    public void loadFromNonExistingDirectoryWithExtensionFilter() {
        assertThatThrownBy(() -> loadFiles.loadFilesFromDirectoryWithExtensionFilter(directoryPath + "1", "png"));
    }

    @Test
    public void loadWithCounter() {
        assertThat(getFileNames(loadFiles.loadFilesFromDirectoryWithCounterMask(
                directoryPath, "file_%s.png", 1, 10))
        ).hasSameElementsAs(Arrays.asList("file_1.png", "file_10.png"));
    }

    @Test
    public void loadWithCounterWithFill() {
        assertThat(getFileNames(loadFiles.loadFilesFromDirectoryWithCounterMask(
                directoryPath, "file_%s.png", 1, 10, 10))
        ).hasSameElementsAs(Arrays.asList("file_01.png", "file_02.png", "file_10.png"));
    }

    @Test
    public void loadFromNonExistingDirectoryWithCounter() {
        assertThatThrownBy(() -> loadFiles.loadFilesFromDirectoryWithCounterMask(
                directoryPath + "1", "file_%s.png", 1, 10));
    }

    private List<String> getFileNames(List<File> files) {
        return files.stream()
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
