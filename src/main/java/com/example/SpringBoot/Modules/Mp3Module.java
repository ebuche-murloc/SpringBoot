package com.example.SpringBoot.Modules;

import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import org.apache.commons.io.FilenameUtils;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Mp3Module implements FileInfoModule {

    private final Map<String, Method> descriptionsToFunctions;

    public Mp3Module() {
        descriptionsToFunctions = new HashMap<>();
        try {
            descriptionsToFunctions.put("Get track duration",
                    Mp3Module.class.getDeclaredMethod("getTrackDuration", File.class));
            descriptionsToFunctions.put("Get track name",
                    Mp3Module.class.getDeclaredMethod("getTrackName", File.class));
            descriptionsToFunctions.put("Get track author",
                    Mp3Module.class.getDeclaredMethod("getTrackAuthor", File.class));
        } catch (Exception ignored) { }
    }

    @Override
    public boolean isSupportFileExtension(File file) {
        return FilenameUtils.getExtension(file.getAbsolutePath()).equals("mp3");
    }

    @Override
    public Map<String, Method> getDescriptionsToFunctions() {
        return descriptionsToFunctions;
    }

    public String getTrackDuration(File file) {
        try {
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
            if (fileFormat instanceof TAudioFileFormat) {
                Map<?, ?> properties = fileFormat.properties();
                Long microSec = (Long) properties.get("duration");
                int sec = (int) (microSec / (1000 * 1000));
                return Integer.toString(sec) + " sec";
            }
        } catch (Exception ignored) { }
        return "";
    }

    public String getTrackName(File file) {
        try {
            Metadata metadata = Mp4MetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                if (directory.getName().equals("File")) {
                    return directory.getDescription(1);
                }
            }
        } catch (Exception ignored) { }
        return "";
    }

    public String getTrackAuthor(File file) {
        try {
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
            if (fileFormat instanceof TAudioFileFormat) {
                Map<?, ?> properties = fileFormat.properties();
                String key = "author";
                return (String) properties.get(key);
            }
        } catch (Exception ignored) { }
        return "";
    }
}
