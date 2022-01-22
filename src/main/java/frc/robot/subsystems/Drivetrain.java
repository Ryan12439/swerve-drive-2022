// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.swervedrivespecialties.swervelib.Mk4SwerveModuleHelper;
import com.swervedrivespecialties.swervelib.SwerveModule;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.drivetrain.DriveDirection;
import frc.robot.subsystems.drivetrain.Odemetry;
import frc.robot.subsystems.drivetrain.Position;
import frc.robot.subsystems.drivetrain.WheelsState;

import static frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private final AHRS m_navx = new AHRS(SPI.Port.kMXP, (byte) 200);

  private final SwerveModule m_frontLeftModule;
  private final SwerveModule m_frontRightModule;
  private final SwerveModule m_backLeftModule;
  private final SwerveModule m_backRightModule;

  public ShuffleboardTab tab;

  private WheelsState wheelsCurrent;

  private double speedModifier = 1;

  private Position currentPos;
  private double timeSinceLastCheck;

  /** Creates a new DrivetrainSubsystem. */
  public Drivetrain() {
    wheelsCurrent = new WheelsState(
      new DriveDirection(0, 0, 0, 0)
    );

    currentPos = new Position(0, 0, 0);

    timeSinceLastCheck = Timer.getFPGATimestamp();

    tab = Shuffleboard.getTab("Drivetrain");

    m_frontLeftModule = Mk4SwerveModuleHelper.createFalcon500(
      tab.getLayout("Front Left Module", BuiltInLayouts.kList)
              .withSize(1, 4)
              .withPosition(0, 0),
      Mk4SwerveModuleHelper.GearRatio.L2,
      FL_MODULE_DM,
      FL_MODULE_SM,
      FL_MODULE_SE,
      FL_STEER_OFFSET
    );

    m_frontRightModule = Mk4SwerveModuleHelper.createFalcon500(
      tab.getLayout("Front Right Module", BuiltInLayouts.kList)
              .withSize(1, 4)
              .withPosition(1, 0),
      Mk4SwerveModuleHelper.GearRatio.L2,
      FR_MODULE_DM,
      FR_MODULE_SM,
      FR_MODULE_SE,
      FR_STEER_OFFSET
    );

    m_backLeftModule = Mk4SwerveModuleHelper.createFalcon500(
      tab.getLayout("Back Left Module", BuiltInLayouts.kList)
              .withSize(1, 4)
              .withPosition(2, 0),
      Mk4SwerveModuleHelper.GearRatio.L2,
      BL_MODULE_DM,
      BL_MODULE_SM,
      BL_MODULE_SE,
      BL_STEER_OFFSET
    );

    m_backRightModule = Mk4SwerveModuleHelper.createFalcon500(
      tab.getLayout("Back Right Module", BuiltInLayouts.kList)
              .withSize(1, 4)
              .withPosition(3, 0),
      Mk4SwerveModuleHelper.GearRatio.L2,
      BR_MODULE_DM,
      BR_MODULE_SM,
      BR_MODULE_SE,
      BR_STEER_OFFSET
    );

    tab.addNumber("X", () -> currentPos.getPos()[0]);
    tab.addNumber("Y", () -> currentPos.getPos()[1]);
  }

  public double getGyro() {
    return Math.toRadians(m_navx.getAngle());
  }

  public void update(WheelsState in) {
    wheelsCurrent = in;
  }

  public WheelsState getPos() {
    double frA = m_frontRightModule.getSteerAngle();
    double flA = m_frontLeftModule.getSteerAngle();
    double brA = m_backRightModule.getSteerAngle();
    double blA = m_backLeftModule.getSteerAngle();

    double frS = m_frontRightModule.getDriveVelocity() / MAX_VELOCITY_METERS_PER_SECOND;
    double flS = m_frontLeftModule.getDriveVelocity() / MAX_VELOCITY_METERS_PER_SECOND;
    double brS = m_backRightModule.getDriveVelocity() / MAX_VELOCITY_METERS_PER_SECOND;
    double blS = m_backLeftModule.getDriveVelocity() / MAX_VELOCITY_METERS_PER_SECOND;

    return new WheelsState(frA, flA, brA, blA, frS, flS, brS, blS);
  }

  public void updateOdo() {
    double time = Timer.getFPGATimestamp() - timeSinceLastCheck;
    timeSinceLastCheck = Timer.getFPGATimestamp();
    DriveDirection in = Odemetry.getOdemetry(getPos(), getGyro());

    in.zero();

    currentPos.addPos(in.getStr() * MAX_VELOCITY_METERS_PER_SECOND * time, in.getFwd() * MAX_VELOCITY_METERS_PER_SECOND * time, getGyro());
  }

  public void zeroGyroscope() {
    m_navx.zeroYaw();
    currentPos = new Position(0, 0, 0);
  }

  public void decreaseSpeed() {
    speedModifier /= 1.1;
    if (speedModifier < 0.1)
      speedModifier = 0.1;
  }

  public void increaseSpeed() {
    speedModifier *= 1.1;
    if (speedModifier > 1)
      speedModifier = 1;
  }

  public void resetSpeed() {
    speedModifier = 1;
  }

  public void stopRobot() {
    speedModifier = 0;
  }

  @Override
  public void periodic() {
    double[] speeds = wheelsCurrent.getSpeeds();
    double[] angles = wheelsCurrent.getAngles();

    m_frontRightModule.set(speeds[0] * MAX_VOLTAGE * speedModifier, angles[0]);
    m_frontLeftModule.set(speeds[1] * MAX_VOLTAGE * speedModifier, angles[1]);
    m_backRightModule.set(speeds[2] * MAX_VOLTAGE * speedModifier, angles[2]);
    m_backLeftModule.set(speeds[3] * MAX_VOLTAGE * speedModifier, angles[3]);

    updateOdo();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
