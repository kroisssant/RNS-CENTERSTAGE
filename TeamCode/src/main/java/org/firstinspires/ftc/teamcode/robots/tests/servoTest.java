package org.firstinspires.ftc.teamcode.robots.tests;

import androidx.core.location.GnssStatusCompat;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.security.Provider;

@TeleOp
@Config
public class servoTest extends LinearOpMode {
    Servo servo1, servo2, servo3, servo4, servo5, servo6, servo7;
    public static double pos;
    @Override
    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo3 = hardwareMap.get(Servo.class, "servo3");
        servo4 = hardwareMap.get(Servo.class, "servo4");
        servo5 = hardwareMap.get(Servo.class, "servo5");
        servo6 = hardwareMap.get(Servo.class, "servo6");
        servo7 = hardwareMap.get(Servo.class, "servo7");
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            if(gamepad1.a) {
                servo1.setPosition(pos);
            }
            if(gamepad1.b) {
                servo2.setPosition(pos);
            }
            if(gamepad1.x) {
                servo3.setPosition(pos);
            }
            if(gamepad1.y) {
                servo4.setPosition(pos);
            }
            if(gamepad1.dpad_down) {
                servo5.setPosition(pos);
            }
            if(gamepad1.dpad_up) {
                servo6.setPosition(pos);
            }
            if(gamepad1.dpad_left) {
                servo7.setPosition(pos);
            }
        }

    }
}
