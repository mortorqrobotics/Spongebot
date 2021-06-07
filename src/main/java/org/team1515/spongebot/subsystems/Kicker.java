/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team1515.spongebot.subsystems;

import org.team1515.spongebot.RobotMap;
import org.team1515.spongebot.subsystems.MagazineSubsystem.MagazineState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;

public class Kicker {
	
	private static Servo spinner1, spinner2;
    private static final boolean invertSpinner = false;
    	
	private static final double MAX_ANGLE = 90.0;
	private static final double MIN_AGLE = 0.0;

	public static boolean moving = false;

	public long startTime = 0;
	public boolean moved = false;

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
		if (MagazineSubsystem.magazineState == MagazineState.SHOOT) {
			if (!moving && joystick.getRawButton(RobotMap.SHOOT_ONE_POWER_CELL)) {
				push();
			}
			
			if (moving && System.currentTimeMillis() - startTime >= 200) {
				goBack();
			}
		}
	}

	public boolean kicked() {
		if (MagazineSubsystem.magazineState == MagazineState.SHOOT) {
			if (!moving && !moved) {
				moved = true;
				push();
			}
			
			if (moving && System.currentTimeMillis() - startTime >= 200) {
				return autonomousGoBack();
			}
		}

		if (!moving && moved) {
			timesRotatedInAutonomous++;
			MagazineSubsystem.nextShootingPosition();
			return true;
		}

		return false;
	}

	public void goBack() {
		int angle = 0;

		spinner2.setAngle(angle);
		spinner1.setAngle(angle);

		int goBackTime = 450;

		if (System.currentTimeMillis() - startTime >= goBackTime) {
			moving = false;
		}
	}

	public int timesRotatedInAutonomous = 0;

	public boolean autonomousGoBack() {
		goBack();

		if (System.currentTimeMillis() - startTime >= 650) {
			moving = false;
			MagazineSubsystem.nextShootingPosition();
			timesRotatedInAutonomous++;

			return true;
		}

		return false;
	}

	public int getNumberOfTimesRotated() {
		return timesRotatedInAutonomous;
	}

	public void push() {
		double angle = 45;

		spinner2.setAngle(angle);
		spinner1.setAngle(angle);

		startTime = System.currentTimeMillis();
		moving = true;

		// if (spinner2.getAngle() >= MAX_ANGLE) {
		// 	push = false;
		// }
	}
}