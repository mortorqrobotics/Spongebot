/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.swervedrivespecialties.exampleswerve.autonomous;

import edu.wpi.first.wpilibj.Servo;

public class ServoSubsystem {
	
	private static Servo spinner;
    private static final boolean invertSpinner = false;
    	
	private static final double MAX_ANGLE = 170.0;
	private static final double MIN_AGLE = 0.0;

	public ServoSubsystem(int id) {
		spinner = new Servo(id);
		
		setSpinnerAngle(50);
	}

	public void initDefaultCommand() {
	}
	
	public void setSpinnerAngle(double angle) {
		setAngle(angle, invertSpinner, spinner, MIN_AGLE, MAX_ANGLE);
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

}