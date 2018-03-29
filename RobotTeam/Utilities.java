package nl.saxion;

import robocode.TeamRobot;

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
     * @author Olivier
     */
    public double getAngle(double targetX, double targetY) {
        double angle = Math.toDegrees(Math.atan2(targetX - robot.getX(), targetY - robot.getY()));

        if (angle < 0) {
            angle += 360.0;
        }
        return angle;
    }
}
