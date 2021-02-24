// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

/** An example command that uses an example subsystem. */
public class IndexerStopperRun extends CommandBase {

  double percentOutput = 0.0;

  public IndexerStopperRun(double percent) {
    percentOutput = percent;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Indexer.getInstance().setSpeedIndexerStopper(percentOutput);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Indexer.getInstance().setSpeedIndexerStopper(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
