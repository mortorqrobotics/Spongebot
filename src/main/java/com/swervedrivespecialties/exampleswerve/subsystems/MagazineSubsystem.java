package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

public class MagazineSubsystem {

    private static final double MAGAZINE_ANGLE_OFFSET = -Math.toRadians(0.0);

    private static final int ONE_TURNING_DISTANCE = 360 / 5;
    private static final double TURNING_SPEED = .5;

    private AnalogEncoder encoder = new AnalogEncoder(RobotMap.MAGAZINE_ENCODER_ID, MAGAZINE_ANGLE_OFFSET); 
    private CANSparkMax motor = new CANSparkMax(RobotMap.MAGAZINE_MOTOR_ID, MotorType.kBrushless);

    private boolean isTurning = false;
    private double startingPosition;

    public MagazineSubsystem() {

    }

    public void turn() {
        startingPosition = encoder.readAngle();
        isTurning = true;
    }

    public void magazinePeriodic() {
        if (startingPosition + ONE_TURNING_DISTANCE >= encoder.readAngle()) {
            isTurning = false;
        }

        if (isTurning)
            motor.set(TURNING_SPEED);
        else
            motor.set(0);
    }

}