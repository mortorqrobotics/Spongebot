package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.Intake;
import com.swervedrivespecialties.exampleswerve.subsystems.MagazineSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.Shooter;
import com.swervedrivespecialties.exampleswerve.utils.Limelight;
import com.swervedrivespecialties.exampleswerve.utils.Lidar;
import com.swervedrivespecialties.exampleswerve.autonomous.AutonomousStates;
import com.swervedrivespecialties.exampleswerve.subsystems.DriveDist;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private static OI oi;

    private static DrivetrainSubsystem drivetrain;

    private Shooter shooter;
    private Intake intake = new Intake();
    // private Limelight limelight = new Limelight();
    // private Lidar lidar = new Lidar();
    // private DriveDist drive = new DriveDist();
    private MagazineSubsystem magazine = new MagazineSubsystem();
    
    public static OI getOi() {
        return oi;
    }

    @Override
    public void robotInit() {
        oi = new OI();
        drivetrain = DrivetrainSubsystem.getInstance();
        
        shooter = new Shooter(oi.primaryJoystick);

    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        intake.intakePeriodic();
        shooter.shooterPeriodic();
        magazine.magazinePeriodic();
        
        //SmartDashboard.putNumber("Distance", ultraSonicSensor.getDistanceInInches());
        // SmartDashboard.putNumber("Area", limelight.getTA());
        // SmartDashboard.putNumber("X", limelight.getTX());
        // SmartDashboard.putNumber("Y", limelight.getTY());
        // SmartDashboard.putNumber("# of corners", limelight.getXCorners().length);
        // SmartDashboard.putNumber("Distance:", limelight.getDistance());
        // SmartDashboard.putNumber("Distance in cm:", lidar.getDistance());
        // SmartDashboard.putNumber("Encoder Value:", drive.getEncoderValue());
    }   

    private double distanceFromWall = 0;
    private double delayTime = 0;
    private double startTime;
    private AutonomousStates states;

    @Override
    public void autonomousInit() {
        super.autonomousInit();

        SmartDashboard.putNumber("Distance From Wall", 0);
        SmartDashboard.putNumber("Delay Time", 0);

        states = AutonomousStates.DELAY_STATE;

        // double distanceFromWall = SmartDashboard.getNumber("Distance From Wall", 0);
        // double delayTime = SmartDashboard.getNumber("Delay Time", 0);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();

        switch (states) {

            case DELAY_STATE:

            if (System.currentTimeMillis() >= startTime + (delayTime * 1000)) {
                states = AutonomousStates.MOVE_TO_CENTER_STATE;
            }
            break;

            case MOVE_TO_CENTER_STATE:
            
            break;

            case ROTATE_TO_TARGET_STATE:

            break;

            case AIM_STATE:

            break;

            case SHOOT_STATE:

            break;

            case STEP_BACK_STATE:

            break;

            default:
            System.out.println("Something is Wrong");
            System.exit(1);

        }
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
