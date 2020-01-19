package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.LimelightSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.LidarSubsystem;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private static OI oi;

    private static DrivetrainSubsystem drivetrain;

    private LimelightSubsystem limelight = new LimelightSubsystem();
    private LidarSubsystem lidar = new LidarSubsystem();
    
    public static OI getOi() {
        return oi;
    }

    @Override
    public void robotInit() {
        oi = new OI();
        drivetrain = DrivetrainSubsystem.getInstance();
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        
        //SmartDashboard.putNumber("Distance", ultraSonicSensor.getDistanceInInches());
        SmartDashboard.putNumber("Area", limelight.getTA());
        SmartDashboard.putNumber("X", limelight.getTX());
        SmartDashboard.putNumber("Y", limelight.getTY());
        SmartDashboard.putNumber("# of corners", limelight.getXCorners().length);
        SmartDashboard.putNumber("Distance:", limelight.getDistance());
        SmartDashboard.putNumber("Distance in cm:", lidar.getDistance());
    }   

    private double distanceFromWall = 0;
    private double delayTime = 0;

    @Override
    public void autonomousInit() {
        super.autonomousInit();

        SmartDashboard.putNumber("Distance From Wall", 0);
        SmartDashboard.putNumber("Delay Time", 0);

        // double distanceFromWall = SmartDashboard.getNumber("Distance From Wall", 0);
        // double delayTime = SmartDashboard.getNumber("Delay Time", 0);
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();

        double distanceFromWall = SmartDashboard.getNumber("Distance From Wall", 0);
        double delayTime = SmartDashboard.getNumber("Delay Time", 0);
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
    }
}
