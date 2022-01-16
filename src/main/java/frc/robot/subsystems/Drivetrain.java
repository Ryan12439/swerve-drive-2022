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

import frc.robot.subsystems.drivetrain.DriveDirection;
import frc.robot.subsystems.drivetrain.WheelsState;

import static frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private final AHRS m_navx = new AHRS(SPI.Port.kMXP, (byte) 200);

  private final SwerveModule m_frontLeftModule;
  private final SwerveModule m_frontRightModule;
  private final SwerveModule m_backLeftModule;
  private final SwerveModule m_backRightModule;

  private WheelsState wheelsCurrent;

  /** Creates a new DrivetrainSubsystem. */
  public Drivetrain() {
    wheelsCurrent = new WheelsState(
      new DriveDirection(0, 0, 0, 0)
    );

    ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

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
  }

  public double getGyro() {
    return (m_navx.getAngle());
  }

  public void update(WheelsState in) {
    wheelsCurrent = in;
  }

  @Override
  public void periodic() {
    double[] speeds = wheelsCurrent.getSpeeds();
    double[] angles = wheelsCurrent.getAngles();

    m_frontRightModule.set(speeds[0] * MAX_VOLTAGE, angles[0]);
    m_frontLeftModule.set(speeds[1] * MAX_VOLTAGE, angles[1]);
    m_backRightModule.set(speeds[2] * MAX_VOLTAGE, angles[2]);
    m_backLeftModule.set(speeds[3] * MAX_VOLTAGE, angles[3]);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
