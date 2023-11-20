package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    DcMotorEx intakeDreapta, intakeStanga;
    MotorGroup intake;
    Servo dropdown;
    public Intake(HardwareMap hardwareMap) {
        intakeDreapta = hardwareMap.get(DcMotorEx.class, "intakeDreapta");
        intakeStanga = hardwareMap.get(DcMotorEx.class, "intakeStanga");
        dropdown = hardwareMap.get(Servo.class, "dropdown");
        intakeStanga.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void setPower(double power) {
        intakeDreapta.setPower(power);
        intakeStanga.setPower(power);
    }
    public void setDropdown(double pos) {
        dropdown.setPosition(pos);
    }
    public void setZeroPowerBehaviour(DcMotorEx.ZeroPowerBehavior zpb) {
        intakeDreapta.setZeroPowerBehavior(zpb);
        intakeStanga.setZeroPowerBehavior(zpb);
    }
    public int[] getTicks(){
        if(intakeStanga.getMode() == DcMotor.RunMode.RUN_USING_ENCODER && intakeDreapta.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
            return new int[]{intakeStanga.getCurrentPosition(), intakeDreapta.getCurrentPosition()};
        }
        return new int[]{0, 0};
    }
}
