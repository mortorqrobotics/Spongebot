package com.swervedrivespecialties.exampleswerve.utils;

import com.swervedrivespecialties.exampleswerve.RobotMap;

import edu.wpi.first.wpilibj.Counter;

public class Lidar {

    private Counter lidar;

    public Lidar() {
        lidar = new Counter(RobotMap.LIDAR_ID);
        
        lidar.setMaxPeriod(1.00);
        lidar.setSemiPeriodMode(true);
        lidar.reset();
    }

    public double getDistance() {
        double distance;
        
        if(lidar.get() < 1) {
            distance = 0;
        }
        else {
            distance = (lidar.getPeriod() * 1000000.0 / 10.0) + RobotMap.LIDAR_OFFSET;
        }

        return distance; //in cm
    }

}