package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Hood;

public class SelectRange extends CommandBase {

    public SelectRange(Globals.Range newRange) {
        Globals.range = newRange;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        switch (Globals.range) {
            case GREEN:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_GREEN);
                break;
            case YELLOW:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_YELLOW);
                break;
            case BLUE:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_BLUE);
                break;
            case RED:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_RED);
                break;
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
