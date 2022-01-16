// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivetrain.DriveDirection;

public class Drivetrain extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public static DriveDirection getMovementFieldCentric(DriveDirection in) {
    double fwd = in.getFwd();
    double str = in.getStr();
    double rot = in.getRot();
    
    double fwdOut = ((fwd * Math.cos(rot)) + (str * Math.sin(rot)));
    double strOut = ((str * Math.cos(rot)) - (fwd * Math.sin(rot)));

    return new DriveDirection(fwdOut, strOut, 0);
  }
}
