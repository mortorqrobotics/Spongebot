package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.MagazineSubsystem;


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

        new JoystickButton(secondaryJoystick, RobotMap.MOVE_MAGAZINE_TO_NEXT_POSITION).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.nextPosition())
        );

        new JoystickButton(secondaryJoystick, RobotMap.MOVE_MAGAZINE_TO_PREVIOUS_POSITION).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.previousPosition())
        );

        new JoystickButton(secondaryJoystick, RobotMap.CHANGE_MAGAZINE_MODE_SHOOT).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.shoot())
        );

        new JoystickButton(secondaryJoystick, RobotMap.CHANGE_MAGAZINE_MODE_INTAKE).whenReleased(
            new InstantCommand(() -> MagazineSubsystem.intake())
        );
    }

    public Joystick getPrimaryJoystick() {
        return primaryJoystick;
    }
}
