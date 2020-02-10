package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

public class Shooter {

    private Joystick m_stick;
    private CANSparkMax leftMotor, rightMotor;

    public Shooter(Joystick m_stick) {
        leftMotor = new CANSparkMax(RobotMap.LEFT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        rightMotor = new CANSparkMax(RobotMap.RIGHT_SHOOTER_MOTOR_ID, MotorType.kBrushless);

        this.m_stick = m_stick;
    }

    public void shooterPeriodic() {
         // Set motor output to joystick value

        double speed = -m_stick.getY(Hand.kLeft);

        if (speed < 0) speed = 0;

        // if (m_stick.getRawButton(1)) 
        //     speed = .1;
        // // else if (m_stick.getRawButton(4)) 
        // //     speed = .3;
        // // else if (m_stick.getRawButton(2)) 
        // //     speed = .4;
        
        
        // if (m_stick.getRawButton(6)) {
        //     if (speed != 0)
        //         speed += .4;
        // }

        speed *= .99;

        if (speed == 0) {
            leftMotor.set(0);
            rightMotor.set(0);
        } else {
            leftMotor.set((speed + .005));
            rightMotor.set(-speed);
        }
        

        SmartDashboard.putNumber("left velocity", -leftMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("right velocity", rightMotor.getEncoder().getVelocity());
    }
}