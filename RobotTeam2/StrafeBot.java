package nl.saxion;

import robocode.MessageEvent;
import robocode.TeamRobot;

import java.awt.*;

public class StrafeBot extends TeamRobot {

    private Utilities utilities;
    private Messaging messaging;
    private Movement movement;


    public void run() {

        setDefenderColor();

        utilities = new Utilities(this);
        messaging = new Messaging(this,utilities);
        movement = new Movement(this,utilities,messaging);

        movement.goToPos();

        while (true) {
            movement.strafeBotStrafing();
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
        if(event.getMessage() instanceof String) {
            String message = (String) event.getMessage();
        }
    }
    
     public void onHitByBullet(HitByBulletEvent event){
        System.out.println(event.getName() + " hit me!");

        // calculate exact location of enemy
        double absoluteBearing = getHeading() + event.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

        setTurnGunRight(bearingFromGun);
        setTurnRadarRight(bearingFromRadar);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

        if (Math.abs(bearingFromGun) <= 3) {
            setTurnGunRight(bearingFromGun);
            setTurnRadarRight(bearingFromRadar); // keep the radar focused on the enemy

            if (getGunHeat() == 0 && getEnergy() > .2) {
                // never fires a bullet bigger than 3, just flooring the value to be safe
                // fires at least a bullet of 2
                setFire(Math.min((400 / e.getDistance()) + 2 , 3));
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
