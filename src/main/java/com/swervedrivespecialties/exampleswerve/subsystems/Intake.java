package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake {

    public static boolean intake = false;
    public static boolean reverse = false;

    private static final int INTAKE_ID = 89;
    private CANSparkMax intakeMotor = new CANSparkMax(INTAKE_ID, MotorType.kBrushed);

    private static final double speed = 0.35;

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

    public void intakePeriodic() {
        if (intake) {
            intakeMotor.set((reverse) ? -speed : speed);
        }
    }
}