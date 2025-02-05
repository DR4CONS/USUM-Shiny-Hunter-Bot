package com.shinybot;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class WindowColorMonitor {

    // Windows API functions
    public interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        // EnumWindows to enumerate through all windows
        boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer arg);

        // Get window title
        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);

        // Get window rectangle (position and size)
        boolean GetWindowRect(Pointer hWnd, RECT rect);

        // Check if window is visible
        boolean IsWindowVisible(Pointer hWnd);

        // Check if window is minimized
        boolean IsIconic(Pointer hWnd);
    }

    // Callback function for EnumWindows
    public interface WNDENUMPROC extends Callback {
        boolean callback(Pointer hWnd, Pointer arg);
    }

    // RECT structure to hold window position and size
    public static class RECT extends Structure {
        public int left;
        public int top;
        public int right;
        public int bottom;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("left", "top", "right", "bottom");
        }
    }

    // Helper class to store window title and handle
    public static class Window {
        public Pointer hWnd;
        public String title;

        public Window(Pointer hWnd, String title) {
            this.hWnd = hWnd;
            this.title = title;
        }
    }

    // List of open windows
    final private static List<Window> windows = new ArrayList<>();

    // Function to return an array of window titles (strings) for open windows
    public static String[] getOpenWindowTitles() {
        // Clear the list of windows to avoid duplicates on multiple calls
        windows.clear();

        // Enum all windows and store their titles
        User32.INSTANCE.EnumWindows((hWnd, arg) -> {
            byte[] windowText = new byte[512];
            User32.INSTANCE.GetWindowTextA(hWnd, windowText, windowText.length);
            String title = Native.toString(windowText);

            // Check if the window is visible and not minimized
            if (!title.isEmpty() && User32.INSTANCE.IsWindowVisible(hWnd) && !User32.INSTANCE.IsIconic(hWnd)) {
                windows.add(new Window(hWnd, title));  // Only add visible and non-minimized windows
            }
            return true;
        }, null);

        // Convert list of window titles to an array of strings
        String[] windowTitles = new String[windows.size()];
        for (int i = 0; i < windows.size(); i++) {
            windowTitles[i] = windows.get(i).title;
        }
        return windowTitles;
    }

    // Function to return the color of the pixel at the center of the specified window
    public static Color getPixelColorAtCenter(String windowTitle, String orientation) throws Exception {
        // Find the window by title
        Window selectedWindow = null;
        for (Window window : windows) {
            if (window.title.equals(windowTitle)) {
                selectedWindow = window;
                break;
            }
        }

        if (selectedWindow == null) {
            System.out.println("Window with title '" + windowTitle + "' not found.");
            return null;
        }

        // Get the window position and size
        RECT rect = new RECT();
        if (User32.INSTANCE.GetWindowRect(selectedWindow.hWnd, rect)) {
            // Calculate the center of the window
            int centerX = (rect.left + rect.right) / 2;
            int centerY = orientation.equals("bottom_screen") ? (rect.top + rect.bottom) / 2 : orientation.equals("default") ? rect.top + 3 * (rect.bottom-rect.top) / 4 : -1;
            if (centerY == -1) {
                throw new Exception("Invalid orientation");
            }

            // Capture the screen using Robot
            Robot robot = new Robot();
            Rectangle windowRect = new Rectangle(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
            BufferedImage screenShot = robot.createScreenCapture(windowRect);

            // Get the pixel color at the center of the window
            Color color = new Color(screenShot.getRGB(centerX - rect.left, centerY - rect.top));

            return color;
        } else {
            System.out.println("Unable to get window position for '" + windowTitle + "'.");
            return null;
        }
    }
}
