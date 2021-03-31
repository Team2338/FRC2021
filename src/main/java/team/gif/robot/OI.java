package team.gif.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import team.gif.lib.AxisButton;
import team.gif.robot.commands.autoaim.AutoAim;
import team.gif.robot.commands.drivetrain.ResetEncoders;
import team.gif.robot.commands.indexer.DeployCollector;
import team.gif.robot.commands.indexer.IndexerRun;
import team.gif.robot.commands.indexer.IndexerStopperRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.MoveHoodPos;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.commands.shooter.SelectRange;

public class OI {
    private static OI instance = null;

    /*
     * TODO: Instantiate all joysticks/controllers and their buttons here
     *
     * Examples:
     * public final Joystick leftStick = new Joystick(0);
     * public final XboxController driver = new XboxController(0);
     *
     * private final JoystickButton leftTrigger = new JoystickButton(leftStick, 0);
     */

    public final XboxController driver = new XboxController(RobotMap.DRIVER_CONTROLLER_ID);
    public final XboxController aux = new XboxController(RobotMap.AUX_CONTROLLER_ID);

    public final JoystickButton dA = new JoystickButton(driver, 1);
    public final JoystickButton dB = new JoystickButton(driver, 2);
    public final JoystickButton dX = new JoystickButton(driver, 3);
    public final JoystickButton dY = new JoystickButton(driver, 4);
    public final JoystickButton dLB = new JoystickButton(driver, 5);
    public final JoystickButton dRB = new JoystickButton(driver, 6);
    public final JoystickButton dBack = new JoystickButton(driver, 7);
    public final JoystickButton dStart = new JoystickButton(driver, 8);
    public final JoystickButton dLS = new JoystickButton(driver, 9);
    public final JoystickButton dRS = new JoystickButton(driver, 10);
    public final AxisButton dRT = new AxisButton(driver,3,.05);
    public final AxisButton dLT = new AxisButton(driver,2,.05);

    public final POVButton dDPadUp = new POVButton(driver, 0);
    public final POVButton dDPadRight = new POVButton(driver, 90);
    public final POVButton dDPadDown = new POVButton(driver, 180);
    public final POVButton dDPadLeft = new POVButton(driver, 270);
    public final POVButton aDPadUp = new POVButton(aux, 0);
    public final POVButton aDPadRight = new POVButton(aux, 90);
    public final POVButton aDPadDown = new POVButton(aux, 180);
    public final POVButton aDPadLeft = new POVButton(aux, 270);

    public final JoystickButton aA = new JoystickButton(aux, 1);
    public final JoystickButton aB = new JoystickButton(aux, 2);
    public final JoystickButton aX = new JoystickButton(aux, 3);
    public final JoystickButton aY = new JoystickButton(aux, 4);
    public final JoystickButton aLB = new JoystickButton(aux, 5);
    public final JoystickButton aRB = new JoystickButton(aux, 6);
    public final JoystickButton aBack = new JoystickButton(aux, 7);
    public final JoystickButton aStart = new JoystickButton(aux, 8);
    public final JoystickButton aLS = new JoystickButton(aux, 9);
    public final JoystickButton aRS = new JoystickButton(aux, 10);
    public final AxisButton aRT = new AxisButton(aux,3,.05);
    public final AxisButton aLT = new AxisButton(aux, 2, .05);

    public OI() {
        /*
         * TODO: Define what each button does
         *
         * Examples:
         * leftTrigger.whenPressed(new CollectCommand());
         * rightTrigger.whileHeld(new EjectCommand());
         *
         */

        //dB.whenPressed(new ResetEncoders());
        dLT.whileHeld(new AutoAim(true));
        dLB.whileHeld(new AutoAim(false));
        dRB.whileHeld(new IndexerRun());
        //dY.whileHeld(new IndexerStopperRun(0.5));
        //dLB.whileHeld(new RevFlywheel()); // 2500 0.45
        dRT.whileHeld(new Fire());

        // Matches Color of Buttons
        dA.whenPressed(new SelectRange(Constants.Shooter.GREEN_ZONE));
        dY.whenPressed(new SelectRange(Constants.Shooter.YELLOW_ZONE));
        dX.whenPressed(new SelectRange(Constants.Shooter.BLUE_ZONE));
        dB.whenPressed(new SelectRange(Constants.Shooter.RED_ZONE));
        
        dDPadDown.whileHeld(new DeployCollector());

        //dA.whenPressed(new MoveHoodPos(8180));
        //dB.whenPressed(new MoveHoodPos(3700));
    }
}
