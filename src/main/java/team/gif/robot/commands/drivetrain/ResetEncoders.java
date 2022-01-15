package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Drivetrain;

public class ResetEncoders extends CommandBase {

    /**
     * Creates a new ExampleCommand.
     */
    public ResetEncoders() {
        //m_subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        //addRequirements(Drivetrain.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Drivetrain.getInstance().resetEncoders();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
