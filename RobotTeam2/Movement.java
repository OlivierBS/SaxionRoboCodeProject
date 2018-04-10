package nl.saxion;

import robocode.TeamRobot;

public class Movement {

    private TeamRobot robot;

    private double strafeBotStartingXPos1, strafeBotStartingYPos1, strafeBotStartingXPos2, strafeBotStartingYPos2;
    private double battleFieldHeight, battleFieldWidth;
    private int strafingCount = 0;
    private int moveDirection = 1;
    private int id;
    private boolean movingForward;

    /**
     * Basic constructor which instantiates variables.
     *
     * @param robot gives us a TeamRobot reference to the robot.
     */
    public Movement(TeamRobot robot) {
        this.robot = robot;

        id = Utilities.getID(robot);
        movingForward = true;

        battleFieldHeight = robot.getBattleFieldHeight();
        battleFieldWidth = robot.getBattleFieldWidth();
        strafeBotStartingXPos1 = battleFieldWidth * 0.25;
        strafeBotStartingYPos1 = battleFieldHeight * 0.40;
        strafeBotStartingXPos2 = battleFieldWidth * 0.75;
        strafeBotStartingYPos2 = battleFieldHeight * 0.60;
    }

    /**
     * Lets the Strafingbot to the starting position.
     *
     * @author Olivier
     */
    public void strafeBotGoToPos() {
        if (id == 1) {
            goToPoint(strafeBotStartingXPos1, strafeBotStartingYPos1);
            turnToDegree(0);
        } else if (id == 2) {
            goToPoint(strafeBotStartingXPos2, strafeBotStartingYPos2);
            turnToDegree(180);
        }
    }


    /**
     * Lets the robot do it's strafing.
     *
     * @author Yaolee
     */
    public void strafeBotStrafing() {
        if (robot.getTime() % 60 == 0) {
            if (strafingCount == 4) {
                robot.turnRight(45);
                strafingCount = 0;
                robot.ahead(40);
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
     * Reverses the direction the strafebot is facing.
     *
     * @author Yaolee
     */
    public void reverseStrafeDirection() {
        if (moveDirection == 1) {
            robot.setBack(10000);
        } else {
            robot.setAhead(10000);
        }
    }

    /**
     * Reverses the direction the sharkbot is facing.
     *
     * @author Yao Lee
     */
    public void reverseSharkDirection() {
        if (movingForward) {
            robot.setBack(10000);
            movingForward = false;
        } else {
            robot.setAhead(10000);
            movingForward = true;
        }
    }

    /**
     * Returns if the sharkbot is moving forward.
     *
     * @return boolean if sharkbot is moving forward.
     */
    public boolean sharkBotIsMovingForward() {
        return movingForward;
    }

    /**
     * Lets the robot go to a certain point.
     *
     * @param x the x value of the point.
     * @param y the y value of the point.
     * @author Olivier
     */
    private void goToPoint(double x, double y) {
        double angle = Utilities.getAngle(robot, x, y);
        turnToDegree(angle);

        while (Math.ceil(robot.getX()) != x) {
            double distance = Utilities.getDistanceToPoint(robot, x, y);
            robot.setAhead(distance);
            robot.execute();
        }
    }

    /**
     * Turns the robot to a certain degree.
     *
     * @param degree the degree to be turned to.
     * @author Olivier
     */
    private void turnToDegree(double degree) {
        if (robot.getHeading() < 180 && robot.getHeading() >= 0) {
            robot.setTurnRight((degree - robot.getHeading()));
            while (robot.getTurnRemaining() > 0) {
                robot.execute();
            }
        } else if (robot.getHeading() >= 180 && robot.getHeading() <= 360) {
            robot.setTurnLeft((-degree + robot.getHeading()));
            while (robot.getTurnRemaining() > 0) {
                robot.execute();
            }
        }
        robot.waitFor(Utilities.hasDegree(robot, degree));
    }
}
