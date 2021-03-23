package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Hood;

public class SelectRange extends CommandBase {

    private int target;

    public SelectRange(int position) {
        target = position;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Selector Init");
        Hood.getInstance().setPosition(target);

        switch (target) {
            case Constants.Shooter.HOOD_POS_GREEN:
                Globals.currentRPM = Constants.Shooter.RPM_GREEN;
                Globals.currentFF = Constants.Shooter.FF_GREEN;
                System.out.println("Green");
                break;
            case Constants.Shooter.HOOD_POS_YELLOW:
                Globals.currentRPM = Constants.Shooter.RPM_YELLOW;
                Globals.currentFF = Constants.Shooter.FF_YELLOW;
                System.out.println("Yellow");
                break;
            case Constants.Shooter.HOOD_POS_BLUE:
                Globals.currentRPM = Constants.Shooter.RPM_BLUE;
                Globals.currentFF = Constants.Shooter.FF_BLUE;
                System.out.println("Blue");
                break;
            case Constants.Shooter.HOOD_POS_RED:
                Globals.currentRPM = Constants.Shooter.RPM_RED;
                Globals.currentFF = Constants.Shooter.FF_RED;
                System.out.println("Red");
                break;
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println("Selector End");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
