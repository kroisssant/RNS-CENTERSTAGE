package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.GyroEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.DifferentialDriveKinematics;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.DifferentialDriveOdometry;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.DifferentialDriveWheelSpeeds;

import org.firstinspires.ftc.teamcode.robots.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    private MotorGroup leftMotors, rightMotors;
    private Encoder leftEncoder, rightEncoder;
    private GyroEx gyro;
    private DifferentialDriveOdometry odometry;
    private DifferentialDriveKinematics kinematics;

    public DriveSubsystem(MotorGroup leftMotors,
                          MotorGroup rightMotors,
                          Encoder leftEncoder,
                          Encoder rightEncoder,
                          GyroEx gyro,
                          Pose2d initialPose) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.gyro = gyro;

        odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), initialPose);
        kinematics = new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);
    }

    @Override
    public void periodic() {
        odometry.update(gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }

    public Pose2d getCurrentPose() {
        return odometry.getPoseMeters();
    }

    public void drive(double leftVelocity, double rightVelocity) {
        leftMotors.set(leftVelocity / DriveConstants.MAX_VEL);
        rightMotors.set(rightVelocity / DriveConstants.MAX_VEL);
    }

}
