package frc.robot.subsystems.drivetrain;

public class Position {
    private double x, y, rot;

    public Position(double x, double y, double rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
    }

    public DriveDirection getDirection(DriveDirection currentDir, Position currentPos) {
        // TODO add get direction code
        return new DriveDirection(0, 0, 0, 0);
    }

    public void addPos(double x, double y, double rot) {
        this.x += x;
        this.y += y;
        this.rot = rot;
    }

    public double[] getPos() {
        double out[] = {x, y, rot};
        return out;
    }
}
