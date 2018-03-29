package nl.saxion;

import robocode.*;
import java.awt.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class CornerDefender extends TeamRobot {

    private Utilities utilities;
    private Messaging messaging;
    private Movement movement;

    public void run() {

        setDefenderColor();

        utilities = new Utilities(this);
        messaging = new Messaging(this,utilities);
        movement = new Movement(this,utilities,messaging);
        movement.cornerDefenderMovetoCorner();

        while (true) {
            movement.cornerDefenderdoStrafing();
            setTurnRadarRight(360); // scan until you find your first enemy
            execute();
        }
    }

    /*
                               +--------------------------------+
            +------------------+        Robot Utilities         +------------------+
                               +--------------------------------+
     */

    /**
     * @author William
     */
    private void setDefenderColor() {
        setBodyColor(new Color(0, 0, 0));
        setGunColor(new Color(255, 0, 0));
        setRadarColor(new Color(0, 0, 0));
        setBulletColor(new Color(255, 0, 0));
        setScanColor(new Color(0, 0, 0));
    }

    /*
                               +--------------------------------+
            +------------------+          Robot Events          +------------------+
                               +--------------------------------+
     */

    @Override
    public void onMessageReceived(MessageEvent event) {
        super.onMessageReceived(event);
        if(event.getMessage() instanceof String){
            String message = (String) event.getMessage();
        }
    }

    /**
     * @author Yaolee
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        // Calculate exact location of the robot
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

        if(!isTeammate(e.getName())) {
            // If it's close enough, fire!
            if (Math.abs(bearingFromGun) <= 3) {
                setTurnGunRight(bearingFromGun);
                setTurnRadarRight(bearingFromRadar); // keep the radar focused on the enemy

                if (getGunHeat() == 0 && getEnergy() > .2) {
                    // never fires a bullet bigger than 3, just flooring the value to be safe
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

    /**
     * @author Yaolee
     */
    @Override
    public void onHitWall(HitWallEvent e) {
        movement.reverseDirection();
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        super.onHitRobot(event);

        if(isTeammate(event.getName())){
            clearAllEvents();
            ahead(-40);
        }
    }
}


