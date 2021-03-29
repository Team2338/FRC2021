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

public class Bounce extends SequentialCommandGroup {

    private final double xInit = 3.6;
    private final double yInit = 7.55;

    /**
     *     x
     *  y [] +90
     */

    public Command swerve () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(0, 0, 0),
                        new Pose2dFeet().set(7.0 - xInit, 7.5 - yInit, 0),
                        new Pose2dFeet().set(7.0 - xInit, 12.5 - yInit, 0),
                        new Pose2dFeet().set(7.5 - xInit, 7.5 - yInit, 0),
                        new Pose2dFeet().set(12.5 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(14.5 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(16.25 - xInit, 8 - yInit, 90),
                        new Pose2dFeet().set(16.25 - xInit, 13 - yInit, 0),
                        new Pose2dFeet().set(16.25 - xInit, 8 - yInit, -90),
                        new Pose2dFeet().set(16.25 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(22.5 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(23.25 - xInit, 13 - yInit, 0),
                        new Pose2dFeet().set(23.25 - xInit, 8 - yInit, 0),
                        new Pose2dFeet().set(27.5 - xInit, 8 - yInit, 0)
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
                    new Pose2dFeet().set(0, 0, 0),
                    new Pose2dFeet().set(7.0- xInit, 7.5 - yInit, 0),
                    new Pose2dFeet().set(7.0 - xInit, 12.5 - yInit, 0),
                    new Pose2dFeet().set(7.5 - xInit, 7.5 - yInit, 0),
                    new Pose2dFeet().set(12.5 - xInit, 2.5 - yInit, 0),
                    new Pose2dFeet().set(14.5 - xInit, 2.5 - yInit, 0),
                    new Pose2dFeet().set(16.25 - xInit, 13 - yInit, 0)
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command reverseMid () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(16.25 - xInit, 13 - yInit, 0),
                        new Pose2dFeet().set(16.25 - xInit, 2.5 - yInit, 0) //15.0
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command forwardFinal () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(16.25 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(22.5 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(23.25 - xInit, 13 - yInit, 0),
                        new Pose2dFeet().set(23.25 - xInit, 8 - yInit, 0),
                        new Pose2dFeet().set(27.5 - xInit, 8 - yInit, 0)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command gooder1 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(0, 0, 0),
                        new Pose2dFeet().set(7.0 - xInit, 12.5 - yInit, 90)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command gooder2 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(7.0 - xInit, 12.5 - yInit, 90),
                        new Pose2dFeet().set(7.5 - xInit, 9 - yInit, -45),
                        new Pose2dFeet().set(9 - xInit, 5 - yInit, -90),
                        new Pose2dFeet().set(12.5 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(15 - xInit, 5 - yInit, 90),
                        new Pose2dFeet().set(15 - xInit, 12.5 - yInit, 90)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command gooder3 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(15 - xInit, 12.5 - yInit, 90),
                        new Pose2dFeet().set(15 - xInit, 12 - yInit, 0),
                        new Pose2dFeet().set(15 - xInit, 5 - yInit, -90),
                        new Pose2dFeet().set(19 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(23 - xInit, 5 - yInit, 90),
                        new Pose2dFeet().set(23 - xInit, 12.5 - yInit, 90)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Bounce() {
        addCommands(
                swerve()
                /*forward(),
                reverseMid(),
                forwardFinal()*/
                /*gooder1(),
                gooder2(),
                gooder3()*/
        );
    }
}
