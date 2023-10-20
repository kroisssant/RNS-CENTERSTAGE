package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;

@TeleOp(name = "test slide", group = "test")
public class setPowerToMotor extends LinearOpMode {
    GAMEPAD gamepad;
    DcMotorEx motor1, motor2;
    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "motor2");
        gamepad = new GAMEPAD(this.gamepad1, this.telemetry);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            gamepad.run();
            if(gamepad.left_bumper.toggle) {
                motor1.setPower(1);
                motor2.setPower(1);
            } else {
                motor1.setPower(0);
                motor2.setPower(0);
            }
        }
    }
}
