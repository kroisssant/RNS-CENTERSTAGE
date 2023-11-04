package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Autonomous
public class trajectorytest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Trajectory traj = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .splineToLinearHeading(new Pose2d(24, 0, Math.toRadians(0)), 0)
                .build();
        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d(24, 0, Math.toRadians(0)))
                .splineToLinearHeading(new Pose2d(0, 0, Math.toRadians(0)), Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-24, 30, Math.toRadians(0)), Math.toRadians(180))
                .build();
        Trajectory traj3 = drive.trajectoryBuilder(new Pose2d(-24, 30, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(50, 30, Math.toRadians(0)))
                .build();
        Trajectory traj4 = drive.trajectoryBuilder(new Pose2d(50, 30, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(-24, 30, Math.toRadians(0)))
                .build();
        telemetry.addLine("da");
        waitForStart();
        drive.followTrajectory(traj);
        drive.followTrajectory(traj1);
        drive.followTrajectory(traj3);
        drive.followTrajectory(traj4);
        drive.followTrajectory(traj3);
        drive.followTrajectory(traj4);
    }
}
