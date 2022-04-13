package com.example.SpringBoot.Modules;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ImageModule implements FileInfoModule {
    private final Map<String, Method> descriptionsToFunctions;

    public ImageModule() {
        descriptionsToFunctions = new HashMap<>();
        try {
            descriptionsToFunctions.put("Get image resolution",
                    ImageModule.class.getDeclaredMethod("getImageResolution", File.class));
            descriptionsToFunctions.put("Get exif information",
                    ImageModule.class.getDeclaredMethod("getExifInformation", File.class));
            descriptionsToFunctions.put("Get image orientation",
                    ImageModule.class.getDeclaredMethod("getImageOrientation", File.class));
        } catch (Exception ignored) { }
    }

    @Override
    public boolean isSupportFileExtension(File file) {
        return FilenameUtils.getExtension(file.getAbsolutePath()).equals("png")
                || FilenameUtils.getExtension(file.getAbsolutePath()).equals("jpg");
    }

    @Override
    public Map<String, Method> getDescriptionsToFunctions() {
        return descriptionsToFunctions;
    }

    public String getImageResolution(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();
            System.out.println("Image resolution: " + width + "x" + height + "px");
        } catch (Exception ignored) { }
        return "";
    }

    public String getExifInformation(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            System.out.println("EXIF information:");
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);
                }
            }
        } catch (Exception ignored) { }
        return "";
    }

    public String getImageOrientation(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();
            String orientation = "not defined";
            if (width == height) {
                orientation = "square";
            } else {
                orientation = width > height ? "horizontal" : "vertical";
            }
            System.out.println("Image orientation: " + orientation);
        } catch (Exception ignored) { }
        return "";
    }
}
