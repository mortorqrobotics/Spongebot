package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagazineSubsystem {


    private static final int ONE_TURNING_DISTANCE = 72 / 14;

    // ONE_TURNING_DISTANCE / 6 = 60
    // private static final double MAGAZINE_ANGLE_OFFSET = -Math.toRadians(0.0);
    // private AnalogEncoder encoder = new AnalogEncoder(RobotMap.MAGAZINE_ENCODER_ID, MAGAZINE_ANGLE_OFFSET); 

    private CANSparkMax motor = new CANSparkMax(RobotMap.MAGAZINE_MOTOR_ID, MotorType.kBrushless);
    private CANPIDController m_pidController;

    double kP = 0.08; 
    double kI = 0.0001;
    double kD = 0.01; 
    double kIz = 0; 
    double kFF = 0; 
    double kMaxOutput = .20; 
    double kMinOutput = -.20;

    public static int rotation = 0;


    public MagazineSubsystem() {
        m_pidController = motor.getPIDController();

        // motor.getEncoder().setPosition(encoder.readAngle());

        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        m_pidController.setReference(rotation, ControlType.kPosition);

        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
    }

    public void magazinePeriodic() {
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);

        if((p != kP)) { m_pidController.setP(p); kP = p; }
        if((i != kI)) { m_pidController.setI(i); kI = i; }
        if((d != kD)) { m_pidController.setD(d); kD = d; }

        SmartDashboard.putNumber("Theoretical position", getTheoreticalPosition());
        SmartDashboard.putNumber("power", motor.getOutputCurrent());
        SmartDashboard.putNumber("magazine angle", motor.getEncoder().getPosition());

        m_pidController.setReference(getTheoreticalPosition(), ControlType.kPosition);
    }

    public static void nextPosition() {
        rotation++;
    }

    private double getTheoreticalPosition() {
        return 1.02857142857*rotation + .0296;
    }
}