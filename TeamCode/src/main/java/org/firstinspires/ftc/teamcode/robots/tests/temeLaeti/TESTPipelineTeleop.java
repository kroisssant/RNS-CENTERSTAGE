package org.firstinspires.ftc.teamcode.robots.tests.temeLaeti;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.opencv.core.Point;


@TeleOp
public class TESTPipelineTeleop extends LinearOpMode {

    private TestPipeline pipeline;
    private SampleMecanumDrive drive;
    private static Camera cam;

    Point center1, center2;
    Point middleScreen;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new SampleMecanumDrive(hardwareMap);
        pipeline = new TestPipeline();
        cam = new Camera();
        center1 = pipeline.CLOSEST_CENTERPOIT1;
        center2 = pipeline.CLOSEST_CENTERPOIT2;
        middleScreen = new Point(260, 240);


        cam.initCamera(pipeline, hardwareMap);

        Trajectory trajectoryToFirstElement = drive.trajectoryBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(center1.y, center1.x, Math.toRadians(center1.x - middleScreen.x)))
                .build();

        Trajectory trajectoryToSecondElement = drive.trajectoryBuilder(trajectoryToFirstElement.end())
                .lineToLinearHeading(new Pose2d(center2.y, center2.x, Math.toRadians(center2.x - middleScreen.x)))
                .build();


        waitForStart();

        while(opModeIsActive()) {

            drive.update();
            drive.followTrajectory(trajectoryToFirstElement);
            drive.followTrajectory(trajectoryToSecondElement);

            telemetry.update();
        }

        cam.camera.stopStreaming();
        cam.camera.closeCameraDevice();
        cam.webCam.close();

    }
}
