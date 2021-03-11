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

    public Command forward () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(0, 0, 0),
                    new Pose2dFeet().set(15 - yInit, 7.5 - yInit, 0),
                    new Pose2dFeet().set(15 - yInit, 2.5 - yInit, 0),
                    new Pose2dFeet().set(10 - yInit, 2.5 - yInit, 0),
                    new Pose2dFeet().set(10 - yInit, 7.5 - yInit, 0),
                    new Pose2dFeet().set(15 - yInit, 7.5 - yInit, 0)
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        SwerveControllerCommand scc = RobotTrajectory.getInstance().createSwerveControllerCommand(trajectory);
        // Run path following command, then stop at the end.
        return scc.andThen(() -> Drivetrain.getInstance().setVoltage(0));
    }

    public BarrelRacing() {
        addCommands(
                forward()
        );
    }
}
