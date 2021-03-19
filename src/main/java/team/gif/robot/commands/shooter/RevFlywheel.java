// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Shooter;

public class RevFlywheel extends CommandBase {

  double setpointRPM = 0.0;
  double ff = 0.0;

  public RevFlywheel() {
    switch (Globals.range) {
      case GREEN:
        setpointRPM = Constants.Shooter.RPM_GREEN;
        ff = Constants.Shooter.FF_GREEN;
        break;
      case YELLOW:
        setpointRPM = Constants.Shooter.RPM_YELLOW;
        ff = Constants.Shooter.FF_YELLOW;
        break;
      case BLUE:
        setpointRPM = Constants.Shooter.RPM_BLUE;
        ff = Constants.Shooter.FF_BLUE;
        break;
      case RED:
        setpointRPM = Constants.Shooter.RPM_RED;
        ff = Constants.Shooter.FF_RED;
        break;
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Shooter.getInstance().setRPM(setpointRPM);
    Shooter.getInstance().setFlywheel2(ff);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().setVoltage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
