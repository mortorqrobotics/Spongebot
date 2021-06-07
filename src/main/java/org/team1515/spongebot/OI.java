package org.team1515.spongebot;

import org.team1515.spongebot.subsystems.DrivetrainSubsystem;
import org.team1515.spongebot.subsystems.MagazineSubsystem;
import org.team1515.spongebot.subsystems.Tube;

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

        new JoystickButton(primaryJoystick, 8).whenReleased(
            new InstantCommand(() -> Tube.overideLimit = !Tube.overideLimit)
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
