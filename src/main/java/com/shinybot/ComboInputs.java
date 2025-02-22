package com.shinybot;

public class ComboInputs {
    // takes the method and controller id, and inputs given method on given controller
    public static void reset(String method, int controllerNum) throws InterruptedException, Exception {
        // possible methods are as follows: "usum_legends", "usum_ub"
        switch (method) {
            case "usum_legends" -> {
                ultraWormholeLegendReset(controllerNum);
            }
            case "usum_ub" -> {
                ultraWormholeUB(controllerNum);
            }
        }
    }
    
    public static void catchMon(boolean save, int controllerNum) throws InterruptedException, Exception {
        System.out.println("Catching Pokemon");
        VirtualController vc = new VirtualController(controllerNum);
        vc.yButtonInput();
        Thread.sleep(500);
        vc.yButtonInput();
        Thread.sleep(25000);
        vc.aButtonInput();
        Thread.sleep(2500);
        vc.startButtonInput();
        Thread.sleep(500);
        vc.aButtonInput();
        Thread.sleep(2000);
        vc.aButtonInput();
        Thread.sleep(4000);
        if (save) {
            saveGame(controllerNum);
        }
    }
    public static void saveGame(int controllerNum) throws InterruptedException, Exception {
        System.out.println("Saving game");
        VirtualController vc = new VirtualController(controllerNum);
        vc.xButtonInput();
        Thread.sleep(1000);
        vc.yButtonInput();
        Thread.sleep(2000);
        vc.aButtonInput();
    }

    // resets game, selects save, and moves forward
    private static void ultraWormholeLegendReset(int controllerNum) throws InterruptedException, Exception {
        VirtualController vc = new VirtualController(controllerNum);
        vc.softResetInput();
        Thread.sleep(6000);
        vc.aButtonInput();
        Thread.sleep(2000);
        vc.aButtonInput();
        vc.pressUpButton();
        Thread.sleep(4000);
        vc.releaseUpButton();
    }

    // resets game, selects save, and presses a to interact with static pokemon
    private static void ultraWormholeUB(int controllerNum) throws InterruptedException, Exception {
        VirtualController vc = new VirtualController(controllerNum);
        vc.releaseUpButton();
        vc.softResetInput();
        Thread.sleep(6000);
        vc.aButtonInput();
        Thread.sleep(2000);
        vc.aButtonInput();
        Thread.sleep(3500);
        vc.aButtonInput();
    }
}
