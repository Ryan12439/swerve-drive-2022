package frc.robot.subsystems.drivetrain;

public class Position {
    private double x, y, rot;

    public Position(double x, double y, double rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
    }

    public DriveDirection getDirection(Position currentPos, double gyro) {
        double max_speed = 0.1;

        double current[] = currentPos.getPos();

        double max = Math.max(Math.max(current[0], current[1]), Math.max(x, y));
        double xOut = (x - current[0]) / max * max_speed;
        double yOut = (y - current[1]) / max * max_speed;

        return new DriveDirection(yOut, xOut, 0, gyro);
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