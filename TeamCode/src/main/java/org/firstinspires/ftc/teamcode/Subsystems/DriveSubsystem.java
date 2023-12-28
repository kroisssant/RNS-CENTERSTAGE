package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;

public class DriveSubsystem extends SubsystemBase {
    Motor leftFront, leftRear;
    Motor rightFront, rightRear;
    MecanumDrive drive;

    public DriveSubsystem(HardwareMap hardwareMap) {
        leftFront = new Motor(hardwareMap, HardwareConstants.ID_LEFT_FRONT_MOTOR);
        leftRear = new Motor(hardwareMap, HardwareConstants.ID_LEFT_REAR_MOTOR);
        rightFront = new Motor(hardwareMap, HardwareConstants.ID_RIGHT_FRONT_MOTOR);
        rightRear = new Motor(hardwareMap, HardwareConstants.ID_RIGHT_REAR_MOTOR);

        rightFront.setInverted(true);
        leftRear.setInverted(true);

        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

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

        drive.driveRobotCentric(str, fwd, rot, true);
    }
}
