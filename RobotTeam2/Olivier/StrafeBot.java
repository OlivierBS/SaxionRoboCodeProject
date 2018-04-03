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
}
