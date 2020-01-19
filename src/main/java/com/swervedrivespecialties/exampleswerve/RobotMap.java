package com.swervedrivespecialties.exampleswerve;

public class RobotMap {
    // WHEELS
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 1; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 0; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 2; // CAN

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 7; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 1; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 8; // CAN

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 6; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 2; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 5; // CAN

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 4; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 3; // CAN

    // Extra Staff 
    public static final int ULTRA_SONIC_SENSOR_PORT = 4;

    public static double SPEED_ONE = 0.5;
    public static double SPEED_TWO = 1.0;
    public static double PERCENT_SPEED = SPEED_ONE;

    // Vision
    public static final double HEIGHT_OF_LIMELIGHT = 23;
    public static final double HEIGHT_OF_TARGET = 40;
    public static final double ANGLE_OF_LIMELIGHT = 0;

    // Lidar
    public static final int LIDAR_OFFSET = -10;
    public static final int LIDAR_ID = 0; //PWM
}