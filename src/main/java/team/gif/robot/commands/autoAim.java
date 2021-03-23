package team.gif.robot.commands;

import edu.wpi.first.hal.simulation.SimulatorJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.lib.VisionSim;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Limelight;
import team.gif.robot.subsystems.drivers.Pigeon;

public class autoAim extends CommandBase {

    private boolean endVar = false;
    
    private int lockTime = 0;
    final private int maxLockTime = 5;
    public VisionSim sim = null;
    
    double rotPower = 1;
    final double marginx = 2;
    
    public boolean isSimulation = false;
    
    public autoAim() {
        // Use addRequirements() here to declare subsystem dependencies.
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("autoaim init");
        Limelight.getInstance().setLEDMode(3);
        if(Robot.isSimulation()){
            isSimulation = true;
            System.out.println("autoaim Simulation");
            sim = new VisionSim();
            sim.reset(-30);
        }
    }
    
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        boolean hasTarget = isSimulation ? true : Limelight.getInstance().hasTarget();
        double tx = isSimulation? sim.getHeading(): Limelight.getInstance().getXOffset();
    
        System.out.println("hasTarget" + hasTarget);
        System.out.println("tx: " + tx);
        
        if((Math.abs(tx) <= marginx) && hasTarget){
            lockTime ++;
            System.out.println("in margin");
            if(lockTime > maxLockTime){
                System.out.println("target locked and settled");
                endVar = true;
            }
        } else if (hasTarget) {
            System.out.println("moving");
            
            lockTime = 0;
            //tx <0 turn clockwise
            rotPower = tx<0 ? rotPower: -1*rotPower;
    
            Drivetrain.getInstance().drive(0,0,rotPower,false);
            System.out.println("rotPower:"+rotPower);
            
            if(isSimulation) {
                sim.turn(rotPower);
            }
        } else {
            System.out.println("target lost");
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
        Limelight.getInstance().setLEDMode(1);
        Drivetrain.getInstance().setVoltage(0);
    }
}
