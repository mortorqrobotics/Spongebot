/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.swervedrivespecialties.exampleswerve.subsystems;

import com.swervedrivespecialties.exampleswerve.RobotMap;
import com.swervedrivespecialties.exampleswerve.subsystems.MagazineSubsystem.MagazineState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Kicker {
	
	private static Servo spinner1, spinner2;
    private static final boolean invertSpinner = false;
    	
	private static final double MAX_ANGLE = 90.0;
	private static final double MIN_AGLE = 0.0;

	public static boolean moving = false;

	private long startTime = 0;

	public Kicker(int id1, int id2) {
		spinner1 = new Servo(id1);
		spinner2 = new Servo(id2);
		goBack();
	}

	public void initDefaultCommand() {
	}
	
	public void setSpinnerAngle(double angle) {
		setAngle(angle, invertSpinner, spinner1, MIN_AGLE, MAX_ANGLE);
		setAngle(-angle, invertSpinner, spinner2, MIN_AGLE, MAX_ANGLE);
	}

	private void setAngle(double angle, boolean invert, Servo servo,
			double minAngle, double maxAngle) {

		if(minAngle < 0) {
			minAngle = 0.0;
		}
		
		if(maxAngle > 170.0){
			maxAngle = 170.0;
		}
		
		if(angle > maxAngle) {
			angle = maxAngle;
		} else if(angle < minAngle) {
			angle = minAngle;
		}
		
		if(invert) {
			angle = maxAngle - angle;
		}
		
		servo.setAngle(angle);

    }

	public void servoPeriodic(Joystick joystick) {
		SmartDashboard.putNumber("LEFT SERVO", spinner2.get());
		SmartDashboard.putNumber("RIGHT SERVO", spinner1.get());

		if (MagazineSubsystem.magazineState == MagazineState.SHOOT) {
			if (!moving && joystick.getRawButton(RobotMap.SHOOT_ONE_POWER_CELL)) {
				push();
			}
			
			if (moving && System.currentTimeMillis() - startTime >= 400) {
				goBack();
			}
		}
	}

	private void goBack() {
		int angle = 5;

		spinner2.setAngle(angle);
		spinner1.setAngle(170 - angle);

		if (System.currentTimeMillis() - startTime >= 700) {
			moving = false;
		}
	}

	private void push() {
		double angle = MAX_ANGLE;

		spinner2.setAngle(angle);
		spinner1.setAngle(170 - angle);

		startTime = System.currentTimeMillis();
		moving = true;

		// if (spinner2.getAngle() >= MAX_ANGLE) {
		// 	push = false;
		// }
	}
}