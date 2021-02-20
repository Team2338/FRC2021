package team.gif.robot;

public abstract class RobotMap {

    // one of these motor ports conflicts with another, screwing with the simulation
    public static final int kFrontLeftDriveMotorPort = 16;// 9;
    public static final int kRearLeftDriveMotorPort = 17;//10;
    public static final int kFrontRightDriveMotorPort = 18;//11;
    public static final int kRearRightDriveMotorPort = 19;// 6;

    public static final int kFrontLeftTurningMotorPort = 15;//8;
    public static final int kRearLeftTurningMotorPort = 20;//7;
    public static final int kFrontRightTurningMotorPort = 21;//5;
    public static final int kRearRightTurningMotorPort = 22;//4;

    public static final int[] kFrontLeftTurningEncoderPorts = new int[] {0, 1};
    public static final int[] kRearLeftTurningEncoderPorts = new int[] {2, 3};
    public static final int[] kFrontRightTurningEncoderPorts = new int[] {4, 5};
    public static final int[] kRearRightTurningEncoderPorts = new int[] {5, 6};

    public static final int[] kFrontLeftDriveEncoderPorts = new int[] {7, 8};
    public static final int[] kRearLeftDriveEncoderPorts = new int[] {9, 10};
    public static final int[] kFrontRightDriveEncoderPorts = new int[] {11, 12};
    public static final int[] kRearRightDriveEncoderPorts = new int[] {13, 14};

    // Pigeon
    public static final int PIGEON = 12;

    // Controllers
    public static final int DRIVER_CONTROLLER_ID = 0;
    public static final int AUX_CONTROLLER_ID = 1;
}
