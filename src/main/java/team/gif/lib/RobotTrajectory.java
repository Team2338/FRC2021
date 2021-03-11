package team.gif.lib;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;

public class RobotTrajectory {

    public RobotTrajectory(){}

    private static RobotTrajectory instance = null;

    public static RobotTrajectory getInstance() {
        if (instance == null) {
            instance = new RobotTrajectory();
        }
        return instance;
    }

    /**
     * Begin Configs
     * */

    public TrajectoryConfig configForward = new TrajectoryConfig(
            Constants.Drivetrain.kMaxSpeedMetersPerSecond,
            Constants.Drivetrain.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.Drivetrain.kDriveKinematics)
            .setReversed(false);
            // Apply the voltage constraint
            //.addConstraint(autoVoltageConstraint)
            //.addConstraint( new CentripetalAccelerationConstraint(1));

    public TrajectoryConfig configReverse = new TrajectoryConfig(
            Constants.Drivetrain.kMaxSpeedMetersPerSecond,
            Constants.Drivetrain.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.Drivetrain.kDriveKinematics)
            .setReversed(true);
    // Apply the voltage constraint
    //.addConstraint(autoVoltageConstraint)
    //.addConstraint( new CentripetalAccelerationConstraint(1));

    /**
     * End Configs
     * */

    public SwerveControllerCommand createSwerveControllerCommand(Trajectory trajectory) {
        var thetaController =
                new ProfiledPIDController(
                        Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
                new SwerveControllerCommand(
                        trajectory,
                        Drivetrain.getInstance()::getPose, // Functional interface to feed supplier
                        Constants.Drivetrain.kDriveKinematics,

                        // Position controllers
                        new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                        new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                        thetaController,
                        Drivetrain.getInstance()::setModuleStates,
                        Drivetrain.getInstance());

        // Reset odometry to the starting pose of the trajectory.
        //Drivetrain.getInstance().resetOdometry(trajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return swerveControllerCommand;
    }
}
