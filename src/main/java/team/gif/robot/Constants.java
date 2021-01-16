// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class Drivetrain {
        public static final boolean kFrontLeftTurningEncoderReversed = false;
        public static final boolean kRearLeftTurningEncoderReversed = true;
        public static final boolean kFrontRightTurningEncoderReversed = false;
        public static final boolean kRearRightTurningEncoderReversed = true;

        public static final boolean kFrontLeftDriveEncoderReversed = false;
        public static final boolean kRearLeftDriveEncoderReversed = true;
        public static final boolean kFrontRightDriveEncoderReversed = false;
        public static final boolean kRearRightDriveEncoderReversed = true;

        public static final double kTrackWidth = 0.5;
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = 0.7;
        // Distance between front and back wheels on robot
        public static final SwerveDriveKinematics kDriveKinematics =
                new SwerveDriveKinematics(
                        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
                        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

        public static final boolean kGyroReversed = false;

        // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
        // These characterization values MUST be determined either experimentally or theoretically
        // for *your* robot's drive.
        // The RobotPy Characterization Toolsuite provides a convenient tool for obtaining these
        // values for your robot.
        public static final double ksVolts = 1;
        public static final double kvVoltSecondsPerMeter = 0.8;
        public static final double kaVoltSecondsSquaredPerMeter = 0.15;

        public static final double kMaxSpeedMetersPerSecond = 3;
    }

    public static class ModuleConstants {
        public static final double kMaxModuleAngularSpeedRadiansPerSecond = 2 * Math.PI;
        public static final double kMaxModuleAngularAccelerationRadiansPerSecondSquared = 2 * Math.PI;

        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterMeters = 0.15;
        public static final double kDriveEncoderDistancePerPulse =
                // Assumes the encoders are directly mounted on the wheel shafts
                (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

        public static final double kTurningEncoderDistancePerPulse =
                // Assumes the encoders are on a 1:1 reduction with the module shaft.
                (2 * Math.PI) / (double) kEncoderCPR;

        public static final double kPModuleTurningController = 1;

        public static final double kPModuleDriveController = 1;
    }
}