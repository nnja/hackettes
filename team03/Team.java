package team03;

import java.awt.*;
import java.awt.geom.Line2D;

import hockey.api.Player;
import hockey.api.GoalKeeper;
import hockey.api.ITeam;
import hockey.api.*;

public class Team implements ITeam {
    public int getLuckyNumber() {
        return 1337;
    }

    public String getShortName() {
        return "T" + 03;
    }

    public String getTeamName() {
        return "Team " + 03;
    }

    public Color getTeamColor() {
        return Color.CYAN;
    }

    public Color getSecondaryTeamColor() {
        return Color.BLACK;
    }

    public GoalKeeper getGoalKeeper() {
        return new BetterGoalie();
    }

    public Player getPlayer(int index) {
        return new AwarePlayer(index);
    }
}

class VoidPlayer extends GoalKeeper {
    private static String[] names = {
            "Voidkeeper", "Left Voiddefender", "Right Voiddefender",
            "Left Voidforward", "Right Voidforward", "Voidcenter"
    };
    private int index;

    public VoidPlayer(int index) {
        this.index = index;
    }

    public int getNumber() {
        return (int) (Math.random() * 100);
    }

    public String getName() {
        return names[index];
    }

    public boolean isLeftHanded() {
        return false;
    }

    public void step() {
    }
}


class TeamPlayer extends Player {
    private static String[] names = {
            "", "Left Defender", "Right Defender",
            "Left Forward", "Right Forward", "Center"
    };
    private int index;

    public TeamPlayer(int index) {
        this.index = index;
    }

    public int getNumber() {
        return (int) (Math.random() * 100);
    }

    public String getName() {
        return names[index];
    }

    public boolean isLeftHanded() {
        return false;
    }

    public void step() {
        if (hasPuck()) {
            skate(2600, 0, 1000);
        } else {
            skate(getPuck(), 1000);
        }

    }
}

class Goalie extends GoalKeeper {
    public Goalie() {
    }

    public int getNumber() {
        return (int) (Math.random() * 100);
    }

    public String getName() {
        return "Goalie";
    }

    public boolean isLeftHanded() {
        return false;
    }

    public void step() {
        skate(-2550, 0, 200);
        turn(getPuck(), MAX_TURN_SPEED);
    }
}

class Shooter extends Player {
    private static int[] numbers = {1, 2, 3, 4, 5, 6};
    private static String[] names = {
            "", "Glock", "Ruger", "Smith", "Wesson", "Colt"
    };
    private int index;

    public Shooter(int index) {
        this.index = index;
    }

    public int getNumber() {
        return numbers[index];
    }

    public String getName() {
        return names[index];
    }

    public boolean isLeftHanded() {
        return false;
    }

    public void step() {
        if (hasPuck()) // If player has the puck...
            if (Math.abs(Util.dangle(getHeading(), // ...and is turned towards the goal.
                    Util.datan2(0 - getY(),
                            2500 - getX()))) < 90) {
                int target = (int) (Math.random() * 200) - 100;
                shoot(2600, target, 10000); // Shoot.
            } else // If not
                skate(2600, 0, 1000); // Turn towards goal.
        else // If not
            skate(getPuck(), 1000); // Get the puck.
    }
}

class ShooterGoalie extends GoalKeeper {
    public int getNumber() {
        return 1;
    }

    public String getName() {
        return "Beretta";
    }

    public boolean isLeftHanded() {
        return false;
    }

    public void step() {
        if (hasPuck()) // If goalie has the puck
            shoot(2600, 0, 10000); // Shoot (or throw)
        skate(-2550, 0, 200); // Stand in the middle of the goal.
        turn(getPuck(), MAX_TURN_SPEED); // Turn towards puck.
    }
}

class BetterGoalie extends GoalKeeper {
    public int getNumber() {
        return 1;
    }

    public String getName() {
        return "Beretta";
    }

    public boolean isLeftHanded() {
        return false;
    }

    public boolean intersects(Point k, Point z, Point p) {
        return new Line2D.Float(k, z).ptLineDist(p) <= 40;
    }

    public void betterShooting() {
        // find a teammate that I have a clear line to
        for (int i=1; i<= 5; i++) {
            boolean clear = true;

            IPlayer teammate = getPlayer(i);
            Point mePoint = new Point(getX(), getY());
            Point teammatePoint = new Point(teammate.getStickX(), teammate.getStickY());

            for (int j=1; j<=11; j++) {
                // found an obstacle, look for next player
                if (!clear){
                    break;
                }

                // ignore other goalie and the player i'm currently looking at
                if (j == 6 || j == i ){
                    continue;
                }

                IPlayer obstacle = getPlayer(j);
                Point obstaclePoint = new Point(obstacle.getX(), obstacle.getY());

                boolean inter = intersects(mePoint, teammatePoint, obstaclePoint);
                setMessage(inter + " me x: " + mePoint.getX() + "y:" + mePoint.getY() + " target x: " + teammatePoint.getX() + "y: " + teammatePoint.getY() +" obs() x: " + obstaclePoint.getX() + "y: " + obstaclePoint.getY());
                if (inter){
                    clear = false;
                }
            }

            if (clear) {
                setMessage("I'm clear to shoot to player# : " + i);

                // turn towards my teammate
                turn(teammate, MAX_TURN_SPEED); // Turn towards puck.

                // shoot
                shoot(teammate.getStick(), 10000); // Shoot (or throw)
                return;
            }
        }

        //  I didn't find a clear teammate, so just shoot towards the enemy goalie  (at least forward)
        shoot(getPlayer(6), 10000); // Shoot (or throw)
    }

    public void betterGoalieBehaviour() {
	IPuck puck = getPuck();
	int x = puck.getX();
	int y = puck.getY();
	int speed = puck.getSpeed();
	int heading = puck.getHeading();


	// if the puck is behind me, then shadow it
	if ( x < -2600 ) {
	    if (y > 0) {
		skate(-2600, 90, 200);
		turn(-2600, 1500, MAX_TURN_SPEED);
//		setMessage("the puck is behind me, y > 0");
	    }
	    else {
		skate(-2600, -90, 200);
		turn(-2600, -1500, MAX_TURN_SPEED);
//		setMessage("the puck is behind me");
	    }
	}
	else {
	    // otherwise, turn towards it
	    turn(getPuck(), MAX_TURN_SPEED); // Turn towards puck.
	    skate(-2550, 0, 200); // Stand in the middle of the goal.
//	    setMessage("the puck is in front of me");
	}
    }

    public void step() {
        betterShooting();

        if (hasPuck()) {// If goalie has the puck
            betterShooting();
        } else {
            betterGoalieBehaviour();
        }
    }

}


class AwarePlayer extends Player {
    private static int[] numbers = {1, 2, 3, 4, 5, 6};
    private static String[] names = {
            "", "Glock", "Ruger", "Smith", "Wesson", "Colt"
    };
    private int index;

    public AwarePlayer(int index) {
        this.index = index;
    }

    public int getNumber() {
        return numbers[index];
    }

    public String getName() {
        return names[index];
    }

    public boolean isLeftHanded() {
        return false;
    }

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
