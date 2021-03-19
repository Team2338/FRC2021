package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Hood;
import team.gif.robot.subsystems.Shooter;

public class MoveHoodPos extends CommandBase {

    private double target;
    private double tolerance = 1000;

    /**
     * Obsolete - Replaced by SelectRange.java
     */

    public MoveHoodPos(double position) {
        target = position;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        Hood.getInstance().setPosition(target);
        //Hood.getInstance().setSpeed(0.25);

        /*if (target > Hood.getInstance().getPosition()) {
            Hood.getInstance().setCruiseVelocity(Constants.Hood.MAX_VELOCITY);
            Hood.getInstance().configF(Constants.Hood.F);
            Hood.getInstance().setMotionMagic(target, Constants.Hood.GRAV_FEED_FORWARD);
            System.out.println("Forward");
        } else {
            Hood.getInstance().setCruiseVelocity(Constants.Hood.REV_MAX_VELOCITY);
            Hood.getInstance().configF(Constants.Hood.REV_F);
            Hood.getInstance().setMotionMagic(target, Constants.Hood.REV_GRAV_FEED_FORWARD);
            System.out.println("Reverse");
        }*/
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        //System.out.println(Hood.getInstance().getVelocity());

        System.out.println("Running");
        /*if (Shooter.getInstance().getHoodPos() > target + tolerance) {
            Shooter.getInstance().setHoodMotorSpeed(-0.075); //-.3
            System.out.println("Reverse");
        } else if (Shooter.getInstance().getHoodPos() < target - tolerance) {
            Shooter.getInstance().setHoodMotorSpeed(0.1); //.3
            System.out.println("Forward");
        } else {
            Shooter.getInstance().setHoodMotorSpeed(0);
            System.out.println("******* STOPPING ********");
        }*/
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //Hood.getInstance().setSpeed(0);
        //Shooter.getInstance().setHoodMotorSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //System.out.println(Hood.getInstance().isFinished());
        //System.out.println("Delta     " + (Shooter.getInstance().getHoodPos() - target));
        //return Math.abs(Shooter.getInstance().getHoodPos() - target) < tolerance;
        //return Hood.getInstance().isFinished();
        return true;
    }
}
