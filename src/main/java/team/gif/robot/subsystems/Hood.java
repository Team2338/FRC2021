package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;

public class Hood extends SubsystemBase {
    private static Hood instance;

    public static Hood getInstance() {
        if (instance == null) {
            instance = new Hood();
        }
        return instance;
    }

    private TalonSRX hoodMotor = new WPI_TalonSRX(RobotMap.SHOOTER_HOOD_MOTOR);

    public Hood() {
        super();

        /* Factory Default all hardware to prevent unexpected behaviour */
        hoodMotor.configFactoryDefault();

        /* Config the sensor used for Primary PID and sensor direction */
        hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.Hood.kPIDLoopIdx,
                Constants.Hood.kTimeoutMs);

        /* Ensure sensor is positive when output is positive */
        hoodMotor.setSensorPhase(Constants.Hood.kSensorPhase);

        /*
         * Set based on what direction you want forward/positive to be.
         * This does not affect sensor phase. 
         */
        hoodMotor.setInverted(Constants.Hood.kMotorInvert);

        /* Config the peak and nominal outputs, 12V means full */
        hoodMotor.configNominalOutputForward(0, Constants.Hood.kTimeoutMs);
        hoodMotor.configNominalOutputReverse(0, Constants.Hood.kTimeoutMs);
        hoodMotor.configPeakOutputForward(0.25, Constants.Hood.kTimeoutMs);
        hoodMotor.configPeakOutputReverse(-0.25, Constants.Hood.kTimeoutMs);

        /*
         * Config the allowable closed-loop error, Closed-Loop output will be
         * neutral within this range. See Table in Section 17.2.1 for native
         * units per rotation.
         */
        hoodMotor.configAllowableClosedloopError(0, Constants.Hood.kPIDLoopIdx, Constants.Hood.kTimeoutMs);

        /* Config Position Closed Loop gains in slot0, typically kF stays zero. */
        hoodMotor.config_kF(Constants.Hood.kPIDLoopIdx, Constants.Hood.kGains.kF, Constants.Hood.kTimeoutMs);
        hoodMotor.config_kP(Constants.Hood.kPIDLoopIdx, Constants.Hood.kGains.kP, Constants.Hood.kTimeoutMs);
        hoodMotor.config_kI(Constants.Hood.kPIDLoopIdx, Constants.Hood.kGains.kI, Constants.Hood.kTimeoutMs);
        hoodMotor.config_kD(Constants.Hood.kPIDLoopIdx, Constants.Hood.kGains.kD, Constants.Hood.kTimeoutMs);

        /*
         * Grab the 360 degree position of the MagEncoder's absolute
         * position, and intitally set the relative sensor to match.
         */
        int absolutePosition = hoodMotor.getSensorCollection().getPulseWidthPosition();

        /* Mask out overflows, keep bottom 12 bits */
        absolutePosition &= 0xFFF;
        if (Constants.Hood.kSensorPhase) {
            absolutePosition *= -1;
        }
        if (Constants.Hood.kMotorInvert) {
            absolutePosition *= -1;
        }

        /* Set the quadrature (relative) sensor to match absolute */
        hoodMotor.setSelectedSensorPosition(absolutePosition, Constants.Hood.kPIDLoopIdx, Constants.Hood.kTimeoutMs);
    }

    public void setPosition(double targetPos) {
        hoodMotor.set(ControlMode.Position, targetPos);
    }

    public void setSpeed(double percent) {
        hoodMotor.set(ControlMode.PercentOutput, percent);
    }

    public double getVelocity() {
        return hoodMotor.getSelectedSensorVelocity();
    }

    /* Motion Magic
    private final TalonSRX hoodMotor;

    private Hood() {
        hoodMotor = new TalonSRX(RobotMap.SHOOTER_HOOD_MOTOR);
        configLift(hoodMotor);

        int absPos = hoodMotor.getSensorCollection().getPulseWidthPosition();
        absPos &= 0xFFF;
        hoodMotor.setSelectedSensorPosition(absPos);
    }

    public void setPercentOutput(double percent) {
        hoodMotor.set(ControlMode.PercentOutput, percent);
//        lift.set(ControlMode.PercentOutput, percent);
    }

    public void setMotionMagic(double position) {
        hoodMotor.set(ControlMode.MotionMagic, position);
    }

    public void setMotionMagic(double position, double arbitraryFeedForward) {
        hoodMotor.set(ControlMode.MotionMagic, position, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
    }

    public void setCruiseVelocity(int ticksPer100ms) {
        hoodMotor.configMotionCruiseVelocity(ticksPer100ms);
    }

    public void configF(double f) {
        hoodMotor.config_kF(0, f);
    }

    public double getPosition() {
        return hoodMotor.getSelectedSensorPosition();
    }

    public boolean getFwdLimit() {
        return hoodMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean getRevLimit() {
        return hoodMotor.getSensorCollection().isRevLimitSwitchClosed();
    }

    public boolean isFinished() {
        return Math.abs(hoodMotor.getClosedLoopError()) < Constants.Hood.ALLOWABLE_ERROR;
    }

    public double getOutputVoltage() {
        return hoodMotor.getMotorOutputVoltage();
    }

    public double getOutputCommand() {
        return hoodMotor.getMotorOutputPercent();
    }

    public double getVelTPS() {
        return hoodMotor.getSelectedSensorVelocity() * 10.0;
    }

    //public double getVelRPS() {
    //    return getVelTPS() * Constants.Drivetrain.TPS_TO_RPS;
    //}

    public double getCurrent() {
        return hoodMotor.getOutputCurrent();
    }

    public double getClosedLoopError() {
        return hoodMotor.getClosedLoopError();
    }

    private void configLift(TalonSRX talon) {
        talon.configFactoryDefault();
//        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
//        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        talon.enableVoltageCompensation(true);
        talon.setSensorPhase(true);
        talon.setInverted(true);
        talon.setNeutralMode(NeutralMode.Brake);

        talon.config_kP(0, Constants.Hood.P);
        talon.config_kI(0, Constants.Hood.I);
        talon.config_kD(0, Constants.Hood.D);
        talon.config_kF(0, Constants.Hood.F);
        talon.configMotionCruiseVelocity(Constants.Hood.MAX_VELOCITY);
        talon.configMotionAcceleration(Constants.Hood.MAX_ACCELERATION);
        talon.configNominalOutputForward(0);
        talon.configNominalOutputReverse(0);
        talon.configPeakOutputForward(0.25);
        talon.configPeakOutputReverse(-0.25);

        //talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        //talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        talon.configForwardSoftLimitThreshold(Constants.Hood.MAX_POS);
        talon.configReverseSoftLimitThreshold(Constants.Hood.MIN_POS);
        talon.overrideLimitSwitchesEnable(false);
        talon.configForwardSoftLimitEnable(true);
        talon.configReverseSoftLimitEnable(true);
//        talon.configClearPositionOnLimitR(true, 0);
    }*/

    public double getPercentOutput() {
        return hoodMotor.getMotorOutputPercent();
    }

    public double getHoodPos() {
        return hoodMotor.getSelectedSensorPosition();
    }

    public void resetHoodEncoder() {
        hoodMotor.setSelectedSensorPosition(0);
    }

}
