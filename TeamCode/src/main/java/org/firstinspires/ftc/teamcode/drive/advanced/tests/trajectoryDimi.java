package org.firstinspires.ftc.teamcode.drive.advanced.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.MecanumControllerCommand;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveKinematics;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveOdometry;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveWheelSpeeds;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumOdoKinematics;
import com.arcrobotics.ftclib.trajectory.Trajectory;
import com.arcrobotics.ftclib.trajectory.TrajectoryGenerator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.advanced.subSystems.MecanumDriveBase;

import java.util.Arrays;

@Autonomous
public class trajectoryDimi extends CommandOpMode {

    private Motor frontLeft, frontRight, backLeft, backRight;
    private RevIMU imu;
    private MecanumControllerCommand mecanumFollower;


    MecanumDriveBase drive;

    @Override
    public void initialize() {
        frontLeft = new Motor(hardwareMap, "leftFront");
        frontRight = new Motor(hardwareMap, "rightFront");
        backLeft = new Motor(hardwareMap, "leftRear");
        backRight = new Motor(hardwareMap, "rightRear");
        imu = new RevIMU(hardwareMap);

        drive = new MecanumDriveBase(false,
                frontLeft, frontRight,
                backLeft, backRight,
                imu, DriveConstants.m_frontLeftLocation,
                DriveConstants.m_frontRightLocation, DriveConstants.m_backLeftLocation,
                DriveConstants.m_backRightLocation);

        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                Arrays.asList(new Translation2d(1, 1), new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
                // Pass config
                drive.getConfig()
        );
        mecanumFollower = new MecanumControllerCommand(exampleTrajectory,
                drive::getPosition, drive.getKinematics(),
                DriveConstants.xPID,
                DriveConstants.yPID, DriveConstants.thetaPID,
                100, drive::drive);
    }
}
