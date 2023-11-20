package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OutTake {
    Servo claw, pressureStanga, pressureDreapta;
    DcMotorEx glisieraDreapta, glisieraStanga;
    Servo bratStanga, bratDreapta;

    public OutTake (HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");

        pressureDreapta = hardwareMap.get(Servo.class, "pressureDreapta");
        pressureStanga = hardwareMap.get(Servo.class, "pressureStanga");
        pressureStanga.setDirection(Servo.Direction.REVERSE);

        bratDreapta = hardwareMap.get(Servo.class, "bratDreapta");
        bratStanga = hardwareMap.get(Servo.class, "bratStanga");
        bratStanga.setDirection(Servo.Direction.REVERSE);

        glisieraDreapta = hardwareMap.get(DcMotorEx.class, "glisieraDreapta");
        glisieraStanga = hardwareMap.get(DcMotorEx.class, "glisieraStanga");

        glisieraDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glisieraStanga.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setZeroPowerBeh(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setZeroPowerBeh(DcMotorEx.ZeroPowerBehavior beh) {
        glisieraDreapta.setZeroPowerBehavior(beh);
        glisieraStanga.setZeroPowerBehavior(beh);
    }

    public void setTargetPosition(int ticks) {
        glisieraDreapta.setTargetPosition(ticks);
    }
    public void run(double power) {
        glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        glisieraDreapta.setPower(power);
        glisieraStanga.setPower(power);
    }
    public void stopGlisiera() {
        glisieraStanga.setPower(0);
        glisieraDreapta.setPower(0);
    }
    public void setClawDreapta(double pos) {
        pressureDreapta.setPosition(pos);
    }
    public void setClawStanga(double pos) {
        pressureStanga.setPosition(pos);
    }

    public void setClaw(double pos) {
        claw.setPosition(pos);
    }

    public void setBrat(double pos) {
        bratDreapta.setPosition(pos);
        bratStanga.setPosition(pos);
    }

    public int getPosition() {
        return glisieraDreapta.getCurrentPosition();
    }
}
