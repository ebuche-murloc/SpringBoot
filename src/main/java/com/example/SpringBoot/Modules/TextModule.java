package com.example.SpringBoot.Modules;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

public class TextModule implements FileInfoModule {
    private final Map<String, Method> descriptionsToFunctions;

    public TextModule() {
        descriptionsToFunctions = new HashMap<>();
        try {
            descriptionsToFunctions.put("Get count of lines",
                    TextModule.class.getDeclaredMethod("getCountOfLines", File.class));
            descriptionsToFunctions.put("Get character occurrence frequency",
                    TextModule.class.getDeclaredMethod("getCharacterOccurrenceFrequency", File.class));
            descriptionsToFunctions.put("Get count of punctuation",
                    TextModule.class.getDeclaredMethod("getCountOfPunctuation", File.class));
        } catch (Exception ignored) { }
    }

    @Override
    public boolean isSupportFileExtension(File file) {
        return FilenameUtils.getExtension(file.getAbsolutePath()).equals("txt");
    }

    @Override
    public Map<String, Method> getDescriptionsToFunctions() {
        return descriptionsToFunctions;
    }

    public String getCountOfLines(File file) {
        try {
            int linesCount = 0;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                linesCount++;
            }
            return "Lines count: " + linesCount;
        } catch (Exception ignored) { }
        return "";
    }

    public String getCharacterOccurrenceFrequency(File file) {
        try {
            int totalCharacters = 0;
            Scanner scanner = new Scanner(file);
            Map<Character, ArrayList<Character>> dictionary = new HashMap<>();
            StringBuilder stringBuilder = new StringBuilder();

            while (scanner.hasNext()) {
                char[] str = scanner.next().toLowerCase().toCharArray();
                for (char symbol : str) {
                    if (!dictionary.containsKey(symbol))
                        dictionary.put(symbol, new ArrayList<>());
                    ArrayList<Character> list = dictionary.get(symbol);
                    list.add(symbol);
                    totalCharacters++;
                }
            }

            stringBuilder.append("Total symbols count - ")
                    .append(totalCharacters)
                    .append("\n");

            for (ArrayList<Character> list : dictionary.values()) {
                int count = list.size();
                stringBuilder.append("Count of each symbol - '")
                        .append(list.get(0))
                        .append("': ")
                        .append(count)
                        .append("\n");
            }
            return stringBuilder.toString();
        } catch (Exception ignored) { }
        return "";
    }

    public String getCountOfPunctuation(File file) {
        try {
            ArrayList<Character> punctuationList = new ArrayList<>(Arrays.asList(',', '.', '!', '\'', '\"', '-', '?', ':', ';'));
            int totalPunctuation = 0;
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                char[] str = scanner.next().toLowerCase().toCharArray();
                for (char symbol : str) {
                    if (punctuationList.contains(symbol))
                        totalPunctuation++;
                }
            }
            return "Punctuation count: " + totalPunctuation;
        } catch (Exception ignored) { }
        return "";
    }
}
