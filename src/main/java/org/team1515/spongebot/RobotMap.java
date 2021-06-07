package org.team1515.spongebot;

public class RobotMap {
    // WHEELS
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 13; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 0; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 15; // CAN

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 3; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 2; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 4; // CAN

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 11; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 10; // CAN

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 8; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 1; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 2; // CAN

    // Extra Staff 
    public static int PDP_ID = 10;

    public static double PERCENT_SPEED = 1.0;
    public static double WHEEL_CIRCUMFERENCE = 12.5663706144; 
    public static double GEAR_RATIO = 8.31;

    // Vision
    public static final double HEIGHT_OF_LIMELIGHT = 23;
    public static final double HEIGHT_OF_TARGET = 40;
    public static final double ANGLE_OF_LIMELIGHT = 0;

    // Lidar
    public static final int LIDAR_OFFSET = -10;
    public static final int LIDAR_ID = 0; //PWM

    //Magazine
    public static final int MAGAZINE_MOTOR_ID = 5; // CAN
    public static final int MAGAZINE_ENCODER_ID = 90; // Analog input

    public static final int MAGAZINE_NEXT_INTAKE_POSITION = 3; // Joystick axis
    public static final int MAGAZINE_PREVIOUS_INTAKE_POSITION = 2; // Joystick axis
    public static final int MAGAZINE_NEXT_SHOOTING_POSITION = 5; // Joystick button
    public static final int MAGAZINE_PREVIOUS_SHOOTING_POSITION = 6; // Joystick button
    public static final int MAGAZINE_RELEASE = 4; // Joystick button

    // SHOOTER
    public static final int LEFT_SHOOTER_MOTOR_ID = 14;
    public static final int RIGHT_SHOOTER_MOTOR_ID = 1;
    public static final int SHOOT_ONE_POWER_CELL = 7; // joustick button
    public static final int SHOOT_FIVE_POWER_CELLS = 8; // joustick button
    
    // INTAKE
    public static final int INTAKE_ID = 9;
    public static final int PRIMARY_JOYSTICK_INTAKE_AXIS = 3;
    public static final int PRIMARY_JOYSTICK_OUTTAKE_AXIS = 2;
    public static final int SECONDARY_JOYSTICK_INTAKE_BUTTON = 18888;

    // SERVOS
    public static final int[] SERVO_IDS = {0, 1};

    // Climber
    public static final int TELESCOPE = 7;
    public static final int WINCH = 12;
    public static final int WINCH_BUTTON = 4;

    //Spinner
    public static final int SPINNER_ID = 39;
    public static final int SPINNER_ARM_ID = 41;
    public static final int SPINNER_ARM_FORWARD_BUTTON = 99;
    public static final int SPINNER_ARM_BACKWARD_BUTTON = 98;
    public static final int SPINNER_SPIN_FORWARD_BUTTON = 97;
    public static final int SPINNER_SPIN_BACKWARD_BUTTON = 4;
}
