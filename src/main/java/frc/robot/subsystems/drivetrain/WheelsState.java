package frc.robot.subsystems.drivetrain;

import static frc.robot.Constants.*;

public class WheelsState {
    
    private double wsFR, wsFL, wsBR, wsBL;
    private double waFR, waFL, waBR, waBL;

    /**
     * Creates a WheelsState object containing the positions and speeds of each wheel from a DriveDirection object.
     * 
     * @param in DriveDirection object containing the movement and rotation that you want
     */
    public WheelsState(DriveDirection in) {
        double fwd = in.GetFwd();
        double str = in.GetStr();;
        double rot = in.GetRot();

        double a = str - rot * (DRIVETRAIN_LENGTH_METERS / DRIVETRAIN_DIAMETER);
        double b = str + rot * (DRIVETRAIN_LENGTH_METERS / DRIVETRAIN_DIAMETER);
        double c = fwd - rot * (DRIVETRAIN_WIDTH_METERS / DRIVETRAIN_DIAMETER);
        double d = fwd + rot * (DRIVETRAIN_WIDTH_METERS / DRIVETRAIN_DIAMETER);

        // Get wheel speeds
        wsFR = Math.sqrt((b * b) + (c * c));
        wsFL = Math.sqrt((b * b) + (d * d));
        wsBR = Math.sqrt((a * a) + (c * c));
        wsBL = Math.sqrt((a * a) + (d * d));

        // Get wheel angles
        waFR = Math.atan2(b, c);
        waFL = Math.atan2(b, d);
        waBR = Math.atan2(a, c);
        waBL = Math.atan2(a, d);

        // Normalize values to 1 if any are greater than 1
        double wsMAX = Math.max(Math.max(wsFR, wsFL),Math.max(wsBR, wsBL));
        if (wsMAX > 1) {
            wsFR /= wsMAX;
            wsFL /= wsMAX;
            wsBR /= wsMAX;
            wsBL /= wsMAX;
        }
    }

    /**
     * Gets the angles of a WheelsState object.
     * 
     * @return Double List, in order of FR, FL, BR, BL
     */
    public double[] GetAngles() {
        double[] out = {waFR, waFL, waBR, waBL};
        return out;
    }

    /**
     * Gets the speeds of a WheelsState object.
     * 
     * @return Double List, in order of FR, FL, BR, BL
     */
    public double[] GetSpeeds() {
        double[] out= {wsFR, wsFL, wsBR, wsBL};
        return out;
    }

    /**
     * Optimizes wheel positioning baised on current wheel postion, to minimize the amount of motion.
     * 
     * @param in WheelsState object containing current position.
     */
    public void Optimize(WheelsState in) {
        double current = in.GetAngles()[0];
        if (Math.abs(waFR - current) > 180) {
            waFR = Math.abs(waFR - 180);
            wsFR = -wsFR;
        }

        current = in.GetAngles()[1];
        if (Math.abs(waFL - current) > 180) {
            waFL = Math.abs(waFL - 180);
            wsFL = -wsFL;
        }

        current = in.GetAngles()[2];
        if (Math.abs(waBR - current) > 180) {
            waBR = Math.abs(waBR - 180);
            wsBR = -wsBR;
        }

        current = in.GetAngles()[3];
        if (Math.abs(waBL - current) > 180) {
            waBL = Math.abs(waBL - 180);
            wsBL = -wsBL;
        }
    }

    public double[] GetFR() {
        double[] out = {wsFR, waFR};
        return out;
    }

    public double[] GetFL() {
        double[] out = {wsFL, waFL};
        return out;
    }

    public double[] GetBR() {
        double[] out = {wsBR, waBR};
        return out;
    }

    public double[] GetBL() {
        double[] out = {wsBL, waBL};
        return out;
    }
}

