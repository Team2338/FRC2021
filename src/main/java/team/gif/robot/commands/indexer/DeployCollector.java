package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class DeployCollector extends CommandBase {

    public DeployCollector() {
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Indexer.getInstance().setSpeedCollector(-0.5);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Indexer.getInstance().setSpeedCollector(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
