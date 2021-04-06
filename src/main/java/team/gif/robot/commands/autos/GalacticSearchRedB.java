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

public class GalacticSearchRedB extends SequentialCommandGroup {

    public Command b3 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2dFeet().set(0.0, 0.0, 0.0),
                new Pose2dFeet().set(-1, 11.5, 105)
            ),
            RobotTrajectory.getInstance().configForwardStart
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public Command finish () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(-1, 11.5, 105),
                        new Pose2dFeet().set(-1, -13, 105)

                ),
                RobotTrajectory.getInstance().configReverseEnd
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public GalacticSearchRedB() {
        System.out.println("Auto: Galactic Search Blue B Selected");

        addCommands(
            new PrintCommand("Auto: Galactic Search Blue B Started"),
            //new IntakeDown(),
            new ParallelDeadlineGroup(
                b3(),
                new IndexerRun()),
            new IndexerRun().withTimeout(0.5),
            finish(),
            new PrintCommand("Auto: Galactic Search Blue B Ended")
        );
    }
}
