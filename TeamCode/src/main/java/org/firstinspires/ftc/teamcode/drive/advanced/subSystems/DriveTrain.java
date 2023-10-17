package org.firstinspires.ftc.teamcode.drive.advanced.subSystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDriveCancelable;

public class DriveTrain extends SampleMecanumDriveCancelable {
    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    public DriveTrain(HardwareMap hw, Telemetry telemetry){
        super(hw);
        this.hardwareMap = hw;
        this.telemetry = telemetry;
    }

    public void lockTo(Pose2d poseToLockTo) {

    }
}
