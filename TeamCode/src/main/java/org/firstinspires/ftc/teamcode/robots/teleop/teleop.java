package org.firstinspires.ftc.teamcode.robots.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.Libs.STATE;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp
public class teleop extends LinearOpMode {
    OutTake outtake;
    Intake intake;
    SampleMecanumDrive drive;
    GAMEPAD GAMEPAD1, GAMEPAD2;
    ElapsedTime timer;
    STATES robotState = STATES.DEFAULT;
    boolean commit = false;
    boolean pressureClosed = false;
    boolean glisieraRun = false;
    boolean fromDefault = false;
    int target, reqTarget;
    @Override
    public void runOpMode() throws InterruptedException {
        timer = new ElapsedTime();
        outtake = new OutTake(hardwareMap);
        intake = new Intake(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        intake.setDropdown(0.25);
        outtake.setPressureStanga(Variables.pressureStangaOpen);
        outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
        outtake.setClaw(Variables.pivotJos);
        outtake.setBrat(Variables.bratJos);
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        GAMEPAD2 = new GAMEPAD(this.gamepad2, telemetry);
        waitForStart();
        timer.startTime();
        while (opModeIsActive() && !isStopRequested()) {
            GAMEPAD1.run();
            GAMEPAD2.run();
            telemetry.addData("fromDefault", fromDefault);
            telemetry.addData("target Glis", outtake.glisieraDreapta.getTargetPosition());
            telemetry.addData("glis", outtake.getPosition());
            telemetry.addData("Claw", outtake.claw.getPosition());
            telemetry.addData("robot state", robotState);
            intake();
            pressure();
            glisiera();
            if(GAMEPAD1.right_bumper.value) {
                drive.setWeightedDrivePower(new Pose2d(
                        -GAMEPAD1.left_stick_y * 0.3,
                        -GAMEPAD1.left_stick_x * 0.3,
                        GAMEPAD1.right_stick_x * 0.3
                ));
            } else {
                drive.setWeightedDrivePower(new Pose2d(
                        -GAMEPAD1.left_stick_y,
                        -GAMEPAD1.left_stick_x,
                        GAMEPAD1.right_stick_x
                ));
            }
            drive.update();
            telemetry.update();
        }

    }
    private void intake() {
        if(GAMEPAD2.right_trigger > 0.3) {
            intake.setPower(0.4);
        } else if(GAMEPAD2.left_trigger > 0.3) {
            intake.setPower(-0.4);
        } else if(GAMEPAD2.dpad_down.value) {
            intake.setPower(0);
        } else if(GAMEPAD1.right_trigger > 0.3) {
            intake.setPower(0.4);
        } else if(GAMEPAD1.left_trigger > 0.3) {
            intake.setPower(-0.4);
        } else {
            intake.setPower(0);
        }
    }
    private void pressure() {
        if(GAMEPAD1.left_bumper.value && robotState == STATES.DEFAULT) {
            outtake.setPressureDreapta(Variables.pressureDreaptaClose);
            outtake.setPressureStanga(Variables.pressureStangaClose);
            pressureClosed = true;
        }
        if(GAMEPAD2.left_bumper.value) {
            if( outtake.pressureStanga.getPosition() != Variables.pressureStangaClose) {
                outtake.setPressureStanga(Variables.pressureStangaClose);

            } else if(outtake.pressureStanga.getPosition() != Variables.pressureStangaOpen) {
                outtake.setPressureStanga(Variables.pressureStangaOpen);
            }
        } else if(GAMEPAD2.right_bumper.value) {
            if (outtake.pressureDreapta.getPosition() != Variables.pressureDreaptaClose) {
                outtake.setPressureDreapta(Variables.pressureDreaptaClose);

            } else if (outtake.pressureDreapta.getPosition() != Variables.pressureDreaptaOpen) {
                outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            }
        }

    }
    private void glisiera() {
        if(GAMEPAD2.a.value) {
            reqTarget = Variables.glisieraLow;
        } else if(GAMEPAD2.b.value) {
            reqTarget = Variables.glisieraMid;
        } else if(GAMEPAD2.y.value) {
            reqTarget = Variables.glisieraHigh;
        }
        if(GAMEPAD2.left_trigger < 0.3 || GAMEPAD2.left_trigger > 0.3) {
            target = (int)(outtake.getPosition() + GAMEPAD2.left_trigger * 10);
            fromDefault = false;
        }
        if(GAMEPAD1.left_bumper.value && robotState == STATES.DEFAULT && pressureClosed && outtake.bratDreapta.getPosition() != Variables.bratSus) {
            timer.reset();
            target = 500;
            fromDefault = true;
            robotState = STATES.EXTENDED;
        }
        if(timer.seconds() > 2 && fromDefault && outtake.bratDreapta.getPosition() != Variables.bratSus) {
            outtake.setBrat(Variables.bratSus);
            outtake.setClaw(Variables.pivotSus);
            fromDefault = false;
        }
        outtake.glisieraPos(target, Variables.supression, Variables.toleranta);
    }

    enum STATES {
        DEFAULT,
        EXTENDED
    }
}
