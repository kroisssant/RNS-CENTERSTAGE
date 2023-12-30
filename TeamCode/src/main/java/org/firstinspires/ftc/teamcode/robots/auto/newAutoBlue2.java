package org.firstinspires.ftc.teamcode.robots.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robots.DriveConstants;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.Arrays;

@Autonomous
public class newAutoBlue2 extends LinearOpMode {
    HSVPipelineAuto pipeline;
    SampleMecanumDrive drive;
    OutTakeOld outtake;
    public static int caz = 3;
    OpenCvCamera camera;
    @Override
    public void runOpMode() throws InterruptedException {
        outtake = new OutTakeOld(hardwareMap);
        pipeline = new HSVPipelineAuto(1);
        drive = new SampleMecanumDrive(hardwareMap);
        initOpenCV();
        Trajectory putPurpleCenter = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToConstantHeading(new Vector2d(-40, 0))
                .build();



        Trajectory toStackCenter = drive.trajectoryBuilder(putPurpleCenter.end())
                .lineToLinearHeading(new Pose2d(-50, 0, Math.toRadians(0)))
                .build();


        Trajectory toStackCenter1 = drive.trajectoryBuilder(new Pose2d(-50,0,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-45, 23, Math.toRadians(90)))
                .build();




        Trajectory toPanel = drive.trajectoryBuilder(new Pose2d(-45,23,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-50, -70, Math.toRadians(90)))
                .build();
        Trajectory toPanel1 = drive.trajectoryBuilder(new Pose2d(-50,-70,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-23, -90, Math.toRadians(90)))
                .build();
        Trajectory toPanelSlow = drive.trajectoryBuilder(new Pose2d(-23,-90,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-23, -94, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();



        Trajectory toStackCenter2 = drive.trajectoryBuilder(new Pose2d(-15,-93,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-60, -70, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(50 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();
        Trajectory toStackCenter3 = drive.trajectoryBuilder(new Pose2d(-60,-70,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-50, 28, Math.toRadians(90)))
                .build();
        Trajectory toStackCenterSlow = drive.trajectoryBuilder(new Pose2d(-50,28,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-53.9, 31.5, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();





        Trajectory toPanel20 = drive.trajectoryBuilder(new Pose2d(-58.9,31.5,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-60, -65, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(50 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();
        Trajectory toPanel21 = drive.trajectoryBuilder(new Pose2d(-60,-65,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-15, -85, Math.toRadians(90)))
                .build();
        Trajectory toPanelSlow2 = drive.trajectoryBuilder(new Pose2d(-15,-85,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-15, -90, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();





        Trajectory putPurpleLeft = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToLinearHeading(new Pose2d(-30, 0, Math.toRadians(-90)))
                .build();
        Trajectory putPurpleLeft1 = drive.trajectoryBuilder(new Pose2d(-25, 0, Math.toRadians(-90)))
                .lineToConstantHeading(new Vector2d(-25,-10))
                .build();



        Trajectory toStackLeft = drive.trajectoryBuilder(new Pose2d(-25, -3, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-50, 10,Math.toRadians(0)))
                .build();


        Trajectory toStackLeft1 = drive.trajectoryBuilder(new Pose2d(-50,10,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-52, 23, Math.toRadians(90)))
                .build();




        Trajectory toPanelLeft = drive.trajectoryBuilder(new Pose2d(-52,23,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-57, -70, Math.toRadians(90)))
                .build();
        Trajectory toPanelLeft1 = drive.trajectoryBuilder(new Pose2d(-57,-70,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-18, -90, Math.toRadians(90)))
                .build();
        Trajectory toPanelLeftSlow = drive.trajectoryBuilder(new Pose2d(-18,-90,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-18, -93, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();



        Trajectory toStackLeft2 = drive.trajectoryBuilder(new Pose2d(-15,-93,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-60, -70, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(50 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();
        Trajectory toStackLeft3 = drive.trajectoryBuilder(new Pose2d(-60,-70,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-55.8, 28, Math.toRadians(90)))
                .build();
        Trajectory toStackLeftSlow = drive.trajectoryBuilder(new Pose2d(-55.8,28,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-58.9, 31.5, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();





        Trajectory toPanelLeft20 = drive.trajectoryBuilder(new Pose2d(-58.9,31.5,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-60, -65, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(50 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();
        Trajectory toPanelLeft21 = drive.trajectoryBuilder(new Pose2d(-60,-65,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-15, -85, Math.toRadians(90)))
                .build();
        Trajectory toPanelLeftSlow2 = drive.trajectoryBuilder(new Pose2d(-15,-85,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-15, -90, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();



        Trajectory putPurpleRight = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToLinearHeading(new Pose2d(-30, 2, Math.toRadians(90)))
                .build();

        Trajectory toStackRight = drive.trajectoryBuilder(new Pose2d(-30, 2, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-50, 10,Math.toRadians(0)))
                .build();


        Trajectory toStackRight1 = drive.trajectoryBuilder(new Pose2d(-50,10,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-54, 27, Math.toRadians(90)))
                .build();




        Trajectory toPanelRight = drive.trajectoryBuilder(new Pose2d(-54,27,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-57, -70, Math.toRadians(90)))
                .build();
        Trajectory toPanelRight1 = drive.trajectoryBuilder(new Pose2d(-57,-70,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-34, -80, Math.toRadians(90)))
                .build();
        Trajectory toPanelRightSlow = drive.trajectoryBuilder(new Pose2d(-34,-80,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-34, -90, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();



        Trajectory toStackRight2 = drive.trajectoryBuilder(new Pose2d(-15,-90,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-60, -70, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(50 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();
        Trajectory toStackRight3 = drive.trajectoryBuilder(new Pose2d(-60,-70,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-53.8, 24, Math.toRadians(90)))
                .build();
        Trajectory toStackRightSlow = drive.trajectoryBuilder(new Pose2d(-53.8,24,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-53.9, 27.5, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();





        Trajectory toPanelRight20 = drive.trajectoryBuilder(new Pose2d(-58.9,31.5,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-60, -65, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(50 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();
        Trajectory toPanelRight21 = drive.trajectoryBuilder(new Pose2d(-60,-65,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-15, -85, Math.toRadians(90)))
                .build();
        Trajectory toPanelRightSlow2 = drive.trajectoryBuilder(new Pose2d(-15,-85,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-15, -90, Math.toRadians(90)),
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(20 ,DriveConstants.TRACK_WIDTH)
                                )
                        ),new ProfileAccelerationConstraint(60)
                )
                .build();

        outtake.setBrat(Variables.bratJos);
        outtake.setClaw(Variables.pivotJos);
        outtake.setPressureDreapta(Variables.pressureDreaptaClose);
        outtake.setPressureStanga(Variables.pressureStangaClose);
        while(opModeInInit() && !isStopRequested()) {
            caz = pipeline.getCaz();
            telemetry.addData("caz", caz);
            telemetry.update();
        }


        waitForStart();
        if(caz == 1) {
            outtake.setBrat(Variables.bratJos+ 0.03);
            drive.followTrajectory(putPurpleCenter);
            outtake.setPressureStanga(Variables.pressureStangaOpen);


            drive.followTrajectory(toStackCenter);
            drive.turn(Math.toRadians(90));
            outtake.setBrat(Variables.bratJos+ 0.094);
            outtake.setClaw(Variables.pivotJos + 0.09);


            drive.followTrajectory(toStackCenter1);
            sleep(500);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            sleep(500);
            outtake.setBrat(Variables.bratJos+ 0.1);
            outtake.setClaw(Variables.pivotJos);
            drive.followTrajectory(toPanel);
            drive.followTrajectory(toPanel1);

            outtake.glisieraPos(400);
            outtake.setBrat(Variables.bratSus +0.05);
            outtake.setClaw(Variables.pivotSus);
            drive.followTrajectory(toPanelSlow);
            sleep(300);


            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(100);




            outtake.glisieraPos(0);
            outtake.setBrat(Variables.bratJos + 0.072);
            outtake.setClaw(Variables.pivotJos + 0.06 );
            sleep(2000);
        } else if(caz == 2) {
            outtake.setBrat(Variables.bratJos+ 0.03);
            drive.followTrajectory(putPurpleLeft);
            drive.followTrajectory(putPurpleLeft1);
            outtake.setPressureStanga(Variables.pressureStangaOpen);


            drive.followTrajectory(toStackLeft);
            drive.turn(Math.toRadians(90));
            outtake.setBrat(Variables.bratJos+ 0.094);
            outtake.setClaw(Variables.pivotJos + 0.09);


            drive.followTrajectory(toStackLeft1);
            sleep(500);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            sleep(500);
            outtake.setBrat(Variables.bratJos+ 0.1);
            outtake.setClaw(Variables.pivotJos);
            drive.followTrajectory(toPanelLeft);
            drive.followTrajectory(toPanelLeft1);

            outtake.glisieraPos(400);
            outtake.setBrat(Variables.bratSus +0.05);
            outtake.setClaw(Variables.pivotSus);
            drive.followTrajectory(toPanelLeftSlow);
            sleep(300);


            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(100);




            outtake.glisieraPos(0);
            outtake.setBrat(Variables.bratJos + 0.072);
            outtake.setClaw(Variables.pivotJos + 0.06 );
            sleep(2000);
        } else if (caz == 3) {
            outtake.setBrat(Variables.bratJos+ 0.03);
            drive.followTrajectory(putPurpleRight);
            outtake.setPressureStanga(Variables.pressureStangaOpen);


            drive.followTrajectory(toStackRight);
            drive.turn(Math.toRadians(90));
            outtake.setBrat(Variables.bratJos+ 0.094);
            outtake.setClaw(Variables.pivotJos + 0.09);


            drive.followTrajectory(toStackRight1);
            sleep(500);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            sleep(500);
            outtake.setBrat(Variables.bratJos+ 0.1);
            outtake.setClaw(Variables.pivotJos);
            drive.followTrajectory(toPanelRight);
            drive.followTrajectory(toPanelRight1);

            outtake.glisieraPos(400);
            outtake.setBrat(Variables.bratSus +0.05);
            outtake.setClaw(Variables.pivotSus);
            drive.followTrajectory(toPanelRightSlow);
            sleep(300);


            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
            sleep(100);




            outtake.glisieraPos(0);
            outtake.setBrat(Variables.bratJos + 0.072);
            outtake.setClaw(Variables.pivotJos + 0.06 );

            sleep(2000);
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
                camera.startStreaming(1280,720, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode)
            {}
        });
    }
}

