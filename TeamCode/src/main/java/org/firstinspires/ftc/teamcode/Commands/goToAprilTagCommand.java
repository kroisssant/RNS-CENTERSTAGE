package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.util.Angle;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.jetbrains.annotations.Contract;

public class goToAprilTagCommand extends CommandBase {
    Pose2d currentPose;
    SampleMecanumDrive drive;
    AprilTagDetection detection;
    double aprilTagPositionCorrectedX;
    double aprilTagPositionCorrectedY;
    double headingOfAprilTag;
    public goToAprilTagCommand(Pose2d actualPose,
                               SampleMecanumDrive drive,
                               AprilTagDetection detection,
                               double heading) {
        this.headingOfAprilTag = Math.toRadians(heading);
        this.currentPose = actualPose;
        this.drive = drive;
        this.detection = detection;
    }
    public Pose2d lockTo(@NonNull Pose2d poseToLockTo) {
        Pose2d distance = poseToLockTo.minus(currentPose);

        Vector2d xy = distance.vec().rotated(-currentPose.getHeading());

        double heading = Angle.normDelta(poseToLockTo.getHeading())
                - Angle.normDelta(currentPose.getHeading());

        return new Pose2d(xy, heading);
    }
     @NonNull
     @Contract(" -> new")
     private Pose2d calculateLocationOfAprilTagCorrected() {
        aprilTagPositionCorrectedX =
                currentPose.getX() + detection.rawPose.x;
        aprilTagPositionCorrectedY =
                currentPose.getY() + detection.rawPose.y;
        return new Pose2d(aprilTagPositionCorrectedX,
                aprilTagPositionCorrectedY,
                headingOfAprilTag);
     }
}
