package frc.robot.subsystems.drivetrain;

public class DriveDirection {
    private double fwd, str;

    public DriveDirection(double fwd, double str) {
        this.fwd = fwd;
        this.str = str;
    }

    public double getFwd() {
        return fwd;
    }

    public double getStr() {
        return str;
    }

    public double[] get() {
        double[] out = {fwd, str};
        return out;
    }
}
