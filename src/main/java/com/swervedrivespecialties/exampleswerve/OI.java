package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
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
            new InstantCommand(() -> RobotMap.PERCENT_SPEED = RobotMap.DEFAULT_SPEED)
        );

        new JoystickButton(primaryJoystick, 5).whenReleased(
            new InstantCommand(() -> RobotMap.PERCENT_SPEED = RobotMap.SECOND_SPEED)
        );
    }

    public Joystick getPrimaryJoystick() {
        return primaryJoystick;
    }
}
