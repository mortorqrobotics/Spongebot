package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class Intake {

    public static boolean intake = false;
    public static boolean reverse = false;

    private CANSparkMax intakeMotor = new CANSparkMax(RobotMap.INTAKE_ID, MotorType.kBrushed);

    private static final double speed = 0.55;

    public static void startIntake() {
        intake = true;
        reverse = false;
    }

    public static void startOuttake() {
        intake = true;
        reverse = true;
    }

    public static void stop() {
        intake = reverse = false;
    }

    public void intakePeriodic(Joystick joystick) {
        if (joystick.getRawAxis(3) > 0.5) {
            intakeMotor.set(speed);
        } else if (joystick.getRawAxis(2) > 0.5) {
            intakeMotor.set(-speed);
        } else {
            intakeMotor.set(0);
        }
        
        // if (intake) {
        //     intakeMotor.set((reverse) ? -speed : speed);
        // } else {
        //     intakeMotor.set(0);
        // }
    }
}