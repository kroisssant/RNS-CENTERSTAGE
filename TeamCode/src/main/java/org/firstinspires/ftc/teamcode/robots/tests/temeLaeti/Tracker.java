package org.firstinspires.ftc.teamcode.robots.tests.temeLaeti;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;


@TeleOp
public class Tracker extends LinearOpMode {

    private HSVpipeline hsVpipeline;
    private SampleMecanumDrive drive;
    private static Camera cam;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new SampleMecanumDrive(hardwareMap);
        hsVpipeline = new HSVpipeline();
        cam = new Camera();

        cam.initCamera(hsVpipeline, hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            drive.update();

            if (Math.abs(hsVpipeline.ACTUAL_CENTERPOINT.x - hsVpipeline.TARGET_CENTERPOINT.x) < 80 &&
                    Math.abs(hsVpipeline.ACTUAL_RADIUS - hsVpipeline.TARGET_RADIUS) < 20) {
                //stop cuz u re fine bestie
                drive.setWeightedDrivePower(new Pose2d(0, 0, 0));

            } else if (Math.abs(hsVpipeline.ACTUAL_CENTERPOINT.x - hsVpipeline.TARGET_CENTERPOINT.x) > 80) {
                //heading
                if (hsVpipeline.ACTUAL_CENTERPOINT.x < hsVpipeline.TARGET_CENTERPOINT.x) {
                    drive.setWeightedDrivePower(new Pose2d(
                            0,
                            0,
                            -Range.clip(Math.abs(hsVpipeline.TARGET_CENTERPOINT.x - hsVpipeline.ACTUAL_CENTERPOINT.x) / 70,
                                    0,
                                    0.2)
                    ));
                } else {
                    drive.setWeightedDrivePower(new Pose2d(
                            0,
                            0,
                            Range.clip(Math.abs(hsVpipeline.TARGET_CENTERPOINT.x - hsVpipeline.ACTUAL_CENTERPOINT.x) / 70,
                                    0,
                                    0.2)
                    ));
                }

            } else if (Math.abs(hsVpipeline.ACTUAL_RADIUS - hsVpipeline.TARGET_RADIUS) > 25) {
                //front/back movement
                if (hsVpipeline.ACTUAL_RADIUS < hsVpipeline.TARGET_RADIUS) {
                    drive.setWeightedDrivePower(new Pose2d(
                            -0.3,
                            0,
                            0
                    ));
                } else {
                    drive.setWeightedDrivePower(new Pose2d(
                            0.3,
                            0,
                            0
                    ));
                }

            }


            telemetry.addData("actual centerpoint y", hsVpipeline.ACTUAL_CENTERPOINT.y);
            telemetry.addData("target centerpoint y", hsVpipeline.TARGET_CENTERPOINT.y);
            telemetry.addLine();
            telemetry.addData("actual centerpoint x", hsVpipeline.ACTUAL_CENTERPOINT.x);
            telemetry.addData("target centerpoint x", hsVpipeline.TARGET_CENTERPOINT.x);
            telemetry.addLine();
            telemetry.addData("actual radius", hsVpipeline.ACTUAL_RADIUS);
            telemetry.addData("target radius", hsVpipeline.TARGET_RADIUS);

            telemetry.update();
        }

        cam.camera.stopStreaming();
        cam.camera.closeCameraDevice();
        cam.webCam.close();

    }
}
