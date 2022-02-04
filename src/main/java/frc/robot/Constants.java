// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    /**
     * The left-to-right distance between the drivetrain wheels
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_WIDTH_METERS = Units.inchesToMeters(9.25)*2; // FIXME Measure and set trackwidth
    /**
     * The front-to-back distance between the drivetrain wheels.
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_LENGTH_METERS = Units.inchesToMeters(9.25)*2; // FIXME Measure and set wheelbase

    // Calculate Ratio
    public static final double DRIVETRAIN_DIAMETER = Math.sqrt((DRIVETRAIN_LENGTH_METERS * DRIVETRAIN_LENGTH_METERS) + (DRIVETRAIN_WIDTH_METERS * DRIVETRAIN_WIDTH_METERS));

    // CAN Bus IDs

    // Front Left Drive Motor
    public static final int FL_MODULE_DM = 13;
    // Front Left Steer Motor
    public static final int FL_MODULE_SM = 14;
    // Front Left Steer Encoder
    public static final int FL_MODULE_SE = 3;

    // Front Right Drive Motor
    public static final int FR_MODULE_DM = 15;
    // Front Right Steer Motor
    public static final int FR_MODULE_SM = 16;
    // Front Right Steer Encoder
    public static final int FR_MODULE_SE = 4;

    // Back Left Drive Motor
    public static final int BL_MODULE_DM = 11;
    // Back Left Steer Motor
    public static final int BL_MODULE_SM = 12;
    // Back Left Steer Encoder
    public static final int BL_MODULE_SE = 2;

    // Back Right Drive Motor
    public static final int BR_MODULE_DM = 17;
    // Back Right Steer Motor
    public static final int BR_MODULE_SM = 18;
    // Back Right Steer Encoder
    public static final int BR_MODULE_SE = 5;

    

    // Offsets
    public static final double FL_STEER_OFFSET = -Math.toRadians(71.279297+0.25);
    public static final double FR_STEER_OFFSET = -Math.toRadians(7.294922+1);
    public static final double BL_STEER_OFFSET = -Math.toRadians(290.214844+0.25);
    public static final double BR_STEER_OFFSET = -Math.toRadians(225.263672+0.75);

    // Max
    public static final double MAX_VOLTAGE = 12.0;
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0 *
        SdsModuleConfigurations.MK4_L2.getDriveReduction() *
        SdsModuleConfigurations.MK4_L2.getWheelDiameter() * Math.PI;
    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
        Math.hypot(DRIVETRAIN_WIDTH_METERS / 2.0, DRIVETRAIN_LENGTH_METERS / 2.0);


    // Controller settings
    public static final double CONTROLLER_DEADBAND = 0.15;

    // Max Decel
    public static final double MAX_DECELERATION = 0.99;

    // Wheel Diameter
    public static final double WHEEL_DIAMETER = SdsModuleConfigurations.MK4_L2.getWheelDiameter();


    // Max Drive Constraints
    public static final boolean CONSTRAINT_ENABLED = false;
    public static final double X_CONSTRAINT_OFFSET = 1;
    public static final double Y_CONSTRAINT_OFFSET = 2;


}
