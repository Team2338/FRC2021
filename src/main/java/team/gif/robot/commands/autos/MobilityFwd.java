package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class MobilityFwd extends SequentialCommandGroup {

    /**
     *    x
     *   [] y
     */

    public Command forward() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of( // S path
                        new Pose2d(0, 0,new Rotation2d(0)),
                        new Pose2d(1, 0,new Rotation2d(0)),
                        new Pose2d(1, 1,new Rotation2d(0))
                        //new Pose2d(0, 2,new Rotation2d(0))
                        //new Pose2d(0, 0,new Rotation2d(0))
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command reverse() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of( // S path
                        new Pose2d(0, 0,new Rotation2d(0)),
                        new Pose2d(-1, 0,new Rotation2d(0)),
                        new Pose2d(-1, -1,new Rotation2d(0))
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public MobilityFwd() {
        addCommands(forward());
        addCommands(reverse());
    }
}
