// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team.gif.lib.autoMode;
import team.gif.robot.commands.autos.BarrelRacing;
import team.gif.robot.commands.autos.Bounce;
import team.gif.robot.commands.autos.MobilityFwd;
import team.gif.robot.commands.autos.Slalom;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.drivetrain.ResetEncoders;
import team.gif.robot.commands.drivetrain.ResetHeading;
import team.gif.robot.commands.mobility;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

import team.gif.robot.subsystems.Hood;
import team.gif.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand = null;

    private SendableChooser<autoMode> autoModeChooser = new SendableChooser<>();

  private autoMode chosenAuto;

  private Command driveCommand = null;

  private RobotContainer m_robotContainer;

  public static ShuffleboardTab autoTab = Shuffleboard.getTab("PreMatch");

  public static OI oi;

  private final Hood hood = Hood.getInstance();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    driveCommand = new Drive(Drivetrain.getInstance());

      tabsetup();
    SmartDashboard.putData("Reset Module Encoders", new ResetEncoders());
    SmartDashboard.putString("Zone", "N/A");

    Hood.getInstance().resetHoodEncoder();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.

    SmartDashboard.putNumber("Front Left", Drivetrain.getInstance().getModuleHeadings()[0]);
    SmartDashboard.putNumber("Rear Left", Drivetrain.getInstance().getModuleHeadings()[1]);
    SmartDashboard.putNumber("Front Right", Drivetrain.getInstance().getModuleHeadings()[2]);
    SmartDashboard.putNumber("Rear Right", Drivetrain.getInstance().getModuleHeadings()[3]);

    SmartDashboard.putNumber("Raw FL", Drivetrain.getInstance().getRawModuleHeadings()[0]);
    SmartDashboard.putNumber("Raw RL", Drivetrain.getInstance().getRawModuleHeadings()[1]);
    SmartDashboard.putNumber("Raw FR", Drivetrain.getInstance().getRawModuleHeadings()[2]);
    SmartDashboard.putNumber("Raw RR", Drivetrain.getInstance().getRawModuleHeadings()[3]);

    SmartDashboard.putNumber("Front Left Percent", Drivetrain.getInstance().getModulePercents()[0]);
    SmartDashboard.putNumber("Rear Left Percent", Drivetrain.getInstance().getModulePercents()[1]);
    SmartDashboard.putNumber("Front Right Percent", Drivetrain.getInstance().getModulePercents()[2]);
    SmartDashboard.putNumber("Rear Right Percent", Drivetrain.getInstance().getModulePercents()[3]);

    SmartDashboard.putNumber("Front Left Velocity", Drivetrain.getInstance().getVelocity());

    SmartDashboard.putData("ResetHead", new ResetHeading());
    SmartDashboard.putString("RPM", Shooter.getInstance().getVelocity_Shuffleboard());

    SmartDashboard.putNumber("Flywheel Percent", Shooter.getInstance().getPercentOutput());

    SmartDashboard.putNumber("Hood Position", Hood.getInstance().getHoodPos());
    //System.out.println(Hood.getInstance().getHoodPos());
    //System.out.println(Hood.getInstance().getPercentOutput());
    //System.out.println(Globals.currentRPM);

    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("Turning FL", Drivetrain.getInstance().getTurningOutputs()[0]);
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    chosenAuto = autoModeChooser.getSelected();

    updateauto();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    } else {
      System.out.println("NOT SCHEDULING AUTO");
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    oi = new OI();

    driveCommand.schedule();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}


    public void tabsetup(){

        autoTab = Shuffleboard.getTab("PreMatch");

        autoModeChooser.setDefaultOption("Mobility Forward", autoMode.MOBILITY_FWD);
      autoModeChooser.addOption("Mobility", autoMode.MOBILITY);
        autoModeChooser.addOption("Barrel Racing", autoMode.BARREL_RACING);
        autoModeChooser.addOption("Slalom", autoMode.SLALOM);
        autoModeChooser.addOption("Bounce", autoMode.BOUNCE);

        autoTab.add("Auto Select",autoModeChooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(1,0)
            .withSize(2,1);
    }

    public void updateauto(){
        if(chosenAuto == autoMode.MOBILITY_FWD){
            m_autonomousCommand = new MobilityFwd();
        } else if (chosenAuto == autoMode.BARREL_RACING){
            m_autonomousCommand = new BarrelRacing();
        } else if(chosenAuto == autoMode.SLALOM) {
            m_autonomousCommand = new Slalom();
        } else if(chosenAuto == autoMode.BOUNCE){
            m_autonomousCommand = new Bounce();
        } else if (chosenAuto == autoMode.MOBILITY) {
          m_autonomousCommand = new mobility();
        } else if(chosenAuto ==null) {
            System.out.println("Autonomous selection is null. Robot will do nothing in auto :(");
        }
    }
}