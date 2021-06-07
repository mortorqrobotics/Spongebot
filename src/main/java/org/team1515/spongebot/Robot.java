package org.team1515.spongebot;

import org.team1515.spongebot.subsystems.DrivetrainSubsystem;
import org.team1515.spongebot.subsystems.Intake;
import org.team1515.spongebot.subsystems.Kicker;
import org.team1515.spongebot.subsystems.MagazineSubsystem;
import org.team1515.spongebot.subsystems.Shooter;
import org.team1515.spongebot.subsystems.Spinner;
import org.team1515.spongebot.subsystems.Tube;
import org.team1515.spongebot.utils.Limelight;

import org.team1515.spongebot.utils.Lidar;
import org.team1515.spongebot.autonomous.AutonomousStates;
import org.team1515.spongebot.commands.DriveDistance;
import org.team1515.spongebot.commands.RotateToTarget;
import org.team1515.spongebot.commands.DriveDistance.Direction;

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
        magazine.magazinePeriodic();

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
    private double delayTime = 2;
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
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
        magazine.magazinePeriodic();

        switch (states) {


            case ROTATE_TO_TARGET_STATE:

            if (rotateToTarget.isCompleted()) {
                states = AutonomousStates.DELAY_STATE;
                shooter.autonomousShooterPeriodic();
            }

            break;

            case DELAY_STATE:
            shooter.autonomousShooterPeriodic();

            if (System.currentTimeMillis() >= startTime + (delayTime * 1000)) {
                states = AutonomousStates.ROTATE_MAGAZINE_STATE;
            }
            
            break;

            case ROTATE_MAGAZINE_STATE:
            Intake.startIntake();
            shooter.autonomousShooterPeriodic();

            if (magazine.inCorrectPosition()) {
                kicker.moved = false;
                states = AutonomousStates.SHOOT_STATE;
            }

            break;

            case SHOOT_STATE:
            shooter.autonomousShooterPeriodic();

            if (kicker.kicked()) {
                if (kicker.getNumberOfTimesRotated() >= 3) {
                    states = AutonomousStates.STEP_BACK_STATE;
                } else {
                    states = AutonomousStates.ROTATE_MAGAZINE_STATE;
                }
            } 

            break;


            case STEP_BACK_STATE:
            shooter.stop();
            driveDistance.start();
            MagazineSubsystem.nextIntakePosition();
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
        intake.intakePeriodic(oi.primaryJoystick, oi.secondaryJoystick);
        climber.periodic();
        spin.periodic();
        kicker.servoPeriodic(oi.secondaryJoystick);
    }
}
