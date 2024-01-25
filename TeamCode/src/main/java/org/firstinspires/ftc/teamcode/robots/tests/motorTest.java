package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
@Config
public class motorTest extends LinearOpMode {
    DcMotorEx motor1, motor2, motor3, motor4, motor5, motor6, motor7, motor8;
    public static double pow;
    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "motor2");
        motor3 = hardwareMap.get(DcMotorEx.class, "motor3");
        motor4 = hardwareMap.get(DcMotorEx.class, "motor4");
        motor5 = hardwareMap.get(DcMotorEx.class, "motor5");
        motor6 = hardwareMap.get(DcMotorEx.class, "motor6");
        motor7 = hardwareMap.get(DcMotorEx.class, "motor7");
        motor8 = hardwareMap.get(DcMotorEx.class, "motor8");
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            if(gamepad1.a) {
                motor1.setPower(pow);
            }
            if(gamepad1.b) {
                motor2.setPower(pow);
            }
            if(gamepad1.x) {
                motor3.setPower(pow);
            }
            if(gamepad1.y) {
                motor4.setPower(pow);
            }
            if(gamepad1.dpad_down) {
                motor5.setPower(pow);
            }
            if(gamepad1.dpad_up) {
                motor6.setPower(pow);
            }
            if(gamepad1.dpad_left) {
                motor7.setPower(pow);
            }
            if(gamepad1.dpad_right) {
                motor8.setPower(pow);
            }
        }
    }
}
