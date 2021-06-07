package org.team1515.spongebot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.team1515.spongebot.RobotMap;
import org.team1515.spongebot.subsystems.MagazineSubsystem.MagazineState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

    public static boolean intake = false;
    public static boolean reverse = false;

    public TalonSRX intakeMotor = new TalonSRX(RobotMap.INTAKE_ID);

    private static final double speed = 0.45;

    public static void startIntake() {
        if (MagazineSubsystem.magazineState != MagazineState.SHOOT) {
            intake = true;
            reverse = false;
        }
    }

    public static void startOuttake() {
        if (MagazineSubsystem.magazineState != MagazineState.SHOOT) {
            intake = true;
            reverse = true;
        }
    }

    public static void stop() {
        intake = reverse = false;
    }

    public void intakePeriodic(Joystick primaryJoystick, Joystick secondarJoystick) {
        // if (MagazineSubsystem.magazineState == MagazineState.SHOOT) {
        //     intakeMotor.set(ControlMode.PercentOutput, 0);
        //     return;
        // }

        if (primaryJoystick.getRawAxis(RobotMap.PRIMARY_JOYSTICK_INTAKE_AXIS) >= 0.5) {
            intakeMotor.set(ControlMode.PercentOutput, -speed);
        } else if (primaryJoystick.getRawAxis(RobotMap.PRIMARY_JOYSTICK_OUTTAKE_AXIS) >= 0.5) {
            intakeMotor.set(ControlMode.PercentOutput, speed);
        } else if (secondarJoystick.getPOV() != -1) {
            intakeMotor.set(ControlMode.PercentOutput, -speed);
        } else {
            intakeMotor.set(ControlMode.PercentOutput, 0);
        }
    }
}