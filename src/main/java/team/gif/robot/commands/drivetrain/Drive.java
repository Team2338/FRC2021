// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.ExampleSubsystem;

/** An example command that uses an example subsystem. */
public class Drive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  //private final Drivetrain m_subsystem;

  double x;
  double y;
  double rot;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Drive(Drivetrain subsystem) {
    //m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Drivetrain.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    x = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
    y = Robot.oi.driver.getX(GenericHID.Hand.kLeft);
    rot = Robot.oi.driver.getX(GenericHID.Hand.kRight);

    x = Math.abs(x) > 0.05 ? x : 0.0;
    y = Math.abs(y) > 0.05 ? y : 0.0;
    rot = Math.abs(rot) > 0.05 ? rot : 0.0;

    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right.
    Drivetrain.drive(
            10.0 * x,
            10.0 * y,
            4.0 * rot,
            false);
    /*Drivetrain.getInstance().setSpeedRR(
            -Robot.oi.driver.getY(GenericHID.Hand.kLeft),
            -Robot.oi.driver.getY(GenericHID.Hand.kRight)
    );
    Drivetrain.getInstance().setSpeedFL(
            -Robot.oi.driver.getY(GenericHID.Hand.kLeft),
            -Robot.oi.driver.getY(GenericHID.Hand.kRight)
    );
    System.out.println("INPUT: " + -Robot.oi.driver.getY(GenericHID.Hand.kRight));*/
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
