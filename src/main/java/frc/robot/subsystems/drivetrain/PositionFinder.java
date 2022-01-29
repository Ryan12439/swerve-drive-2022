package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.PIDController;

public class PositionFinder {
    private PIDController xController;
    private PIDController yController;
    private PIDController rController;

    private double x, y, rot;

    private int finish;

    public PositionFinder(Position in) {
        xController = new PIDController(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        yController = new PIDController(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        rController = new PIDController(ShuffleboardPID.rotP.getDouble(0), ShuffleboardPID.rotI.getDouble(0), ShuffleboardPID.rotD.getDouble(0));

        double set[] = in.getPos();
        x = set[0];
        y = set[1];
        rot = set[2];
    }
    
    public DriveDirection getDirection(Position currentPos) {
        double current[] = currentPos.getPos();

        xController.setPID(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        yController.setPID(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        rController.setPID(ShuffleboardPID.rotP.getDouble(0), ShuffleboardPID.rotI.getDouble(0), ShuffleboardPID.rotD.getDouble(0));

        double xOut = xController.calculate(current[0], x);
        double yOut = yController.calculate(current[1], y);
        double rOut = rController.calculate(current[2], rot);

        return new DriveDirection(yOut, xOut, rOut, current[2]);
    }

    public boolean isDone() {
        boolean done = 
            xController.atSetpoint() &&
            yController.atSetpoint() &&
            rController.atSetpoint();

        if (done)
            finish++;
        else
            finish = 0;
        
        return done && (finish > 20);
    }
}
