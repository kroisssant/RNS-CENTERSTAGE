package org.firstinspires.ftc.teamcode.Libs;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// For this motors should be set for Velocity Control FTCLIB motor class
public class DifferentialSwerveModule {
    int         gearRatio;
    double      WHEEL_DIAMETER;
    MotorEx     motorUp, motorDown;
    double             desiredWheelSpeedRPM = 0.0;
    double             desiredYawDegrees = 0.0;
    public DifferentialSwerveModule(int gearRatioMotorToModule, MotorEx motorUp, MotorEx motorDown, double WHEEL_DIAMETER) {
        this.gearRatio = gearRatioMotorToModule;
        this.motorUp = motorUp;
        this.motorDown = motorDown;
        this.WHEEL_DIAMETER = WHEEL_DIAMETER;
    }
    public void init() {
        motorDown.setRunMode(Motor.RunMode.VelocityControl);
        motorUp.setRunMode(Motor.RunMode.VelocityControl);

    }
    public double motorRPMToWheelRPM() {
        return(((motorUp.getVelocity()+motorDown.getVelocity())/2)*gearRatio);
    }
    public double motorRPMtoWheelYawn() {
        return(((motorUp.getVelocity()-motorDown.getVelocity())/2)*gearRatio);
    }
}
