package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OutTake {
    public Servo claw, pressureStanga, pressureDreapta;
    public DcMotorEx glisieraDreapta, glisieraStanga;
    public Servo bratStanga, bratDreapta;

    public OutTake (HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");

        pressureDreapta = hardwareMap.get(Servo.class, "pressureDreapta");
        pressureStanga = hardwareMap.get(Servo.class, "pressureStanga");
        pressureStanga.setDirection(Servo.Direction.REVERSE);

        bratStanga = hardwareMap.get(Servo.class, "bratStanga");
        bratDreapta = hardwareMap.get(Servo.class, "bratDreapta");
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

    public void run(double power, int target) {
        glisieraDreapta.setTargetPosition(target);
        glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        glisieraDreapta.setPower(power);
        glisieraStanga.setPower(glisieraDreapta.getPower());
    }
    public void stopGlisiera() {
        glisieraStanga.setPower(0);
        glisieraDreapta.setPower(0);
    }
    public void setPressureDreapta(double pos) {
        pressureDreapta.setPosition(pos);
    }
    public void setPressureStanga(double pos) {
        pressureStanga.setPosition(pos);
    }
    public double getPressureDreapta(){return (double) pressureDreapta.getPosition();};
    public void setClaw(double pos) {
        claw.setPosition(pos);
    }

    public void setBrat(double pos) {
        bratDreapta.setPosition(pos);
        bratStanga.setPosition(pos);
    }

    public void glisieraPos(){
        if(glisieraDreapta.getCurrentPosition()>=Variables.target-Variables.toleranta && glisieraDreapta.getCurrentPosition()<=Variables.target+Variables.toleranta) {
            glisieraDreapta.setPower(0);
            glisieraStanga.setPower(0);
        }else if(glisieraDreapta.getCurrentPosition()>Variables.target-Variables.supression && glisieraDreapta.getCurrentPosition()<Variables.target-Variables.toleranta){
            glisieraDreapta.setTargetPosition(Variables.target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.supress_pow * Variables.pow);
            glisieraStanga.setPower(Variables.supress_pow * glisieraDreapta.getPower() * Variables.multiplier_non_encoder * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()<Variables.target-Variables.supression && glisieraDreapta.getCurrentPosition()<Variables.target-Variables.toleranta){
            glisieraDreapta.setTargetPosition(Variables.target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.pow);
            glisieraStanga.setPower(glisieraDreapta.getPower() * Variables.multiplier_non_encoder * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()<Variables.target+Variables.supression && glisieraDreapta.getCurrentPosition()>Variables.target+Variables.toleranta){
            glisieraDreapta.setTargetPosition(Variables.target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.supress_pow * Variables.pow);
            glisieraStanga.setPower(-1 * Variables.supress_pow * Variables.multiplier_non_encoder * glisieraDreapta.getPower() * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()>Variables.target+Variables.supression && glisieraDreapta.getCurrentPosition()>Variables.target+Variables.toleranta){
            glisieraDreapta.setTargetPosition(Variables.target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.pow);
            glisieraStanga.setPower( -1 * Variables.multiplier_non_encoder * glisieraDreapta.getPower() * Variables.speed_control);
        }
    }

    public void glisieraTelemetry(Telemetry telemetry1){
        telemetry1.addData("glisiera dreapta Pos", glisieraDreapta.getCurrentPosition());
        telemetry1.addData("glisiera stanga Pos", glisieraStanga.getCurrentPosition());
        telemetry1.update();
    }

    public int getPosition() {
        return glisieraDreapta.getCurrentPosition();
    }
}
