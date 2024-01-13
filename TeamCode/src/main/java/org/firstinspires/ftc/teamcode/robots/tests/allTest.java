package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robots.constatns.UniversalValues;
import org.firstinspires.ftc.teamcode.robots.subSystems.GlisieraSubsystem;
import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.robots.subSystems.ScoringSubsystem;

@TeleOp
public class allTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        GlisieraSubsystem glisiera = new GlisieraSubsystem(hardwareMap, telemetry);
        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap);
        ScoringSubsystem scoring = new ScoringSubsystem(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            if(gamepad1.a) {
                scoring.setTiwst(UniversalValues.twistScore);
            }
            if(gamepad1.b) {
                scoring.setPivot(UniversalValues.pivotJos);
            }
            if(gamepad1.x) {
                scoring.setBrat(UniversalValues.bratJos);
            }
            if(gamepad1.y) {
                scoring.setPressure(UniversalValues.pressure_close);
            }
            if(gamepad1.dpad_up) {
                intake.setDropdown(UniversalValues.DROPDOWN_DOWN);
            }
            if(gamepad1.dpad_down) {
                intake.setStackBlocker(UniversalValues.STACKBLOCKER_DOWN);
            }
            if(gamepad1.left_trigger > 0.3) {
                intake.setIntakeMobilPower(0.9);
            }
            if(gamepad1.right_trigger > 0.3) {
                intake.setIntakePower(0.9);
            }
            if(gamepad1.right_bumper) {
                intake.glisieraIntake.setTargetPosition(UniversalValues.GLISIERA_INTAKE_DEFAULT);
                intake.glisieraIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                intake.glisieraIntake.setPower(1);
            }
            if(gamepad1.left_bumper) {
                glisiera.glisieraRight.setTargetPosition(UniversalValues.GLISIERA_LOW);
                glisiera.glisieraRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                glisiera.glisieraRight.setPower(1);
            }
        }
    }
}
