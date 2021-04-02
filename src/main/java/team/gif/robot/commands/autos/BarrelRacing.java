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

public class BarrelRacing extends SequentialCommandGroup {

    private final double xInit = 2.5;
    private final double yInit = 7.5;

    /**
     *     x
     *  y [] +90
     */

    public Command swerve () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(0, 0, 0),
                        new Pose2dFeet().set(12.5 - xInit, 7.5 - yInit, 0),
                        new Pose2dFeet().set(15 - xInit, 2.5 - yInit, -135),
                        new Pose2dFeet().set(8 - xInit, 2.5 - yInit, 135),
                        new Pose2dFeet().set(9 - xInit, 8 - yInit, 0),
                        new Pose2dFeet().set(20 - xInit, 8 - yInit, 0),
                        new Pose2dFeet().set(23.5 - xInit, 12 - yInit, 135),
                        new Pose2dFeet().set(14 - xInit, 12 - yInit, -90), //8
                        new Pose2dFeet().set(25 - xInit, 1 - yInit, 0),
                        new Pose2dFeet().set(30 - xInit, 5 - yInit, 90),
                        new Pose2dFeet().set(25 - xInit, 9 - yInit, 180),
                        new Pose2dFeet().set(-4, 1, 180),
                        new Pose2dFeet().set(-4.5, 1, 90),
                        new Pose2dFeet().set(-5, 1, 0)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command forward1 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(0, 0, 0),
                    new Pose2dFeet().set(15 - xInit, 7.5 - yInit, 0),
                    new Pose2dFeet().set(15 - xInit, 2.5 - yInit, 0)
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command reverse1 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(15 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(9 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(9 - xInit, 7.5 - yInit, 0)
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command forward2 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(9 - xInit, 7.5 - yInit, 0),
                        new Pose2dFeet().set(22.5 - xInit, 7.5 - yInit, 0),
                        new Pose2dFeet().set(22.5 - xInit, 12.5 - yInit, 0)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command reverse2 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(22.5 - xInit, 12.5 - yInit, 0),
                        new Pose2dFeet().set(13 - xInit, 12.5 - yInit, 0)
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command forward3 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(13 - xInit, 12.5 - yInit, 0),
                        new Pose2dFeet().set(20 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(27.5 - xInit, 2.5 - yInit, 0),
                        new Pose2dFeet().set(27.5 - xInit, 8 - yInit, 0)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command reverseFinal () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(27.5 - xInit, 8 - yInit, 0),
                        new Pose2dFeet().set(0, 0, 0)
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public BarrelRacing() {
        addCommands(
                swerve()
                /*forward1(),
                reverse1(),
                forward2(),
                reverse2(),
                forward3(),
                reverseFinal()*/
        );
    }
}
