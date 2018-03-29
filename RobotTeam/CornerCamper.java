package nl.saxion;

import robocode.*;
import java.awt.*;

public class CornerCamper extends TeamRobot {

    private Utilities utilities;
    private Messaging messaging;
    private Movement movement;

    private boolean isCamper = true; // robot begint als camper
    private boolean isShark;
    private boolean movingForward = true;
    boolean inWall;

    
    public void run() {

        setDefenderColor();

        utilities = new Utilities(this);
        messaging = new Messaging(this,utilities);
        movement = new Movement(this,utilities,messaging);
        movement.cornerCamperMovetoCorner();
        
       while (isCamper) {
            if (getGunHeat() == 0 && getEnergy() > .2) {
                setFire(1);
            }
            execute();
        }

        while (isShark) {
            if (getX() > 50 && getY() > 50 && getBattleFieldWidth() - getX() > 50 && getBattleFieldHeight() - getY() > 50 && inWall == true) {
                inWall = false;
            }
            if (getX() <= 50 || getY() <= 50 || getBattleFieldWidth() - getX() <= 50 || getBattleFieldHeight() - getY() <= 50) {
                if (inWall == false) {
                    movement.reverseDirection();
                    inWall = true;
                }
            }

            if (getRadarTurnRemaining() == 0.0) {
                setTurnRadarRight(360);
            }

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
        if (event.getMessage() instanceof String) {
            System.out.println((String) event.getMessage());

        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
          if (isShark) {
            if (!isTeammate(event.getName())) {
                // Calculate exact location of the robot
                double absoluteBearing = getHeading() + event.getBearing();
                double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
                double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());

                // 80 and 100 degrees will make us move closer every turn
                if (movingForward) {
                    setTurnRight(normalRelativeAngleDegrees(event.getBearing() + 80));
                } else {
                    setTurnRight(normalRelativeAngleDegrees(event.getBearing() + 100));
                }

                // If it's close enough, fire!
                if (Math.abs(bearingFromGun) <= 3) {
                    setTurnGunRight(bearingFromGun);
                    setTurnRadarRight(bearingFromRadar); // keep the radar focused on the enemy

                    if (getGunHeat() == 0 && getEnergy() > .2) {
                        setFire(Math.min(400 / event.getDistance(), 3));
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
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        super.onRobotDeath(event);
        if (isTeammate(event.getName())) {
            messaging.messageCornerCamperDead(event.getName());

            if(utilities.getID() == utilities.getID(event.getName())){
                isCamper = false;
                isShark = true;
            }
        }
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        super.onHitRobot(event);

         if(isTeammate(event.getName())){
            if(event.isMyFault()) {
                clearAllEvents();
                ahead(-20);
                movement.cornerCamperMovetoCorner();
            }
        }
        
        if (isShark) {
            if (event.isMyFault()) {
                movement.reverseDirection();
            }
        }
    }
}
