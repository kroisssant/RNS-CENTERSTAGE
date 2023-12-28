package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;

public class IntakeSubsystem extends SubsystemBase {
    Motor motorIntake;

    public IntakeSubsystem(HardwareMap hardwareMap){
        motorIntake = new Motor(hardwareMap, HardwareConstants.ID_MOTOR_INTAKE);
        motorIntake.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
    }

    public void setIntakePower(double power){
        motorIntake.set(power);
    }

    public void runFwd(){
        motorIntake.set(Constants.INTAKE_POWER);
    }

    public void runRvs(){
        motorIntake.set(-Constants.INTAKE_POWER);
    }

    public void end(){
        motorIntake.set(0);
    }
}
