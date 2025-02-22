package com.shinybot;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.RECT;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class WindowScreenshot {
    public static void saveScreenshot(String windowName, String filePath, String fileName) {
        try {
            WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
            
            if (hwnd == null) {
                System.out.println("Window not found!");
                return;
            }

            // Get window position and size
            RECT rect = new RECT();
            User32.INSTANCE.GetWindowRect(hwnd, rect);

            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;

            // Capture the specific window
            Robot robot = new Robot();
            BufferedImage screenshot = robot.createScreenCapture(new Rectangle(rect.left, rect.top, width, height));

            // Save screenshot
            File outputfile = new File(filePath + fileName + ".png");
            ImageIO.write(screenshot, "png", outputfile);
            System.out.println("Screenshot saved: " + outputfile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
