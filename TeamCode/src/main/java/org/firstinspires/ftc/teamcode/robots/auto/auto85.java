package org.firstinspires.ftc.teamcode.robots.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class auto85 extends LinearOpMode {
    OpenCvCamera camera;
    int caz = 1;
    SampleMecanumDrive drive;
    Intake intake;
    OutTake outtake;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap);
        outtake = new OutTake(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        outtake.setPressureStanga(Variables.pressureStangaOpen);
        outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
        outtake.setBrat(Variables.bratJos);
        outtake.setClaw(Variables.pivotJos);
        Trajectory toGalbenAlianta = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                        .lineToConstantHeading(new Vector2d(18, 0))
                                .build();
        Trajectory toBackdrop1 = drive.trajectoryBuilder(toGalbenAlianta.end())
                        .lineToConstantHeading(new Vector2d(0, 0)).addTemporalMarker(0.5,() -> {intake.setPower(0.4);})
                                .build();
        Trajectory toBackdropRight = drive.trajectoryBuilder(toBackdrop1.end())
                        .lineToLinearHeading(new Pose2d(-47, 42, 0))
                                .build();
        Trajectory toBackdropCenter = drive.trajectoryBuilder(toBackdrop1.end())
                        .lineToLinearHeading(new Pose2d(-48, 35, 0))
                                .build();
        Trajectory toBackdropLeft = drive.trajectoryBuilder(toBackdrop1.end())
                        .lineToLinearHeading(new Pose2d(48, 28, 0))
                                .build();
        Trajectory putPurpleRight = drive.trajectoryBuilder(toBackdropRight.end())
                        .lineToLinearHeading(new Pose2d(-7, 30, 0))
                            .build();
        TrajectorySequence toAlliancePurpleRight = drive.trajectorySequenceBuilder(putPurpleRight.end())
                .lineToConstantHeading(new Vector2d(-15, 40))
                .lineToConstantHeading(new Vector2d(-15, 0))
                .lineToConstantHeading(new Vector2d(37, 0)).addTemporalMarker(0.01, () -> {
                    intake.setPower(-0.1);
                })
                .build();
        Trajectory putAlliancePurpleRight = drive.trajectoryBuilder(toAlliancePurpleRight.end())
                .lineToLinearHeading(new Pose2d(55, 20, Math.toRadians(180)))
                .build();
        Trajectory toPurpleFromCenter = drive.trajectoryBuilder(toBackdropCenter.end())
                        .lineToLinearHeading(new Pose2d(0, 5, Math.toRadians(90)))
                            .build();
        Trajectory toPurpleFromLeft = drive.trajectoryBuilder(toBackdropLeft.end())
                        .lineToLinearHeading(new Pose2d(0, 12, 0))
                                .build();
        Trajectory putPurpleCenter = drive.trajectoryBuilder(toPurpleFromCenter.end())
                        .lineToConstantHeading(new Vector2d(0, 28))
                                .build();
        Trajectory backToOirign = drive.trajectoryBuilder(putPurpleCenter.end())
                .lineToLinearHeading(new Pose2d(0, 0, 0))
                .build();
        Trajectory toAliancePurple = drive.trajectoryBuilder(backToOirign.end())
                        .lineToConstantHeading(new Vector2d(48, 0))
                                .build();
        Trajectory placePurpleCenterAlliance1 = drive.trajectoryBuilder(toAliancePurple.end())
                        .lineToLinearHeading(new Pose2d(50, 49, Math.toRadians(-90)))
                                .build();
        intake.setDropdown(0);
        outtake.setPressureStanga(Variables.pressureStangaClose);
        waitForStart();
        if(caz == 2) {
            intake.setDropdown(0.27);
            intake.setPower(0.6);
            drive.followTrajectory(toGalbenAlianta);
            sleep(700);
            drive.followTrajectory(toBackdrop1);
            intake.setPower(0);
            sleep(300);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setPressureDreapta(Variables.pressureStangaClose);
            drive.followTrajectory(toBackdropCenter);
            outtake.glisieraPos(500, Variables.supression, Variables.toleranta);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(500);
            outtake.glisieraPos(200, Variables.supression, Variables.toleranta);
            sleep(100);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(300);
            outtake.glisieraPos(500, Variables.supression, Variables.toleranta);
            sleep(600);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setPressureDreapta(Variables.pressureDreaptaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(500);
            outtake.glisieraPos(0, Variables.supression, Variables.toleranta);
            intake.setDropdown(0);
            drive.followTrajectory(toPurpleFromCenter);
            drive.followTrajectory(putPurpleCenter);
            drive.followTrajectory(backToOirign);
            drive.followTrajectory(toAliancePurple);
            intake.setDropdown(0.27);
            intake.setPower(0.2);
            drive.followTrajectory(placePurpleCenterAlliance1);
            intake.setPower(0);
        } else if(caz == 1) {
            intake.setDropdown(0.27);
            intake.setPower(0.6);
            drive.followTrajectory(toGalbenAlianta);
            sleep(800);
            drive.followTrajectory(toBackdrop1);
            intake.setPower(0);
            sleep(300);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setPressureDreapta(Variables.pressureStangaClose);
            drive.followTrajectory(toBackdropRight);
            outtake.glisieraPos(500, Variables.supression, Variables.toleranta);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(500);
            outtake.glisieraPos(200, Variables.supression, Variables.toleranta);
            sleep(100);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(300);
            outtake.glisieraPos(500, Variables.supression, Variables.toleranta);
            sleep(600);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setPressureDreapta(Variables.pressureDreaptaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(500);
            outtake.glisieraPos(0, Variables.supression, Variables.toleranta);
            intake.setDropdown(0);
            drive.followTrajectory(putPurpleRight);
            drive.followTrajectorySequence(toAlliancePurpleRight);
            intake.setDropdown(0.28);
            sleep(100);
            drive.followTrajectory(putAlliancePurpleRight);
        }if(caz == 3) {
            intake.setDropdown(0.27);
            intake.setPower(0.6);
            drive.followTrajectory(toGalbenAlianta);
            sleep(800);
            drive.followTrajectory(toBackdrop1);
            intake.setPower(0);
            sleep(300);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setPressureDreapta(Variables.pressureStangaClose);
            drive.followTrajectory(toBackdropLeft);
            outtake.glisieraPos(500, Variables.supression, Variables.toleranta);
            sleep(1000);
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            sleep(500);
            outtake.glisieraPos(200, Variables.supression, Variables.toleranta);
            sleep(100);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(300);
            outtake.glisieraPos(500, Variables.supression, Variables.toleranta);
            sleep(600);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            outtake.setPressureDreapta(Variables.pressureDreaptaClose);
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            sleep(500);
            outtake.glisieraPos(0, Variables.supression, Variables.toleranta);
            intake.setDropdown(0);
        }
    }
    private void initOpenCV() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        HSVPipelineAuto pipeline = new HSVPipelineAuto(1);

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
