// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ColorSensor;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ColorSensorCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ColorSensor m_colorSensor;
  private final ShuffleboardTab tab;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ColorSensorCommand(ColorSensor subsystem) {
    m_colorSensor = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);

    tab = Shuffleboard.getTab("Color Sensor");

    tab.addNumber("Red", () -> m_colorSensor.getColor().red).withSize(1, 1).withPosition(0, 0);
    tab.addNumber("Green", () -> m_colorSensor.getColor().green).withSize(1, 1).withPosition(1, 0);
    tab.addNumber("Blue", () -> m_colorSensor.getColor().blue).withSize(1, 1).withPosition(2, 0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
