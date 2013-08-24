package team03;

import java.awt.Color;

import hockey.api.Player;
import hockey.api.GoalKeeper;
import hockey.api.ITeam;
import hockey.api.*;

public class Team implements ITeam {
    public int getLuckyNumber() { return 1337; }
    public String getShortName() { return "T"+03; }
    public String getTeamName() { return "Team "+03; }
    public Color getTeamColor() { return Color.CYAN; }
    public Color getSecondaryTeamColor() { return Color.BLACK; }
    public GoalKeeper getGoalKeeper() { return new Goalie(); }
    public Player getPlayer(int index) { 
        return new AwarePlayer(index);
    }
}

class TeamPlayer extends Player {
    private static String[] names = {
	"", "Left Defender", "Right Defender", 
	"Left Forward", "Right Forward", "Center"
    };
    private int index;

    public TeamPlayer(int index) { this.index = index; }
    public int getNumber() { return (int)(Math.random()*100); }
    public String getName() { return names[index]; }
    public boolean isLeftHanded() { return false; }
    public void step() {
        if (hasPuck()) {
            skate(2600, 0, 1000);
        } else {
            skate(getPuck(), 1000);
        }

    }
}

class Goalie extends GoalKeeper {
    public Goalie() { }
    public int getNumber() { return (int)(Math.random()*100); }
    public String getName() { return "Goalie"; }
    public boolean isLeftHanded() { return false; }
    public void step() {
        skate(-2550,0,200);
        turn(getPuck(), MAX_TURN_SPEED);
    }
}

class Shooter extends Player {
    private static int[] numbers = {1, 2, 3, 4, 5, 6};
    private static String[] names = {
    "", "Glock", "Ruger", "Smith", "Wesson", "Colt"
    };
    private int index;

    public Shooter(int index) { this.index = index; }
    public int getNumber() { return numbers[index]; }
    public String getName() { return names[index]; }
    public boolean isLeftHanded() { return false; }
    public void step() {
    if (hasPuck()) // If player has the puck...
        if (Math.abs(Util.dangle(getHeading(), // ...and is turned towards the goal.
                     Util.datan2(0 - getY(),
                         2500 - getX()))) < 90) {
        int target = (int)(Math.random()*200)-100;
        shoot(2600, target, 10000); // Shoot.
        } else // If not
        skate(2600, 0, 1000); // Turn towards goal.
    else // If not
        skate(getPuck(), 1000); // Get the puck.
    }
}

class ShooterGoalie extends GoalKeeper {
    public int getNumber() { return 1; }
    public String getName() { return "Beretta"; }
    public boolean isLeftHanded() { return false; }
    public void step() {
    if (hasPuck()) // If goalie has the puck
        shoot(2600, 0, 10000); // Shoot (or throw)
    skate(-2550, 0, 200); // Stand in the middle of the goal.
    turn(getPuck(), MAX_TURN_SPEED); // Turn towards puck.
    }
}

class AwarePlayer extends Player {
  private static int[] numbers = {1, 2, 3, 4, 5, 6};
  private static String[] names = {
    "", "Glock", "Ruger", "Smith", "Wesson", "Colt"
    };
    private int index;

    public AwarePlayer(int index) { this.index = index; }
    public int getNumber() { return numbers[index]; }
    public String getName() { return names[index]; }
    public boolean isLeftHanded() { return false; }
    public boolean teamHasPuck() {
        IPuck puck = getPuck();

        if (puck == null) {
            return false;
        }
        if (!puck.isHeld()) {
            return false;
        }
        if (puck.getHolder().isOpponent()) {
            return false;
        }
        return true;
    }
    public void step() {
        if (hasPuck()) {
            setMessage("I have the puck now.");
            skate(2600, 0, 1000);
        } else if (teamHasPuck()) {
            setMessage("My team has the puck");
            skate(-2550, 0, 10000);
        } else {
            setMessage("No one or opponent has puck");
            skate(getPuck(), 10000);
        }
    }
}
