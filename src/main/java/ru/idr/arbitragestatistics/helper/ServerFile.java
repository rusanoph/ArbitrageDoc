package ru.idr.arbitragestatistics.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerFile {

    @SafeVarargs
    public static String fileText(String documentFileName, String documentPath, String... restDocumentPath) throws IOException {
        String directoryURI = Paths.get(documentPath, restDocumentPath).toString();
        Path documentURI = Paths.get(directoryURI, documentFileName).toAbsolutePath();

        String text = new String(Files.readAllBytes(documentURI), StandardCharsets.UTF_8);
        
        return text;
    }
    
    public static String fileText(String documentPath, String documentFileName) throws IOException {
        return ServerFile.fileText(documentFileName, documentPath, new String[]{});
    }



    public static Set<String> listDirectoryServer(String directoryPath, String... restDocumentPath) {
        String directoryURI = Paths.get(directoryPath, restDocumentPath).toAbsolutePath().toString();

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
    

    public static Set<String> deepListDirectoryServer(String directoryPath) {
        String directoryURI = Paths.get("", "txtFiles", directoryPath).toAbsolutePath().toString();

        File directory = new File(directoryURI);
        File[] directoryListFiles = directory.listFiles();

        if (directoryListFiles == null) {
            return new HashSet<String>();
        }

        Set<String> currentDepthLevelDirectories = Stream.of(directory.listFiles())
            .filter(file -> file.isDirectory())
            .map(file -> Paths.get(directoryPath, file.getName()).toString())
            .collect(Collectors.toSet());

        for (String direcoty : currentDepthLevelDirectories) {
            // String currentPath = Paths.get(directoryPath, direcoty).toString();
            currentDepthLevelDirectories.addAll(deepListDirectoryServer(direcoty));
        }

        return currentDepthLevelDirectories;
    }

    
    @SafeVarargs
    public static Set<String> listFilesServer(String directoryPath, String... RestDirectoryPath) {
        String directoryURI = Paths.get(directoryPath, RestDirectoryPath).toAbsolutePath().toString();

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

    public static Set<String> listFilesServer(String directoryPath) {
        return ServerFile.listFilesServer("", "txtFiles", directoryPath);
    }

}
