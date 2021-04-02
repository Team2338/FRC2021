package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.commands.indexer.IndexerRun;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

/**
 *     x
 *  y [] -90
 */

public class GalacticSearchBlueA extends SequentialCommandGroup {

    public Command e6 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2dFeet().set(0.0, 0.0, 0.0),
                new Pose2dFeet().set(0.25, -0.5, -105),
                new Pose2dFeet().set(0.25, -2.5, -105)
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command b7 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(0.25, -2.5, -105),
                        new Pose2dFeet().set(0, -2.75, 0),
                        new Pose2dFeet().set(-0.25, -2.5, 90),
                        new Pose2dFeet().set(4.0, 5.0, 90.0)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command c9 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(4.0, 5.0, 90.0),
                        new Pose2dFeet().set(7.5,3,0)
                ),
                RobotTrajectory.getInstance().configForwardStart
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc;//.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command finish () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(7.5,3,0),
                        new Pose2dFeet().set(15, 3, 0)
                ),
                RobotTrajectory.getInstance().configForwardEnd
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public GalacticSearchBlueA() {
        System.out.println("Auto: Galactic Search Blue B Selected");

        addCommands(
            new PrintCommand("Auto: Galactic Search Blue B Started"),
            //new IntakeDown(),
            new ParallelDeadlineGroup(
                e6(),
                new IndexerRun()),
            new ParallelDeadlineGroup(
                b7(),
                new IndexerRun()),
            new ParallelDeadlineGroup(
                c9(),
                new IndexerRun()),
            new ParallelDeadlineGroup(
                finish(),
                new IndexerRun()),
            new PrintCommand("Auto: Galactic Search Blue B Ended")
        );
    }
}
