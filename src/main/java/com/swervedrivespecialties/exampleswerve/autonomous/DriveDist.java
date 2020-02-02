package com.swervedrivespecialties.exampleswerve.autonomous;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.geometry.Translation2d;

public class DriveDist {

    CANEncoder driveEncoder;
    
    CANSparkMax driveMotor;

    public DriveDist() {
        driveMotor = new CANSparkMax(1, MotorType.kBrushless);
        driveEncoder = driveMotor.getEncoder();
    }

    public void drive(double distance) {
        driveEncoder.setPosition(0);

        while(distanceTraveled() < distance) {
            DrivetrainSubsystem.getInstance().drive(new Translation2d(0, 0.5), 0, true);
        }
        DrivetrainSubsystem.getInstance().drive(new Translation2d(0, 0), 0, true);
    }

    public double distanceTraveled() {
        return getWheelRotations() * RobotMap.WHEEL_CIRCUMFERENCE;
    }

    public double getEncoderValue() {
        return driveEncoder.getPosition();
    }

    public double getWheelRotations() {
        return getEncoderValue() / RobotMap.GEAR_RATIO;
    }
}