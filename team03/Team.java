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
    public Player getPlayer(int index) { return new TeamPlayer(index); }
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

