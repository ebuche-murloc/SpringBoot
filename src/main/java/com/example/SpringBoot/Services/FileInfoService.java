package com.example.SpringBoot.Services;

import com.example.SpringBoot.Modules.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileInfoService {

    private final FileInfoModule[] modules;

    public FileInfoService() {
        modules = new FileInfoModule[4];
        modules[0] = new CatalogModule();
        modules[1] = new Mp3Module();
        modules[2] = new ImageModule();
        modules[3] = new TextModule();
    }

    public Map<String, Method> getDescriptionsToFunctions(File file) {

        for (FileInfoModule module : modules) {
            if (module.isSupportFileExtension(file)) {
                return module.getDescriptionsToFunctions();
            }
        }

        return new HashMap<>();
    }
}
