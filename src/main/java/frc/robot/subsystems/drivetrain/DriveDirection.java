package frc.robot.subsystems.drivetrain;

import static frc.robot.Constants.*;

public class DriveDirection {
    private double fwd, str, rot, facing;

    public DriveDirection(double fwd, double str, double rot, double facing) {
        double max = Math.max(Math.max(fwd, str), rot);
        if (max > 1) {
            fwd /= max;
            str /= max;
            rot /= max;
        }
        
        this.fwd = fwd;
        this.str = str;
        this.rot = rot;
        this.facing = facing;
    }

    public DriveDirection(double fwd, double str, double rot, double facing, boolean metersPerSecond) {
        if (metersPerSecond) {
            fwd /= MAX_VELOCITY_METERS_PER_SECOND;
            str /= MAX_VELOCITY_METERS_PER_SECOND;
            rot /= MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
        }

        double max = Math.max(Math.max(fwd, str), rot);
        if (max > 1) {
            fwd /= max;
            str /= max;
            rot /= max;
        }

        this.fwd = fwd;
        this.str = str;
        this.rot = rot;
        this.facing = facing;
    }

    public double getFwd() {
        return fwd;
    }

    public double getStr() {
        return str;
    }

    public double getRot() {
        return rot;
    }

    public double getFacing() {
        return facing;
    }

    public double[] get() {
        double[] out = {fwd, str, rot, facing};
        return out;
    }

    public void zero() {
        double fwdOut = ((fwd * Math.cos(facing)) + (str * Math.sin(facing)));
        double strOut = ((str * Math.cos(facing)) - (fwd * Math.sin(facing)));
    
        fwd = fwdOut;
        str = strOut;
        facing = 0;
    }
}
