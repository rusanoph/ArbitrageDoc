package ru.idr.arbitragestatistics.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerFile {
    
    public static String fileText(String documentPath, String documentFileName) throws IOException {
        Path documentURI = Paths.get("", "txtFiles", documentPath, documentFileName).toAbsolutePath();
        String text = new String(Files.readAllBytes(documentURI), StandardCharsets.UTF_8);
        
        return text;
    }

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
