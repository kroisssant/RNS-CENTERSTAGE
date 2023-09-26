package org.firstinspires.ftc.teamcode.drive.advanced.States;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Trajectorys {
    SampleMecanumDrive drive;

    Trajectory toAlliceYellow;
    Trajectory toBackDrop1;

    Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));

    public Trajectorys(SampleMecanumDrive drive) {
        this.drive = drive;
    }

    public void init() {
        // Yellow State Trajectorys
        toAlliceYellow = drive.trajectoryBuilder(startPose)
                .lineToLinearHeading(new Pose2d(0, 0, 90))
                .build();
        toBackDrop1 = drive.trajectoryBuilder(toAlliceYellow.end())
                .lineToLinearHeading(new Pose2d(0,0, 80))
                .build();
    }
}
