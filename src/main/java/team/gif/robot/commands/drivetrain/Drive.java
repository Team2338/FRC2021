// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;

public class Drive extends CommandBase {

    private static final double JOYSTICK_DEADZONE = 0.05;
    double x;
    double y;
    double rot;

    /**
     * Creates a new Drive Command.
     */
    public Drive() {
        addRequirements(Drivetrain.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        /*
         * Note: Front in Teleop is different from "official" front!
         */

        if (!Globals.isAiming) {
            x = Robot.oi.leftStick.getY();
            y = -Robot.oi.leftStick.getX();
            rot = Robot.oi.rightStick.getTwist();

//      x = Robot.oi.driver.getY(GenericHID.Hand.kLeft);
//      y = -Robot.oi.driver.getX(GenericHID.Hand.kLeft);
//      rot = Robot.oi.driver.getX(GenericHID.Hand.kRight);

            x = Math.abs(x) > JOYSTICK_DEADZONE ? x : 0;
            y = Math.abs(y) > JOYSTICK_DEADZONE ? y : 0;
            rot = Math.abs(rot) > JOYSTICK_DEADZONE ? rot : 0;

            // A split-stick arcade command, with forward/backward controlled by the left
            // hand, and turning controlled by the right.
            Drivetrain.drive(
                    6.0 * (x * Math.abs(x)), //was 6
                    6.0 * (y * Math.abs(y)),
                    4.0 * rot,
                    false);
        }
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
