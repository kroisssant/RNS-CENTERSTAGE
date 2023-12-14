package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "setBratPos")
@Config
public class setServoBratPos extends LinearOpMode {
    Servo servo1, servo2;
    public static double pos = 0.1;
    @Override
    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo2.setDirection(Servo.Direction.REVERSE);
        servo1.setPosition(0.1);
        servo2.setPosition(0.1);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            servo1.setPosition(pos);
            servo2.setPosition(pos);
        }
    }
}
