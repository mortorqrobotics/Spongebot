package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagazineSubsystem {


    private static final int ONE_TURNING_DISTANCE = 72 / 14;

    // ONE_TURNING_DISTANCE / 6 = 60
    // private static final double MAGAZINE_ANGLE_OFFSET = -Math.toRadians(0.0);
    // private AnalogEncoder encoder = new AnalogEncoder(RobotMap.MAGAZINE_ENCODER_ID, MAGAZINE_ANGLE_OFFSET); 

    private CANSparkMax motor = new CANSparkMax(RobotMap.MAGAZINE_MOTOR_ID, MotorType.kBrushless);
    private CANPIDController m_pidController;
    private PIDController test;

    double kP = 0.50; 
    double kI = 0.000;
    double kD = 0.00; 
    double kIz = 0; 
    double kFF = 0; 
    double kMaxOutput = .20; 
    double kMinOutput = -.20;

    public static double rotation = 0;


    public MagazineSubsystem() {
        m_pidController = motor.getPIDController();

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

        test = new PIDController(kP, kI, kD);
    }

    public void magazinePeriodic(Joystick joystick) {
        // if (joystick.getRawButton(3)) {
        //     motor.set(.10);
        // } else {
        //     motor.set(0);
        // }
        // return;

        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);

        if((p != kP)) { m_pidController.setP(p); kP = p; }
        if((i != kI)) { m_pidController.setI(i); kI = i; }
        if((d != kD)) { m_pidController.setD(d); kD = d; }


        m_pidController.setReference(getTheoreticalPosition(), ControlType.kPosition);

        SmartDashboard.putNumber("power", motor.getOutputCurrent());
        SmartDashboard.putNumber("SetPoint", getTheoreticalPosition());
        SmartDashboard.putNumber("Process Variable", motor.getEncoder().getPosition());
        SmartDashboard.putNumber("Output", motor.getAppliedOutput());
    }

    public static void nextPosition() {
        rotation++;
    }

    public static void switchMode() {
        rotation += .5;
    }

    private double getTheoreticalPosition() {
        double offset = (rotation == ((int) rotation)) ? .0296 : .0296 / 2;
        return 1.02857142857*rotation + offset;
    }
}