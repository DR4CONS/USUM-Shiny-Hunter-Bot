package com.shinybot;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

public class ActiveConfig extends Config {

    private String currentProfile = "New Profile";
    private String currentScreen = "Select Screen";
    private boolean currentlyHunting = false;
    int attempts;
    final private String fileName = "settings";
    JSONObject attemptObject;

    public ActiveConfig() throws IOException {
        JSONObject settings;
        try {
            settings = readSettings("Shinybot/", fileName);
            currentlyHunting = settings.getBoolean("currentlyHunting");
            currentProfile = settings.getString("currentProfile");
            currentScreen = settings.getString("currentScreen");
            attemptObject = settings.getJSONObject("attempts");
            attempts = attemptObject.getInt(currentProfile);
        } catch (IOException e) {
            settings = new JSONObject();
            settings.put("currentlyHunting", currentlyHunting);
            settings.put("currentProfile", currentProfile);
            settings.put("currentScreen", currentScreen);
            attemptObject = new JSONObject();
            attemptObject.put("New Profile", 0);
            settings.put("attempts", attemptObject);
            saveSettings();
        }
    }

    public void saveSettings() throws IOException {
        saveSettings(fileName);
    }

    @Override
    public void saveSettings(String filename) throws IOException {
        JSONObject settings = readSettings("Shinybot/", fileName);
        attemptObject = settings.getJSONObject("attempts");
        settings.put("currentlyHunting", currentlyHunting);
        settings.put("currentProfile", currentProfile);
        settings.put("currentScreen", currentScreen);
        attemptObject.put(currentProfile, attempts);
        settings.put("attempts", attemptObject);

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
        try {
            this.attempts = attemptObject.getInt(currentProfile);
        } catch (Exception e) {}
    }

    public String getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(String currentScreen) {
        this.currentScreen = currentScreen;
    }

    public int getAttempts() {
        try {
            JSONObject temp = readSettings("Shinybot/", fileName);
            JSONObject atmptCount = temp.getJSONObject("attempts");
            this.attempts = atmptCount.getInt(currentProfile);
            return attempts;
        } catch (Exception e) {
            return attempts;
        }
    }

    public void setAttempts(int count) {
        attempts = count;
        try {
            saveSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}