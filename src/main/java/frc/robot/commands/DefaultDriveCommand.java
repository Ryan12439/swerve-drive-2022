// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.drivetrain.DriveDirection;
import frc.robot.subsystems.drivetrain.WheelsState;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DefaultDriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  private final DoubleSupplier fwdSupplier;
  private final DoubleSupplier strSupplier;
  private final DoubleSupplier rotSupplier;
  private DriveDirection driveDir;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DefaultDriveCommand(Drivetrain drivetrain, DoubleSupplier fwd, DoubleSupplier str, DoubleSupplier rot) {
    this.drivetrain = drivetrain;
    fwdSupplier = fwd;
    strSupplier = str;
    rotSupplier = rot;
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveDir = new DriveDirection(
      fwdSupplier.getAsDouble(), 
      strSupplier.getAsDouble(), 
      rotSupplier.getAsDouble(), 
      drivetrain.getGyro()
    );

    driveDir.zero();

    drivetrain.update(
      new WheelsState(driveDir)
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
