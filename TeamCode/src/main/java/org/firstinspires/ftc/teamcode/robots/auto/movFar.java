package org.firstinspires.ftc.teamcode.robots.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robots.constatns.UniversalValues;
import org.firstinspires.ftc.teamcode.robots.subSystems.GlisieraSubsystem;
import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.robots.subSystems.ScoringSubsystem;
import org.openftc.easyopencv.OpenCvCamera;

public class movFar extends LinearOpMode {
    HSVPipelineAuto pipeline;
    SampleMecanumDrive drive;
    ScoringSubsystem outake;
    IntakeSubsystem intake;
    GlisieraSubsystem glisiera;
    OpenCvCamera camera;
    int caz = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        Trajectory toMovCenter = drive.trajectoryBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .lineToConstantHeading(new Vector2d(31, 0)).build();
        Trajectory toMovFar = drive.trajectoryBuilder(new Pose2d(0,0,Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(30, -9, Math.toRadians(-90)))
                .build();
        Trajectory toMovClose = drive.trajectoryBuilder(new Pose2d(0,0, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(30, +10, Math.toRadians(0)))
                .build();
        pipeline = new HSVPipelineAuto(1);
        // initOpenCV();
        drive = new SampleMecanumDrive(hardwareMap);
        outake = new ScoringSubsystem(hardwareMap);
        glisiera = new GlisieraSubsystem(hardwareMap, this.telemetry);
        intake = new IntakeSubsystem(hardwareMap);
        intake.setDropdown(UniversalValues.DROPDOWN_DOWN);
        waitForStart();
        if(caz == 1) {
            drive.followTrajectory(toMovCenter);
        }
        if(caz == 2) {
            drive.followTrajectory(toMovClose);
        }
        if(caz == 3) {
            drive.followTrajectory(toMovFar);
        }
    }
}
