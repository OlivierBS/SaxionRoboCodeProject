package nl.saxion.Sprint2Team;

import robocode.TeamRobot;

public class Movement {

    private double startingXPos1;
    private double startingYPos1;
    private double startingXPos2;
    private double startingYPos2;

    private TeamRobot robot;
    private Utilities utilities;

    private double battleFieldHeight;
    private double battleFieldWidth;

    private int strafingCount = 0;

    private int id;
    private int moveDirection = 1; // 1 = forward, -1 = backwards

    public Movement(TeamRobot robot, Utilities utilities){
        this.robot = robot;
        this.utilities = utilities;
        id = utilities.getID();

        battleFieldHeight = robot.getBattleFieldHeight();
        battleFieldWidth = robot.getBattleFieldWidth();
        startingXPos1 = battleFieldWidth * 0.25;
        startingYPos1 = battleFieldHeight * 0.40;
        startingXPos2 = battleFieldWidth * 0.75;
        startingYPos2 = battleFieldHeight * 0.60;
    }

    /**
     * @author Olivier
     */
    public void goToPos() {
        if(id == 1){
            goToPoint(startingXPos1,startingYPos1);
            turnToDegree(0);
        }else if(id == 2){
            goToPoint(startingXPos2,startingYPos2);
            turnToDegree(180);
        }

    }

    /**
     * @author Yaolee
     */
    public void strafeBotStrafing() {
        // strafe by changing direction every 25 ticks
        if (robot.getTime() % 22 == 0) {
            if(strafingCount == 4){
                robot.turnRight(45);
                strafingCount = 0;
                robot.ahead(20);
            }
            strafingCount++;
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

    /**
     * @author Olivier
     */
    private void goToPoint(double x, double y){
        double angle = utilities.getAngle(x,y);
        turnToDegree(angle);

        while(Math.ceil(robot.getX()) != x ){
            double distance = utilities.getDistanceToPoint(x,y);
            robot.setAhead(distance);
            robot.execute();
        }
    }

    /**
     * @author Olivier
     */
    private void turnToDegree(double degree){
        if (robot.getHeading() < 180 && robot.getHeading() >= 0) {
            robot.setTurnRight((degree - robot.getHeading()));
            while(robot.getTurnRemaining() > 0){
                robot.execute();
            }
        } else if (robot.getHeading() >= 180 && robot.getHeading() <= 360) {
            robot.setTurnLeft((-degree + robot.getHeading()));
            while(robot.getTurnRemaining() > 0){
                robot.execute();
            }
        }
        robot.waitFor(utilities.hasDegree(degree));
    }

}
