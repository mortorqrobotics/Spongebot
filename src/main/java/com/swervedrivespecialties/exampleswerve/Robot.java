package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.Intake;
import com.swervedrivespecialties.exampleswerve.subsystems.Kicker;
import com.swervedrivespecialties.exampleswerve.subsystems.MagazineSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.Shooter;
import com.swervedrivespecialties.exampleswerve.subsystems.Spinner;
import com.swervedrivespecialties.exampleswerve.subsystems.Tube;
import com.swervedrivespecialties.exampleswerve.utils.Limelight;

import com.swervedrivespecialties.exampleswerve.utils.Lidar;
import com.swervedrivespecialties.exampleswerve.autonomous.AutonomousStates;
import com.swervedrivespecialties.exampleswerve.commands.DriveDistance;
import com.swervedrivespecialties.exampleswerve.commands.RotateToTarget;
import com.swervedrivespecialties.exampleswerve.commands.DriveDistance.Direction;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private static OI oi;

    private static DrivetrainSubsystem drivetrain;
    private PowerDistributionPanel pdp;

    private Shooter shooter;
    private Intake intake;
    private Kicker kicker;
    private Tube climber;
    private Spinner spin;
    private RotateToTarget rotateToTarget;
    private Limelight limelight = new Limelight();
    private Lidar lidar = new Lidar();
    // private DriveDist drive = new DriveDist();
    private MagazineSubsystem magazine;

    public static OI getOi() {
        return oi;
    }

    @Override
    public void robotInit() {
        oi = new OI();
        intake = new Intake();
        kicker = new Kicker(RobotMap.SERVO_IDS[0], RobotMap.SERVO_IDS[1]);

        climber = new Tube(oi.primaryJoystick);
        drivetrain = DrivetrainSubsystem.getInstance();
        
        shooter = new Shooter(oi.secondaryJoystick);
        pdp = new PowerDistributionPanel(RobotMap.PDP_ID);
        pdp.clearStickyFaults();

        spin = new Spinner(oi.primaryJoystick, oi.secondaryJoystick);
        
        magazine = new MagazineSubsystem(oi.secondaryJoystick, intake.intakeMotor);
        rotateToTarget = new RotateToTarget(limelight);
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();

        drivetrain.periodic();
        intake.intakePeriodic(oi.primaryJoystick, oi.secondaryJoystick);
        climber.periodic();
        magazine.magazinePeriodic();

        spin.periodic();

        kicker.servoPeriodic(oi.secondaryJoystick);

        if (oi.primaryJoystick.getRawButton(1)) {
            rotateToTarget.start();
        }

        // SmartDashboard.putNumber("Distance", ultraSonicSensor.getDistanceInInches());
        // SmartDashboard.putNumber("Area", limelight.getTA());
        // SmartDashboard.putNumber("X", limelight.getTX());    
        // SmartDashboard.putNumber("Y", limelight.getTY());
        // SmartDashboard.putNumber("# of corners", limelight.getXCorners().length);
        SmartDashboard.putNumber("Distance in ft:", lidar.getDistance());
        // SmartDashboard.putNumber("Encoder Value:", drive.getEncoderValue());
    }   

    private double distanceFromWall = 0;
    private double delayTime = 0;
    private double startTime;
    private AutonomousStates states;
    private DriveDistance driveDistance;

    @Override
    public void autonomousInit() {
        super.autonomousInit();

        SmartDashboard.putNumber("Distance From Wall", 0);
        SmartDashboard.putNumber("Delay Time", 0);

        states = AutonomousStates.ROTATE_TO_TARGET_STATE;
        rotateToTarget.start();

        // double distanceFromWall = SmartDashboard.getNumber("Distance From Wall", 0);
        // double delayTime = SmartDashboard.getNumber("Delay Time", 0);
        startTime = System.currentTimeMillis();
        driveDistance = new DriveDistance(Direction.BACKWARD);
        DrivetrainSubsystem.getInstance().resetGyroscope();
        kicker.timesRotatedInAutonomous = 0;
        // driveDistance.start();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();

        switch (states) {


            case ROTATE_TO_TARGET_STATE:

            if (rotateToTarget.isCompleted()) {
                states = AutonomousStates.SHOOT_STATE;
                shooter.autonomousShooterPeriodic();
            }

            break;

            case DELAY_STATE:

            if (System.currentTimeMillis() >= startTime + (delayTime * 1000)) {
                states = AutonomousStates.SHOOT_STATE;
            }
            
            break;

            case SHOOT_STATE:
            shooter.autonomousShooterPeriodic();
            
            if (!magazine.inCorrectPosition()) return;

            if (!kicker.moving) {
                kicker.push();
            } 

            if (kicker.moving && System.currentTimeMillis() - kicker.startTime >= 400) {
                kicker.autonomousGoBack();
                if (kicker.getNumberOfTimesRotated() >= 3) {
                    MagazineSubsystem.nextIntakePosition();
                    states = AutonomousStates.STEP_BACK_STATE;
                }
			}

            break;

            case STEP_BACK_STATE:
            shooter.stop();
            driveDistance.start();
            states = AutonomousStates.END_STATE;
            break;

            case END_STATE:
            shooter.stop();
            return;

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
        shooter.teleopShooterPeriodic();
    }
}
