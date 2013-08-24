package samples;

import java.awt.Color;

import hockey.api.Player;
import hockey.api.GoalKeeper;
import hockey.api.ITeam;

public class Offsiders implements ITeam {
    public int getLuckyNumber() { return 324; }
    public String getShortName() { return "OFF"; }
    public String getTeamName() { return "Thee Offsiders"; }
    public Color getTeamColor() { return Color.RED; }
    public Color getSecondaryTeamColor() { return Color.RED; }
    public GoalKeeper getGoalKeeper() { return new FrontLine(); }
    public Player getPlayer(int index) { 
	return index < 5 ? (Player)new FrontLine() : (Player)new Sender();
    }
}

class FrontLine extends GoalKeeper {
    public void init() {}
    public int getNumber() { return 0; }
    public String getName() { return "Forwaaaaard!"; }
    public boolean isLeftHanded() { return false; }
    public void faceOff() { }
    public void penaltyShot() { }
    public void step() {
	shoot(getHeading() + 180, 100);
	skate(3000, 500, 100000);
    }
}

class Sender extends Player {
    public void init() {}
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

