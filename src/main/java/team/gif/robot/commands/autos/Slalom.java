package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class Slalom extends SequentialCommandGroup {

    /**
     *     x
     *  y [] +90
     */

    public Command swerve () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(2.5 - 2.5, 2.5 - 2.5, 0), // E1
                        new Pose2dFeet().set(7.0 - 2.5, 2.5 - 2.5, 0.1),
                        new Pose2dFeet().set(7.0 - 2.5, 8.5 - 2.5, 0.1),
                        new Pose2dFeet().set(20.5 - 2.5, 8.5 - 2.5, 0.1),
                        new Pose2dFeet().set(25.0 - 2.5, 2.333 - 2.5, 0.1),
                        new Pose2dFeet().set(27.0 - 2.5, 3.0 - 2.5, 0.1),
                        new Pose2dFeet().set(27.0 - 2.5, 7.0 - 2.5, 0.1),
                        new Pose2dFeet().set(22.5 - 2.5, 7.5 - 2.5, 0.1),
                        new Pose2dFeet().set(20.5 - 2.5, 1.5 - 2.5, 0.1),
                        new Pose2dFeet().set(8.0 - 2.5, 1.5 - 2.5, 0.1),
                        new Pose2dFeet().set(6.0 - 2.5, 7.5 - 2.5, 0.1),
                        new Pose2dFeet().set(2.6 - 2.5, 7.4 - 2.5, 0.1)

                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command forward () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(2.5 - 2.5, 2.5 - 2.5, 0), // E1
                    new Pose2dFeet().set(7.0 - 2.5, 2.5 - 2.5, 0),
                    new Pose2dFeet().set(7.0 - 2.5, 8.5 - 2.5, 0),
                    new Pose2dFeet().set(20.5 - 2.5, 8.5 - 2.5, 0),
                    new Pose2dFeet().set(25.0 - 2.5, 2.333 - 2.5, 0),
                    new Pose2dFeet().set(27.0 - 2.5, 3.0 - 2.5, -90),
                    new Pose2dFeet().set(27.0 - 2.5, 7.0 - 2.5, 180),
                    new Pose2dFeet().set(22.5 - 2.5, 7.5 - 2.5, 180),
                    new Pose2dFeet().set(20.5 - 2.5, 1.5 - 2.5, 180),
                    new Pose2dFeet().set(8.0 - 2.5, 1.5 - 2.5, 180),
                    new Pose2dFeet().set(6.0 - 2.5, 7.5 - 2.5, 180),
                    new Pose2dFeet().set(2.5 - 2.5, 7.5 - 2.5, 180)

            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(27.5 - 2.5, 5.0 - 2.5, 0),
                    new Pose2dFeet().set(27.5 - 2.5, 6.0 - 2.5, 0),
                    new Pose2dFeet().set(25.0 - 2.5, 7.5 - 2.5, 0),
                    new Pose2dFeet().set(20.0 - 2.5, 2.333 - 2.5, 0),
                    new Pose2dFeet().set(8.0 - 2.5, 2.33 - 2.5, 0),
                    new Pose2dFeet().set(6.0 - 2.5, 7.5 - 2.5, 0),
                    new Pose2dFeet().set(2.5 - 2.5, 7.5 - 2.5, 0)
            ),
            RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Slalom() {
        addCommands(
                //swerve() // ~26
                forward() // 17.68
                //reverse()
        );
    }
}
