package nl.saxion.Sprint2Team;

import robocode.Condition;
import robocode.TeamRobot;
import java.awt.*;

public class Utilities {

    private TeamRobot robot;

    public Utilities(TeamRobot robot){
        this.robot = robot;
    }

    /**
     * @author Olivier
     */
    public int getID() {
        String name = robot.getName();
        if (!name.contains("(")) {
            return 1;
        } else {
            String requiredString = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
            return Integer.parseInt(requiredString);
        }
    }

    /**
     * @author Olivier
     */
    public int getID(String name) {
        if (!name.contains("(")) {
            return 1;
        } else {
            String requiredString = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
            return Integer.parseInt(requiredString);
        }
    }

    /**
     * @author William
     */
    public void setDefenderColor(Color robotBodyColor, Color robotGunColor, Color robotRadarColor, Color robotBulletColor, Color robotScanColor) {
        robot.setBodyColor(robotBodyColor);
        robot.setGunColor(robotGunColor);
        robot.setRadarColor(robotRadarColor);
        robot.setBulletColor(robotBulletColor);
        robot.setScanColor(robotScanColor);
    }

    /**
     * @author Olivier
     */
    public double getAngle(double targetX, double targetY) {
        double angle = Math.toDegrees(Math.atan2(targetX - robot.getX(), targetY - robot.getY()));

        if (angle < 0) {
            angle += 360.0;
        }
        return angle;
    }
    /**
     * @author Olivier
     */
    public double getDistanceToPoint(double targetX, double targetY){
        double distance = Math.hypot(robot.getX()-targetX,robot.getY()-targetY);
        return distance;
    }

    /**
     * @author Olivier
     */
    public Point getClosestPoint(double targetX1, double targetY1, double targetX2, double targetY2){
        int distanceFromTarget1 = (int) getDistanceToPoint(targetX1, targetY1);
        int distanceFromTarget2 = (int) getDistanceToPoint(targetX2, targetY2);

        Point p = null;

        if(distanceFromTarget1 < distanceFromTarget2){
            p = new Point((int)targetX1,(int)targetY1);
        }else if(distanceFromTarget1 > distanceFromTarget2){
            p = new Point((int)targetX2,(int)targetY2);
        }
        return p;
    }

    /**
     * @author Olivier
     */
    public Condition hasDegree(double degree){
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
}
