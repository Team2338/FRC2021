package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Hood;

public class SelectRange extends CommandBase {

    private int position;

    public SelectRange(int zoneID) {
        position = zoneID;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        //System.out.println("Selector Init");

        switch (position) {
            case Constants.Shooter.GREEN_ZONE:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_GREEN);
                Globals.currentRPM = Constants.Shooter.RPM_GREEN;
                Globals.currentFF = Constants.Shooter.FF_GREEN;
                SmartDashboard.putString("Zone", "Green");
                break;
            case Constants.Shooter.YELLOW_ZONE:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_YELLOW);
                Globals.currentRPM = Constants.Shooter.RPM_YELLOW;
                Globals.currentFF = Constants.Shooter.FF_YELLOW;
                SmartDashboard.putString("Zone", "Yellow");
                break;
            case Constants.Shooter.BLUE_ZONE:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_BLUE);
                Globals.currentRPM = Constants.Shooter.RPM_BLUE;
                Globals.currentFF = Constants.Shooter.FF_BLUE;
                SmartDashboard.putString("Zone", "Blue");
                break;
            case Constants.Shooter.RED_ZONE:
                Hood.getInstance().setPosition(Constants.Shooter.HOOD_POS_RED);
                Globals.currentRPM = Constants.Shooter.RPM_RED;
                Globals.currentFF = Constants.Shooter.FF_RED;
                SmartDashboard.putString("Zone", "Red");
                break;
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //System.out.println("Selector End");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
