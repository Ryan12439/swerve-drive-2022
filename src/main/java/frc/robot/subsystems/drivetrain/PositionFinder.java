package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class PositionFinder {
    private PIDController xController;
    private PIDController yController;
    private PIDController rController;

    private double x, y, rot;

    private int finish;

    private ShuffleboardTab tab = Shuffleboard.getTab("PID");

    private NetworkTableEntry axisP = tab.add("axisP", 0.25)
        .withSize(1, 1)
        .withPosition(0, 0)
        .getEntry();
    private NetworkTableEntry axisI = tab.add("axisI", 0)
        .withSize(1, 1)
        .withPosition(0, 1)
        .getEntry();
    private NetworkTableEntry axisD = tab.add("axisD", 0)
        .withSize(1, 1)
        .withPosition(0, 2)
        .getEntry();

    private NetworkTableEntry rotP = tab.add("rotP", 0.25)
        .withSize(1, 1)
        .withPosition(1, 0)
        .getEntry();
    private NetworkTableEntry rotI = tab.add("rotI", 0)
        .withSize(1, 1)
        .withPosition(1, 1)
        .getEntry();
    private NetworkTableEntry rotD = tab.add("rotD", 0)
        .withSize(1, 1)
        .withPosition(1, 2)
        .getEntry();

    public PositionFinder(Position in) {
        xController = new PIDController(axisP.getDouble(0), axisI.getDouble(0), axisD.getDouble(0));
        yController = new PIDController(axisP.getDouble(0), axisI.getDouble(0), axisD.getDouble(0));
        rController = new PIDController(rotP.getDouble(0), rotI.getDouble(0), rotD.getDouble(0));

        double set[] = in.getPos();
        x = set[0];
        y = set[1];
        rot = set[2];
    }
    
    public DriveDirection getDirection(Position currentPos) {
        double current[] = currentPos.getPos();

        xController.setPID(axisP.getDouble(0), axisI.getDouble(0), axisD.getDouble(0));
        yController.setPID(axisP.getDouble(0), axisI.getDouble(0), axisD.getDouble(0));
        rController.setPID(rotP.getDouble(0), rotI.getDouble(0), rotD.getDouble(0));

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
