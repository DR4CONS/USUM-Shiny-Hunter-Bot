package com.shinybot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShinyBotConfig extends Config {
    // available modes and orientations
    final private String[] modes = { "usum_legends", "usum_ub" };
    final private String[] orientations = { "default", "bottom_screen" };

    // config fields with defaults
    private int controllerNum = 1;
    private int currentMode = 0;
    private int currentOrientation = 0;
    private int gameSpeed = 100;
    private boolean hasShinyCharm = false;
    private boolean isSavingPictures = false;
    private boolean isListening = false;
    private String profileName = "New Profile";
    private int minimumDelay = 500;
    private int[] encounterColor = new int[3];
    private int[] battleColor = new int[3];

    // default constructor is new profile
    public ShinyBotConfig() {
        this("New Profile");
    }

    public ShinyBotConfig(String profileName) {
        if (!profileName.equals("New Profile")) { // if profile is not default, load values from file
            try {
                JSONObject settings = readSettings("Shinybot/profiles/", profileName);
                currentMode = settings.has("currentMode") ? settings.getInt("currentMode") : currentMode;
                currentOrientation = settings.has("currentOrientation") ? settings.getInt("currentOrientation")
                        : currentOrientation;
                gameSpeed = settings.has("gameSpeed") ? settings.getInt("gameSpeed") : gameSpeed;
                hasShinyCharm = settings.has("hasShinyCharm") ? settings.getBoolean("hasShinyCharm") : hasShinyCharm;
                isSavingPictures = settings.has("isSavingPictures") ? settings.getBoolean("isSavingPictures")
                        : isSavingPictures;
                isListening = settings.has("isListening") ? settings.getBoolean("isListening") : isListening;
                minimumDelay = settings.has("minimumDelay") ? settings.getInt("minimumDelay") : minimumDelay;
                controllerNum = settings.has("controllerNum") ? settings.getInt("controllerNum") : controllerNum;

                for (int i = 0; i < 3; i++) {
                    encounterColor[i] = 0;
                    battleColor[i] = 0;
                }

                if (settings.has("encounterColor")) {
                    JSONArray colorNums = settings.getJSONArray("encounterColor");
                    for (int i = 0; i < 3; i++) {
                        encounterColor[i] = colorNums.getInt(i);
                    }
                }

                if (settings.has("battleColor")) {
                    JSONArray colorNums = settings.getJSONArray("battleColor");
                    for (int i = 0; i < 3; i++) {
                        battleColor[i] = colorNums.getInt(i);
                    }
                }

                this.profileName = profileName;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void saveSettings(String filename) throws IOException { // method to save settings to a json object
        JSONObject settings = new JSONObject();

        settings.put("currentMode", currentMode);
        settings.put("currentOrientation", currentOrientation);
        settings.put("gameSpeed", gameSpeed);
        settings.put("hasShinyCharm", hasShinyCharm);
        settings.put("minimumDelay", minimumDelay);
        settings.put("encounterColor", encounterColor);
        settings.put("battleColor", battleColor);
        settings.put("controllerNum", controllerNum);
        settings.put("isSavingPictures", isSavingPictures);
        settings.put("isListening", isListening);

        writeSettings(new File("Shinybot/profiles/"), filename, settings);
    }

    // options getters
    public String[] getModes() {
        return modes;
    }

    public String[] getOrientations() {
        return orientations;
    }

    public static String[] getProfiles() {
        File directory = new File("./ShinyBot/profiles/");

        ArrayList<String> profileFiles = new ArrayList<>();
        profileFiles.add("New Profile");

        if (directory.isDirectory()) {
            // Use a FilenameFilter to filter .json files
            String[] jsonFiles = directory.list((dir, name) -> name.toLowerCase().endsWith(".json"));

            // Print the JSON filenames
            if (jsonFiles != null) {
                for (String fileName : jsonFiles) {
                    String cutFileName = fileName.substring(0, fileName.lastIndexOf('.'));
                    profileFiles.add(cutFileName);
                }
            } else {
                System.out.println("No .json files found or an error occurred.");
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }

        String[] returnString = new String[profileFiles.size()];

        for (int i = 0; i < profileFiles.size(); i++) {
            returnString[i] = profileFiles.get(i);
        }
        return returnString;
    }

    // getters and setters
    public String getCurrentMode() {
        return modes[currentMode];
    }

    public void setCurrentMode(String currentMode) {
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].equals(currentMode)) {
                this.currentMode = i;
            }
        }
    }

    public String getCurrentOrientation() {
        return orientations[currentOrientation];
    }

    public void setCurrentOrientation(String currentOrientation) {
        for (int i = 0; i < orientations.length; i++) {
            if (orientations[i].equals(currentOrientation)) {
                this.currentOrientation = i;
            }
        }
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public boolean hasShinyCharm() {
        return hasShinyCharm;
    }

    public void setHasShinyCharm(boolean hasShinyCharm) {
        this.hasShinyCharm = hasShinyCharm;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getMinimumDelay() {
        return minimumDelay;
    }

    public void setMinimumDelay(int minimum) {
        this.minimumDelay = minimum;
    }

    public int[] getEncounterColor() {
        return encounterColor;
    }

    public void setEncounterColor(int red, int green, int blue) {
        encounterColor[0] = red;
        encounterColor[1] = green;
        encounterColor[2] = blue;
    }

    public void setSingleEncounterColor(String color, int value) {
        switch (color) {
            case "red" -> {
                encounterColor[0] = value;
            }
            case "green" -> {
                encounterColor[1] = value;
            }
            case "blue" -> {
                encounterColor[2] = value;
            }
        }
    }

    public int[] getBattleColor() {
        return battleColor;
    }

    public void setBattleColor(int red, int green, int blue) {
        battleColor[0] = red;
        battleColor[1] = green;
        battleColor[2] = blue;
    }

    public void setSingleBattleColor(String color, int value) {
        switch (color) {
            case "red" -> {
                battleColor[0] = value;
            }
            case "green" -> {
                battleColor[1] = value;
            }
            case "blue" -> {
                battleColor[2] = value;
            }
        }
    }

    public int getControllerNum() {
        return controllerNum;
    }

    public void setControllerNum(int controllerNum) {
        this.controllerNum = controllerNum;
    }

    public boolean isSavingPictures() {
        return isSavingPictures;
    }

    public void setSavingPictures(boolean isSavingPictures) {
        this.isSavingPictures = isSavingPictures;
    }

    public boolean isListening() {
        return isListening;
    }

    public void setListening(boolean isListening) {
        this.isListening = isListening;
    }
}