package org.firstinspires.ftc.teamcode.robots.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.States.STATES;
import org.firstinspires.ftc.teamcode.robots.subSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp(group = "main", name = "plplplp")
public class SexPulaPistol69 extends LinearOpMode {

    Intake intake;
    OutTake outTake;
    GAMEPAD GAMEPAD1, GAMEPAD2; //1 chassis
    DriveTrain drive;
    ElapsedTime timerForGlisieraSus;
    ElapsedTime timerForGlisieraJos;

    ElapsedTime timerT;
    ElapsedTime timerT2;

    int prevTarget = 100;


    boolean pressure = false;
    boolean brat_out = false;
    boolean glisiera_extended = false;


    int target_sus = 1000;
    int target_jos = 0;

    int target = target_jos;

    boolean override = false;

    //state variables for the state machineSTATES reqState = null;
    STATES robotState = STATES.DEFAULT;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        GAMEPAD2 = new GAMEPAD(this.gamepad2, telemetry);
        drive = new DriveTrain(hardwareMap, telemetry);
        timerForGlisieraSus = new ElapsedTime();
        timerForGlisieraJos = new ElapsedTime();
        intake = new Intake(hardwareMap);
        outTake = new OutTake(hardwareMap);

        outTake.setClaw(Variables.pivotJos);
        outTake.setBrat(Variables.bratJos);
        outTake.setPressureStanga(Variables.pressureStangaOpen);
        outTake.setPressureDreapta(Variables.pressureDreaptaOpen);


        timerT = new ElapsedTime();
        timerT2 = new ElapsedTime();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            GAMEPAD1.run();
            GAMEPAD2.run();

            mechanisms();
            normalDriving(GAMEPAD1);

            outTake.glisieraPos(target,Variables.supression, Variables.toleranta);

//            if(glisiera_extended == true && brat_out == false && pressure== true  && timerT.seconds() >= 1  && timerT.seconds() <=3){
//                outTake.setBrat(Variables.bratSus);
//                outTake.setClaw(Variables.pivotSus);
//                brat_out = true;
//            }

//            if(glisiera_extended == true && brat_out == true && pressure== false && timerT2.seconds() >= 1 && timerT2.seconds() <=2){
//                outTake.setBrat(Variables.bratJos);
//                outTake.setClaw(Variables.bratJos);
//                brat_out = false;
//            }
//            if(glisiera_extended == true && brat_out == false && pressure== false && timerT2.seconds() >= 2 &&  timerT2.seconds() <=100){
//                target = target_jos;
//                glisiera_extended = false;
//            }





            telemetry.update();
            telemetry.addData("time sus", timerForGlisieraSus);
            telemetry.addData("time jos", timerForGlisieraJos);
            telemetry.addData("pressure ", pressure);
            telemetry.addData("brat out", brat_out);
            telemetry.addData("glisiera extended", glisiera_extended);

