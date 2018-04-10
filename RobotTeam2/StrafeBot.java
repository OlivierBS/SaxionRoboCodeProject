package nl.saxion;

import robocode.*;
import java.awt.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class StrafeBot extends TeamRobot {

    private static final Color BODY_COLOR = new Color(143, 161, 255);
    private static final Color GUN_COLOR = new Color(14, 0, 57);
    private static final Color RADAR_COLOR = new Color(143, 161, 255);
    private static final Color BULLET_COLOR = new Color(86, 182, 56);
    private static final Color SCAN_COLOR = new Color(237, 239, 255);

    private Movement movement;

    /**
     * Standard run method, which gets called when robot spawns.
     */
    public void run() {
        Utilities.setRobotColor(this, BODY_COLOR, GUN_COLOR, RADAR_COLOR, BULLET_COLOR, SCAN_COLOR);

        movement = new Movement(this);
        movement.strafeBotGoToPos();

        while (true) {
            setTurnRadarRight(360); // scan until you find your first enemy
            movement.strafeBotStrafing();
            execute();
        }
    }

    /**
     * Lets the robot shoot at a target.
     * @author Yao Lee, William
     * @param event ScannedRobotEvent event.
     */
    private void firingMechanicStrafer(ScannedRobotEvent event) {
        double absoluteBearing = getHeading() + event.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

        if (Math.abs(bearingFromGun) <= 3) {
            setTurnGunRight(bearingFromGun);
            setTurnRadarRight(bearingFromRadar); // Keep the radar focused on the enemy

            if (getGunHeat() == 0 && getEnergy() > .2) {
                // Never fires a bullet with a power bigger than 3
                // Fires a bullet with at least a power of 2
                setFire(Math.min((400 / event.getDistance()) + 2, 3));
            }
        } else {
            setTurnGunRight(bearingFromGun);
            setTurnRadarRight(bearingFromRadar);
        }
        if (bearingFromGun == 0) {
            scan();
        }
    }

    /*
                               +--------------------------------+
            +------------------+          Robot Events          +------------------+
                               +--------------------------------+
     */

    /**
     * onHitByBullet targets enemy robot that hit the robot.
     * @author Yao Lee
     * @param event HitByBulletEvent event.
     */
    public void onHitByBullet(HitByBulletEvent event) {
        // calculate exact location of enemy
        double absoluteBearing = getHeading() + event.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

        setTurnGunRight(bearingFromGun);
        setTurnRadarRight(bearingFromRadar);
    }

    /**
     * onScannedRobot assigns new target or targets target.
     * @author Mike, Olivier, William
     * @param event ScannedRobotEvent event.
     */
    public void onScannedRobot(ScannedRobotEvent event) {
        if (!isTeammate(event.getName())) {
            firingMechanicStrafer(event);
        }
    }

    /**
     * Reverses direction if it's the fault of the robot.
     * @author Yao Lee
     * @param event HitRobotEvent event.
     */
    public void onHitRobot(HitRobotEvent event) {
        if(event.isMyFault()){
            movement.reverseStrafeDirection();
        }
    }
}
