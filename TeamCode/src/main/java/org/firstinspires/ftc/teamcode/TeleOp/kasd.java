package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class kasd extends LinearOpMode {
    Servo bratStanga, bratDreapta;
    @Override
    public void runOpMode() throws InterruptedException {
        bratStanga = hardwareMap.get(Servo.class, "bratStanga");
        bratDreapta = hardwareMap.get(Servo.class, "bratDreapta");
        bratStanga.setPosition(0.07);
        bratDreapta.setPosition(0.07);
    }
}
