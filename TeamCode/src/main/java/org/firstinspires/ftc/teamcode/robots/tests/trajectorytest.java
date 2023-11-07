package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.opencv.core.Mat;

@Autonomous
public class trajectorytest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Trajectory traj = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .lineToLinearHeading(new Pose2d(15, 0, Math.toRadians(0)))
                .build();
        Trajectory traj1 = drive.trajectoryBuilder(traj.end(), true)
                .splineToLinearHeading(new Pose2d(0, 0, Math.toRadians(0)), Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-15, 45, Math.toRadians(0)), Math.toRadians(180))
                .build();
        // c1
        Trajectory traj3 = drive.trajectoryBuilder(traj1.end())
                .lineToLinearHeading(new Pose2d(67, 45, Math.toRadians(0)))
                .build();
        Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                .lineToLinearHeading(new Pose2d(-15, 45, Math.toRadians(0)))
                .build();
        // c2
        Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                .lineToLinearHeading(new Pose2d(67, 45.5, Math.toRadians(0)))
                .build();
        Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
                .lineToLinearHeading(new Pose2d(-15, 45.5, Math.toRadians(0)))
                .build();
        // c3
        Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
                .splineToSplineHeading(new Pose2d(47,46, Math.toRadians(0)), Math.toRadians(0)) // 47,46
                .splineToSplineHeading(new Pose2d(67, 60, Math.toRadians(30))
                        , Math.toRadians(30))// 67, 60
                .build();
        Trajectory traj8 = drive.trajectoryBuilder(traj7.end(), true)
                .lineToLinearHeading(new Pose2d(-15, 46, Math.toRadians(0)))

//                .splineToLinearHeading(new Pose2d(47, 46, Math.toRadians(0)), Math.toRadians(180))
//                .splineToLinearHeading(new Pose2d(-15, 46, Math.toRadians(0)), Math.toRadians(180))
                .build();
//        Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
//                .lineToLinearHeading(new Pose2d(67, 46, Math.toRadians(0)))
//                .build();
//        Trajectory traj8 = drive.trajectoryBuilder(traj7.end())
//                .lineToLinearHeading(new Pose2d(-15, 46, Math.toRadians(0)))
//                .build();
        // c4
        Trajectory traj9 = drive.trajectoryBuilder(traj8.end())
                .lineToLinearHeading(new Pose2d(67, 46, Math.toRadians(0)))
                .build();
        Trajectory traj10 = drive.trajectoryBuilder(traj9.end())
                .lineToLinearHeading(new Pose2d(-15, 46, Math.toRadians(0)))
                .build();
//        Trajectory traj11 = drive.trajectoryBuilder(traj10.end())
//                .lineToLinearHeading(new Pose2d(0, 0, 0))
//                .build();
        Trajectory traj12 = drive.trajectoryBuilder(traj10.end())
                        .lineToLinearHeading(new Pose2d(10, 36, Math.toRadians(-90)))
                        .build();
        Trajectory traj13 = drive.trajectoryBuilder(traj12.end())
                .lineToConstantHeading(new Vector2d(-10, 30))
                .build();
        telemetry.addLine("da");
        telemetry.update();
        waitForStart();
        drive.followTrajectory(traj);
        drive.followTrajectory(traj1);
        drive.followTrajectory(traj3);
        drive.followTrajectory(traj4);
        drive.followTrajectory(traj5);
        drive.followTrajectory(traj6);
        drive.followTrajectory(traj7);
//        drive.followTrajectory(traj8);
//        drive.followTrajectory(traj9);
//        drive.followTrajectory(traj10);
//        // drive.followTrajectory(traj11);
//        drive.followTrajectory(traj12);
//        drive.followTrajectory(traj13)  ;

    }
}
