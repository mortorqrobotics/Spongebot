package com.swervedrivespecialties.exampleswerve.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;

public class AnalogEncoder {

    private DoubleSupplier angleSupplier;
    
    public AnalogEncoder(int port, double offset) {
        angleEncoder(new AnalogInput(port), offset);
    }
    
    public void angleEncoder(AnalogInput encoder, double offset) {
        angleSupplier = () -> {
            double angle = (1.0 - encoder.getVoltage() / RobotController.getVoltage5V()) * 2.0 * Math.PI;
            angle += offset;
            angle %= 2.0 * Math.PI;
            if (angle < 0.0) {
                angle += 2.0 * Math.PI;
            }

            return angle;
        };
    }

    public double readAngle() {
        return angleSupplier.getAsDouble();
    }
}