package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class encoderTest extends LinearOpMode {
    DcMotorEx f, s;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), this.telemetry);
        f = hardwareMap.get(DcMotorEx.class ,"rightFront");
        s = hardwareMap.get(DcMotorEx.class, "rightRear");
        while(true) {
            telemetry.addData("f", f.getCurrentPosition());
            telemetry.addData("s", s.getCurrentPosition());
            telemetry.update();
        }
    }
}
