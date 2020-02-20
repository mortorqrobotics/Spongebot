package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
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
    public Joystick secondaryJoystick = new Joystick(1);

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

        

        new JoystickButton(secondaryJoystick, RobotMap.MAGAZINE_NEXT_SHOOTING_POSITION).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.nextShootingPosition())
        );

        new JoystickButton(secondaryJoystick, RobotMap.MAGAZINE_PREVIOUS_SHOOTING_POSITION).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.previousShootingPosition())
        );

        new JoystickAxis(secondaryJoystick, RobotMap.MAGAZINE_NEXT_INTAKE_POSITION).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.nextIntakePosition())
        );

        new JoystickAxis(secondaryJoystick, RobotMap.MAGAZINE_PREVIOUS_INTAKE_POSITION).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.previousIntakePosition())
        );
    }

    public Joystick getPrimaryJoystick() {
        return primaryJoystick;
    }
}
