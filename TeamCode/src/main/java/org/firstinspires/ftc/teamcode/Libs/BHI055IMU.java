package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BHI055IMU {
    private BNO055IMU revIMU;

    /***
     * Heading relative to starting position
     */
    double globalHeading;

    /**
     * Heading relative to last offset
     */
    double relativeHeading;

    /**
     * Offset between global heading and relative heading
     */
    double offset;

    private int multiplier;

    /**
     * Create a new object for the built-in gyro/imu in the Rev Expansion Hub
     *
     * @param hw      Hardware map
     * @param imuName Name of sensor in configuration
     */
    public BHI055IMU(HardwareMap hw, String imuName) {
        revIMU = hw.get(BNO055IMU.class, imuName);
        multiplier = 1;
    }

    public BHI055IMU(HardwareMap hw) {
        this(hw, "imu");
    }



}
