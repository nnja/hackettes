package samples;

import java.awt.Color;
import hockey.api.GoalKeeper;
import hockey.api.Player;
import hockey.api.ITeam;
import hockey.api.Util;

public class TriggerHappy implements ITeam {
    public String getShortName() { return "TRG"; }
    public String getTeamName() { return "Trigger Happy"; }
    public Color getTeamColor() { return Color.BLACK; }
    public Color getSecondaryTeamColor() { return Color.RED; }
    public int getLuckyNumber() { return 13; }
    public GoalKeeper getGoalKeeper() { return new ShooterGoalie(); }
    public Player getPlayer(int index) {
	return new Shooter(index);
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
