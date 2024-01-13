package org.firstinspires.ftc.teamcode.robots.teleop;

import android.icu.util.UniversalTimeScale;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaCurrentGameAccess;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robots.constatns.UniversalValues;
import org.firstinspires.ftc.teamcode.robots.subSystems.GlisieraSubsystem;
import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.robots.subSystems.ScoringSubsystem;

@TeleOp
public class teleop extends LinearOpMode {
    GlisieraSubsystem glisieraOutake;
    ElapsedTime timer;
    int glisieraTargetPos = 0;
    statesOuttake reqState = null;
    statesOuttake outtakeState = statesOuttake.DEFAULT;
    statesSequence sequenceState = statesSequence.DONE;
    IntakeSubsystem intake;
    ScoringSubsystem scoring;
    SampleMecanumDrive drive;
    GAMEPAD GAMEPAD1, GAMEPAD2;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        glisieraOutake = new GlisieraSubsystem(this.hardwareMap, this.telemetry);
        intake = new IntakeSubsystem(this.hardwareMap);
        scoring = new ScoringSubsystem(this.hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        GAMEPAD2 = new GAMEPAD(this.gamepad2, telemetry);
        scoring.setPressure(UniversalValues.pressure_open);
        scoring.setBrat(UniversalValues.bratJos);
        scoring.setTiwst(UniversalValues.twistDef);
        scoring.setPivot(UniversalValues.pivotJos);
        timer = new ElapsedTime();

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            GAMEPAD1.run();
            GAMEPAD2.run();
            driveControl();
            outake();
            intake();
            scoring();
            telemetry();
        }
    }


    // Intake function
    private void intake() {
        // d1
        if(GAMEPAD1.left_trigger > 0.3) {
            intake.setIntakePower(-UniversalValues.INTAKE_POW);
            intake.setIntakeMobilPower(UniversalValues.INTAKE_MOBIL_POW);
        } else if(GAMEPAD1.right_trigger > 0.3) {
            intake.setIntakePower(UniversalValues.INTAKE_POW);
            intake.setIntakeMobilPower(-UniversalValues.INTAKE_MOBIL_POW);
        } else {
            intake.setIntakePower(0);
            intake.setIntakeMobilPower(0);
        }

        // d2
        if(GAMEPAD2.dpad_down.toggle) {
            intake.setDropdown(UniversalValues.DROPDOWN_DOWN);
        } else if(!GAMEPAD2.dpad_down.toggle) {
            intake.setDropdown(UniversalValues.DROPDOWN_UP);
        }

        if(GAMEPAD2.dpad_up.toggle) {
            intake.setIntakePos(UniversalValues.GLISIERA_INTAKE_FORWARD);
        } else if(!GAMEPAD2.dpad_up.toggle) {
            intake.setIntakePos(UniversalValues.GLISIERA_INTAKE_DEFAULT);
        }
    }

    private void scoring() {
        if(GAMEPAD1.right_bumper.toggle) {
            scoring.setPressure(UniversalValues.pressure_close);
        } else if(!GAMEPAD1.right_bumper.toggle) {
            scoring.setPressure(UniversalValues.pressure_open);
        }
    }
    //outake with state machine
    public void outake() {
        // d2 chose what where he wants the slides to be
        // d2
        if(GAMEPAD2.y.value) {
            reqState = statesOuttake.HIGH;
        } else if(GAMEPAD2.b.value) {
            reqState = statesOuttake.MID;
        } else if(GAMEPAD2.a.value) {
            reqState = statesOuttake.LOW;
        }
        // Here comes the hard part
        // In every case it first checks if the states it s comming from is FAULT
        // If yes then he starts the sequance for it to go in a position form DEFAULT
        // Else it just goes
        //d1
        if(GAMEPAD1.y.toggle && reqState != null && sequenceState == statesSequence.DONE) {
            switch (reqState) {
                case LOW:
                    glisieraTargetPos = UniversalValues.GLISIERA_LOW;
                    if(outtakeState == statesOuttake.DEFAULT) {
                        sequenceState = statesSequence.WAITING1;
                    } else {
                        glisieraOutake.setPosition(glisieraTargetPos);
                        scoring.setBrat(UniversalValues.bratSus);
                        scoring.setPivot(UniversalValues.pivotSus);
                    }
                    timer.reset();
                    break;
                case MID:
                    glisieraTargetPos = UniversalValues.GLISIERA_MID;
                    if(outtakeState == statesOuttake.DEFAULT) {
                        sequenceState = statesSequence.WAITING1;
                    } else {
                        glisieraOutake.setPosition(glisieraTargetPos);
                        scoring.setBrat(UniversalValues.bratSus);
                        scoring.setPivot(UniversalValues.pivotSus);
                    }
                    timer.reset();
                    break;
                case HIGH:
                    glisieraTargetPos = UniversalValues.GLISIERA_HIGH;
                    if(outtakeState == statesOuttake.DEFAULT) {
                        sequenceState = statesSequence.WAITING1;
                    } else {
                        glisieraOutake.setPosition(glisieraTargetPos);
                        scoring.setBrat(UniversalValues.bratSus);
                        scoring.setPivot(UniversalValues.pivotSus);
                    }
                    timer.reset();
                    break;
            }

        } else if(!GAMEPAD1.y.toggle && outtakeState != statesOuttake.DEFAULT && sequenceState == statesSequence.DONE) {
            reqState = statesOuttake.DEFAULT;
            sequenceState = statesSequence.WAITING1;
            timer.reset();
        }

        if(reqState == statesOuttake.DEFAULT && outtakeState != statesOuttake.DEFAULT && sequenceState != statesSequence.DONE) {{
                switch (sequenceState){
                    case WAITING1:
                        scoring.setPivot(UniversalValues.pivotJos);
                        scoring.setBrat(0.1);
                        scoring.setTiwst(UniversalValues.twistDef);
                        intake.setIntakePos(500);
                        sequenceState = statesSequence.WAITING2;
                        break;

                    case WAITING2:
                        if(timer.milliseconds() > 400) {
                            glisieraOutake.setPosition(UniversalValues.GLISIERA_DEFAULT);
                            sequenceState = statesSequence.WAITING3;
                        }
                        break;
                    case WAITING3:
                        if(timer.milliseconds() > 700) {
                            scoring.setBrat(UniversalValues.bratJos);
                            sequenceState = statesSequence.DONE;
                            reqState = outtakeState;
                            outtakeState = statesOuttake.DEFAULT;
                            intake.setIntakePos(0);
                        }
                        break;

                }
            }
        }
        if(outtakeState == statesOuttake.DEFAULT && reqState != null && sequenceState != statesSequence.DONE) {
            switch (sequenceState) {
                case WAITING1:
                    scoring.setBrat(UniversalValues.bratIntermediary);
                    sequenceState = statesSequence.WAITING2;
                    break;
                case WAITING2:
                    if(timer.milliseconds() > 200) {
                        intake.setIntakePos(1000);
                        sequenceState = statesSequence.WAITING3;
                    }
                    break;
                case WAITING3:
                        scoring.setPivot(UniversalValues.pivotIntermediary);
                        sequenceState = statesSequence.WAITING4;

                    break;
                case WAITING4:
                    if (timer.milliseconds() > 500) {
                        glisieraOutake.setPosition(glisieraTargetPos);
                        sequenceState = statesSequence.WAITING5;
                    }
                    break;
                case WAITING5:
                    if(timer.milliseconds() > 1000) {
                        scoring.setBrat(UniversalValues.bratSus);
                        scoring.setPivot(UniversalValues.pivotSus);
                        scoring.setTiwst(UniversalValues.twistScore);
                        outtakeState = reqState;
                        reqState = null;
                        sequenceState = statesSequence.DONE;
                    }
                    break;
            }
        }
    }
    private void pivot() {

    }
    public void updateControlLoops () {
        glisieraOutake.updateControlLoop();
        intake.updateControlLoop();
    }
    private void driveControl() {
        if(GAMEPAD1.right_bumper.value) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            GAMEPAD1.left_stick_y * 0.3,
                            -GAMEPAD1.left_stick_x  * 0.3,
                            -GAMEPAD1.right_stick_x  * 0.3
                    )
            );
        } else {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            GAMEPAD1.left_stick_y,
                            -GAMEPAD1.left_stick_x,
                            -GAMEPAD1.right_stick_x
                    )
            );
        } if(((GAMEPAD1.left_stick_y > 0.3 || GAMEPAD1.left_stick_x > 0.3) || (GAMEPAD1.left_stick_y < -0.3 || GAMEPAD1.left_stick_x <-0.3)) && outtakeState == statesOuttake.DEFAULT && sequenceState == statesSequence.DONE) {
            scoring.setBrat(0.15);
        } else if(((GAMEPAD1.left_stick_y < 0.3 || GAMEPAD1.left_stick_x < 0.3) || (GAMEPAD1.left_stick_y > -0.3 || GAMEPAD1.left_stick_x <-0.3)) && outtakeState == statesOuttake.DEFAULT && sequenceState == statesSequence.DONE) {
            scoring.setBrat(UniversalValues.bratJos);
        }
        drive.update();
    }

    private void telemetry() {
        telemetry.addData("seqanceState", sequenceState);
        telemetry.addData("glisieraState", outtakeState);
        telemetry.addData("reqState", reqState);
        telemetry.addData("glisieraTargetPos", glisieraTargetPos);
        telemetry.addData("timer", timer.milliseconds());
        telemetry.update();
    }

    enum statesOuttake {
        DEFAULT,
        LOW,
        MID,
        HIGH
    }
    enum statesSequence {
        DONE,
        WAITING1,
        WAITING2,
        WAITING3,
        WAITING4,
        WAITING5,
    }
}
