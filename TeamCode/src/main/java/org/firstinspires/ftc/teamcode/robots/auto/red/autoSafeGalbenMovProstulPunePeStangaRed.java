package org.firstinspires.ftc.teamcode.robots.auto.red;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robots.auto.HSVPipelineAuto;
import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@Autonomous
public class autoSafeGalbenMovProstulPunePeStangaRed extends LinearOpMode {
    IntakeOld intake;
    OutTakeOld outtake;
    SampleMecanumDrive drive;
    OpenCvCamera camera;
    HSVPipelineAuto pipeline = new HSVPipelineAuto(2);
    public int caz = pipeline.caz;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new IntakeOld(hardwareMap);
        outtake = new OutTakeOld(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);

        initOpenCV();

        Trajectory putPurpleCenter = drive.trajectoryBuilder(new Pose2d(0, 0, 0), true)
                .lineToLinearHeading(new Pose2d(24, 0 , Math.toRadians(0)))
                .build();
        Trajectory putGalbenCenter = drive.trajectoryBuilder(putPurpleCenter.end())
                .lineToLinearHeading(new Pose2d(28, -40, Math.toRadians(+90)))
                .build();
        Trajectory park1 = drive.trajectoryBuilder(putGalbenCenter.end())
                .lineToConstantHeading(new Vector2d(0, -30))
                .build();
        Trajectory park = drive.trajectoryBuilder(park1.end())
                .lineToConstantHeading(new Vector2d(0, -40))
                .build();

        Trajectory putPurpleRight = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToLinearHeading(new Pose2d(20, +5, Math.toRadians(+90)))
                .build();
        Trajectory putGalbenRight = drive.trajectoryBuilder(putPurpleRight.end())
                .lineToConstantHeading(new Vector2d(42, -35))
                .build();
        Trajectory parkRight = drive.trajectoryBuilder(putGalbenRight.end())
                .lineToConstantHeading(new Vector2d(0, -30))
                .build();
        Trajectory parkRight1 = drive.trajectoryBuilder(parkRight.end())
                .lineToConstantHeading(new Vector2d(0, -40))
                .build();

        Trajectory putPurpleLeft = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToLinearHeading(new Pose2d(16, -15, Math.toRadians(0)))
                .build();
        Trajectory putGalbenLeft = drive.trajectoryBuilder(putPurpleLeft.end())
                .lineToLinearHeading(new Pose2d(52, -45, Math.toRadians(+90))).build(); // 26
        Trajectory parkLeft = drive.trajectoryBuilder(putGalbenLeft.end())
                .lineToConstantHeading(new Vector2d(0, -30))
                .build();
        Trajectory parkLeft1 = drive.trajectoryBuilder(parkLeft.end())
                .lineToConstantHeading(new Vector2d(0, -40))
                .build();
        intake.setDropdown(0);
        outtake.setPressureStanga(Variables.pressureStangaClose);
        outtake.setPressureDreapta(Variables.pressureDreaptaClose);
        outtake.setBrat(Variables.bratJos);
        outtake.setClaw(Variables.pivotJos);

        waitForStart();
        if(pipeline.getCaz() == 1) {
            drive.followTrajectory(putPurpleCenter);
            intake.setPower(-0.45);
            sleep(100);
            intake.setPower(0);
            drive.followTrajectory(putGalbenCenter);
            outtake.glisieraPos(500);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(100);
            outtake.glisieraPos(200);
            sleep(500);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            sleep(500);
            outtake.glisieraPos(500);
            outtake.setPressureDreapta(Variables.pressureDreaptaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(1000);
            outtake.glisieraPos(0);
            drive.followTrajectory(park1);
            drive.followTrajectory(park);
        } else if(pipeline.getCaz() == 2) {
            drive.followTrajectory(putPurpleRight);
            intake.setPower(-0.2);
            sleep(100);
            intake.setPower(0);
            drive.followTrajectory(putGalbenRight);
            outtake.glisieraPos(500);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(100);
            outtake.glisieraPos(200);
            sleep(500);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            sleep(500);
            outtake.glisieraPos(500);
            outtake.setPressureDreapta(Variables.pressureDreaptaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(1000);
            outtake.glisieraPos(0);
            drive.followTrajectory(parkRight);
            drive.followTrajectory(parkRight1);
        }
        else if(pipeline.getCaz() == 3) {
            drive.followTrajectory(putPurpleLeft);
            intake.setPower(-0.2);
            sleep(100);
            intake.setPower(0);
            drive.followTrajectory(putGalbenLeft);
            outtake.glisieraPos(500);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(100);
            outtake.glisieraPos(200);
            sleep(500);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            sleep(500);
            outtake.glisieraPos(500);
            outtake.setPressureDreapta(Variables.pressureDreaptaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(1000);
            outtake.glisieraPos(0);
            drive.followTrajectory(parkLeft);
            drive.followTrajectory(parkLeft1);
        }

    }
    private void initOpenCV() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {}
        });
    }
}
