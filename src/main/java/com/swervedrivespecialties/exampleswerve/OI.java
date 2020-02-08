package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.Intake;
import com.swervedrivespecialties.exampleswerve.subsystems.MagazineSubsystem;

import org.frcteam2910.common.robot.input.JoystickAxis;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class OI {
    /*
       Add your joysticks and buttons here
     */
    public Joystick primaryJoystick = new Joystick(0);

    public OI() {
        // Back button zeroes the drivetrain
        new JoystickButton(primaryJoystick, 7).whenPressed(
                new InstantCommand(() -> DrivetrainSubsystem.getInstance().resetGyroscope())
        );

        new JoystickButton(primaryJoystick, 5).whileHeld(
            new InstantCommand(() -> RobotMap.PERCENT_SPEED = RobotMap.SECOND_SPEED)
        );

        new JoystickButton(primaryJoystick, 5).whenReleased(
            new InstantCommand(() -> RobotMap.PERCENT_SPEED = RobotMap.DEFAULT_SPEED)
        );

        new JoystickAxis(primaryJoystick, 3).whileHeld(
            new InstantCommand(() -> Intake.startIntake())
        );

        new JoystickAxis(primaryJoystick, 3).whenReleased(
            new InstantCommand(() -> Intake.stop())
        );

        new JoystickAxis(primaryJoystick, 2).whileHeld(
            new InstantCommand(() -> Intake.startOuttake())
        );

        new JoystickAxis(primaryJoystick, 2).whenReleased(
            new InstantCommand(() -> Intake.stop())
        );

        new JoystickButton(primaryJoystick, RobotMap.MOVE_MAGAZINE_TO_NEXT_POSITION).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.nextPosition())
        );
    }

    public Joystick getPrimaryJoystick() {
        return primaryJoystick;
    }
}
