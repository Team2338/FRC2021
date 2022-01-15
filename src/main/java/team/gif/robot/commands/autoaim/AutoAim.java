package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.lib.VisionSim;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Limelight;

public class AutoAim extends CommandBase {

    private boolean targetLocked = false;
    private boolean robotHasSettled = false;
    private boolean isRunningFlywheel;
    private boolean accuracyMode;
    double offset;
    VisionSim fakeLimelight = new VisionSim();

    private final double velocitycap = .5;

    public AutoAim(boolean accuracy) {
        accuracyMode = accuracy;
        //addRequirements(Drivetrain.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        targetLocked = false;
        isRunningFlywheel = false;
        //fakeLimelight.reset(-20);
        robotHasSettled = false;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // bot must not be moving anymore
        if ( !robotHasSettled ) {
            if ( Math.abs(Drivetrain.getInstance().getVelocity()[0]) < velocitycap && Math.abs(Drivetrain.getInstance().getVelocity()[2]) < velocitycap ){
                robotHasSettled = true;
                Globals.isAiming = true;
                System.out.println("AutoFire: Robot has settled");
            }
        }

        if (robotHasSettled) {

            if (targetLocked) {
                //System.out.println("Target Acquired");

                offset = Limelight.getInstance().getXOffset();

                if (offset > -1.0 && offset < 1.0) {
                    Drivetrain.getInstance().setVoltage(0);
                    // fire
                    System.out.println("* Fire *");

                    if (!accuracyMode) {
                        if (Math.abs(Globals.currentRPM - Shooter.getInstance().getVelocity()) < 25) {
                            Indexer.getInstance().setSpeedIndexer(0.65);
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
                    robotHasSettled = false;
                    Globals.isAiming = false;
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
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        robotHasSettled = false;

        Drivetrain.getInstance().setVoltage(0);

        Indexer.getInstance().setSpeedIndexer(0);
        Indexer.getInstance().setSpeedIndexerStopper(0);
        Indexer.getInstance().setSpeedSingulator(0);
        Shooter.getInstance().setVoltage(0);

        Globals.isAiming = false;
    }
}
