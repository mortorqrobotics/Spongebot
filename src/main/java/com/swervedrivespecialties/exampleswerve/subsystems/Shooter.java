package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

public class Shooter {

    private Joystick m_stick;
    private CANSparkMax leftMotor, rightMotor;
    private CANPIDController left_pidController, right_pidController;

    double kP = 0.15000; 
    double kI = 0.000001;
    double kD = 3.00; 
    double kIz = 0; 
    double kFF = 0; 
    double kMaxOutput = 1; 
    double kMinOutput = -1;

    public Shooter(Joystick m_stick) {
        leftMotor = new CANSparkMax(RobotMap.LEFT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        // left_pidController = leftMotor.getPIDController();

        // left_pidController.setP(kP);
        // left_pidController.setI(kI);
        // left_pidController.setD(kD);
        // left_pidController.setIZone(kIz);
        // left_pidController.setFF(kFF);
        // left_pidController.setOutputRange(kMinOutput, kMaxOutput);

        rightMotor = new CANSparkMax(RobotMap.RIGHT_SHOOTER_MOTOR_ID, MotorType.kBrushless);

        // SmartDashboard.putNumber("P Gain", kP);
        // SmartDashboard.putNumber("I Gain", kI);
        // SmartDashboard.putNumber("D Gain", kD);

        this.m_stick = m_stick;
    }

    public void shooterPeriodic() {
        //  Set motor output to joystick value

        double speed = 0;//-m_stick.getY(Hand.kLeft);

        if (speed < 0) speed = 0;

        if (m_stick.getRawButton(8)) 
            speed = 0.8;

        speed *= .99;

        if (speed <= 0.05) {
            leftMotor.set(0);
            rightMotor.set(0);
        } else {
            leftMotor.set((speed + .005));
            rightMotor.set(-speed);
        }
        
        // double p = SmartDashboard.getNumber("P Gain", 0);
        // double i = SmartDashboard.getNumber("I Gain", 0);
        // double d = SmartDashboard.getNumber("D Gain", 0);

        // if((p != kP)) { left_pidController.setP(p); kP = p; }
        // if((i != kI)) { left_pidController.setI(i); kI = i; }
        // if((d != kD)) { left_pidController.setD(d); kD = d; }

        // left_pidController.setReference(300, ControlType.kVelocity);

        SmartDashboard.putNumber("Process Variable", leftMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Output", leftMotor.getAppliedOutput());

        // SmartDashboard.putNumber("left velocity", -leftMotor.getEncoder().getVelocity());
        // SmartDashboard.putNumber("right velocity", rightMotor.getEncoder().getVelocity());
    }
}