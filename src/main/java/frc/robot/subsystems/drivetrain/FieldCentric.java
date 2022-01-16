package frc.robot.subsystems.drivetrain;

public class FieldCentric {
    public static DriveDirection getMovement(double str, double fwd, double rot) {
        double fwdOut = ((fwd * Math.cos(rot)) + (str * Math.sin(rot)));
        double strOut = ((str * Math.cos(rot)) - (fwd * Math.sin(rot)));

        return new DriveDirection(fwdOut, strOut);
    }
}
