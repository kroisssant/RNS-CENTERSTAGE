package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;


@TeleOp
public class motorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        waitForStart();
        while(opModeIsActive()) {
            drive.leftFront.setPower(1);
            sleep(5000);
            drive.leftFront.setPower(0);
            drive.leftRear.setPower(1);
            sleep(5000);
            drive.leftRear.setPower(0);
            drive.rightRear.setPower(1);
            sleep(5000);
            drive.rightRear.setPower(0);
            drive.rightFront.setPower(1);
            sleep(5000);
            drive.rightFront.setPower(0);
        }
    }
}
