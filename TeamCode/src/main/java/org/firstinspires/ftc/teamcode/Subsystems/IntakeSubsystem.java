package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;
import org.firstinspires.ftc.teamcode.Utils.RNSMotor;

public class IntakeSubsystem extends SubsystemBase {
    RNSMotor motorIntake;

    public boolean dropdownToggle = true;
    Servo dropdown;

    public IntakeSubsystem(HardwareMap hardwareMap){
        motorIntake = new RNSMotor(hardwareMap, HardwareConstants.ID_MOTOR_INTAKE);
        motorIntake.setZeroPowerBehavior(RNSMotor.ZeroPowerBehavior.FLOAT);
        dropdown = hardwareMap.get(Servo.class, HardwareConstants.ID_DROPDOWN);
        dropdownUp();

    }

    public void setDropdown(double pos){
        dropdown.setPosition(pos);
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

    public void dropdownUp(){
        dropdown.setPosition(Constants.DROPDOWN_SUS);
        dropdownToggle = false;
    }
    public void dropdownDown(){
        dropdown.setPosition(Constants.DROPDOWN_JOS);
        dropdownToggle = true;
    }

    public boolean isDropDownDown() {
        return dropdown.getPosition() == Constants.DROPDOWN_JOS;
    }

    public void end(){
        motorIntake.set(0);
    }
}