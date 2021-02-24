package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;
import team.gif.robot.subsystems.drivers.Pigeon;

@SuppressWarnings("PMD.ExcessiveImports")
public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance = null;

    //private static WPI_TalonSRX pigeonTalon = new WPI_TalonSRX(RobotMap.INDEXER_STOPPER_MOTOR);

    // Robot swerve modules
    private static final SwerveModule m_frontLeft =
            new SwerveModule(
                    RobotMap.kFrontLeftDriveMotorPort,
                    RobotMap.kFrontLeftTurningMotorPort,
                    Constants.Drivetrain.kFrontLeftTurningMotorReversed,
                    Constants.Drivetrain.kFrontLeftDriveMotorReversed,
                    Constants.Drivetrain.kFrontLeftTurningEncoderReversed,
                    Constants.Drivetrain.kFrontLeftOffset);

    private static final SwerveModule m_rearLeft =
            new SwerveModule(
                    RobotMap.kRearLeftDriveMotorPort,
                    RobotMap.kRearLeftTurningMotorPort,
                    Constants.Drivetrain.kRearLeftTurningMotorReversed,
                    Constants.Drivetrain.kRearLeftDriveMotorReversed,
                    Constants.Drivetrain.kRearLeftTurningEncoderReversed,
                    Constants.Drivetrain.kRearLeftOffset);

    private static final SwerveModule m_frontRight =
            new SwerveModule(
                    RobotMap.kFrontRightDriveMotorPort,
                    RobotMap.kFrontRightTurningMotorPort,
                    Constants.Drivetrain.kFrontRightTurningMotorReversed,
                    Constants.Drivetrain.kFrontRightDriveMotorReversed,
                    Constants.Drivetrain.kFrontRightTurningEncoderReversed,
                    Constants.Drivetrain.kFrontRightOffset);

    private static final SwerveModule m_rearRight =
            new SwerveModule(
                    RobotMap.kRearRightDriveMotorPort,
                    RobotMap.kRearRightTurningMotorPort,
                    Constants.Drivetrain.kRearRightTurningMotorReversed,
                    Constants.Drivetrain.kRearRightDriveMotorReversed,
                    Constants.Drivetrain.kRearRightTurningEncoderReversed,
                    Constants.Drivetrain.kRearRightOffset);

    // The gyro sensor
    private static final Pigeon m_gyro = new Pigeon(Indexer.indexerStopperMotor);

    // Odometry class for tracking robot pose
    SwerveDriveOdometry m_odometry =
            new SwerveDriveOdometry(Constants.Drivetrain.kDriveKinematics, m_gyro.getRotation2d());

    public static Drivetrain getInstance() {
        if (instance == null) {
            System.out.println("drivetrain init");
            instance = new Drivetrain();
        }
        return instance;
    }

    /** Creates a new DriveSubsystem. */
    public Drivetrain() {}

    @Override
    public void periodic() {
        //System.out.println("TURN: " + m_rearRight.getTurnDegrees());
        //System.out.println("DRIVE: " + m_frontLeft.getVelocity());
        //System.out.println("PIGEON: " + getHeading());
        // Update the odometry in the periodic block
        m_odometry.update(
                new Rotation2d(getHeading()),
                m_frontLeft.getState(),
                m_rearLeft.getState(),
                m_frontRight.getState(),
                m_rearRight.getState());
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        m_odometry.resetPosition(pose, m_gyro.getRotation2d());
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed Speed of the robot in the x direction (forward).
     * @param ySpeed Speed of the robot in the y direction (sideways).
     * @param rot Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the field.
     */
    @SuppressWarnings("ParameterName")
    public static void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        var swerveModuleStates =
                Constants.Drivetrain.kDriveKinematics.toSwerveModuleStates(
                        fieldRelative
                                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d())
                                : new ChassisSpeeds(xSpeed, ySpeed, rot));
        SwerveDriveKinematics.normalizeWheelSpeeds(
                swerveModuleStates, Constants.Drivetrain.kMaxSpeedMetersPerSecond);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_rearLeft.setDesiredState(swerveModuleStates[2]);
        m_rearRight.setDesiredState(swerveModuleStates[3]);
    }

    /**
     * Sets the swerve ModuleStates.
     *
     * @param desiredStates The desired SwerveModule states.
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.normalizeWheelSpeeds(
                desiredStates, Constants.Drivetrain.kMaxSpeedMetersPerSecond);
        m_frontLeft.setDesiredState(desiredStates[0]);
        m_frontRight.setDesiredState(desiredStates[1]);
        m_rearLeft.setDesiredState(desiredStates[2]);
        m_rearRight.setDesiredState(desiredStates[3]);
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        m_frontLeft.resetEncoders();
        m_rearLeft.resetEncoders();
        m_frontRight.resetEncoders();
        m_rearRight.resetEncoders();
    }

    /** Zeroes the heading of the robot. */
    public void zeroHeading() {
        m_gyro.resetPigeonPosition();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public double getHeading() {
        return m_gyro.getRotation2d().getDegrees();
    }

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    /*public double getTurnRate() {
        return m_gyro.getRate() * (Constants.Drivetrain.kGyroReversed ? -1.0 : 1.0);
    }*/

    public void setSpeedFL (double drive, double turn) {
        m_frontLeft.setSpeed(drive, turn);
    }

    public void setSpeedRR (double drive, double turn) {
        m_rearRight.setSpeed(drive, turn);
    }

    public double getVelocity() {
        return m_frontLeft.getVelocity();
    }

    public double[] getModuleHeadings() {
        double[] headings = {
                m_frontLeft.getTurningHeading() % (2.0 * Math.PI),
                m_rearLeft.getTurningHeading() % (2.0 * Math.PI),
                m_frontRight.getTurningHeading() % (2.0 * Math.PI),
                m_rearRight.getTurningHeading() % (2.0 * Math.PI)
        };
        return headings;
    }

    public double[] getModulePercents() {
        double[] headings = {
                m_frontLeft.getDrivePercent(),
                m_rearLeft.getDrivePercent(),
                m_frontRight.getDrivePercent(),
                m_rearRight.getDrivePercent()
        };
        return headings;
    }
}
