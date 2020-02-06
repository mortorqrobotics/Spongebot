package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shooter {

    private Joystick m_stick;

    private static final int LEFT_MOTOR_ID = 99;
    private static final int RIGHT_MOTOR_ID = 99;

    private CANSparkMax leftMotor, rightMotor;

    public Shooter(Joystick m_stick) {
        leftMotor = new CANSparkMax(LEFT_MOTOR_ID, MotorType.kBrushless);
        rightMotor = new CANSparkMax(RIGHT_MOTOR_ID, MotorType.kBrushless);

        this.m_stick = m_stick;
    }

    public void shooterPeriodic() {
         // Set motor output to joystick value

        double speed = 0;

        if (m_stick.getRawButton(1)) 
            speed = .3;
        else if (m_stick.getRawButton(3)) 
            speed = .4;
        else if (m_stick.getRawButton(4)) 
            speed = .5;
        else if (m_stick.getRawButton(2)) 
            speed = .6;
        
        
        if (m_stick.getRawButton(6)) {
            if (speed != 0)
                speed += .4;
        }

        speed *= .99;

        leftMotor.set(-(speed + .005));
        rightMotor.set(speed);

        SmartDashboard.putNumber("left velocity", -leftMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("right velocity", rightMotor.getEncoder().getVelocity());
    }
}