package nl.saxion;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class SharkBot extends AdvancedRobot {
    boolean movingForward;
    boolean inWall;

    public void run() {
        setAhead(10000); // go ahead until you get commanded to do differently
        setTurnRadarRight(360); // scan until you find your first enemy
        movingForward = true;

        if (getX() <= 50 || getY() <= 50 || getBattleFieldWidth() - getX() <= 50 || getBattleFieldHeight() - getY() <= 50) {
            inWall = true;
        } else {
            inWall = false;
        }

        while (true) {

            if (getX() > 50 && getY() > 50 && getBattleFieldWidth() - getX() > 50 && getBattleFieldHeight() - getY() > 50 && inWall == true) {
                inWall = false;
            }
            if (getX() <= 50 || getY() <= 50 || getBattleFieldWidth() - getX() <= 50 || getBattleFieldHeight() - getY() <= 50) {
                if (inWall == false) {
                    reverseDirection();
                    inWall = true;
                }
            }

            if (getRadarTurnRemaining() == 0.0) {
                setTurnRadarRight(360);
            }

            execute();
        }
    }

    public void reverseDirection() {
        if (movingForward) {
            setBack(10000);
            movingForward = false;
        } else {
            setAhead(10000);
            movingForward = true;
        }
    }

    public void onHitWall(HitWallEvent e) {
        reverseDirection();
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Calculate exact location of the robot
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

        // 80 and 100 degrees will make us move closer every turn
        if (movingForward) {
            setTurnRight(normalRelativeAngleDegrees(e.getBearing() + 80));
        } else {
            setTurnRight(normalRelativeAngleDegrees(e.getBearing() + 100));
        }

        // If it's close enough, fire!
        if (Math.abs(bearingFromGun) <= 3) {
            setTurnGunRight(bearingFromGun);
            setTurnRadarRight(bearingFromRadar); // keep the radar focused on the enemy

            if (getGunHeat() == 0 && getEnergy() > .2) {
                if (e.getDistance() < 100) {
                    fire(3.0);
                } else if (e.getDistance() < 300) {
                    fire(1.5);
                } else {
                    fire(1);
                }
            }
        } else {
            setTurnGunRight(bearingFromGun);
            setTurnRadarRight(bearingFromRadar);
        }

        if (bearingFromGun == 0) {
            scan();
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            reverseDirection();
        }
    }
}
