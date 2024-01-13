package org.firstinspires.ftc.teamcode.robots.subSystems;

import androidx.annotation.NonNull;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.FeedbackController;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.PIDEx;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficientsEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.constatns.SubsystemConstants;

public class IntakeSubsystem {
    public DcMotorEx   glisieraIntake;
    DcMotorEx   intake;
    DcMotorEx   intakeMobil;
    Servo       dropdown;
    Servo       stackBlocker;
    BasicPID    glisieraIntakePID = new BasicPID(SubsystemConstants.glisieraIntakePIDCoef);
    int intakePos;
    public IntakeSubsystem (@NonNull HardwareMap hardwareMap){
        glisieraIntake = hardwareMap.get(DcMotorEx.class, "glisieraIntake");
        glisieraIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glisieraIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMobil = hardwareMap.get(DcMotorEx.class, "intakeMobil");
        intakeMobil.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dropdown = hardwareMap.get(Servo.class, "dropdown");

        stackBlocker = hardwareMap.get(Servo.class, "stackBlocker");
    }

    public void setDropdown(double pos) {
        dropdown.setPosition(pos);
    }
    public void setIntakePower(double pow) {
        intake.setPower(pow);
    }
    public void setIntakeMobilPower(double pow) {
        intakeMobil.setPower(pow);
    }

    public void setIntakePos(int pos) {
        //intakePos = pos;
        glisieraIntake.setTargetPosition(pos);
        glisieraIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        glisieraIntake.setPower(1);
    }
    public void setStackBlocker(double pos) {
        stackBlocker.setPosition(pos);
    }
    public double calculatePower() {
        return glisieraIntakePID.calculate(intakePos, glisieraIntake.getCurrentPosition());
    }
    public void updateControlLoop () {
        glisieraIntake.setPower(calculatePower());
    }
}
