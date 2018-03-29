package nl.saxion;

import robocode.TeamRobot;
import java.io.IOException;

public class Movement {

    private static final String READY_MESSAGE = "READY";

    private TeamRobot robot;
    private Utilities utilities;
    private Messaging messaging;

    private int id;
    private int moveDirection = 1; // 1 = forward, -1 = backwards

    public Movement(TeamRobot robot, Utilities utilities, Messaging messaging){
        this.robot = robot;
        this.utilities = utilities;
        this.messaging = messaging;
        id = utilities.getID();
    }

    /*
                               +--------------------------------+
            +------------------+         Corner Camper          +------------------+
                               +--------------------------------+
     */

    public void cornerCamperMovetoCorner(){
        if(id == 1){
            cornerCamperLeftCorner();
        }else if(id == 2){
            cornerCamperGoRightCorner();
        }
    }

    /**
     * @author Olivier
     */
    private void cornerCamperGoRightCorner() {

       double target_X = 65.0;
       double target_Y = 25.0;

        double angle = utilities.getAngle(target_X, target_Y);
        double turnDegrees = -angle +  robot.getHeading();

        robot.turnLeft(turnDegrees);

        while (Math.ceil( robot.getX()) != target_X) {
            double distance = Math.hypot( robot.getX() - target_X,  robot.getY() -  target_Y);
            robot.ahead(distance);
        }

        double turnGunDegrees = -45 +  robot.getGunHeading();
        robot.turnLeft(turnGunDegrees);

        try {
            robot.broadcastMessage(READY_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @author Olivier
     */
    private void cornerCamperLeftCorner() {

        double target_X = 25.0;
        double target_Y = 65.0;

        double angle = utilities.getAngle(target_X, target_Y);
        double turnDegrees = -angle + robot.getHeading();

        robot.turnLeft(turnDegrees);

        while (Math.ceil(robot.getX()) != target_X) {
            double distance = Math.hypot(robot.getX() - target_X, robot.getY() - target_Y);
            robot.ahead(distance);
        }

        double turnGunDegrees = -45 + robot.getGunHeading();
        robot.turnLeft(turnGunDegrees);

        try {
            robot.broadcastMessage(READY_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
                               +--------------------------------+
            +------------------+        Corner Defender         +------------------+
                               +--------------------------------+
     */

    public void cornerDefenderMovetoCorner(){
        if(id == 1){
            cornerDefenderGoLeftCorner();
        }else if(id == 2){
            cornerDefenderGoRightCorner();
        }
    }


    /**
     * @Author Olivier
     */
    private void cornerDefenderGoLeftCorner() {
        if (robot.getHeading() > 270) {
            double turnDegrees = -270 + robot.getHeading();
            robot.turnLeft(turnDegrees);

        } else if (robot.getHeading() < 270) {
            double turnDegrees = (270.0 - robot.getHeading());
            robot.turnRight(turnDegrees);

        }

        while(Math.ceil(robot.getX()) != 50.0){
            robot.ahead(robot.getX() - 50);
        }

        robot.turnLeft(90);

        double percentage = robot.getBattleFieldHeight() * 0.35;
        double distance = robot.getY() - percentage;

        robot.ahead(distance);

        robot.turnLeft(45 - robot.getHeading());
    }

    /**
     * @author William
     */
    public void cornerDefenderGoRightCorner() {
        double dynamicWidth;
        robot.turnLeft(robot.getHeading() - 180);

        while(Math.ceil(robot.getY()) != 50.0) {
            robot.setAhead(robot.getY() - 50);
            while (robot.getDistanceRemaining() > 0) {
                robot.execute();
            }
        }
        robot.turnRight(90);

        dynamicWidth = (robot.getBattleFieldWidth() * 0.35);

        robot.setAhead(robot.getX() - robot.getWidth() - dynamicWidth);
        while (robot.getDistanceRemaining() > 0) {
            robot.execute();
        }

        robot.turnLeft(45 - robot.getHeading());
    }

    /**
     * @author Yaolee
     */
    public void cornerDefenderdoStrafing() {
        // strafe by changing direction every 25 ticks
        if (robot.getTime() % 25 == 0) {
            robot.setMaxVelocity(3);
            moveDirection *= -1;
            robot.setAhead(200 * moveDirection);
        }
    }

    /*
                               +--------------------------------+
            +------------------+       Movement Utilities       +------------------+
                               +--------------------------------+
     */

    /**
     * @author Yaolee
     */
    public void reverseDirection() {
        if (moveDirection == 1) {
            robot.setBack(10000);
        } else {
            robot.setAhead(10000);
        }
    }

}
