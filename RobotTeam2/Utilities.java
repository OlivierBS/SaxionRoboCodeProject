package nl.saxion;

import robocode.Condition;
import robocode.TeamRobot;
import java.awt.*;

public class Utilities {

    /**
     * Returns the id of a robot.
     * @author Olivier
     * @param robot robot instance to get the name.
     * @return Integer with the id of the robot.
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
     * @author Olivier
     * @param name the robot's name.
     * @return Integer with the id of the robot.
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
     * @author Olivier
     * @param robot robot instance to get the degree.
     * @param targetX the X target of the point.
     * @param targetY the Y target of the point.
     * @return Double with the angle to a certain point.
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
     * @author Olivier
     * @param robot robot instance to get the distance.
     * @param targetX the X target of the point.
     * @param targetY the Y target of the point.
     * @return Double with the distance to the point.
     */
    public static double getDistanceToPoint(TeamRobot robot, double targetX, double targetY){
        return Math.hypot(robot.getX()-targetX,robot.getY()-targetY);
    }

    /**
     * Returns the closest point of two points.
     * @author Olivier
     * @param robot robot instance to get the distance.
     * @param targetX1 the X target of point 1.
     * @param targetY1 the Y target of point 1.
     * @param targetX2 the X target of point 2.
     * @param targetY2 the Y target of point 2.
     * @return Point with the closest x,y.
     */
    public static Point getClosestPoint(TeamRobot robot, double targetX1, double targetY1, double targetX2, double targetY2){
        int distanceFromTarget1 = (int) getDistanceToPoint(robot, targetX1, targetY1);
        int distanceFromTarget2 = (int) getDistanceToPoint(robot, targetX2, targetY2);

        Point p = null;

        if(distanceFromTarget1 < distanceFromTarget2){
            p = new Point((int)targetX1,(int)targetY1);
        }else if(distanceFromTarget1 > distanceFromTarget2){
            p = new Point((int)targetX2,(int)targetY2);
        }
        return p;
    }

    /**
     * Returns the condition for a certain degree.
     * @author Olivier
     * @param robot robot instance to get heading of.
     * @param degree degree that is to be checked.
     * @return Condition with the degree that was given.
     */
     public static Condition hasDegree(TeamRobot robot, double degree){
        Condition condition = new Condition() {
            @Override
            public boolean test() {
                if(Math.ceil(robot.getHeading()) == Math.ceil(degree)){
                    return true;
                }else{
                    return false;
                }
            }
        };
        return condition;
    }

    /**
     * Sets the color of a robot.
     * @author William
     * @param robot robot instance to set the colors to.
     * @param robotBodyColor robot's body color.
     * @param robotGunColor robot's gun color.
     * @param robotRadarColor robot's rader color.
     * @param robotBulletColor robot's bullet color.
     * @param robotScanColor robot's scan color.
     */
    public static void setRobotColor(TeamRobot robot, Color robotBodyColor, Color robotGunColor, Color robotRadarColor, Color robotBulletColor, Color robotScanColor) {
        robot.setBodyColor(robotBodyColor);
        robot.setGunColor(robotGunColor);
        robot.setRadarColor(robotRadarColor);
        robot.setBulletColor(robotBulletColor);
        robot.setScanColor(robotScanColor);
    }
}
