// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

public class Indexer extends SubsystemBase {
  private static Indexer instance = null;

  private static CANSparkMax indexerMotor;
  public static WPI_TalonSRX indexerStopperMotor = new WPI_TalonSRX(RobotMap.INDEXER_STOPPER_MOTOR);

  public static Indexer getInstance() {
    if (instance == null) {
      System.out.println("indexer init");
      instance = new Indexer();
    }
    return instance;
  }

  /** Creates a new Indexer. */
  public Indexer() {
    super();

    indexerMotor = new CANSparkMax(RobotMap.INDEXER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    //indexerStopperMotor = new WPI_TalonSRX(RobotMap.INDEXER_STOPPER_MOTOR);

    indexerMotor.restoreFactoryDefaults();
    indexerMotor.setInverted(false);
    indexerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    indexerStopperMotor.configFactoryDefault();
    indexerStopperMotor.setInverted(true);
    indexerStopperMotor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void setSpeedIndexer(double percent) {
    indexerMotor.set(percent);
  }

  public void setSpeedIndexerStopper(double percent) {
    indexerStopperMotor.set(percent);
  }
}
