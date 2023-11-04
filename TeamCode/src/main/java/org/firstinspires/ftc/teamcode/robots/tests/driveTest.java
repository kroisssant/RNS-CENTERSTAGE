package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDriveCancelable;

import java.sql.ResultSet;

@TeleOp
public class driveTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        waitForStart();
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        while(opModeIsActive()) {
            if(gamepad1.left_stick_y > 0.3) {
                drive.setWeightedDrivePower(new Pose2d(1 ,0, gamepad1.right_stick_x));
            } else {
                drive.setWeightedDrivePower(new Pose2d(0, 0, 0));
            }

            drive.getWheelPositions();

            telemetry.addData("1", drive.getWheelPositions().get(0));
            telemetry.addData("2", drive.getWheelPositions().get(1));
            telemetry.addData("3", drive.getWheelPositions().get(2));
            telemetry.addData("4", drive.getWheelPositions().get(3));
            telemetry.update();

            drive.update();
        }
    }
}
