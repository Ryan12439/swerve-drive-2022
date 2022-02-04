package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.PIDController;

public class PositionFinder {
    private PIDController xController;
    private PIDController yController;
    private PIDController rController;

    private double rot;

    private VelocityControl vControl;

    public PositionFinder(Position in, Position currentPos, DriveDirection currentVel) {
        xController = new PIDController(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        yController = new PIDController(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        rController = new PIDController(ShuffleboardPID.rotP.getDouble(0), ShuffleboardPID.rotI.getDouble(0), ShuffleboardPID.rotD.getDouble(0));

        rController.enableContinuousInput(-Math.PI, Math.PI);

        double set[] = in.getPos();
        rot = set[2];

        vControl = new VelocityControl(in, currentPos, currentVel);
    }
    
    public DriveDirection getDirection(Position currentPos, DriveDirection currentVel) {
        double current[] = currentPos.getPos();

        xController.setPID(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        yController.setPID(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        rController.setPID(ShuffleboardPID.rotP.getDouble(0), ShuffleboardPID.rotI.getDouble(0), ShuffleboardPID.rotD.getDouble(0));

        Position goTo = vControl.getNextPos(currentPos, currentVel);

        double next[] = goTo.getPos();

        double xOut = xController.calculate(current[0], next[0]);
        double yOut = yController.calculate(current[1], next[1]);
        double rOut = rController.calculate(current[2], rot);

        return new DriveDirection(yOut, xOut, rOut, current[2]);
    }

    public boolean isDone() {
        boolean done = 
            xController.atSetpoint() &&
            yController.atSetpoint() &&
            rController.atSetpoint();

        return done;
    }
}
