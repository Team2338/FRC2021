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
     *  y [] -90
     */

    public Command swerve () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(2.5 - 2.5, 2.5 - 2.5, 0), // E1
                        new Pose2dFeet().set(6.8 - 2.5, 2.5 - 2.5, 90),
                        new Pose2dFeet().set(6.8 - 2.5, 8.5 - 2.5, 90),
                        new Pose2dFeet().set(12 - 2.5, 8.5 - 2.5, 0),
                        new Pose2dFeet().set(20.5 - 2.5, 8.5 - 2.5, -90),
                        new Pose2dFeet().set(24.5 - 2.5, 2.333 - 2.5, -45),
                        new Pose2dFeet().set(27.0 - 2.5, 2.5 - 2.5, 45),
                        //new Pose2dFeet().set(27.0 - 2.5, 6.5 - 2.5, 90),
                        new Pose2dFeet().set(27.0 - 2.5, 7.5 - 2.5, 90),
                        new Pose2dFeet().set(22.5 - 2.5, 7.5 - 2.5, -90),
                        new Pose2dFeet().set(20.5 - 2.5, 1.5 - 2.5, -90),
                        new Pose2dFeet().set(14 - 2.5, 1.5 - 2.5, 180),
                        new Pose2dFeet().set(8.0 - 2.5, 1.5 - 2.5, 90),
                        new Pose2dFeet().set(6.0 - 2.5, 7.5 - 2.5, 135),
                        new Pose2dFeet().set(2.5 - 2.5, 7.5 - 2.5, 180)

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
                    new Pose2dFeet().set(4.8 - 2.5, 2.5 - 2.5, 0),
                    new Pose2dFeet().set(8.8 - 2.5, 8.5 - 2.5, 0),
                    new Pose2dFeet().set(22 - 2.5, 8.5 - 2.5, 0),
                    new Pose2dFeet().set(25.5 - 2.5, 1.75 - 2.5, 0),
                    new Pose2dFeet().set(28 - 2.5, 1.75 - 2.5, 0),
                    new Pose2dFeet().set(28 - 2.5, 7.5 - 2.5, 0)
                    /*new Pose2dFeet().set(22.5 - 2.5, 7.5 - 2.5, 180),
                    new Pose2dFeet().set(20.5 - 2.5, 1.5 - 2.5, 180),
                    new Pose2dFeet().set(8.0 - 2.5, 1.5 - 2.5, 180),
                    new Pose2dFeet().set(6.0 - 2.5, 7.5 - 2.5, 180),
                    new Pose2dFeet().set(2.5 - 2.5, 7.5 - 2.5, 180)*/

            ),
            RobotTrajectory.getInstance().configForwardStart
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc;//.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    //new Pose2dFeet().set(27.5 - 2.5, 6.5 - 2.5, 0),
                    new Pose2dFeet().set(28 - 2.5, 7.5 - 2.5, 0),
                    new Pose2dFeet().set(22.5 - 2.5, 7.5 - 2.5, 0),
                    new Pose2dFeet().set(20.5 - 2.5, 1.5 - 2.5, 0),
                    new Pose2dFeet().set(7.5 - 2.5, 1.5 - 2.5, 0),
                    new Pose2dFeet().set(5.5 - 2.5, 7.5 - 2.5, 0),
                    new Pose2dFeet().set(2.5 - 2.5, 7.5 - 2.5, 0)
                    /*new Pose2dFeet().set(27.5 - 2.5, 5.0 - 2.5, 0),
                    new Pose2dFeet().set(27.5 - 2.5, 6.0 - 2.5, 0),
                    new Pose2dFeet().set(25.0 - 2.5, 7.5 - 2.5, 0),
                    new Pose2dFeet().set(20.0 - 2.5, 2.333 - 2.5, 0),
                    new Pose2dFeet().set(8.0 - 2.5, 2.33 - 2.5, 0),
                    new Pose2dFeet().set(6.0 - 2.5, 7.5 - 2.5, 0),
                    new Pose2dFeet().set(2.5 - 2.5, 7.5 - 2.5, 0)*/
            ),
            RobotTrajectory.getInstance().configReverseEnd
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Slalom() {
        addCommands(
                //swerve()
                forward(),
                reverse()
        );
    }
}
