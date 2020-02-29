package com.swervedrivespecialties.exampleswerve.commands;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import org.frcteam2910.common.robot.Utilities;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.geometry.Translation2d;

public class DriveDistance extends Command {

    Direction direction;
    double startingPosition;

    long startingTime;
    long secondsUntilStopped = 1;

    public DriveDistance(Direction direction) {
        requires(DrivetrainSubsystem.getInstance());

        this.direction = direction;
    }

    @Override
    public synchronized void start() {
        startingTime = System.currentTimeMillis();
        super.start();
    }


    @Override
    protected void execute() {
        if(System.currentTimeMillis() - startingTime >= secondsUntilStopped * 1000) {
            DrivetrainSubsystem.getInstance().drive(new Translation2d(0, 0), 0, false);
            end();
            return;
        }

        double forward = 0;
        double strafe = 0;

        double speed = .7;

        switch (direction) {
            case LEFT:
            strafe = speed;
            break;
            case RIGHT:
            strafe = -speed;
            break;
            case FORWARD:
            forward = speed;
            break;
            case BACKWARD:
            forward = -speed;
            break;
        }

        forward = Utilities.deadband(forward);
        // Square the forward stick
        forward = Math.copySign(Math.pow(forward, 2.0), forward);

        strafe = Utilities.deadband(strafe);
        // Square the strafe stick
        strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

        DrivetrainSubsystem.getInstance().drive(new Translation2d(forward, strafe), 0, true);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    public enum Direction {
        LEFT,
        RIGHT,
        BACKWARD,
        FORWARD;
    }
}