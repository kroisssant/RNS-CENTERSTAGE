package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ScoringSubsystem {
    Servo   bratDreapta, bratStanga;
    Servo   pivot;
    Servo   pressureStanga, pressureDreapta;
    Servo   twist;

    public ScoringSubsystem(HardwareMap hardwareMap) {
        bratDreapta = hardwareMap.get(Servo.class, "bratDreapta");
        bratStanga = hardwareMap.get(Servo.class, "bratStranga");
        bratDreapta.setDirection(Servo.Direction.REVERSE);

        pivot = hardwareMap.get(Servo.class, "pivot");

        pressureStanga = hardwareMap.get(Servo.class, "pressureStanga");
        pressureDreapta = hardwareMap.get(Servo.class, "pressureDrepta");
        pressureDreapta.setDirection(Servo.Direction.REVERSE);

        twist = hardwareMap.get(Servo.class, "twist");
    }
    public void setPressure(double pos) {
         pressureDreapta.setPosition(pos);
         pressureStanga.setPosition(pos);
    }
    public void setTiwst(double pos) {
        twist.setPosition(pos);
    }
    public void setBrat(double pos) {
        bratDreapta.setPosition(pos);
        bratStanga.setPosition(pos);
    }
    public void setPivot(double pos) {
        pivot.setPosition(pos);
    }


}
