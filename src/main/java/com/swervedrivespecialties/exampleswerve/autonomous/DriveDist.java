package com.swervedrivespecialties.exampleswerve.autonomous;

import com.revrobotics.CANEncoder;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.geometry.Translation2d;

public class DriveDist {

    CANEncoder driveEncoder;

    public DriveDist(CANEncoder encoder) {
        driveEncoder = encoder;
        driveEncoder.setPosition(0);
    }

    public void drive(double distance) {

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