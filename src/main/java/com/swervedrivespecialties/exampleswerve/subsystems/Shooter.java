package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

public class Shooter {

    private Joystick m_stick;
    private CANSparkMax leftMotor, rightMotor;
    private CANPIDController left_pidController, right_pidController;

    public Shooter(Joystick m_stick) {
        leftMotor = new CANSparkMax(RobotMap.LEFT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        left_pidController = leftMotor.getPIDController();

        left_pidController.setP(0.00020);
        left_pidController.setI(0.000001);
        left_pidController.setD(0.20000);
        left_pidController.setIZone(0);
        left_pidController.setFF(0.00000);
        left_pidController.setOutputRange(-1, 1);

        rightMotor = new CANSparkMax(RobotMap.RIGHT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        right_pidController = rightMotor.getPIDController();
        
        right_pidController.setP(0.00015);
        right_pidController.setI(0.000001);
        right_pidController.setD(0.085);
        right_pidController.setIZone(0);
        right_pidController.setFF(0.000001);
        right_pidController.setOutputRange(-1, 1);

        this.m_stick = m_stick;
    }

    public void shooterPeriodic() {
        double setPoint = 0;

        if (m_stick.getPOV() == 0) {
            setPoint = 5000;
        } else if (m_stick.getPOV() == 90) {
            setPoint = 3500;
        } else if (m_stick.getPOV() == 180) {
            setPoint = 4000;
        } else if (m_stick.getPOV() == 270) {
            setPoint = 4500;
        }

        left_pidController.setReference(setPoint, ControlType.kVelocity);
        right_pidController.setReference(-setPoint, ControlType.kVelocity);
    }
}