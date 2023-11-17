package ru.idr.arbitragestatistics.helper;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerFile {
    
    public static Set<String> listDirectoryServer(String directoryPath) {
        String directoryURI = Paths.get("", "txtFiles", directoryPath).toAbsolutePath().toString();

        File directory = new File(directoryURI);
        File[] directoryListFiles = directory.listFiles();

        if (directoryListFiles == null) {
            return null;
        }

        return Stream.of(directory.listFiles())
            .filter(file -> file.isDirectory())
            .map(File::getName)
            .collect(Collectors.toSet());
    }
    
    public static Set<String> listFilesServer(String directoryPath) {
        String directoryURI = Paths.get("", "txtFiles", directoryPath).toAbsolutePath().toString();

        File directory = new File(directoryURI);
        File[] directoryListFiles = directory.listFiles();

        if (directoryListFiles == null) {
            return null;
        }

        return Stream.of(directory.listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getName)
            .collect(Collectors.toSet());
    }

}
