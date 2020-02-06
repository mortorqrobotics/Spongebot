package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

public class MagazineSubsystem {


    private static final int ONE_TURNING_DISTANCE = 72 / 14;

    // ONE_TURNING_DISTANCE / 6 = 60

    public double getPositionFromDegree(double degree){
        return ONE_TURNING_DISTANCE / (360.0 / degree);
    }
    // private static final double MAGAZINE_ANGLE_OFFSET = -Math.toRadians(0.0);
    // private AnalogEncoder encoder = new AnalogEncoder(RobotMap.MAGAZINE_ENCODER_ID, MAGAZINE_ANGLE_OFFSET); 

    private CANSparkMax motor = new CANSparkMax(RobotMap.MAGAZINE_MOTOR_ID, MotorType.kBrushless);
    private CANPIDController m_pidController;

    double kP = 0.1; 
    double kI = 1e-4;
    double kD = 1; 
    double kIz = 0; 
    double kFF = 0; 
    double kMaxOutput = 1; 
    double kMinOutput = -1;

    public MagazineSubsystem() {
        m_pidController = motor.getPIDController();

        // motor.getEncoder().setPosition(encoder.readAngle());

        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);
    }

    public void magazinePeriodic() {

        // set velocity, set voltage, position

        double rotation = getPositionFromDegree(360 / 5);

        m_pidController.setReference(rotation, ControlType.kPosition);
    }

}