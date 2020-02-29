package com.swervedrivespecialties.exampleswerve.utils;

import com.swervedrivespecialties.exampleswerve.RobotMap;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private NetworkTable table;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;

    NetworkTableEntry xcorners, ycorners;

    private double txOffset = 1;

    public Limelight() {
        
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");

        xcorners = table.getEntry("tcornx");
        ycorners = table.getEntry("tcorny");
    }

    public double getTX() {
        return tx.getDouble(0.0) + txOffset;
    }

    public double getTA() {
        return ta.getDouble(0.0);
    }

    public double getTY() {
        return ty.getDouble(0.0);
    }

    public double[] getXCorners() {
        return xcorners.getDoubleArray(new double[0]);
    }

    public double getDistance() {
        double angleInRadians = Math.toRadians(RobotMap.ANGLE_OF_LIMELIGHT + getTY());
        return (RobotMap.HEIGHT_OF_TARGET - RobotMap.HEIGHT_OF_LIMELIGHT) / (Math.tan(angleInRadians));
    }
}