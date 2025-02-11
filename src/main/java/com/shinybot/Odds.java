package com.shinybot;

public class Odds {
    // given attempts and config for odds values, returns the chance of the case in given attempts
    public static double getChance(String mode, boolean hasShinyCharm, int attempts) {
        final String[] modes = {"usum_legends", "usum_ub"};
    
        final double[] baseOdds = {4096.0,4096.0};
        final double[] charmOdds = {1395.0,1395.0};
        
        double odds = 0;
        for (int i = 0; i < modes.length; i++) {
            if (mode.equals(modes[i])) {
                odds = (hasShinyCharm ? charmOdds[i] : baseOdds[i]);
            }
        }
        return 1 - Math.pow(1 - ( 1.0 / odds ), attempts);
    }
    // given config for specific hunt and charm boolean, returns the x in "1/x odds" 
    public static int getOdds(String mode, boolean hasShinyCharm) {
        final String[] modes = {"usum_legends", "usum_ub"};
    
        final int[] baseOdds = {4096,4096};
        final int[] charmOdds = {1395,1395};
        int odds = 0;
        for (int i = 0; i < modes.length; i++) {
            if (mode.equals(modes[i])) {
                odds = (hasShinyCharm ? charmOdds[i] : baseOdds[i]);
            }
        }
        return odds;
    }
}