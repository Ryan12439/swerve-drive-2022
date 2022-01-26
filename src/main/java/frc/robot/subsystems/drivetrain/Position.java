package frc.robot.subsystems.drivetrain;

public class Position {
    private double x, y, rot;

    public Position(double x, double y, double rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
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