            drive.update();
        }

    }

    public void mechanisms() {


        //TODO:  INTAKE
        if (GAMEPAD2.left_trigger > 0.3) {
            intake.setPower(-0.6);
        } else if (GAMEPAD2.right_trigger > 0.3) {
            intake.setPower(0.6);
        } else if (GAMEPAD1.left_trigger > 0.3) {
            intake.setPower(-0.4);
        } else if (GAMEPAD1.right_trigger > 0.3) {
            intake.setPower(0.4);
        } else {
            intake.setPower(0);
        }
//
//        if(GAMEPAD2.dpad_left.pressed != 0 && pressureOpen == true){
//            outTake.setPressureStanga(Variables.pressureStangaClose);
//            outTake.setPressureDreapta(Variables.pressureDreaptaClose);
//            pressureOpen = false;
//        } else if (GAMEPAD2.dpad_left.pressed != 0 && pressureOpen == false) {
//            outTake.setPressureStanga(Variables.pressureStangaOpen);
//            outTake.setPressureDreapta(Variables.pressureDreaptaOpen);
//            pressureOpen = true;
//        }
//
//        if(GAMEPAD2.a.pressed != 0){
//            prevTarget = Variables.glisieraLow;
//        } else if(GAMEPAD2.b.pressed != 0){
//            prevTarget = Variables.glisieraMid;
//        }else if(GAMEPAD2.x.pressed != 0){
//            prevTarget = Variables.glisieraHigh;
//        }

        outTake.glisieraPos(target, Variables.supressionManual, Variables.supression);


        if (GAMEPAD1.x.toggle) {
            outTake.setPressureDreapta(Variables.pressureDreaptaClose);
            outTake.setPressureStanga(Variables.pressureStangaClose);
            pressure = true;
        } else {
            outTake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outTake.setPressureStanga(Variables.pressureStangaOpen);
            pressure = false;
        }


        if (GAMEPAD1.right_bumper.value) {
            target = target_sus;
            glisiera_extended = true;
            timerT.reset();
        } else {
            target = target_jos;
            glisiera_extended = false;
            timerT.reset();
        }


        if (GAMEPAD1.a.toggle) {
            outTake.setBrat(Variables.bratSus);
            outTake.setClaw(Variables.pivotSus);
            brat_out = true;
        } else {
            outTake.setBrat(Variables.bratJos);
            outTake.setClaw(Variables.pivotJos);
            brat_out = false;
        }










//
//
//        if(GAMEPAD2.left_stick_y > 0.3){
//            target = outTake.glisieraDreapta.getCurrentPosition() + 120;
//            glisiera_extended = true;
//        } else if (GAMEPAD2.left_stick_y < -0.3){
//            target = outTake.glisieraDreapta.getCurrentPosition() + 120;
//        }else if(GAMEPAD2.right_stick_y > 0.3 && bratIsUp == false){
//            outTake.setBrat(Variables.bratSus);
//            bratIsUp = true;
//        } else if(GAMEPAD2.right_stick_y < -0.3 && bratIsUp == true) {
//            outTake.setBrat(Variables.bratJos);
//            bratIsUp = false;
//        }else if(GAMEPAD2.dpad_up.value){
//            outTake.setClaw(Variables.pivotSus);
//        } else if(GAMEPAD2.dpad_down.value){
//            outTake.setClaw(Variables.pivotJos);
//        } else if(GAMEPAD1.left_bumper.pressed == 1){
//            if(pressureOpen == true && bratIsUp == false && glisiera_extended == false){
//                outTake.setPressureDreapta(Variables.pressureDreaptaClose);
//                outTake.setPressureStanga(Variables.pressureStangaClose);
//                pressureOpen = false;
//            } else if(pressureOpen == false && bratIsUp == false && glisiera_extended == false){
////                outTake.glisieraPos(target, Variables.supression, Variables.toleranta);
//                target = prevTarget;
//                timerForGlisieraSus.reset();
//                glisiera_extended = true;
//            } else if(pressureOpen == false && bratIsUp == true && glisiera_extended == true) {
////                outTake.setPressureDreapta(Variables.pressureDreaptaOpen);
////                outTake.setPressureStanga(Variables.pressureStangaOpen);
//                timerForGlisieraJos.reset();
//                pressureOpen = true;
//            }
//        }
//
//        if(timerUp(2.0) && pressureOpen == false && bratIsUp == false && glisiera_extended == true){
//            outTake.setBrat(Variables.bratSus);
//            outTake.setClaw(Variables.pivotSus);
//            bratIsUp = true;
//        }
//
//        if(timerDown(4.0) && pressureOpen == true && bratIsUp == false && glisiera_extended == true){
//            target = Variables.glisieraDown;
//            glisiera_extended = false;
//        }
//
//        if(timerDown(2.0) && pressureOpen == true && bratIsUp == true && glisiera_extended == true){
//            outTake.setBrat(Variables.bratJos);
//            outTake.setClaw(Variables.pivotJos);
//
//            outTake.setPressureDreapta(Variables.pressureDreaptaOpen);
//            outTake.setPressureStanga(Variables.pressureStangaOpen);
////            timerForGlisieraJos.reset();
//            bratIsUp = false;
//        }
//
//        if(glisiera_extended == true && target < 100){
//            target=100;
//            outTake.glisieraPos(target,Variables.supression, Variables.toleranta);
//        }
//    }
//
//    public boolean timerUp(double sec){
//        if(timerForGlisieraSus.seconds() >= sec && timerForGlisieraSus.seconds() < sec+2 ){
//            return true;
//        } else return false;
//    }
//
//    public boolean timerDown(double sec){
//        if(timerForGlisieraJos.seconds() >= sec && timerForGlisieraJos.seconds() < sec+2 ){
//            return true;
//        } else return false;
    }







    public void normalDriving(GAMEPAD gp){
        if (gp.left_bumper.value) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            GAMEPAD1.left_stick_y * 0.3,
                            -GAMEPAD1.left_stick_x * 0.3,
                            -GAMEPAD1.right_stick_x * 0.3
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
        }
    }

}