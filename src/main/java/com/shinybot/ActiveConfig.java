package com.shinybot;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

public class ActiveConfig extends Config {

    private String currentProfile = "New Profile";
    private String currentScreen = "Select Screen";
    private boolean currentlyHunting = false;
    JSONObject attempts;
    final private String fileName = "settings";

    public ActiveConfig() throws IOException {
        JSONObject settings;
        try {
            System.out.println("Trying");
            settings = readSettings("Shinybot/", fileName);
            currentlyHunting = settings.getBoolean("currentlyHunting");
            currentProfile = settings.getString("currentProfile");
            currentScreen = settings.getString("currentScreen");
            attempts = settings.getJSONObject("attempts");
        } catch (IOException e) {
            System.out.println("catching");
            settings = new JSONObject();
            settings.put("currentlyHunting", currentlyHunting);
            settings.put("currentProfile", currentProfile);
            settings.put("currentScreen", currentScreen);
            attempts = new JSONObject();
            attempts.put("New Profile", 0);
            settings.put("attempts", attempts);
            System.out.println("Saving");
            saveSettings();
        }
    }

    public void saveSettings() throws IOException {
        saveSettings(fileName);
    }

    @Override
    public void saveSettings(String filename) throws IOException {
        JSONObject settings = new JSONObject();

        settings.put("currentlyHunting", currentlyHunting);
        settings.put("currentProfile", currentProfile);
        settings.put("currentScreen", currentScreen);
        settings.put("attempts", attempts);

        writeSettings(new File("Shinybot/"), filename, settings);
    }

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

    public int getAttempts() {
        return attempts.getInt(currentProfile);
    }

    public void setAttempts(int count) {
        attempts.put(currentProfile, count);
    }
}
