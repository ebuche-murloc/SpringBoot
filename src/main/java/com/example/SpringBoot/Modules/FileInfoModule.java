package com.example.SpringBoot.Modules;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

public interface FileInfoModule {

    boolean isSupportFileExtension(File file);

    Map<String, Method> getDescriptionsToFunctions();

}
