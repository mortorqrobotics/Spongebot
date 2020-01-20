package com.swervedrivespecialties.exampleswerve.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class GyroSubsystem {

    public AHRS navX;

    public GyroSubsystem() {
        navX = new AHRS(SPI.Port.kMXP);
    }

    public double getAngle() {
        return navX.getAngle();
    }

    public double getYaw() {
        return navX.getYaw();
    }

}