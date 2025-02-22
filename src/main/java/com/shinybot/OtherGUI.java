package com.shinybot;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OtherGUI {

    // Method to show an error popup that stays on top
    public static void showErrorPopup(JFrame parentFrame, String errorMessage) {
        JDialog errorDialog = new JDialog(parentFrame, "Error", true);
        errorDialog.setLayout(new BorderLayout());

        // Add an error message
        JLabel errorLabel = new JLabel(errorMessage, JLabel.CENTER);
        errorDialog.add(errorLabel, BorderLayout.CENTER);

        // Add a button to close the dialog
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> errorDialog.dispose()); // Close the dialog

        errorDialog.add(closeButton, BorderLayout.SOUTH);

        // Set dialog properties
        errorDialog.setSize(250, 100);
        errorDialog.setAlwaysOnTop(true); // Keep the popup on top
        errorDialog.setLocationRelativeTo(parentFrame); // Center the dialog
        errorDialog.setVisible(true);
    }

    // Method to show a success popup that stays on top
    public static void showSuccessPopup(JFrame parentFrame, String message) {
        JDialog successDialog = new JDialog(parentFrame, "Success!", true);
        successDialog.setLayout(new BorderLayout());

        // Add a message
        JLabel errorLabel = new JLabel(message, JLabel.CENTER);
        successDialog.add(errorLabel, BorderLayout.CENTER);

        // Add a button to close the dialog
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> successDialog.dispose()); // Close the dialog

        successDialog.add(closeButton, BorderLayout.SOUTH);

        // Set dialog properties
        successDialog.setSize(250, 100);
        successDialog.setAlwaysOnTop(true); // Keep the popup on top
        successDialog.setLocationRelativeTo(parentFrame); // Center the dialog
        successDialog.setVisible(true);
    }

    // Method to show an input popup that stays on top
    public static String showInputPopup(JFrame parentFrame, String message) {
        JDialog inputDialog = new JDialog(parentFrame, "Input", true);
        inputDialog.setLayout(new FlowLayout());

        // Add a label and a text field for input
        JLabel inputLabel = new JLabel(message);
        JTextField inputField = new JTextField(20);
        inputDialog.add(inputLabel);
        inputDialog.add(inputField);

        // Add a button to submit the input
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            inputDialog.dispose(); // Close the dialog
        });

        inputDialog.add(submitButton);

        // Set dialog properties
        inputDialog.setSize(300, 150);
        inputDialog.setAlwaysOnTop(true); // Keep the popup on top
        inputDialog.setLocationRelativeTo(parentFrame); // Center the dialog
        inputDialog.setVisible(true);

        // Return the user input once the dialog is closed
        return inputField.getText(); // Return the input text
    }

    // method to bring up window to set controller binds
    public static void showControllerInputs(JFrame parentFrame, int controllerNumber) throws Exception {
        JDialog inputDialog = new JDialog(parentFrame, "Controller Inputs", true); // Modle dialog
        inputDialog.setLayout(new FlowLayout());

        VirtualController vc = new VirtualController(controllerNumber);

        // Add a label
        JLabel inputLabel = new JLabel("Controls to bind:");
        inputDialog.add(inputLabel);

        // Add buttons to submit the input
        JButton upButton = new JButton("Up Button");
        upButton.addActionListener(e -> {
            try {
                vc.upButtonInput();   
            } catch (InterruptedException error) {
                showErrorPopup(parentFrame, error.toString());
            }
        });
        JButton aButton = new JButton("A Button");
        aButton.addActionListener(e -> {
            try {
                vc.aButtonInput();   
            } catch (InterruptedException error) {
                showErrorPopup(parentFrame, error.toString());
            }
        });
        JButton startButton = new JButton("Start Button");
        startButton.addActionListener(e -> {
            try {
                vc.startButtonInput();   
            } catch (InterruptedException error) {
                showErrorPopup(parentFrame, error.toString());
            }
        });
        JButton rButton = new JButton("R Button");
        rButton.addActionListener(e -> {
            try {
                vc.rButtonInput();   
            } catch (InterruptedException error) {
                showErrorPopup(parentFrame, error.toString());
            }
        });
        JButton lButton = new JButton("L Button");
        lButton.addActionListener(e -> {
            try {
                vc.lButtonInput();   
            } catch (InterruptedException error) {
                showErrorPopup(parentFrame, error.toString());
            }
        });
        JButton yButton = new JButton("Y Button");
        yButton.addActionListener(e -> {
            try {
                vc.yButtonInput();   
            } catch (InterruptedException error) {
                showErrorPopup(parentFrame, error.toString());
            }
        });
        JButton xButton = new JButton("X Button");
        xButton.addActionListener(e -> {
            try {
                vc.xButtonInput();   
            } catch (InterruptedException error) {
                showErrorPopup(parentFrame, error.toString());
            }
        });

        inputDialog.add(lButton);
        inputDialog.add(new JLabel(" "));
        inputDialog.add(rButton);
        inputDialog.add(new JLabel(" "));
        inputDialog.add(startButton);
        inputDialog.add(new JLabel(" "));
        inputDialog.add(upButton);
        inputDialog.add(new JLabel(" "));
        inputDialog.add(aButton);
        inputDialog.add(new JLabel(" "));
        inputDialog.add(rButton);
        inputDialog.add(new JLabel(" "));
        inputDialog.add(yButton);
        inputDialog.add(new JLabel(" "));
        inputDialog.add(xButton);

        // Set dialog properties
        inputDialog.setSize(450, 110);
        inputDialog.setAlwaysOnTop(true); // Keep the popup on top
        inputDialog.setLocationRelativeTo(parentFrame); // Center the dialog
        inputDialog.setVisible(true);
    }
}
