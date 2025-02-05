package com.shinybot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public abstract class Config {

    public abstract void saveSettings(String filename) throws IOException;

    // writes the json settings to the given filename
    static boolean writeSettings(File folder, String filename, JSONObject settings) throws IOException {
        if (!folder.exists()) {
            if (folder.mkdirs()) {  // mkdirs() also creates any necessary parent directories
                System.out.println("Folder and any necessary parent directories were created.");
            } else {
                System.out.println("Failed to create folder.");
            }
        }

        String filePath = folder + "\\" + filename + ".json";
        // Write the JSONObject to the file
        try ( // Create a FileWriter object to write to the file
                FileWriter file = new FileWriter(filePath)) {
            // Write the JSONObject to the file
            settings.write(file);
            // Close the file to save the changes
        } catch (IOException e) {
            throw new IOException("An error occurred while saving settings.");
        }

        return false;
    }

    // reads from the given filename and returns a JSON object
    static JSONObject readSettings(String folderPath, String filename) throws IOException {
        JSONObject settings;
        try {
            // Read the entire content of the JSON file into a String
            String content = new String(Files.readAllBytes(Paths.get(folderPath, filename + ".json")));

            // Parse the content into a JSONObject
            settings = new JSONObject(content);

            // return the settings
            return settings;
        } catch (IOException e) {
            throw new IOException("Couldn't find config file " + filename + ".json");
        }

    }
}
