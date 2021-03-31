package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.lib.VisionSim;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Limelight;
import team.gif.robot.subsystems.drivers.Pigeon;

public class AutoAim extends CommandBase {

    private boolean targetLocked = false;
    private boolean robotHasSettled = false;
    private boolean isRunningFlywheel;
    private boolean accuracyMode;
    double offset;
    VisionSim fakeLimelight = new VisionSim();

    public AutoAim(boolean accuracy) {
        accuracyMode = accuracy;
        //addRequirements(Drivetrain.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Globals.isAiming = true;
        targetLocked = false;
        isRunningFlywheel = false;
        //fakeLimelight.reset(-20);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (targetLocked) {
            //System.out.println("Target Acquired");

            offset = Limelight.getInstance().getXOffset();

            if (offset > -1.0 && offset < 1.0) {
                Drivetrain.getInstance().setVoltage(0);
                // fire
                System.out.println("* Fire *");

                if (!accuracyMode) {
                    if (Math.abs(Globals.currentRPM - Shooter.getInstance().getVelocity()) < 35) {
                        Indexer.getInstance().setSpeedIndexer(0.5);
                        Indexer.getInstance().setSpeedIndexerStopper(1.0);
                        Indexer.getInstance().setSpeedSingulator(0.5);
                        System.out.println("*** Loading ***");
                    } else {
                        Indexer.getInstance().setSpeedIndexer(0);
                        Indexer.getInstance().setSpeedIndexerStopper(0);
                        Indexer.getInstance().setSpeedSingulator(0);
                        System.out.println("### Pause ###");
                    }
                }

            } else {
                System.out.println("Re-Adjusting: " + offset);
                targetLocked = false;
            }

        } else {
            offset = Limelight.getInstance().getXOffset(); // fakeLimelight.getHeading()
            // Use < Limelight.getInstance().getXOffset() >

            if (!isRunningFlywheel && offset > -5.0 && offset < 5.0) {
                Shooter.getInstance().setRPM(Globals.currentRPM);
                Shooter.getInstance().setFlywheel2(Globals.currentFF);
                isRunningFlywheel = true;
            }

            if (offset > -1.0 && offset < 1.0) {
                Drivetrain.getInstance().setVoltage(0);
                targetLocked = true;
            } else {

                if (offset < 0) {
                    Drivetrain.drive(0, 0, -0.5, false);
                } else {
                    Drivetrain.drive(0, 0, 0.5, false);
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

        Indexer.getInstance().setSpeedIndexer(0);
        Indexer.getInstance().setSpeedIndexerStopper(0);
        Indexer.getInstance().setSpeedSingulator(0);
        Shooter.getInstance().setVoltage(0);

        Globals.isAiming = false;
    }
}
