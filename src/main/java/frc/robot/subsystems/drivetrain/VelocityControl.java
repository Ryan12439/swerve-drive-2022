package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;

import static frc.robot.Constants.*;

public class VelocityControl {
    private TrapezoidProfile xProfile;
    private TrapezoidProfile yProfile;

    private TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(20, 3);
    
    private TrapezoidProfile.State xEndState;
    private TrapezoidProfile.State xCurrentState;
    
    private TrapezoidProfile.State yEndState;
    private TrapezoidProfile.State yCurrentState;

    private double timeSinceLastCheck;
    
    public VelocityControl(Position endPosition, Position current, DriveDirection currentVel) {
        double in[] = endPosition.getPos();

        double in2[] = current.getPos();
        
        xEndState = new TrapezoidProfile.State(in[0], 0);
        yEndState = new TrapezoidProfile.State(in[1], 0);

        xCurrentState = new TrapezoidProfile.State(in2[0], currentVel.getStr() * MAX_VELOCITY_METERS_PER_SECOND);
        yCurrentState = new TrapezoidProfile.State(in2[1], currentVel.getFwd() * MAX_VELOCITY_METERS_PER_SECOND);

        timeSinceLastCheck = Timer.getFPGATimestamp();
    }

    public Position getNextPos(Position current) {
        double time = (Timer.getFPGATimestamp() - timeSinceLastCheck);

        double in[] = current.getPos();

        // xCurrentState = new TrapezoidProfile.State(in[0], currentVel.getStr() * MAX_VELOCITY_METERS_PER_SECOND);
        // yCurrentState = new TrapezoidProfile.State(in[1], currentVel.getFwd() * MAX_VELOCITY_METERS_PER_SECOND);

        xProfile = new TrapezoidProfile(constraints, xEndState, xCurrentState);
        yProfile = new TrapezoidProfile(constraints, yEndState, yCurrentState);

        double x = xProfile.calculate(time).position;
        double y = yProfile.calculate(time).position;

        ShuffleboardPID.xPos.setDouble(x);
        ShuffleboardPID.yPos.setDouble(y);

        return new Position(x, y, in[2]);
    }
}
