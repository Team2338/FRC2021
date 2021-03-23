package team.gif.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Limelight;
import team.gif.robot.subsystems.drivers.Pigeon;

public class autoAim extends CommandBase {

    private boolean endVar = false;
    
    private int lockTime = 0;
    final private int maxLockTime = 5;
    
    double rotPower = .15;
    final double marginx = 2;
    
    public autoAim() {
        // Use addRequirements() here to declare subsystem dependencies.
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("autoaim init");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        boolean hasTarget = Limelight.getInstance().hasTarget();
        double tx = Limelight.getInstance().getXOffset();
        
        if((Math.abs(tx) <= marginx) && hasTarget){
            lockTime ++;
            if(lockTime > maxLockTime){
                endVar = true;
            }
        } else if (hasTarget) {
            lockTime = 0;
            //tx <0 turn clockwise
            rotPower = tx<0 ? rotPower: -1*rotPower;
            Drivetrain.getInstance().drive(0,0,rotPower,false);
        } else {
            endVar = true;
        }
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return endVar;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setVoltage(0);
    }
}
