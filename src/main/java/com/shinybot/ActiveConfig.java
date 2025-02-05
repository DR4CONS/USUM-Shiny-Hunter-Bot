package com.shinybot;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

public class ActiveConfig extends Config {
    // fields
    private String currentProfile = "New Profile";
    private String currentScreen = "Select Screen";
    private boolean currentlyHunting = false;
    final private String fileName = "settings";

    public ActiveConfig() throws IOException { // this will always have the same filename, so no fields
        JSONObject settings;
        // trys to read the file, if the file doesn't exist, it saves the defaults to the filename
        try {
            settings = readSettings("Shinybot/", fileName);
        } catch (IOException e) {
            settings = new JSONObject();
            settings.put("currentlyHunting", currentlyHunting);
            settings.put("currentProfile", currentProfile);
            settings.put("currentScreen", currentScreen);

            saveSettings();
            throw e;
        }
        currentlyHunting = settings.getBoolean("currentlyHunting");
        currentProfile = settings.getString("currentProfile");
        currentScreen = settings.getString("currentScreen");
    }

    public void saveSettings() throws IOException {// saves settings
        saveSettings(fileName);
    }

    @Override
    public void saveSettings(String filename) throws IOException { // saves all settings to settings.json
        JSONObject settings = new JSONObject();

        settings.put("currentlyHunting", currentlyHunting);
        settings.put("currentProfile", currentProfile);
        settings.put("currentScreen", currentScreen);

        writeSettings(new File("Shinybot/"), filename, settings);
    }

    // getters and setters
    public boolean isCurrentlyHunting() {
        return currentlyHunting;
    }
    public void setCurrentlyHunting(boolean currentlyHunting) {
        this.currentlyHunting = currentlyHunting;
    }
    public String getCurrentProfile() {
        return currentProfile;
    }
    public void setCurrentProfile(String currentProfile) {
        this.currentProfile = currentProfile;
    }
    public String getCurrentScreen() {
        return currentScreen;
    }
    public void setCurrentScreen(String currentScreen) {
        this.currentScreen = currentScreen;
    }
}
