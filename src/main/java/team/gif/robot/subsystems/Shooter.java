// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;

public class Shooter extends SubsystemBase {
  private static Shooter instance = null;

  private static CANSparkMax flywheelMotor;
  private static CANPIDController flywheelPIDController;
  private static CANEncoder flywheelEncoder;

  private int stallMaxAmps = 40;

  public static Shooter getInstance() {
    if (instance == null) {
      System.out.println("shooter init");
      instance = new Shooter();
    }
    return instance;
  }

  /** Creates a new Shooter. */
  public Shooter() {
    super();

    flywheelMotor = new CANSparkMax(RobotMap.FLYWHEEL_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    flywheelPIDController = flywheelMotor.getPIDController();
    flywheelEncoder = flywheelMotor.getEncoder();

    flywheelMotor.restoreFactoryDefaults();
    flywheelMotor.enableVoltageCompensation(12);
    flywheelMotor.setInverted(false);
    flywheelMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    flywheelMotor.setSmartCurrentLimit(stallMaxAmps,stallMaxAmps);

    flywheelPIDController.setP(Constants.Shooter.kP);
    flywheelPIDController.setFF(Constants.Shooter.kF);
    flywheelPIDController.setOutputRange(0, 1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void setRPM(double rpm) {
    flywheelPIDController.setReference(rpm, ControlType.kVelocity);
  }

  public void setVoltage(double voltage) {
    flywheelMotor.setVoltage(voltage);
  }

  public double getVelocity () { return flywheelEncoder.getVelocity();}

  public String getVelocity_Shuffleboard(){ return String.format("%12.0f",getVelocity());}
}
