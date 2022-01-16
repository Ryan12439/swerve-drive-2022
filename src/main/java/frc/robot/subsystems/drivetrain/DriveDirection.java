package frc.robot.subsystems.drivetrain;

public class DriveDirection {
    private double fwd, str, rot;

    public DriveDirection(double fwd, double str, double rot) {
        this.fwd = fwd;
        this.str = str;
        this.rot = rot;
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

    public double[] get() {
        double[] out = {fwd, str, rot};
        return out;
    }
}
