package org.firstinspires.ftc.teamcode.robots.tests.junk;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@TeleOp
@Disabled
public class setPower extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            drive.rightRear.setPower(1);
        }
    }
}
