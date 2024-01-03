package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Utils.RNSMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;

public class DriveSubsystem extends SubsystemBase {
    RNSMotor leftFront, leftRear;
    RNSMotor rightFront, rightRear;
    MecanumDrive drive;

    public DriveSubsystem(HardwareMap hardwareMap) {
        leftFront = new RNSMotor(hardwareMap, HardwareConstants.ID_LEFT_FRONT_MOTOR);
        leftRear = new RNSMotor(hardwareMap, HardwareConstants.ID_LEFT_REAR_MOTOR);
        rightFront = new RNSMotor(hardwareMap, HardwareConstants.ID_RIGHT_FRONT_MOTOR);
        rightRear = new RNSMotor(hardwareMap, HardwareConstants.ID_RIGHT_REAR_MOTOR);

        leftFront.setZeroPowerBehavior(RNSMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(RNSMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(RNSMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(RNSMotor.ZeroPowerBehavior.BRAKE);

        drive = new MecanumDrive(leftFront, rightFront, leftRear, rightRear);
    }

    /**
     * Drives the robot using arcade controls.
     *
     * @param str the commanded strafe movement
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation movement
     */
    public void drive(double str, double fwd, double rot) {
        str = (Math.abs(str) >= 0.1) ? str : 0;
        fwd = (Math.abs(fwd) >= 0.1) ? fwd : 0;
        rot = (Math.abs(rot) >= 0.1) ? rot : 0;

        drive.driveRobotCentric(str, -fwd, rot, true);
    }
}
