package org.firstinspires.ftc.teamcode.robots.tests.junk;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;


@TeleOp
@Disabled
public class motorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Servo servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();
        while(opModeIsActive()&& !isStopRequested()) {


//            drive.leftFront.setPower(1);
//            sleep(5000);
//            drive.leftFront.setPower(0);
//            drive.leftRear.setPower(1);
//            sleep(5000);
//            drive.leftRear.setPower(0);
//            drive.rightRear.setPower(1);
//            sleep(5000);
//            drive.rightRear.setPower(0);
//            drive.rightFront.setPower(1);
//            sleep(5000);
//            drive.rightFront.setPower(0);
        }
    }
}
