package samples;

import java.awt.Color;
import hockey.api.GoalKeeper;
import hockey.api.Player;
import hockey.api.ITeam;

public class KnatteHockey implements ITeam {
    public String getShortName() { return "JLG"; }
    public String getTeamName() { return "Junior League"; }
    public Color getTeamColor() { return Color.BLUE; }
    public Color getSecondaryTeamColor() { return Color.RED; }
    public int getLuckyNumber() { return 13; }
    public GoalKeeper getGoalKeeper() { return new KnatteGoalie(); }
    public Player getPlayer(int index) {
	return new Knatte(index);
    }
}

class Knatte extends Player {
    private static int[] numbers = {1, 2, 3, 4, 5, 6};
    private static String[] names = {
      //"", "Teddy", "Freddy", "Knatte", "Fnatte", "Tjatte"
      "", "Morty", "Ferdie", "Huey", "Dewey", "Louie"
    };
    private int index;

    public Knatte(int index) { this.index = index; }
    public int getNumber() { return numbers[index]; }
    public String getName() { return names[index]; }
    public boolean isLeftHanded() { return false; }
    public void step() {
	if (hasPuck()) // If we have the puck
	    skate(2600, 0, 1000); // skate towards the goal.
	else // If not
	    skate(getPuck(), 1000); // get the puck.
    }
}

class KnatteGoalie extends GoalKeeper {
    public int getNumber() { return 1; }
    public String getName() { return "Phooey"; }
    public boolean isLeftHanded() { return false; }
    public void step() {
	skate(-2550, 0, 200); // Stand in the middle of the goal.
	turn(getPuck(), MAX_TURN_SPEED); // Turn towards the puck.
    }
}

