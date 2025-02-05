package com.shinybot;

public class SoftReseter {
    // takes the method and controller id, and inputs given method on given controller
    public static void reset(String method, int controllerNum) throws InterruptedException {
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

    // resets game, selects save, and moves forward
    private static void ultraWormholeLegendReset(int controllerNum) throws InterruptedException {
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
    private static void ultraWormholeUB(int controllerNum) throws InterruptedException {
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
