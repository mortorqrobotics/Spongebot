package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

public class Spinner {

    TalonSRX spinner;
    TalonSRX spinnerArm;
    Joystick primaryJoystick, secondarJoystick;

    public Spinner(Joystick primaryJoystick, Joystick secondarJoystick) {
        spinner = new TalonSRX(RobotMap.SPINNER_ID);
        spinnerArm = new TalonSRX(RobotMap.SPINNER_ARM_ID);
        this.primaryJoystick = primaryJoystick;
        this.secondarJoystick = secondarJoystick;
    }

    public void periodic() {
        if (secondarJoystick.getRawButton(RobotMap.SPINNER_SPIN_FORWARD_BUTTON)) {
            spinner.set(ControlMode.PercentOutput, .2);
        } else if (secondarJoystick.getRawButton(RobotMap.SPINNER_SPIN_BACKWARD_BUTTON)) {
            spinner.set(ControlMode.PercentOutput, -.2);
        } else {
            spinner.set(ControlMode.PercentOutput, 0);
        }

        if (primaryJoystick.getRawButton(RobotMap.SPINNER_ARM_FORWARD_BUTTON)) {
            spinnerArm.set(ControlMode.PercentOutput, 0.2);
        } else if (primaryJoystick.getRawButton(RobotMap.SPINNER_ARM_BACKWARD_BUTTON)) {
            spinnerArm.set(ControlMode.PercentOutput, -0.2);
        } else {
            spinnerArm.set(ControlMode.PercentOutput, 0);
        }
    }
}