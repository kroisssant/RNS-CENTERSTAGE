package org.firstinspires.ftc.teamcode.Utils;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Constants.Constants;

public class RNSMotor extends Motor {
    HardwareMap hardwareMap;
    String id;

    public RNSMotor(HardwareMap hardwareMap, String id) {
        super(hardwareMap, id);
        this.hardwareMap = hardwareMap;
        this.id = id;
    }

    public void set(double power) {
        super.set(power * (Constants.NOMINAL_VOLTAGE / hardwareMap.getAll(VoltageSensor.class).get(0).getVoltage()));
    }
}
