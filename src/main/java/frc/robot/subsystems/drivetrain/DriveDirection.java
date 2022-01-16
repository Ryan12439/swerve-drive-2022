package frc.robot.subsystems.drivetrain;

public class DriveDirection {
    private double fwd, str, rot;

    public DriveDirection(double fwd, double str, double rot) {
        this.fwd = fwd;
        this.str = str;
        this.rot = rot;
    }

    public double GetFwd() {
        return fwd;
    }

    public double GetStr() {
        return str;
    }

    public double GetRot() {
        return rot;
    }

    public double[] Get() {
        double[] out = {fwd, str, rot};
        return out;
    }

    public void Zero() {
        double fwdOut = ((fwd * Math.cos(rot)) + (str * Math.sin(rot)));
        double strOut = ((str * Math.cos(rot)) - (fwd * Math.sin(rot)));
    
        fwd = fwdOut;
        str = strOut;
        rot = 0;
    }
}
