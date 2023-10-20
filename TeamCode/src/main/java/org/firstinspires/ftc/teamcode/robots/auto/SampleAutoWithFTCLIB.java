package org.firstinspires.ftc.teamcode.robots.auto;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RamseteCommand;
import com.arcrobotics.ftclib.controller.wpilibcontroller.RamseteController;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.DifferentialDriveKinematics;
import com.arcrobotics.ftclib.trajectory.Trajectory;
import com.arcrobotics.ftclib.trajectory.TrajectoryConfig;
import com.arcrobotics.ftclib.trajectory.TrajectoryGenerator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robots.subSystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;

import java.util.Arrays;


@Autonomous
public class SampleAutoWithFTCLIB extends CommandOpMode {


    private Motor frontLeft, frontRight, backLeft, backRight;
    private RevIMU imu;
    private RamseteCommand ramseteFollower;
    private DriveSubsystem driveSubsystem;

    @Override
    public void initialize() {
        frontLeft = new Motor(hardwareMap, "front_left");
        frontRight = new Motor(hardwareMap, "front_right");
        backLeft = new Motor(hardwareMap, "back_left");
        backRight = new Motor(hardwareMap, "back_right");

        imu = new RevIMU(hardwareMap);
        imu.init();

        driveSubsystem = new DriveSubsystem(
                new MotorGroup(frontLeft, backLeft),
                new MotorGroup(frontRight, backRight),
                frontLeft.encoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE),
                frontRight.encoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE),
                imu,
                new Pose2d()
        );

        DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);
        TrajectoryConfig config = new TrajectoryConfig(DriveConstants.MAX_VEL, DriveConstants.MAX_ACCEL)
                .setKinematics(kinematics);

        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                Arrays.asList(new Translation2d(1, 1), new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
                // Pass config
                config
        );

        ramseteFollower = new RamseteCommand(
                exampleTrajectory,
                driveSubsystem::getCurrentPose,
                new RamseteController(DriveConstants.B, DriveConstants.ZETA),
                kinematics,
                driveSubsystem::drive
        );

        schedule(ramseteFollower);

    }

}