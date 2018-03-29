package nl.saxion;

import robocode.*;
import java.awt.*;

public class CornerCamper extends TeamRobot {

    private Utilities utilities;
    private Messaging messaging;
    private Movement movement;

    public void run() {

        setDefenderColor();

        utilities = new Utilities(this);
        messaging = new Messaging(this,utilities);
        movement = new Movement(this,utilities,messaging);
        movement.cornerCamperMovetoCorner();

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
        if (event.getMessage() instanceof String) {
            System.out.println((String) event.getMessage());

        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        super.onScannedRobot(event);

    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        super.onRobotDeath(event);
        if (isTeammate(event.getName())) {
            messaging.messageCornerCamperDead(event.getName());

        }
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        super.onHitRobot(event);

        if(isTeammate(event.getName())){
            clearAllEvents();
            ahead(-10);
            movement.cornerCamperMovetoCorner();
        }
    }
}
