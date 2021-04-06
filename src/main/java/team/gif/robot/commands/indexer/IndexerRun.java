// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class IndexerRun extends CommandBase {

  public IndexerRun() {
    // Use addRequirements() here to declare subsystem dependencies.
    //addRequirements(Indexer.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Indexer.getInstance().setSpeedIndexer(0.3);
    Indexer.getInstance().setSpeedIndexerStopper(-0.2);
    Indexer.getInstance().setSpeedSingulator(0.5);
    Indexer.getInstance().setSpeedCollector(0.6);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Indexer.getInstance().setSpeedIndexer(0);
    Indexer.getInstance().setSpeedIndexerStopper(0);
    Indexer.getInstance().setSpeedSingulator(0);
    Indexer.getInstance().setSpeedCollector(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
