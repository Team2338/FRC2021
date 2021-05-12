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
import team.gif.robot.commands.autos.*;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.drivetrain.ResetEncoders;
import team.gif.robot.commands.drivetrain.ResetHeading;
import team.gif.robot.commands.mobility;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.drivers.Limelight;
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

  private boolean _runAutoScheduler = true;
  private boolean _runSecondAutoScheduler = false;
  private boolean _runThirdAutoScheduler = false;
  private boolean isRedPath = false;

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

    /*SmartDashboard.putNumber("Raw FL", Drivetrain.getInstance().getRawModuleHeadings()[0]);
    SmartDashboard.putNumber("Raw RL", Drivetrain.getInstance().getRawModuleHeadings()[1]);
    SmartDashboard.putNumber("Raw FR", Drivetrain.getInstance().getRawModuleHeadings()[2]);
    SmartDashboard.putNumber("Raw RR", Drivetrain.getInstance().getRawModuleHeadings()[3]);*/

    /*SmartDashboard.putNumber("Front Left Percent", Drivetrain.getInstance().getModulePercents()[0]);
    SmartDashboard.putNumber("Rear Left Percent", Drivetrain.getInstance().getModulePercents()[1]);
    SmartDashboard.putNumber("Front Right Percent", Drivetrain.getInstance().getModulePercents()[2]);
    SmartDashboard.putNumber("Rear Right Percent", Drivetrain.getInstance().getModulePercents()[3]);*/

    SmartDashboard.putNumber("Front Left Velocity", Drivetrain.getInstance().getVelocity()[0]);

    SmartDashboard.putData("ResetHead", new ResetHeading());
    SmartDashboard.putString("RPM", Shooter.getInstance().getVelocity_Shuffleboard());

    SmartDashboard.putNumber("Flywheel Percent", Shooter.getInstance().getPercentOutput());

    SmartDashboard.putNumber("Hood Position", Hood.getInstance().getHoodPos());
    //System.out.println(Hood.getInstance().getHoodPos());
    //System.out.println(Hood.getInstance().getPercentOutput());
    //System.out.println(Globals.currentRPM);

    SmartDashboard.putBoolean("Sensor One", Indexer.getInstance().getStateOne());
    SmartDashboard.putBoolean("Sensor Two", Indexer.getInstance().getStateTwo());
    //System.out.println(Limelight.getInstance().getYOffset());

    CommandScheduler.getInstance().run();
    /*SmartDashboard.putNumber("Turning FL", Drivetrain.getInstance().getTurningOutputs()[0]);
    SmartDashboard.putNumber("Turning RL", Drivetrain.getInstance().getTurningOutputs()[1]);
    SmartDashboard.putNumber("Turning FR", Drivetrain.getInstance().getTurningOutputs()[2]);
    SmartDashboard.putNumber("Turning RR", Drivetrain.getInstance().getTurningOutputs()[3]);*/

    System.out.println(Drivetrain.getInstance().getVelocity()[3]);
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
    /*if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    } else {
      System.out.println("NOT SCHEDULING AUTO");
    }*/

    _runAutoScheduler = true;
    isRedPath = false;
  }

  public boolean anySensor() {
    return (
        Indexer.getInstance().getStateOne()
        || Indexer.getInstance().getStateTwo()
    );
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    if (_runAutoScheduler) {
      if (m_autonomousCommand != null) {
        System.out.println("Delay over. Auto selection scheduler started.");
        m_autonomousCommand.schedule();
      }
      _runAutoScheduler = false;
    }

    // code for second path
    if (_runSecondAutoScheduler && !m_autonomousCommand.isScheduled() ) { // first auto is done
      Pigeon.getInstance().resetPigeonPosition();
      Drivetrain.getInstance().resetPose();

      if (anySensor()) {
        m_autonomousCommand = new GalacticSearchRed();
        isRedPath = true;
        System.out.println("Path is RED");
      } else {
        m_autonomousCommand = new GalacticSearchBlue();
        System.out.println("Path is BLUE");
      }
      m_autonomousCommand.schedule();
      _runSecondAutoScheduler = false;
      _runThirdAutoScheduler = true;
    }

    // code for third path
    if (_runThirdAutoScheduler && !m_autonomousCommand.isScheduled() ) { // second auto is done
      Pigeon.getInstance().resetPigeonPosition();
      Drivetrain.getInstance().resetPose();

      if (isRedPath) {
        if ( Indexer.getInstance().getStateTwo()) {
          m_autonomousCommand = new GalacticSearchRedB();
          System.out.println("Changing to Red B");
        } else {
          m_autonomousCommand = new GalacticSearchRedA();
          System.out.println("Changing to Red A");
        }
      } else { // Blue Path
        if ( anySensor()) {
          m_autonomousCommand = new GalacticSearchBlueB();
          System.out.println("Changing to Blue B");
        } else {
          m_autonomousCommand = new GalacticSearchBlueA();
          System.out.println("Changing to Blue A");
        }
      }
      m_autonomousCommand.schedule();
      _runThirdAutoScheduler = false;
    }
  }

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
        autoModeChooser.addOption("Galactic Search", autoMode.GALACTIC_SEARCH);

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
        } else if(chosenAuto == autoMode.GALACTIC_SEARCH){
          m_autonomousCommand = new GalacticSearchColor();
          _runSecondAutoScheduler = true;
          _runThirdAutoScheduler = false;
        } else if(chosenAuto ==null) {
            System.out.println("Autonomous selection is null. Robot will do nothing in auto :(");
        }
    }
}