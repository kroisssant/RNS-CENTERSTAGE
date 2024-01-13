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
    public boolean pressureToggle = false;

    public ScoringSubsystem(HardwareMap hardwareMap){
        pressureDreapta = hardwareMap.get(Servo.class, HardwareConstants.ID_PRESSURE_DREAPTA);
        pressureStanga = hardwareMap.get(Servo.class, HardwareConstants.ID_PRESSURE_STANGA);

        bratDreapta = hardwareMap.get(Servo.class, HardwareConstants.ID_BRAT_DREAPTA);
        bratStanga = hardwareMap.get(Servo.class, HardwareConstants.ID_BRAT_STANGA);

        bratStanga.setDirection(Servo.Direction.REVERSE);
        pressureDreapta.setDirection(Servo.Direction.REVERSE);

        pivot = hardwareMap.get(Servo.class, HardwareConstants.ID_PIVOT);

        pivot.setDirection(Servo.Direction.REVERSE);
//        setBratPos(Constants.BRAT_JOS+0.01);
        setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
        setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
        pivot.setPosition(Constants.PIVOT_JOS);
        setBratPos(0.07);
    }

    public void setBratPos(double position){
        bratDreapta.setPosition(position);
        bratStanga.setPosition(position);
    }

    public void setPressureDreaptaPos(double position){
        pressureDreapta.setPosition(position);
    }

    public void setPressureStangaPos(double position){
        pressureStanga.setPosition(position);
    }

    public void pressureOpen(){
        setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
        setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
        pressureToggle = false;
    }

    public void pressureClose(){
        setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_INCHIS);
        setPressureStangaPos(Constants.PRESSURE_STANGA_INCHIS);
        pressureToggle = true;
    }

    public void setPivot(double position){
        pivot.setPosition(position);
    }

    public double getBratPos() {
        return bratDreapta.getPosition();
    }
}
