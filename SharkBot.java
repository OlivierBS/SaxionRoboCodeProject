package nl.saxion;

import robocode.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class TeamSharkbot extends TeamRobot {
    boolean movingForward;

    public void run() {
        setAhead(10000);
        setTurnRadarRight(360); // scan until first enemy
        movingForward = true;

        while (true) {
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
        if (!isTeammate(e.getName())) {
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
                    setFire(Math.min(400 / e.getDistance(), 3));
                }

            } else {
                setTurnGunRight(bearingFromGun);
                setTurnRadarRight(bearingFromRadar);
            }

            if (bearingFromGun == 0) {
                scan();
            }
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            reverseDirection();
        }
    }
}
