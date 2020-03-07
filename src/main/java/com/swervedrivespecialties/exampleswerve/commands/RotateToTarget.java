package com.swervedrivespecialties.exampleswerve.commands;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;
import org.frcteam2910.common.robot.Utilities;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.utils.Limelight;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.geometry.Translation2d;

public class RotateToTarget extends Command {
    
    private Limelight limelight;
    private long startTime;
    
    private static final PidConstants ANGLE_CONSTANTS = new PidConstants(0.1, 0.0, 0.0000);
    private PidController angleController = new PidController(ANGLE_CONSTANTS);

    private double speed = .5;

    public RotateToTarget(Limelight limelight) {
        this.limelight = limelight;

        angleController.setInputRange(0.0, 2.0 * Math.PI);
        angleController.setContinuous(true);
        angleController.setOutputRange(-speed, speed);
    }

    @Override
    public synchronized void start() {
        startTime = System.currentTimeMillis();
        super.start();
    }

    @Override
    protected void execute() {
        // angleController.setSetpoint(0);

        if ((limelight.getTX() <= 1 && limelight.getTX() >= -1) || System.currentTimeMillis() - startTime >= 2000) {
            end();
            return;
        }

        double error = Math.abs(limelight.getTX());
        if (error > 30) error = 30;
        if (error < 7) error = 3;
        error /= 30;

        double speed = 1 * error;
        if (speed < .2) speed = .2;

        double rotation =  -speed;
        if (limelight.getTX() < -1) rotation *= -1;

        rotation = Utilities.deadband(rotation);
        // Square the rotation stick
        rotation = Math.copySign(Math.pow(rotation, 2.0), rotation);

        DrivetrainSubsystem.getInstance().drive(new Translation2d(0, 0), rotation, false);

        // DrivetrainSubsystem.getInstance().drive(new Translation2d(0, 0), angleController.calculate(limelight.getTX(), TimedRobot.kDefaultPeriod), false);
    }

    @Override
    protected boolean isFinished() {
        if (limelight.getTX() <= .5 && limelight.getTX() >= -.5) {
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean isCompleted() {
        return ((limelight.getTX() <= 1 && limelight.getTX() >= -1) || System.currentTimeMillis() - startTime >= 2000);
    }
}