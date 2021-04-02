package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.commands.indexer.DeployCollector;
import team.gif.robot.commands.indexer.IndexerRun;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

/**
 *     x
 *  y [] -90
 */

public class GalacticSearchColor extends SequentialCommandGroup {

    private double xInit = 3.0 + (7.0/12.0);
    private double yInit = 5.0;

    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2dFeet().set(0.0, 0.0, 0.0),
                new Pose2dFeet().set(12.5 - xInit, 5.0 - yInit, 0.0)
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public GalacticSearchColor() {
        System.out.println("Auto: Galactic Search Selected");

        addCommands(
            new PrintCommand("Auto: Galactic Search Started"),
            //new DeployCollector().withTimeout(1.0),
            new ParallelDeadlineGroup(
                reverse(),
                new SequentialCommandGroup(
                        new DeployCollector().withTimeout(0.8),
                        new IndexerRun()
                )
            ),
            new IndexerRun().withTimeout(0.8),
            new PrintCommand("Auto: Galactic Search Ended")
        );
    }
}
