package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robots.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDriveCancelable;

public class DriveTrain extends SampleMecanumDrive {
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private static double kPHeading = DriveConstants.kPHeading;
    private static double kPXY = DriveConstants.kPxy;

    public DriveTrain(HardwareMap hw, Telemetry telemetry){
        super(hw);
        this.hardwareMap = hw;
        this.telemetry = telemetry;
    }

    public void lockTo(Pose2d poseToLockTo) {
        Pose2d currentPose = getPoseEstimate();
        Pose2d distance = poseToLockTo.minus(currentPose);
        Vector2d xy = distance.vec().rotated(-currentPose.getHeading());
        double heading = Angle.normDelta(poseToLockTo.getHeading()) - Angle.normDelta(currentPose.getHeading());
        setWeightedDrivePower(new Pose2d(xy.times(kPXY), heading * kPHeading));

    }
}
