package samples;

import java.awt.Color;

import hockey.api.Player;
import hockey.api.GoalKeeper;
import hockey.api.ITeam;

public class Icingers implements ITeam {
    public int getLuckyNumber() { return 324; }
    public String getShortName() { return "OFF"; }
    public String getTeamName() { return "Thee Icingers"; }
    public Color getTeamColor() { return Color.MAGENTA; }
    public Color getSecondaryTeamColor() { return Color.MAGENTA; }
    public GoalKeeper getGoalKeeper() { return new BackLine(); }
    public Player getPlayer(int index) { 
	return index < 5 ? (Player)new BackLine() : (Player)new Sender();
    }
}

class BackLine extends GoalKeeper {
    public void init() {}
    public int getNumber() { return 0; }
    public String getName() { return "Backwaaaaard!"; }
    public boolean isLeftHanded() { return false; }
    public void faceOff() { }
    public void penaltyShot() { }
    public void step() {
	shoot(getHeading() + 180, 100);
	skate(-3000, 500, 100000);
    }
}

/*
class Sender extends Player {
    public int getNumber() { return 99; }
    public String getName() { return "Sender"; }
    public boolean isLeftHanded() { return false; }
    public void faceOff() { }
    public void penaltyShot() { }
    public void step() {
	if (!hasPuck())
	    skate(getPuck(), 10000);
	else
	    shoot(3000, -500, 100000);
    }
}

*/
