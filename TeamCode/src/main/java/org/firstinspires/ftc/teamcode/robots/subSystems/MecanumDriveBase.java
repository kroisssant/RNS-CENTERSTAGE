package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.arcrobotics.ftclib.command.MecanumControllerCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveKinematics;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveOdometry;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveWheelSpeeds;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumOdoKinematics;
import com.arcrobotics.ftclib.trajectory.TrajectoryConfig;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robots.DriveConstants;

public class MecanumDriveBase extends SubsystemBase {
    ElapsedTime timer;
    public Motor frontLeft, frontRight, backLeft, backRight;
    // public RevIMU imu;
    public MecanumControllerCommand mecanumFollower;
    private MecanumDrive mecanumDrive;
    public MecanumDriveKinematics mcKinematics;
    public MecanumDriveOdometry mcOdo;
    public MecanumOdoKinematics mcOdoKin;
    TrajectoryConfig config;
    MecanumDriveWheelSpeeds mcWheelSpeeds;
    Translation2d m_frontLeftLocation =
            new Translation2d(0.1981, 0.1981);
    Translation2d m_frontRightLocation =
            new Translation2d(0.1981, -0.1981);
    Translation2d m_backLeftLocation =
            new Translation2d(-0.1981, 0.1981);
    Translation2d m_backRightLocation =
            new Translation2d(-0.1981, -0.1981);


    public MecanumDriveBase(boolean autoInvert,
                            Motor frontLeft, Motor frontRight,
                            Motor backLeft, Motor backRight,
//                            RevIMU imu,
                            Translation2d m_frontLeftLocation,
                            Translation2d m_frontRightLocation,Translation2d m_backLeftLocation,
                            Translation2d m_backRightLocation) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        // this.imu = imu;
        this.m_frontLeftLocation = m_frontLeftLocation;
        this.m_frontRightLocation = m_frontRightLocation;
        this.m_backLeftLocation = m_backLeftLocation;
        this.m_backRightLocation = m_backRightLocation;
        this.mecanumDrive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
        mcKinematics = new MecanumDriveKinematics(m_frontLeftLocation, m_frontRightLocation,
                m_backLeftLocation, m_backRightLocation);
        mcWheelSpeeds = new MecanumDriveWheelSpeeds(frontLeft.getCorrectedVelocity(),
                frontRight.getCorrectedVelocity(),
                backLeft.getCorrectedVelocity(), backRight.getCorrectedVelocity());
//        mcOdo = new MecanumDriveOdometry(mcKinematics,
//                imu.getRotation2d());

        this.config = new TrajectoryConfig(DriveConstants.MAX_VEL, DriveConstants.MAX_ACCEL)
                .setKinematics(mcKinematics);

        timer = new ElapsedTime();
        timer.reset();
//        this.imu.init();
//        imu.init();
    }



    @Override
    public void periodic() {
        super.periodic();
        // mcOdo.updateWithTime(timer.seconds(), imu.getRotation2d(), getWheelsSpeed());
    }

    public void drive(MecanumDriveWheelSpeeds speeds) {
        mecanumDrive.driveWithMotorPowers(speeds.frontLeftMetersPerSecond, speeds.frontRightMetersPerSecond,
                speeds.rearLeftMetersPerSecond, speeds.rearRightMetersPerSecond);

    }

    public Pose2d getPosition() {
        return mcOdo.getPoseMeters();
    }

    public MecanumDriveKinematics getKinematics() {
        return mcKinematics;
    }

    public MecanumDriveWheelSpeeds getWheelsSpeed() {
        return mcWheelSpeeds = new MecanumDriveWheelSpeeds(frontLeft.getCorrectedVelocity(),
                frontRight.getCorrectedVelocity(),
                backLeft.getCorrectedVelocity(),
                backRight.getCorrectedVelocity());
    }

    public TrajectoryConfig getConfig() {
        return config;
    }
}
