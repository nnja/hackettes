package team15;

import java.awt.Color;
import hockey.api.GoalKeeper;
import hockey.api.IPlayer;
import hockey.api.Player;
import hockey.api.ITeam;
import hockey.api.Util;

public class Team implements ITeam {
    public String getShortName() { return "Kb"; }
    public String getTeamName() { return "KILLABYTES"; }
    public Color getTeamColor() { return Color.PINK; }
    public Color getSecondaryTeamColor() { return Color.BLACK; }
    public int getLuckyNumber() { return 13; }
    public GoalKeeper getGoalKeeper() { return new BullieGoalie(); }
    public Player getPlayer(int index) {
	return new Bullie(index);
    }
}

class Bullie extends Player {
    private static int[] numbers = {1, 2, 3, 4, 5, 6};
    private static String[] names = {
	"", "Nathan", "Nina", "Maggie", "Sarah", "KILLA BYTE DESTROYER"
    };
    private int index;

    public Bullie(int index) { this.index = index; }
    public int getNumber() { return numbers[index]; }
    public String getName() { return names[index]; }
    public boolean isLeftHanded() { return false; }
    public void step() {
	if (hasPuck()) { // If we have the puck.
        if (getX() >= 5200/2) { // behind the net
            skate(2500, getY(), 1000);
        }
        else if (getX() > 1500) {
            if (Math.abs(getY()) > 1000) {
                pass();
            } else {
                shoot(2600, 0, 10000); // Shoot.
            }
            /*
            if (Math.abs(Util.dangle(getHeading(), // ...and is turned towards the goal.
                     Util.datan2(0 - getY(),
                         2500 - getX()))) < 90) {
            int target = (int)(Math.random()*200)-100;
            shoot(2600, target, 10000); // Shoot.
            } else {
                pass();
            } */
        } else if (getX() < 0) {
            shoot(500, getY(), 1000);
        } else {
            skate(2600, getY(), 1000);
        }
    }
    // nobody's holding the puck
    else if (getPuck().getHolder() == null) {
        if (goToPuck()) {
            skate(getPuck(), 1000);
        } else { 
            skate(getPuck().getX(), getY(), 1000);
        }
    }
    // is the puck holder on our team
    else if (!getPuck().getHolder().isOpponent()) {
        if (getPuck().getX() <= (5200/6)) {
            // offsides prevention
            skate(getPuck().getX(), getY(), 1000);
            // tackle();
        } else {
            if (getIndex() %2 ==0) {
                skate(2600, getY(), 1000); // Skate towards the goal.
            } else {
                skate(2600, 0, 1000);
            }
        }
    }
    // if the puck holder is our opponent.
    else if (getPuck().getHolder().isOpponent()) {
        if (goToPuck()) {
            skate(getPuck(), 5000);
        } else {
            if (getIndex() %2 == 0) {
                skate(-2600, 0, 5000);
            } else {
                tackle();
            }
        } 
    }

	else if (Util.dist(getX() - getPuck().getX(), // If the puck is within 5m. 
			   getY() - getPuck().getY()) < 500)
	    skate(getPuck(), 1000); // Get puck                  
	else {
        tackle();
	}
    }

    void tackle() {
        IPlayer best = null;
        for (int i = 0; i < 12; ++i) { // Loop through all players.
        IPlayer cur = getPlayer(i);

        if (cur.isOpponent() && // If player is opponent...
        (best == null || 
        Util.dist(getX() - cur.getX(), // ...and closest so far...
           getY() - cur.getY()) <
        Util.dist(getX() - best.getX(),
           getY() - best.getY())))
        best = cur; // ...save player.
        }

        skate(best, 1000); // Tackle closest opponent.
    }

    void pass() {
        IPlayer best = null;
        for (int i = 1; i < 5; ++i) { // Loop through all players.
        IPlayer cur = getPlayer(i);

        if (!cur.isOpponent() && i != getIndex() && (best == null || 
           Util.dist(getX() - cur.getX(), // ...and closest so far...
           getY() - cur.getY()) <
           Util.dist(getX() - best.getX(),
           getY() - best.getY())))
           best = cur; // ...save player.
        }

        shoot(best, 1000); // Shoot towards closest teammate
    }

    boolean goToPuck() {
        IPlayer best = null;
        double dist1 = 100000;
        int player1 = 0;
        double dist2 = 100000;
        int player2 = 0;
        for (int i = 1; i <= 5; ++i) { // Loop through all players.
           IPlayer cur = getPlayer(i);
           double distToPuck = Util.dist(cur.getX() - getPuck().getX(),
            cur.getY() - getPuck().getY()); 
           if (distToPuck < dist1) {
             player1 = i;
             dist2 = dist1;
             dist1 = distToPuck;
           } else if (distToPuck < dist2) {
             player2 = i;
             dist2 = distToPuck;
           }
        }
        if (player1 == getIndex() || player2 == getIndex()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void faceOff() {
        if (getIndex() == 5) {
            skate(getPuck(), 1000); 
        }
    }
}

class BullieGoalie extends GoalKeeper {
    public int getNumber() { return 1; }
    public String getName() { return "Franz Jaeger"; }
    public boolean isLeftHanded() { return false; }
    public void step() {
	skate(-2550, 0, 200); // Stand in the middle of the goal.
	turn(getPuck(), MAX_TURN_SPEED); // Turn towards puck.
    }
}
