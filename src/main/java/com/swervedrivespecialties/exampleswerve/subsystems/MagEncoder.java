package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MagEncoder {
	/** Hardware */
	TalonSRX _talon;
	Joystick _joy = new Joystick(0);
	double offset;

    /* Nonzero to block the config until success, zero to skip checking */
    final int kTimeoutMs = 30;
	
    /**
	 * If the measured travel has a discontinuity, Note the extremities or
	 * "book ends" of the travel.
	 */
	final boolean kDiscontinuityPresent = true;
	final int kBookEnd_0 = 910;		/* 80 deg */
	final int kBookEnd_1 = 1137;	/* 100 deg */

	/**
	 * This function is called once new MagEncoder() is called in the Robot.robotInit()
	 */
	public MagEncoder(TalonSRX talonSRX, double offset) {
		_talon = talonSRX;
		this.offset = offset;

		/* Factory Default Hardware to prevent unexpected behaviour */
		_talon.configFactoryDefault();

		/* Seed quadrature to be absolute and continuous */
		initQuadrature();
		
		/* Configure Selected Sensor for Talon */
		_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,	// Feedback
											0, 											// PID ID
											kTimeoutMs);								// Timeout
	}

	public MagEncoder(int talonPort, double offset) {
		_talon = new TalonSRX(talonPort);
		this.offset = offset;

		/* Factory Default Hardware to prevent unexpected behaviour */
		_talon.configFactoryDefault();

		/* Seed quadrature to be absolute and continuous */
		initQuadrature();
		
		/* Configure Selected Sensor for Talon */
		_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,	// Feedback
											0, 											// PID ID
											kTimeoutMs);								// Timeout
	}

	public void periodic() {
		// SmartDashboard.putString("Encoder", toDeg(_talon.getSelectedSensorPosition(0)));
	}

	public double readAngle() {
		String angle_string = toDeg(_talon.getSelectedSensorPosition(0));
		double angle = Double.parseDouble(angle_string);
		return angle - offset;
	}

	/**
	 * Get the selected sensor register and print it 
	 */
	public void disabledPeriodic() {
		/**
		 * When button is pressed, seed the quadrature register. You can do this
		 * once on boot or during teleop/auton init. If you power cycle the 
		 * Talon, press the button to confirm it's position is restored.
		 */
		if (_joy.getRawButton(1)) {
			initQuadrature();
		}

		/**
		 * Quadrature is selected for soft-lim/closed-loop/etc. initQuadrature()
		 * will initialize quad to become absolute by using PWD
		 */
		int selSenPos = _talon.getSelectedSensorPosition(0);
		int pulseWidthWithoutOverflows = 
				_talon.getSensorCollection().getPulseWidthPosition() & 0xFFF;

		/**
		 * Display how we've adjusted PWM to produce a QUAD signal that is
		 * absolute and continuous. Show in sensor units and in rotation
		 * degrees.
		 */
		System.out.print("pulseWidPos:" + pulseWidthWithoutOverflows +
						 "   =>    " + "selSenPos:" + selSenPos);
		System.out.print("      ");
		System.out.print("pulseWidDeg:" + toDeg(pulseWidthWithoutOverflows) +
						 "   =>    " + "selSenDeg:" + toDeg(selSenPos));
		System.out.println();
	}

	/**
	 * Seed the quadrature position to become absolute. This routine also
	 * ensures the travel is continuous.
	 */
	public void initQuadrature() {
		/* get the absolute pulse width position */
		int pulseWidth = _talon.getSensorCollection().getPulseWidthPosition();

		/**
		 * If there is a discontinuity in our measured range, subtract one half
		 * rotation to remove it
		 */
		if (kDiscontinuityPresent) {

			/* Calculate the center */
			int newCenter;
			newCenter = (kBookEnd_0 + kBookEnd_1) / 2;
			newCenter &= 0xFFF;

			/**
			 * Apply the offset so the discontinuity is in the unused portion of
			 * the sensor
			 */
			pulseWidth -= newCenter;
		}

		/**
		 * Mask out the bottom 12 bits to normalize to [0,4095],
		 * or in other words, to stay within [0,360) degrees 
		 */
		pulseWidth = pulseWidth & 0xFFF;

		/* Update Quadrature position */
		_talon.getSensorCollection().setQuadraturePosition(pulseWidth, kTimeoutMs);
	}

	/**
	 * @param units CTRE mag encoder sensor units 
	 * @return degrees rounded to tenths.
	 */
	String toDeg(int units) {
		double deg = units * 360.0 / 4096.0;

		/* truncate to 0.1 res */
		deg *= 10;
		deg = (int) deg;
		deg /= 10;

		return "" + deg;
	}
}