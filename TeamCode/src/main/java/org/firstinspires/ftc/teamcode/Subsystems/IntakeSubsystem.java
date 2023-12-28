package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;

public class IntakeSubsystem extends SubsystemBase {
    Motor motorIntake;

    public IntakeSubsystem(HardwareMap hardwareMap){
        motorIntake = new Motor(hardwareMap, HardwareConstants.ID_MOTOR_INTAKE);
    }

    public void setIntakePower(double power){
        motorIntake.set(power);
    }
}
