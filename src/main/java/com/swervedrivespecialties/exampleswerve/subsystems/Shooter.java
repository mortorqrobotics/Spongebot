package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;
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

    private boolean running = false;
    public enum ShooterSpeed {
        UP, RIGHT, LEFT, DOWN;
    }

    public Shooter(Joystick m_stick) {
        leftMotor = new CANSparkMax(RobotMap.LEFT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        left_pidController = leftMotor.getPIDController();

        left_pidController.setP(0.00022);
        left_pidController.setI(0.0000013);
        left_pidController.setD(0.20000);
        left_pidController.setIZone(0);
        left_pidController.setFF(0.00000);
        left_pidController.setOutputRange(-1, 1);

        rightMotor = new CANSparkMax(RobotMap.RIGHT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        right_pidController = rightMotor.getPIDController();
        
        right_pidController.setP(0.00017);
        right_pidController.setI(0.0000013);
        right_pidController.setD(0.085);
        right_pidController.setIZone(0);
        right_pidController.setFF(0.000001);
        right_pidController.setOutputRange(-1, 1);

        this.m_stick = m_stick;
        
    }

    public void teleopShooterPeriodic() {
        double setPoint = 0;

        if (m_stick.getRawButton(1)) {
            setPoint =  2250;
        } else if (m_stick.getRawButton(3)) {
            setPoint = 2350;
        }  else if (m_stick.getRawButton(2)) {
            setPoint = 2400;
        }

        if (setPoint == 0) {
            leftMotor.stopMotor();
            rightMotor.stopMotor();
            return;
        }

        left_pidController.setReference(setPoint, ControlType.kVelocity);
        right_pidController.setReference(-setPoint, ControlType.kVelocity);

        SmartDashboard.putNumber("Rmotor", -rightMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Lmotor", leftMotor.getEncoder().getVelocity());
    }

    public void autonomousShooterPeriodic() {
        double setPoint = 2250;

        left_pidController.setReference(setPoint, ControlType.kVelocity);
        right_pidController.setReference(-setPoint, ControlType.kVelocity);

        SmartDashboard.putNumber("Rmotor", -rightMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Lmotor", leftMotor.getEncoder().getVelocity());
    }

    public void stop() {
        double setPoint = 0;

        leftMotor.stopMotor();
        rightMotor.stopMotor();
    }
}