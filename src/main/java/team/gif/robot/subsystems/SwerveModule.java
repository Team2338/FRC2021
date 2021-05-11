package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;

import team.gif.robot.Constants;

public class SwerveModule {
    private final WPI_TalonFX m_driveMotor;
    private final WPI_TalonSRX m_turningMotor;

    private boolean turningInverted;
    private double turningOffset;

    //private final CANEncoder m_driveEncoder;
    //private final AnalogPotentiometer m_turningEncoder;

    private final PIDController m_drivePIDController =
            new PIDController(Constants.ModuleConstants.kPModuleDriveController, 0, 0);

    // Using a TrapezoidProfile PIDController to allow for smooth turning
    private final ProfiledPIDController m_turningPIDController =
            new ProfiledPIDController(
                    Constants.ModuleConstants.kPModuleTurningController,
                    0,
                    0,
                    new TrapezoidProfile.Constraints(
                            Constants.ModuleConstants.kMaxModuleAngularSpeedRadiansPerSecond,
                            Constants.ModuleConstants.kMaxModuleAngularAccelerationRadiansPerSecondSquared));

    /**
     * Constructs a SwerveModule.
     *
     * @param driveMotorChannel ID for the drive motor.
     * @param turningMotorChannel ID for the turning motor.
     */
    public SwerveModule(
            int driveMotorChannel,
            int turningMotorChannel,
            boolean turningMotorReversed,
            boolean driveMotorReversed,
            boolean turningEncoderReversed,
            double turningMotorOffset) {

        m_driveMotor = new WPI_TalonFX(driveMotorChannel);
        m_turningMotor = new WPI_TalonSRX(turningMotorChannel);

        m_driveMotor.configFactoryDefault();
        m_driveMotor.setNeutralMode(NeutralMode.Brake);
        m_driveMotor.setInverted(driveMotorReversed);

        //m_driveEncoder = m_driveMotor.getEncoder();

        m_turningMotor.configFactoryDefault();
        m_turningMotor.setNeutralMode(NeutralMode.Brake);
        m_turningMotor.setInverted(turningMotorReversed);

        m_turningMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,0 ,0);

        // Set the distance per pulse for the drive encoder. We can simply use the
        // distance traveled for one rotation of the wheel divided by the encoder
        // resolution.
        //m_driveEncoder.setDistancePerPulse(Constants.ModuleConstants.kDriveEncoderDistancePerPulse);
        //m_driveEncoder.setVelocityConversionFactor((Math.PI * Constants.ModuleConstants.kWheelDiameterMeters) / (60.0 * Constants.ModuleConstants.kGearRatio));
        //2.0 * Math.PI * Constants.Drivetrain.DRIVE_WHEEL_RADIUS

        // Set whether drive encoder should be reversed or not
        //m_driveEncoder.setReverseDirection(driveEncoderReversed);
        //m_driveEncoder.setInverted(driveEncoderReversed);

        // Set the distance (in this case, angle) per pulse for the turning encoder.
        // This is the the angle through an entire rotation (2 * wpi::math::pi)
        // divided by the encoder resolution.
        //m_turningEncoder.setDistancePerPulse(Constants.ModuleConstants.kTurningEncoderDistancePerPulse);
        m_turningMotor.configSelectedFeedbackCoefficient(1);

        // Set whether turning encoder should be reversed or not
        //m_turningEncoder.setReverseDirection(turningEncoderReversed);
        /** Analog Potentiometers cannot be reversed? */
        turningInverted = turningEncoderReversed;
        turningOffset = turningMotorOffset;

        // Limit the PID Controller's input range between -pi and pi and set the input
        // to be continuous.
        m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module.
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningHeading()));
    }

    private double getDriveVelocity() {
        return (m_driveMotor.getSelectedSensorVelocity() * 1000 * Math.PI * Constants.ModuleConstants.kWheelDiameterMeters) /
            (Constants.ModuleConstants.kFalconEncoderCPR * Constants.ModuleConstants.kGearRatio);
    }

    /**
     *
     * @return heading of module in degrees
     */
    public double getTurningHeading() {
        double heading = m_turningMotor.getSelectedSensorPosition();
        heading *= turningInverted ? -1.0 : 1.0;
        heading *= (2.0 * Math.PI) / Constants.ModuleConstants.kEncoderCPR;
        heading -= turningOffset;
        //heading %= (2.0 * Math.PI);
        //System.out.println(heading);
        return heading;
    }

    public double getRawHeading() {
        return m_turningMotor.getSelectedSensorPosition();
    }

    /**
     * Sets the desired state for the module.
     *
     * @param state Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState state) {

        // Modules will pick the closest equivalent angle, reversing drive motors if necessary
        var stateOptimized = SwerveModuleState.optimize(state,
                new Rotation2d(getTurningHeading()));

        // Calculate the drive output from the drive PID controller.
        final var driveOutput =
                m_drivePIDController.calculate(getDriveVelocity(), stateOptimized.speedMetersPerSecond);

        // Calculate the turning motor output from the turning PID controller.
        final var turnOutput =
                m_turningPIDController.calculate(getTurningHeading(), stateOptimized.angle.getRadians());

        // Calculate the turning motor output from the turning PID controller.
        m_driveMotor.set(driveOutput);
        m_turningMotor.set(turnOutput);
    }

    /** Zeros all the SwerveModule encoders. */
    public void resetEncoders() {
        m_driveMotor.setSelectedSensorPosition(0);
        m_turningMotor.setSelectedSensorPosition(0.0, 0, 0);
    }

    public void setSpeed(double drive, double turn) {
        m_driveMotor.set(drive);
        m_turningMotor.set(turn);
    }

    public void setVoltage(double drive, double turn) {
        m_driveMotor.setVoltage(drive);
        m_turningMotor.setVoltage(drive);
    }

    /*public double getTurnDegrees() {
        return getTurningHeading();
    }*/

    //public double getVelocity() {
    //    return m_driveEncoder.getVelocity();
    //}

    public double getDrivePercent() {
        return m_driveMotor.get();
    }

    public double getTurningOuput() {
        return m_turningMotor.get();
    }
}
