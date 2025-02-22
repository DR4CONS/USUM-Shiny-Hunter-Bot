package com.shinybot;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher {
    private volatile boolean running = true;
    private Thread watcherThread;
    private final String imagesPath = "Shinybot/pictures/";
    private final Path fileToWatch;
    private final Path secondFileName;  // The second file name to check for renaming
    private final WatchService watchService;
    private final Path directory;
    private ShinyHuntBotGUI hunter;
    private final int controllerId;

    // Constructor to initialize the file watcher with the path of the file to monitor
    public FileWatcher(String filePath, String huntName, ShinyHuntBotGUI hunter) throws IOException { // ShinyHuntBotGUI hunter
        this.hunter = hunter;
        this.fileToWatch = Paths.get(imagesPath + filePath);
        this.secondFileName = Paths.get(imagesPath + huntName);  // The second file to watch for renaming
        this.directory = fileToWatch.getParent();
        this.watchService = FileSystems.getDefault().newWatchService();
        controllerId = Integer.parseInt("" + huntName.charAt(huntName.length()-1));
        start();
    }

    // Start the watcher to monitor the file for renames or deletions
    public void start() {
        watcherThread = new Thread(() -> {
            try {
                // Register the directory to watch for delete events
                directory.register(watchService, ENTRY_DELETE);

                System.out.println("Waiting for response from bot");

                while (running) {
                    WatchKey key = watchService.poll(); // Use poll() to avoid blocking forever
                    if (key == null) {
                        Thread.sleep(500); // Small delay to reduce CPU usage
                        continue;
                    }

                    // Process events
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changedFile = (Path) event.context();

                        if (event.kind() == ENTRY_DELETE) {
                            // If the file has been deleted, handle it
                            if (changedFile.equals(fileToWatch.getFileName())) {                                
                                // After deletion, check if the second file exists in the directory
                                if (checkForSecondFileName()) {
                                    System.out.println("Button id: " + changedFile.toString().charAt(changedFile.getFileName().toString().length()-5));
                                    try {
                                        ComboInputs.catchMon(true, controllerId);    
                                    } catch (Exception e) {}
                                    hunter.changeToggleButton("Start");
                                } else {
                                    System.out.println("Continuing Hunt.");
                                    hunter.changeToggleButton("Stop");
                                    hunter.startHunt();
                                }
                                interrupt();
                                return;
                            }
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Error: " + e.toString());
            }
            hunter.changeToggleButton("Start");
        });
        watcherThread.start();
    }

    // Interrupt function to stop the watcher thread
    public void interrupt() {
        running = false;
        watcherThread.interrupt(); // Interrupt the thread if it's waiting or sleeping
    }

    // Check if the second file exists in the directory (possibly renamed file)
    private boolean checkForSecondFileName() {
        // Check if the second file exists in the directory
        Path secondFilePath = directory.resolve(secondFileName.getFileName());
        return Files.exists(secondFilePath);  // Return true if the second file exists
    }
}
