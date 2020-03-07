package com.swervedrivespecialties.exampleswerve.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagazineSubsystem {

    public enum MagazineState {
        SHOOT,
        INTAKE,
        RELEASED;
    }

    private static final double FIFTH_TURNING_DISTANCE = (72.0 / 14.0) / 5.0;

    private CANSparkMax motor = new CANSparkMax(RobotMap.MAGAZINE_MOTOR_ID, MotorType.kBrushless);
    private CANPIDController m_pidController;

    private static final double ANGLE_OFFSET = 11.0;
    private MagEncoder magEncoder;

    double kP = 0.2100000; 
    double kI = 0.00015;
    double kD = 7.250000; 
    double kIz = 0; 
    double kFF = 0; 
    double kMaxOutput = .20; 
    double kMinOutput = -.20;

    public static double rotation = 0;

    public static MagazineState magazineState = MagazineState.SHOOT;

    private static Joystick joystick;

    public MagazineSubsystem(Joystick joystick, TalonSRX encoderTalon) {
        m_pidController = motor.getPIDController();
        motor.setControlFramePeriodMs(10);

        this.joystick = joystick;

        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        m_pidController.setReference(rotation, ControlType.kPosition);

        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("Rotations", rotation);


        magEncoder = new MagEncoder(encoderTalon, ANGLE_OFFSET);
        motor.getEncoder().setPosition(readPosition());
    }

    private double readPosition() {
        double angle = magEncoder.readAngle();
        angle *= ((72.0 / 14.0) / 360.0);

        return angle;
        // return magEncoder.readAngle() % 360;
    }

    public boolean inCorrectPosition() {
        double error = readPosition() - getTheoreticalPosition();
        double velocity = Math.abs(motor.getEncoder().getVelocity());
        if (Math.abs(error) <= .01 && velocity < 5.0) {
            return true;
        }
        
        return false;
    }

    public void magazinePeriodic() {
        // Temporary
        // rotation = SmartDashboard.getNumber("Rotations", 0);

        SmartDashboard.putNumber("Velocity", motor.getEncoder().getVelocity());
        SmartDashboard.putNumber("SetPoint", getTheoreticalPosition());
        SmartDashboard.putNumber("Process Variable", motor.getEncoder().getPosition());

        SmartDashboard.putNumber("Angle", magEncoder.readAngle());
        SmartDashboard.putNumber("Actual Angle", readPosition());
        SmartDashboard.putNumber("Relative Angle:", motor.getEncoder().getPosition());

        SmartDashboard.putNumber("Process Variable", motor.getEncoder().getPosition());
        SmartDashboard.putNumber("Output", motor.getEncoder().getVelocity());

        motor.getEncoder().setPosition(readPosition());

        if (joystick.getRawButton(RobotMap.MAGAZINE_RELEASE)) {
            motor.stopMotor();
            return;
        }

        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);

        if((p != kP)) { m_pidController.setP(p); kP = p; }
        if((i != kI)) { m_pidController.setI(i); kI = i; }
        if((d != kD)) { m_pidController.setD(d); kD = d; }


        m_pidController.setReference(getTheoreticalPosition(), ControlType.kPosition);
    }

    public static void nextIntakePosition() {
        if (magazineState == MagazineState.SHOOT)
            switchMode(true);
        else
            rotation++;

        magazineState = MagazineState.INTAKE;
    }

    public static void previousIntakePosition() {
        if (magazineState == MagazineState.SHOOT)
            switchMode(false);
        else
            rotation--;

        magazineState = MagazineState.INTAKE;
    }

    public static void nextShootingPosition() {
        if (magazineState == MagazineState.INTAKE) Kicker.moving = false;

        if (Kicker.moving) return;

        if (magazineState == MagazineState.INTAKE)
            switchMode(true);
        else
            rotation++;

        magazineState = MagazineState.SHOOT;
    }

    public static void previousShootingPosition() {
        if (magazineState == MagazineState.INTAKE) Kicker.moving = false;

        if (Kicker.moving) return;
        
        if (magazineState == MagazineState.INTAKE)
            switchMode(false);
        else
            rotation--;

        magazineState = MagazineState.SHOOT;
    }

    public static void switchMode(boolean direction) {
        // if direction is true rotate right if false rotate to the left
        rotation += (direction == true) ? .5 : -.5;

        // Switch the magazine state
        magazineState = (magazineState == MagazineState.SHOOT) ? MagazineState.INTAKE : MagazineState.SHOOT;
    }

    private double getTheoreticalPosition() {
        return FIFTH_TURNING_DISTANCE * rotation;
    }
}