package nl.saxion;

import robocode.Condition;
import robocode.TeamRobot;
import java.awt.*;

public class Utilities {

    /**
     * Returns the id of a robot.
     *
     * @param robot robot instance to get the name.
     * @return Integer with the id of the robot.
     * @author Olivier
     */
    public static int getID(TeamRobot robot) {
        String name = robot.getName();
        if (!name.contains("(")) {
            return 1;
        } else {
            String requiredString = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
            return Integer.parseInt(requiredString);
        }
    }

    /**
     * Returns the id of a robot with the robot's name.
     *
     * @param name the robot's name.
     * @return Integer with the id of the robot.
     * @author Olivier
     */
    public static int getID(String name) {
        if (!name.contains("(")) {
            return 1;
        } else {
            String requiredString = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
            return Integer.parseInt(requiredString);
        }
    }

    /**
     * Returns the angle to a certain point.
     *
     * @param robot   robot instance to get the degree.
     * @param targetX the X target of the point.
     * @param targetY the Y target of the point.
     * @return Double with the angle to a certain point.
     * @author Olivier
     */
    public static double getAngle(TeamRobot robot, double targetX, double targetY) {
        double angle = Math.toDegrees(Math.atan2(targetX - robot.getX(), targetY - robot.getY()));

        if (angle < 0) {
            angle += 360.0;
        }
        return angle;
    }

    /**
     * Returns the distance to a point.
     *
     * @param robot   robot instance to get the distance.
     * @param targetX the X target of the point.
     * @param targetY the Y target of the point.
     * @return Double with the distance to the point.
     * @author Olivier
     */
    public static double getDistanceToPoint(TeamRobot robot, double targetX, double targetY) {
        return Math.hypot(robot.getX() - targetX, robot.getY() - targetY);
    }

    /**
     * Returns the condition for a certain degree.
     *
     * @param robot  robot instance to get heading of.
     * @param degree degree that is to be checked.
     * @return Condition with the degree that was given.
     * @author Olivier
     */
    public static Condition hasDegree(TeamRobot robot, double degree) {
        Condition condition = new Condition() {
            @Override
            public boolean test() {
                if (Math.ceil(robot.getHeading()) == Math.ceil(degree)) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        return condition;
    }

    /**
     * Sets the color of a robot.
     *
     * @param robot            robot instance to set the colors to.
     * @param robotBodyColor   robot's body color.
     * @param robotGunColor    robot's gun color.
     * @param robotRadarColor  robot's rader color.
     * @param robotBulletColor robot's bullet color.
     * @param robotScanColor   robot's scan color.
     * @author William
     */
    public static void setRobotColor(TeamRobot robot, Color robotBodyColor, Color robotGunColor, Color robotRadarColor, Color robotBulletColor, Color robotScanColor) {
        robot.setBodyColor(robotBodyColor);
        robot.setGunColor(robotGunColor);
        robot.setRadarColor(robotRadarColor);
        robot.setBulletColor(robotBulletColor);
        robot.setScanColor(robotScanColor);
    }
}
