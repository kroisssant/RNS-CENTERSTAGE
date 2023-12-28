package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;

public class ScoringSubsystem extends SubsystemBase {
    Servo pressureDreapta, pressureStanga;
    Servo pivot;
    Servo bratDreapta, bratStanga;

    public ScoringSubsystem(HardwareMap hardwareMap){
        pressureDreapta = hardwareMap.get(Servo.class, HardwareConstants.ID_PRESSURE_DREAPTA);
        pressureStanga = hardwareMap.get(Servo.class, HardwareConstants.ID_PRESSURE_STANGA);

        bratDreapta = hardwareMap.get(Servo.class, HardwareConstants.ID_BRAT_DREAPTA);
        bratStanga = hardwareMap.get(Servo.class, HardwareConstants.ID_BRAT_STANGA);

        bratDreapta.setDirection(Servo.Direction.REVERSE);
        pressureDreapta.setDirection(Servo.Direction.REVERSE);
        pivot = hardwareMap.get(Servo.class, HardwareConstants.ID_PIVOT);

        setBratPos(Constants.BRAT_JOS);
        setPressurePos(Constants.PRESSURE_DESCHIS);
        pivot.setPosition(Constants.PIVOT_JOS);
    }

    public void setBratPos(double position){
        bratDreapta.setPosition(position);
        bratStanga.setPosition(position);
    }

    public void setPressurePos(double position){
        pressureDreapta.setPosition(position);
        pressureStanga.setPosition(position);
    }

    public void setPivot(double position){
        pivot.setPosition(position);
    }
}
