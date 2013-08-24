package library;

import hockey.api.Util;
import hockey.api.*;

public static class Samples { 
    // Returns true if your projected X move would be offsides.
    public static boolean isOffside(Player player, double x) {
        double blueLine = 5200/3;
        if (player.getX() < blueLine + x) {
           // Not going to blue line, return false.
           return false; 
        } else {
            // Crossing blue line, check to make sure puck is there already.
            IPuck puck = player.getPuck(); // puck position
            if (puck >= blueLine) {
                return false;
            } else {
                return true;
            }
        }
    }
}
