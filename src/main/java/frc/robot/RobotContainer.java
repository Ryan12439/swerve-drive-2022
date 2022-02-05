// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.GoToCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.drivetrain.Position;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;

import static frc.robot.Constants.*;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final XboxController m_controller = new XboxController(0);

  private final GoToCommand m_autoCommand[] = {
    new GoToCommand(m_drivetrain, new Position(0, 0, 0)),
    new GoToCommand(m_drivetrain, new Position(0.5, 0.5, 0)),
    new GoToCommand(m_drivetrain, new Position(-0.5, 0.5, 0)),
    new GoToCommand(m_drivetrain, new Position(-0.5, -0.5, 0)),
    new GoToCommand(m_drivetrain, new Position(0.5, -0.5, 0)),
    new GoToCommand(m_drivetrain, new Position(0, 0, 0))
  };

  private final DefaultDriveCommand m_teleop =       
  new DefaultDriveCommand(
    m_drivetrain, 
    () -> deadband(-m_controller.getLeftY(), CONTROLLER_DEADBAND), 
    () -> deadband(m_controller.getLeftX(), CONTROLLER_DEADBAND), 
    () -> deadband(m_controller.getRightX(), CONTROLLER_DEADBAND)
  );

  private final ShuffleboardTab tab = Shuffleboard.getTab("Limelight");

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_drivetrain.setDefaultCommand(    
      m_teleop
    );

    configureButtonBindings();

    m_drivetrain.tab.addNumber("Left stick X", () -> m_controller.getLeftX()).withSize(1, 1).withPosition(4, 2);
    m_drivetrain.tab.addNumber("Left stick Y", () -> m_controller.getLeftY()).withSize(1, 1).withPosition(5, 2);
    m_drivetrain.tab.addNumber("Right stick X", () -> m_controller.getRightX()).withSize(1, 1).withPosition(4, 3);

    m_drivetrain.tab.addNumber("Drive Mode", () -> m_teleop.getMode()).withSize(1, 1).withPosition(5, 3);

    tab.addCamera("Limelight", "Limelight", "http://10.22.20.45:5800").withSize(9, 4).withPosition(0, 0);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new Button(m_controller::getBackButton)
            // No requirements because we don't need to interrupt anything
            .whenPressed(m_drivetrain::zeroGyroscope);
    
    new Button(m_controller::getLeftBumper).whenPressed(m_drivetrain::decreaseSpeed);
    new Button(m_controller::getRightBumper).whenPressed(m_drivetrain::increaseSpeed);
    new Button(m_controller::getStartButton).whenPressed(m_drivetrain::resetSpeed);
    new Button(m_controller::getAButton).whenPressed(m_drivetrain::stopRobot);

    new Button(m_controller::getYButton).whenPressed(m_teleop::changeMode);

    new Button(m_controller::getXButton).whenPressed(new GoToCommand(m_drivetrain, new Position(0, 0, 0)));
    new Button(() -> m_controller.getPOV() == 180).whenPressed(new GoToCommand(m_drivetrain, new Position(1, 1, Math.PI)));

    new Button(() -> {
      return deadband(-m_controller.getLeftY(), CONTROLLER_DEADBAND) + 
      deadband(-m_controller.getLeftX(), CONTROLLER_DEADBAND) + 
      deadband(-m_controller.getRightX(), CONTROLLER_DEADBAND) > 0;
    }).whenPressed(m_teleop);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command[] getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public Drivetrain getDrivetrain() {
    return m_drivetrain;
  }

  private static double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }
}

