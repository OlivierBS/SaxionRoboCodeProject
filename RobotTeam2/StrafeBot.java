package nl.saxion.Sprint2Team;

import robocode.*;

import java.awt.*;
import java.io.IOException;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class StrafeBot extends TeamRobot {

    private static final Color BODY_COLOR = new Color(143, 161, 255);
    private static final Color GUN_COLOR = new Color(14, 0, 57);
    private static final Color RADAR_COLOR = new Color(205, 255, 199);
    private static final Color BULLET_COLOR = new Color(58, 2, 7);
    private static final Color SCAN_COLOR = new Color(237, 239, 255);

    private Utilities utilities;
    private Movement movement;

    private String currentTarget = null;
    private boolean targetAssigned = false;

    public void run() {
        utilities = new Utilities(this);
        movement = new Movement(this, utilities);
        utilities.setDefenderColor(BODY_COLOR, GUN_COLOR, RADAR_COLOR, BULLET_COLOR, SCAN_COLOR);
        movement.goToPos();

        while (true) {
            movement.strafeBotStrafing();
            setTurnRadarRight(360); // Scan until you find your first enemy
            execute();
        }
    }

    /*
                               +--------------------------------+
            +------------------+          Robot Events          +------------------+
                               +--------------------------------+
     */

    /**
     * @author Mike, William, Olivier
     */
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getMessage() instanceof String) {
            String message = messageEvent.getMessage().toString();
            if (message.contains("TARGET:")) {
                targetAssigned = true;
                currentTarget = message.substring(6);
            }

        }
    }

    /**
     * @author Yao Lee
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
     * @author Mike, Olivier, William
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        if (!isTeammate(e.getName())) {
            if (!targetAssigned) {
                try {
                    broadcastMessage("TARGET:" + e.getName());
                    currentTarget = e.getName();
                    targetAssigned = true;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            if (targetAssigned) {
                if (e.getName().equals(currentTarget)) {
                    firingMechanicStrafer(e);
                } else {
                    scan();
                }
            } else {
                firingMechanicStrafer(e);
            }
        }
    }

    /**
     * @author Yao Lee, William
     */
    public void firingMechanicStrafer(ScannedRobotEvent e) {
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

        if (Math.abs(bearingFromGun) <= 3) {
            setTurnGunRight(bearingFromGun);
            setTurnRadarRight(bearingFromRadar); // Keep the radar focused on the enemy

            if (getGunHeat() == 0 && getEnergy() > .2) {
                // Never fires a bullet with a power bigger than 3
                // Fires a bullet with at least a power of 2
                setFire(Math.min((400 / e.getDistance()) + 2, 3));
            }
        } else {
            setTurnGunRight(bearingFromGun);
            setTurnRadarRight(bearingFromRadar);
        }

        if (bearingFromGun == 0) {
            scan();
        }
    }

    /**
     * @author Mike, William
     */
    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        if (currentTarget.equals(event.getName())) {
            targetAssigned = false;
        }
    }
}
