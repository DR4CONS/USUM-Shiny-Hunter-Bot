package com.shinybot;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ItemEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ShinyHuntBotGUI {

    // configs to store data
    final private ActiveConfig config = new ActiveConfig();
    private ShinyBotConfig profile = new ShinyBotConfig(config.getCurrentProfile());

    // GUI Fields
    final private JCheckBox shinyCharmCheckbox = new JCheckBox();

    final private JTextField[] encounterRGB = new JTextField[3];
    final private JTextField[] battleRGB = new JTextField[3];

    final private JButton setEncounterRGB = new JButton("Set Color");
    final private JButton setBattleRGB = new JButton("Set Color");

    final private JButton toggleHunt = new JButton("Start");
    final private JButton saveProfile = new JButton("Save Profile");
    final private JButton controllerBinds = new JButton("   Controller   ");
    final private JButton resetAttempts = new JButton("Reset Attempts");

    final private JComboBox<String> currentMode = new JComboBox<>(profile.getModes());
    final private JComboBox<String> currentOrientation = new JComboBox<>(profile.getOrientations());
    final private JTextField gameSpeed = new JTextField();
    final private JTextField minimumDelay = new JTextField();
    final private JTextField controllerNum = new JTextField();

    private JComboBox<String> windows;
    private JComboBox<String> currentProfile;

    JFrame frame = new JFrame("ShinyBot");

    private final JLabel attempts;
    private final JLabel odds;
    private Thread huntThread;

    // Create GUI
    public ShinyHuntBotGUI() throws IOException {

        // create main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 420); //heh

        // Create the main panel with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create all labels to line up later
        JLabel[] leftLabels = new JLabel[8];
        JPanel[] leftPanels = new JPanel[8];
        for (int i = 0; i < 8; i++) {
            leftPanels[i] = new JPanel();
            leftPanels[i].setLayout(new BoxLayout(leftPanels[i], BoxLayout.X_AXIS));
            leftPanels[i].setAlignmentX(Component.LEFT_ALIGNMENT); // Align left
            leftLabels[i] = new JLabel();
            leftLabels[i].setPreferredSize(new Dimension(135, 20));
        }

        // profile selector
        leftLabels[0].setText("Profile:  ");
        leftPanels[0].add(leftLabels[0]);
        currentProfile = new JComboBox<>(new DefaultComboBoxModel<>(ShinyBotConfig.getProfiles())); // list of profiles derived from .json files in the save directory
        currentProfile.setSelectedItem(config.getCurrentProfile());
        currentProfile.setMaximumSize(new Dimension(200, 25)); // max size of selector box
        currentProfile.setPreferredSize(new Dimension(200, 25));
        leftPanels[0].add(currentProfile);
        currentProfile.addActionListener(e -> {
            try {
                selectProfile((String) currentProfile.getSelectedItem());
            } catch (IOException error) {
                OtherGUI.showErrorPopup(frame, error.toString());
            }
        }); // listener

        // selector for screen to monitor
        leftPanels[0].add(new JLabel("     Screen:   "));
        windows = new JComboBox<>(new DefaultComboBoxModel<>(WindowColorMonitor.getOpenWindowTitles())); // fills contents of box with all open windows
        windows.setMaximumSize(new Dimension(200, 35));
        windows.setPreferredSize(new Dimension(200, 35));
        windows.setSelectedItem(config.getCurrentScreen());
        leftPanels[0].add(windows);
        windows.addActionListener(e -> {
            try {
                windowChange((String) windows.getSelectedItem());
            } catch (IOException error) {
                OtherGUI.showErrorPopup(frame, error.toString());
            }
        }); // listener
        
        // mode/game selector
        leftLabels[1].setText("                          Mode:     ");
        leftPanels[1].add(leftLabels[1]);
        currentMode.setMaximumSize(new Dimension(150, 25)); // selector size limitor
        leftPanels[1].add(currentMode);
        currentMode.addActionListener(e -> selectCurrentMode((String) currentMode.getSelectedItem())); // listener

        // screen layout selector
        leftLabels[2].setText("         Screen Layout:     ");
        leftPanels[2].add(leftLabels[2]);
        currentOrientation.setMaximumSize(new Dimension(150, 25)); // selector size limitor
        leftPanels[2].add(currentOrientation);
        currentOrientation.addActionListener(e -> selectOrientation((String) currentOrientation.getSelectedItem())); //listener

        // text box for game speed as a percentage
        leftLabels[3].setText("      Game Speed (%):   ");
        leftPanels[3].add(leftLabels[3]);
        restrictInputToNumbersAndLimit(gameSpeed, 3); // restrict inputs to 3 numbers
        leftPanels[3].add(gameSpeed);
        gameSpeed.setMaximumSize(new Dimension(35, 20)); // text box size limitor
        gameSpeed.setPreferredSize(new Dimension(35, 20)); // text box size limitor
        gameSpeed.getDocument().addDocumentListener(new DocumentListener() { // listener
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // This is typically called for style changes, not plain text updates.
                handleChange();
            }

            private void handleChange() {
                String textField = gameSpeed.getText();
                profile.setGameSpeed(Integer.parseInt((textField.equals("")) ? "0" : textField));
            }
        });

        // text box input for minimum delay to consider a shiny
        leftLabels[4].setText("Minimum Delay(ms):   ");
        leftPanels[4].add(leftLabels[4]);
        restrictInputToNumbersAndLimit(minimumDelay, 3);
        minimumDelay.setMaximumSize(new Dimension(35, 20));
        minimumDelay.setPreferredSize(new Dimension(35, 20));
        leftPanels[4].add(minimumDelay);
        minimumDelay.getDocument().addDocumentListener(new DocumentListener() { // listener
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // This is typically called for style changes, not plain text updates.
                handleChange();
            }

            private void handleChange() {
                String textField = minimumDelay.getText();
                profile.setMinimumDelay(Integer.parseInt((textField.equals("")) ? "0" : textField));
            }
        });

        // three text boxes for the red green and blue of the screen color when the encounter starts
        leftLabels[5].setText("          Encounter RGB:");
        leftPanels[5].add(leftLabels[5]);
        final String[] rgb = {"red", "green", "blue"};
        for (int i = 0; i < 3; i++) {
            encounterRGB[i] = new JTextField(3); // Limit visible width to 3 characters
            restrictInputToNumbersAndLimit(encounterRGB[i], 3); // Limit input to 3 digits
            leftPanels[5].add(encounterRGB[i]);
            encounterRGB[i].setMaximumSize(new Dimension(35, 20));
            final int loopNum = i;
            encounterRGB[i].getDocument().addDocumentListener(new DocumentListener() { // listener
                @Override
                public void insertUpdate(DocumentEvent e) {
                    handleChange();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    handleChange();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // This is typically called for style changes, not plain text updates.
                    handleChange();
                }

                private void handleChange() {
                    String textField = encounterRGB[loopNum].getText();
                    profile.setSingleEncounterColor(rgb[loopNum], (Integer.parseInt((textField.equals("")) ? "0" : textField)));
                }
            });
            if (i < 3) {
                leftPanels[5].add(new JLabel("  ")); // spacing for boxes
            }
        }
        setEncounterRGB.setSize(new Dimension(10, 10));
        leftPanels[5].add(setEncounterRGB); // button sets rgb to current on screen
        setEncounterRGB.addActionListener(e -> {
            try {
                setEncounterRGB();
            } catch (Exception error) {
                OtherGUI.showErrorPopup(frame, error.toString());
            }
        }); // listener
        // this is for the controller number, used for parallel hunting
        leftPanels[5].add(new Label("                                         Controller number:")); 
        restrictInputToNumbersAndLimit(controllerNum, 2);
        controllerNum.setPreferredSize(new Dimension(20, 25));
        leftPanels[5].add(controllerNum);
        controllerNum.getDocument().addDocumentListener(new DocumentListener() { // listener
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // This is typically called for style changes, not plain text updates.
                handleChange();
            }

            private void handleChange() {
                String textField = controllerNum.getText();
                selectControllerNumber(textField.equals("") ? 0 : Integer.parseInt(textField));
            }
        });
        
        // three text boxes for the red green and blue of the screen color when the battle starts
        leftLabels[6].setText("                  Battle RGB:");
        leftPanels[6].add(leftLabels[6]);
        for (int i = 0; i < 3; i++) {
            battleRGB[i] = new JTextField(3); // Limit visible width to 3 characters
            restrictInputToNumbersAndLimit(battleRGB[i], 3); // Limit input to 3 digits
            leftPanels[6].add(battleRGB[i]);
            battleRGB[i].setMaximumSize(new Dimension(35, 20));
            final int loopNum = i;
            battleRGB[i].getDocument().addDocumentListener(new DocumentListener() { // listener
                @Override
                public void insertUpdate(DocumentEvent e) {
                    handleChange();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    handleChange();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // This is typically called for style changes, not plain text updates.
                    handleChange();
                }

                private void handleChange() {
                    String textField = battleRGB[loopNum].getText();
                    profile.setSingleBattleColor(rgb[loopNum], (Integer.parseInt((textField.equals("")) ? "0" : textField)));
                }
            });
            // Add spacing between text fields
            if (i < 3) {
                leftPanels[6].add(new JLabel("  "));
            }
        }
        setBattleRGB.setSize(new Dimension(10, 10)); // button size limiter
        leftPanels[6].add(setBattleRGB); // button sets rgb to current on screen
        setBattleRGB.addActionListener(e -> {
            try {
                setBattleRGB();
            } catch (Exception error) {
                OtherGUI.showErrorPopup(frame, error.toString());
            }
        }); // listener
        
        // checkbox for if the user has a shiny charm (used for odds calculation)
        leftLabels[7].setText("      Has Shiny Charm:    ");
        leftPanels[7].add(leftLabels[7]);
        leftPanels[7].add(shinyCharmCheckbox);
        shinyCharmCheckbox.addItemListener(e -> { // listener for checkbox
            boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
            hasShinyCharm(isChecked);
        });

        leftPanels[6].add(new JLabel("           "));
        leftPanels[6].add(saveProfile); // button to save current config
        leftPanels[6].add(new JLabel("    "));
        leftPanels[6].add(controllerBinds); // button to help bind controls
        saveProfile.addActionListener(e -> {
            try {
                saveButton();
            } catch (IOException error) {
                OtherGUI.showErrorPopup(frame, error.toString());
            }
        }); // listener
        controllerBinds.addActionListener(e -> OtherGUI.showControllerInputs(frame, profile.getControllerNum())); // controller inputs for binding

        leftPanels[7].add(new JLabel("                                                                                          "));
        leftPanels[7].add(toggleHunt); // start/stop hunt
        toggleHunt.addActionListener(e -> {
            if (toggleHunt.getText().equals("Start")) {
                startHunt(); // starts Shiny hunt
            } else {
                stopHunt();
            }
        });

        // show odds
        attempts = new JLabel(String.format("     Attempts: %d/%d     ", profile.getAttempts(), Odds.getOdds(profile.getCurrentMode(), profile.hasShinyCharm())));
        odds = new JLabel(String.format("     Chance of a shiny by now: %.2f%%", Odds.getChance(profile.getCurrentMode(), profile.hasShinyCharm(), profile.getAttempts()) * 100));
        leftPanels[1].add(attempts);
        leftPanels[1].add(resetAttempts);
        resetAttempts.addActionListener(e -> {
            profile.setAttempts(0);
            attempts.setText(String.format("     Attempts: %d/%d     ", profile.getAttempts(), Odds.getOdds(profile.getCurrentMode(), profile.hasShinyCharm())));
            odds.setText(String.format("     Chance of a shiny by now: %.2f%%", Odds.getChance(profile.getCurrentMode(), profile.hasShinyCharm(), profile.getAttempts()) * 100));
            try {
                profile.saveSettings(config.getCurrentProfile());
            } catch (IOException error) {
                OtherGUI.showErrorPopup(frame, error.toString());
            }
        });
        leftPanels[2].add(odds);

        // place all panels on frame
        for (int i = 0; i < 8; i++) {
            mainPanel.add(leftPanels[i]);
            mainPanel.add(Box.createVerticalStrut((i == 0 ? 50 : 20)));
        }

        // add panel to frame
        frame.add(mainPanel);

        // set visible and static size
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                // create GUI
                ShinyHuntBotGUI mainGUI = new ShinyHuntBotGUI();

                // initial initialization (makes total sense right?)
                mainGUI.initializeFields();
            } catch (IOException e) {
                System.out.println(e);
            }
        });
    }

    public void selectControllerNumber(int num) {
        profile.setControllerNum(num);
    }

    private void selectOrientation(String orientation) {// sets the orientation
        profile.setCurrentOrientation(orientation);
    }

    private void saveButton() throws IOException {// saves settings to the currentProfile in ActiveConfig
        System.out.println(profile.toString());
        String theName = config.getCurrentProfile();
        if (theName.equals("New Profile")) { // if new profile is selected, then prompt the user to create a new name
            theName = OtherGUI.showInputPopup(frame, "Enter name for new profile:");
        }
        profile.saveSettings(theName);
        currentProfile.setModel(new DefaultComboBoxModel<>(ShinyBotConfig.getProfiles()));
        currentProfile.revalidate();
        currentProfile.repaint();
        currentProfile.setSelectedItem(theName);
    }

    public void hasShinyCharm(boolean isChecked) {// sets the shiny charm flag
        profile.setHasShinyCharm(isChecked);
        attempts.setText(String.format("     Attempts: %d/%d     ", profile.getAttempts(), Odds.getOdds(profile.getCurrentMode(), profile.hasShinyCharm())));
    }

    public void selectCurrentMode(String mode) {// sets the current mode in config
        profile.setCurrentMode(mode);
    }

    public void selectProfile(String profileName) throws IOException {// sets the current profile in ActiveConfig and reinitializes with new profile
        config.setCurrentProfile(profileName);
        config.saveSettings();
        initializeFields();
    }

    public void windowChange(String windowTitle) throws IOException {// Gets the window to watch for shinyhunt
        windows.setModel(new DefaultComboBoxModel<>(WindowColorMonitor.getOpenWindowTitles()));
        config.setCurrentScreen(windowTitle);
        windows.setSelectedItem(windowTitle);
        config.saveSettings();
    }

    private void restrictInputToNumbersAndLimit(JTextField textField, int maxLength) { // limits inputs of textfields to be only maxLength numbers
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }

            private void validateInput() {
                SwingUtilities.invokeLater(() -> {
                    String text = textField.getText();

                    // Remove non-numeric characters
                    text = text.replaceAll("[^\\d]", "");

                    // Limit the length
                    if (text.length() > maxLength) {
                        text = text.substring(0, maxLength);
                    }

                    // Update the text field if needed
                    if (!text.equals(textField.getText())) {
                        textField.setText(text);
                    }
                });
            }
        });
    }

    public void incrimentAttempts() {// adds 1 to attempts and updates counter/odds
        int num = profile.getAttempts(); // get current attempts and save with one added
        profile.setAttempts(++num);
        try {
            config.saveSettings();
        } catch (IOException e) {
            OtherGUI.showErrorPopup(frame, e.toString());
        }
        // update GUI
        attempts.setText(String.format("     Attempts: %d/%d     ", profile.getAttempts(), Odds.getOdds(profile.getCurrentMode(), profile.hasShinyCharm())));
        odds.setText(String.format("     Chance of a shiny by now: %.2f%%", Odds.getChance(profile.getCurrentMode(), profile.hasShinyCharm(), profile.getAttempts()) * 100));
    }

    public void setBattleRGB() throws Exception { // sets the rgb from the selected screen and orientation
        String windowName = config.getCurrentScreen();
        Color currentColor = WindowColorMonitor.getPixelColorAtCenter(windowName, profile.getCurrentOrientation());
        int[] currentRGB = {currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()};
        for (int i = 0; i < 3; i++) {
            battleRGB[i].setText("" + currentRGB[i]);
        }
    }

    public void setEncounterRGB() throws Exception { // sets the rgb from the selected screen and orientation
        String windowName = config.getCurrentScreen();
        Color currentColor = WindowColorMonitor.getPixelColorAtCenter(windowName, profile.getCurrentOrientation());
        int[] currentRGB = {currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()};
        for (int i = 0; i < 3; i++) {
            encounterRGB[i].setText("" + currentRGB[i]);
        }
    }

    public void stopHunt() { // stops the shiny hunt
        toggleHunt.setText("Start");
        config.setCurrentlyHunting(false); // Stop the loop by setting the flag to false
        if (huntThread != null) {
            huntThread.interrupt(); // Interrupt the thread if needed
        }
        try {
            config.saveSettings();
        } catch (IOException e) {
            OtherGUI.showErrorPopup(frame, e.toString());
        }
    }

    private void startHunt() { // shiny hunting program
        OtherGUI.showSuccessPopup(frame, "Starting Shiny Hunt");
        toggleHunt.setText("Stop"); // started, so give option for start

        // Create a new thread for the infinite loop so that GUI is usable before loop ends
        huntThread = new Thread(new Runnable() {
            @Override
            public void run() {
                config.setCurrentlyHunting(true);
                // set configs from profile and extra flags and trackers
                int[] battleRGB = profile.getBattleColor(), encounterRGB = profile.getEncounterColor();
                Color battleColor = new Color(battleRGB[0], battleRGB[1], battleRGB[2]),
                        encounterColor = new Color(encounterRGB[0], encounterRGB[1], encounterRGB[2]), color;
                Boolean shinyEncounter = false;
                float baseTime = 0;
                while (!shinyEncounter && config.isCurrentlyHunting()) { // while no shiny and not stopped
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    try {
                        SoftReseter.reset(profile.getCurrentMode(), profile.getControllerNum()); // does soft reset
                        Boolean hasEncountered = false, battleStarted = false; // encounter hasnt happened, nor battle
                        long encounterTime = 0, battleTime = 0; //  time variables
                        while (!battleStarted) { // while the battle hasn't started, keep grabbing color and checking
                            color = WindowColorMonitor.getPixelColorAtCenter(config.getCurrentScreen(), profile.getCurrentOrientation()); // grab color
                            if (color == null) { // if the color is empty, that means the window wasn't found
                                throw new Exception("Window not found");
                            }
                            if (!hasEncountered && color.equals(encounterColor)) { // if it has not yet encountered, record starting time and set encounter true
                                hasEncountered = true;
                                encounterTime = System.currentTimeMillis();
                                System.out.print("Started Time...  ");
                            } else if (hasEncountered && color.equals(battleColor)) { // if already encountered and the battle has started, end time and stop loop
                                battleTime = System.currentTimeMillis();
                                battleStarted = true;
                                System.out.print("Ended Time!");
                            }
                        }
                        // if first encounter, get time as base time to compare to later.
                        float encounterDelay = battleTime - encounterTime - baseTime;
                        baseTime = (baseTime == 0 ? battleTime - encounterTime : baseTime);
                        // if not first time, find the difference. If greater than the given minimum delay, its a possible shiny
                        shinyEncounter = battleTime - encounterTime - baseTime > profile.getMinimumDelay();
                        System.out.printf(" Encounter %d time period: " + (encounterDelay > 0 ? " %-4.0f Shiny: %s%n" : "-%-4.0f Shiny: %s%n"), profile.getAttempts() + 1, Math.abs(encounterDelay), shinyEncounter);
                    } catch (InterruptedException e) {
                        OtherGUI.showErrorPopup(frame, "Shiny Hunt Interrupted.");
                    }catch (Exception e) {
                        OtherGUI.showErrorPopup(frame, e.toString());
                        break;
                    }
                    // increment attempts
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            incrimentAttempts();
                        }
                    });
                }
                if (shinyEncounter) {
                    OtherGUI.showSuccessPopup(frame, "Success! Delay: " + baseTime);
                }
                // hunt is over, so set button to start again
                toggleHunt.setText("Start");   
            }
        });
        huntThread.start(); // Start the thread
    }

    private void initializeFields() {// takes values in config and fills out GUI with the values

        // choose profile currently selected
        profile = new ShinyBotConfig(config.getCurrentProfile());

        // set ComboBox selections
        currentMode.setSelectedItem(profile.getCurrentMode());
        currentOrientation.setSelectedItem(profile.getCurrentOrientation());

        // set text box contents
        gameSpeed.setText("" + profile.getGameSpeed());
        minimumDelay.setText("" + profile.getMinimumDelay());
        controllerNum.setText("" + profile.getControllerNum());

        // set checkbox state
        shinyCharmCheckbox.setSelected(profile.hasShinyCharm());

        // set rgb textbox contents
        int[] encounterColor = profile.getEncounterColor(), battleColor = profile.getBattleColor();
        for (int i = 0; i < 3; i++) {
            encounterRGB[i].setText("" + encounterColor[i]);
            battleRGB[i].setText("" + battleColor[i]);
        }
    }
}
