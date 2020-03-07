package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

public class Tube {

    private final Joystick m_stick;
    private final CANSparkMax telescope, winch;

    public static boolean overideLimit = false;

    public Tube(final Joystick m_stick) {
        telescope = new CANSparkMax(RobotMap.TELESCOPE, MotorType.kBrushless);
        winch = new CANSparkMax(RobotMap.WINCH, MotorType.kBrushless);
        this.m_stick = m_stick;
    }

    public void periodic() {
         // Set motor output to joystick value

        double speed = 0;
        if (m_stick.getRawButton(5)) {
            speed = -.2;
        } else if (m_stick.getRawButton(6)) {
            speed = .3;
        }

        overideLimit = true;

        if (speed > 0 && telescope.getEncoder().getPosition() >= 29 && !overideLimit) {
            telescope.stopMotor();
        } else if(speed < 0 && telescope.getEncoder().getPosition() <= 1 && !overideLimit) {
            telescope.stopMotor();
        } else {
            telescope.set(speed);
        }

        // WINCH MUST HAVE A NEGATIVE SPEED!!!!!
        double winchSpeed = -.50;

        if (m_stick.getRawButton(RobotMap.WINCH_BUTTON))
            winch.set(winchSpeed);
        else
            winch.set(0);

        SmartDashboard.putNumber("telescope position", telescope.getEncoder().getPosition());
    }
}