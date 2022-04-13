package com.example.SpringBoot.Modules;

import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class CatalogModule implements FileInfoModule {

    private final Map<String, Method> descriptionsToFunctions;

    public CatalogModule() {
        descriptionsToFunctions = new HashMap<>();
        try {
            descriptionsToFunctions.put("Get files names",
                    CatalogModule.class.getDeclaredMethod("getFilesInDirectory", File.class));
            descriptionsToFunctions.put("Get files size",
                    CatalogModule.class.getDeclaredMethod("getFilesSize", File.class));
            descriptionsToFunctions.put("Get files count",
                    CatalogModule.class.getDeclaredMethod("getFilesCount", File.class));
        } catch (Exception ignored) {

        }

    }

    @Override
    public boolean isSupportFileExtension(File file) {
        return file.isDirectory();
    }

    @Override
    public Map<String, Method> getDescriptionsToFunctions() {
        return descriptionsToFunctions;
    }

    public String getFilesInDirectory(File directory) {
        if (!isSupportFileExtension(directory)) {
            throw new RuntimeException("file is not a directory");
        }

        StringBuilder fileNames = new StringBuilder();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                fileNames.append(file.getName()).append("\n");
            }
        }

        return fileNames.toString();
    }

    public String getFilesSize(File directory) {
        if (!isSupportFileExtension(directory)) {
            throw new RuntimeException("file is not a directory");
        }

        long totalSize = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                totalSize += file.length() / 1024;
            }
        }

        return totalSize + " KB";
    }

    public String getFilesCount(File directory) {
        if (!isSupportFileExtension(directory)) {
            throw new RuntimeException("file is not a directory");
        }

        File[] files = directory.listFiles();
        if (files != null) {
            return Integer.toString(files.length);
        }
        return "0";
    }

}
