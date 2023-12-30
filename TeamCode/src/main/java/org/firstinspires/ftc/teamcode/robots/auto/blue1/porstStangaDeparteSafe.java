package org.firstinspires.ftc.teamcode.robots.auto.blue1;

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
public class porstStangaDeparteSafe extends LinearOpMode {
    IntakeOld intake;
    OutTakeOld outtake;
    SampleMecanumDrive drive;
    OpenCvCamera camera;
    HSVPipelineAuto pipeline = new HSVPipelineAuto(1);
    public int caz = pipeline.caz;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new IntakeOld(hardwareMap);
        outtake = new OutTakeOld(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);

        initOpenCV();
        Trajectory movDreapta = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToLinearHeading(new Pose2d(24, 0, Math.toRadians(-90)))
                .build();
        Trajectory galbenDreapta1 = drive.trajectoryBuilder(movDreapta.end())
                .lineToConstantHeading(new Vector2d(60, 0)).build();
        Trajectory galbenDreapta2 = drive.trajectoryBuilder(galbenDreapta1.end())
                .lineToConstantHeading(new Vector2d(60, 74)).build();
        Trajectory galbenDreapta3 = drive.trajectoryBuilder(galbenDreapta2.end())
                .lineToConstantHeading(new Vector2d(30, 92)).build();;
        Trajectory park = drive.trajectoryBuilder(galbenDreapta3.end())
                .lineToConstantHeading(new Vector2d(0, 82)).build();
        Trajectory park1 = drive.trajectoryBuilder(park.end())
                .lineToConstantHeading(new Vector2d(0, 100)).build();

        Trajectory movStanga = drive.trajectoryBuilder(new Pose2d(0, 0 ,0))
                .lineToLinearHeading(new Pose2d(24, 0, Math.toRadians(90))).build();
        Trajectory galbenStanga1 = drive.trajectoryBuilder(movStanga.end())
                .lineToConstantHeading(new Vector2d(60, 0)).build();
        Trajectory galbenStanga2 = drive.trajectoryBuilder(galbenStanga1.end())
                .lineToConstantHeading(new Vector2d(60, 54)).build();
        Trajectory galbenStanga3 = drive.trajectoryBuilder(galbenStanga2.end())
                .lineToLinearHeading(new Pose2d(22, 92, Math.toRadians(-90))).build();
        Trajectory parkStanga = drive.trajectoryBuilder(galbenStanga3.end())
                .lineToConstantHeading(new Vector2d(5, 80))
                .build();
        Trajectory partStanga1 = drive.trajectoryBuilder(parkStanga.end())
                .lineToConstantHeading(new  Vector2d(5, 92))
                .build();

        Trajectory movCenter = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToConstantHeading(new Vector2d(26, 0))
                .build();
        Trajectory galben1 = drive.trajectoryBuilder(movCenter.end())
                .lineToConstantHeading(new Vector2d(24, -20)).build();
        Trajectory galben2 = drive.trajectoryBuilder(galben1.end())
                .lineToConstantHeading(new Vector2d(60, -10)).build();
        Trajectory galben3 = drive.trajectoryBuilder(galben2.end())
                .lineToLinearHeading(new Pose2d(60, 54, Math.toRadians(-90))).build();
        Trajectory galben4 = drive.trajectoryBuilder(galben3.end())
                .lineToConstantHeading(new Vector2d(14, 100)).build();
        Trajectory parkCenter = drive.trajectoryBuilder(galben4.end())
                .lineToConstantHeading(new Vector2d(0, 80))
                .build();
        Trajectory parkCenter1 = drive.trajectoryBuilder(parkCenter.end())
                .lineToConstantHeading(new Vector2d(0, 100))
                .build();

        intake.setDropdown(0);
        outtake.setPressureStanga(Variables.pressureStangaClose);
        outtake.setPressureDreapta(Variables.pressureDreaptaClose);
        outtake.setBrat(Variables.bratJos);
        outtake.setClaw(Variables.pivotJos);
        waitForStart();
        if(pipeline.getCaz() == 2) {
            drive.followTrajectory(movDreapta);
            intake.setPower(-0.45);
            sleep(100);
            intake.setPower(0);
            drive.followTrajectory(galbenDreapta1);
            drive.followTrajectory(galbenDreapta2);
            drive.followTrajectory(galbenDreapta3);
            outtake.glisieraPos(500);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(100);
            outtake.glisieraPos(200);
            sleep(500);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(500);
            outtake.glisieraPos(500);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(1000);
            outtake.glisieraPos(0);
            drive.followTrajectory(park);
            drive.followTrajectory(park1);
        } else if(pipeline.getCaz() == 3) {
            drive.followTrajectory(movStanga);
            intake.setPower(-0.45);
            sleep(100);
            intake.setPower(0);
            drive.followTrajectory(galbenStanga1);
            drive.followTrajectory(galbenStanga2);
            drive.followTrajectory(galbenStanga3);
            outtake.glisieraPos(500);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(100);
            outtake.glisieraPos(200);
            sleep(500);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(500);
            outtake.glisieraPos(500);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(1000);
            outtake.glisieraPos(0);
            drive.followTrajectory(parkStanga);
            drive.followTrajectory(partStanga1);
        } else if(pipeline.getCaz() ==1 ) {
            drive.followTrajectory(movCenter);
            intake.setPower(-0.45);
            sleep(100);
            intake.setPower(0);
            drive.followTrajectory(galben1);
            drive.followTrajectory(galben2);
            drive.followTrajectory(galben3);
            drive.followTrajectory(galben4);
            outtake.glisieraPos(500);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(100);
            outtake.glisieraPos(200);
            sleep(500);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(500);
            outtake.glisieraPos(500);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(1000);
            outtake.glisieraPos(0);
            drive.followTrajectory(parkCenter);
            drive.followTrajectory(parkCenter1);
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

