package frc.robot.subsystems.drivetrain;

import static frc.robot.Constants.*;

public class WheelsState {
    
    private double wsFR, wsFL, wsBR, wsBL;
    private double waFR, waFL, waBR, waBL;

    public WheelsState(DriveDirection in) {
        double fwd = in.getFwd();
        double str = in.getStr();;
        double rot = in.getRot();

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

    // public void Optimize(WheelsState in) {
        
    // }
}
