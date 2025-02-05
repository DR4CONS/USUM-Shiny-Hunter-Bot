package com.shinybot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class VirtualController {

    private int joystickId, lButtonId, rButtonId, startButtonId, aButtonId, upButtonId;

    private interface VJoyInterface extends Library {
        VJoyInterface INSTANCE = (VJoyInterface) loadLibrary();

        boolean AcquireVJD(int rID);
        void RelinquishVJD(int rID);
        boolean SetBtn(boolean pressed, int rID, int buttonID);
    }


    // takes a controller number and sets button and stick id's
    public VirtualController(int controllerNumber) {
        joystickId = controllerNumber < 1 ? 1 : controllerNumber;
        int firstButtonId = ( controllerNumber - 1 ) * 5; 
        lButtonId = ++firstButtonId;
        rButtonId = ++firstButtonId;
        startButtonId = ++firstButtonId;
        aButtonId = ++firstButtonId;
        upButtonId = ++firstButtonId;
    }

    // defaults to 1
    public VirtualController() {
        this(1);
    }

    private static Object loadLibrary() {
        try {
            // Extract the DLL from resources
            String libName = "vJoyInterface.dll";
            InputStream in = VirtualController.class.getResourceAsStream("/" + libName);
            if (in == null) {
                throw new IllegalArgumentException("Library not found in resources: " + libName);
            }

            // Create a temporary file for the DLL
            File tempFile = File.createTempFile("vJoyInterface", ".dll");
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            tempFile.deleteOnExit();

            // Load the DLL
            return Native.loadLibrary(tempFile.getAbsolutePath(), VJoyInterface.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load vJoyInterface.dll", e);
        }
    }

    VJoyInterface vJoy = VJoyInterface.INSTANCE;

    // toggles buttons on
    public Boolean pressLButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(true, joystickId, lButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean pressRButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(true, joystickId, rButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean pressStartButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(true, joystickId, startButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean pressAButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(true, joystickId, aButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean pressUpButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(true, joystickId, upButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    
    // toggles buttons off
    public Boolean releaseLButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(false, joystickId, lButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean releaseRButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(false, joystickId, rButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean releaseStartButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(false, joystickId, startButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean releaseAButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(false, joystickId, aButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean releaseUpButton() {
        if (vJoy.AcquireVJD(joystickId)) {
            // Simulate button press
            vJoy.SetBtn(false, joystickId, upButtonId);
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    
    // 0.1 second long button inputs
    public Boolean lButtonInput() throws InterruptedException {
        if (vJoy.AcquireVJD(joystickId)) {
            vJoy.SetBtn(true, joystickId, lButtonId);
            
            Thread.sleep(100);
    
            vJoy.SetBtn(false, joystickId, lButtonId);
    
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean rButtonInput() throws InterruptedException {
        if (vJoy.AcquireVJD(joystickId)) {
            vJoy.SetBtn(true, joystickId, rButtonId);
            Thread.sleep(100);
            vJoy.SetBtn(false, joystickId, rButtonId);
            
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean startButtonInput() throws InterruptedException {
        if (vJoy.AcquireVJD(joystickId)) {
            vJoy.SetBtn(true, joystickId, startButtonId);
            
            Thread.sleep(100);
    
            vJoy.SetBtn(false, joystickId, startButtonId);
    
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean aButtonInput() throws InterruptedException {
        if (vJoy.AcquireVJD(joystickId)) {
            vJoy.SetBtn(true, joystickId, aButtonId);
            
            Thread.sleep(100);
    
            vJoy.SetBtn(false, joystickId, aButtonId);
    
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    public Boolean upButtonInput() throws InterruptedException {
        if (vJoy.AcquireVJD(joystickId)) {
            vJoy.SetBtn(true, joystickId, upButtonId);
            
            Thread.sleep(100);
    
            vJoy.SetBtn(false, joystickId, upButtonId);
    
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
    
    // presses L, R, and Start buttons then releases after 0.5 seconds
    public Boolean softResetInput() throws InterruptedException {
        int[] buttons = {lButtonId, rButtonId, startButtonId};
        if (vJoy.AcquireVJD(joystickId)) {
            for (int button : buttons) {
                vJoy.SetBtn(true, joystickId, button);
            }
            
            Thread.sleep(500);
    
            for (int button : buttons) {
                vJoy.SetBtn(false, joystickId, button);
            }
    
            vJoy.RelinquishVJD(joystickId);
            return true;
        } 
        return false;
    }
}

