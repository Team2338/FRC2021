package team.gif.robot;

public abstract class RobotMap {

    public static final int kFrontLeftDriveMotorPort = 0;
    public static final int kRearLeftDriveMotorPort = 2;
    public static final int kFrontRightDriveMotorPort = 4;
    public static final int kRearRightDriveMotorPort = 6;

    public static final int kFrontLeftTurningMotorPort = 1;
    public static final int kRearLeftTurningMotorPort = 3;
    public static final int kFrontRightTurningMotorPort = 5;
    public static final int kRearRightTurningMotorPort = 7;

    public static final int[] kFrontLeftTurningEncoderPorts = new int[] {0, 1};
    public static final int[] kRearLeftTurningEncoderPorts = new int[] {2, 3};
    public static final int[] kFrontRightTurningEncoderPorts = new int[] {4, 5};
    public static final int[] kRearRightTurningEncoderPorts = new int[] {5, 6};

    public static final int[] kFrontLeftDriveEncoderPorts = new int[] {7, 8};
    public static final int[] kRearLeftDriveEncoderPorts = new int[] {9, 10};
    public static final int[] kFrontRightDriveEncoderPorts = new int[] {11, 12};
    public static final int[] kRearRightDriveEncoderPorts = new int[] {13, 14};

    // Pigeon
    public static final int PIGEON = 0;

    // Controllers
    public static final int DRIVER_CONTROLLER_ID = 0;
    public static final int AUX_CONTROLLER_ID = 1;
}
