package org.firstinspires.ftc.teamcode.robots.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.States.STATES;
import org.firstinspires.ftc.teamcode.robots.subSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp(group = "main", name = "teleopMAIN")
public class teleopV1Main extends LinearOpMode {

    //Declaration of classes that wil be used in this teleop
    IntakeOld intake;
    int target = 0;
    OutTakeOld outTake;
    GAMEPAD GAMEPAD1, GAMEPAD2; //1 chassis
    DriveTrain drive;
    ElapsedTime timerForGlisieraSus;
    ElapsedTime timerForGlisieraJos;

    boolean pressureOpen = true;
    boolean bratIsUp = false;

    STATES reqState = null;
    STATES robotState = STATES.DEFAULT;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        GAMEPAD2 = new GAMEPAD(this.gamepad2, telemetry);
        drive = new DriveTrain(hardwareMap, telemetry);
        timerForGlisieraSus = new ElapsedTime();
        timerForGlisieraJos = new ElapsedTime();
        intake = new IntakeOld(hardwareMap);
        outTake = new OutTakeOld(hardwareMap);


        outTake.setClaw(Variables.pivotJos);
        outTake.setBrat(Variables.bratJos);
        outTake.setPressureStanga(Variables.pressureStangaOpen);
        outTake.setPressureDreapta(Variables.pressureDreaptaOpen);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){
            GAMEPAD1.run();
            GAMEPAD2.run();

            intake();
            states();

            if(gamepad1.left_bumper) {
                drive.setWeightedDrivePower(
                        new Pose2d(
                                -gamepad1.left_stick_y * 0.3,
                                -gamepad1.left_stick_x * 0.3,
                                gamepad1.right_stick_x * 0.3
                        )
                );
            }
            else {
                drive.setWeightedDrivePower(
                        new Pose2d(
                                -gamepad1.left_stick_y,
                                -gamepad1.left_stick_x,
                                gamepad1.right_stick_x
                        )
                );
            }
            drive.update();

            telemetry.addData("pressureOpen", pressureOpen);
            telemetry.addData("target", outTake.glisieraDreapta.getTargetPosition());
            telemetry.addData("current pos", outTake.getPosition());
            telemetry.addData("reqState", reqState);
            telemetry.addData("robotState", robotState);
            telemetry.addData("TIMER", timerForGlisieraSus);
            telemetry.update();
        }

    }

    private void intake(){
        if(GAMEPAD2.right_trigger > 0.3){
            intake.setPower(0.4);
        } else if(GAMEPAD2.left_trigger > 0.3){
            intake.setPower(-0.4);
        } else if(GAMEPAD1.right_trigger > 0.3){
            intake.setPower(0.4);
        } else if(GAMEPAD1.left_trigger > 0.3){
            intake.setPower(-0.4);
        } else {
            intake.setPower(0);
        }
    }

    private void states() throws InterruptedException {
        if (GAMEPAD2.a.value) {
            reqState = STATES.LOW;
        } else if (GAMEPAD2.b.value) {
            reqState = STATES.MID;
        } else if (GAMEPAD2.y.value) {
            reqState = STATES.HIGH;
        }

        if(GAMEPAD1.right_bumper.value && pressureOpen == true && robotState == STATES.DEFAULT){
            outTake.setPressureDreapta(Variables.pressureDreaptaClose);
            outTake.setPressureStanga(Variables.pressureStangaClose);
            pressureOpen = false;
        }

        outTake.glisieraPos(target,Variables.supression, Variables.toleranta);


        if (GAMEPAD1.right_bumper.value && reqState != null && pressureOpen == false) {
            if(reqState == STATES.LOW) {
                robotState = STATES.LOW;
                if (bratIsUp == false) {
                    timerForGlisieraSus.reset();
                }
                target = Variables.glisieraLow;
            }
            if(reqState == STATES.MID) {
                robotState = STATES.MID;
                if (bratIsUp == false) {
                    timerForGlisieraSus.reset();
                }
                target = Variables.glisieraMid;
            }
            if(reqState == STATES.HIGH){
                    robotState = STATES.HIGH;
                    if(bratIsUp == false){
                        timerForGlisieraSus.reset();
                    }
                    target = Variables.glisieraHigh;
            }

        } else if(GAMEPAD1.right_bumper.value && pressureOpen == true && robotState!= STATES.DEFAULT && bratIsUp){
            timerForGlisieraJos.reset();
            outTake.setBrat(Variables.bratJos);
            outTake.setClaw(Variables.pivotJos);
            bratIsUp = false;
            reqState = robotState;
        }
        if(GAMEPAD1.right_bumper.value && pressureOpen == false && STATES.DEFAULT != robotState ){
            outTake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outTake.setPressureStanga(Variables.pressureStangaOpen);
            pressureOpen = true;
        }

        if(timerForGlisieraSus.seconds() >= 2  && !pressureOpen && !bratIsUp && STATES.DEFAULT != robotState){
            outTake.setBrat(Variables.bratSus);
            outTake.setClaw(Variables.pivotSus);
            bratIsUp = true;
        }


    }


}
