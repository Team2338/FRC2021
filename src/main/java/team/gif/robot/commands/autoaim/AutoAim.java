package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.lib.VisionSim;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class AutoAim extends CommandBase {

    private boolean targetLocked = false;
    private boolean robotHasSettled = false;
    VisionSim fakeLimelight = new VisionSim();

    public AutoAim() {
        //addRequirements(Drivetrain.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Globals.isAiming = true;
        targetLocked = false;
        fakeLimelight.reset(-20);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (targetLocked) {
            System.out.println("Target Acquired");
        } else {
            double offset = fakeLimelight.getHeading(); //TODO: get real limelight
            // Use < Limelight.getInstance().getXOffset() >
            System.out.println(offset);

            if (offset > -1.0 && offset < 1.0) {
                Drivetrain.getInstance().setVoltage(0);
                targetLocked = true;
            } else {
                fakeLimelight.turn(0.1);

                if (offset < 0) {
                    Drivetrain.drive(0, 0, 3, false);
                } else {
                    Drivetrain.drive(0, 0, -3, false);
                }
                targetLocked = false;
            }
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setVoltage(0);
        Globals.isAiming = false;
    }
}
