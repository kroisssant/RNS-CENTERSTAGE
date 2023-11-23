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
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp(group = "main", name = "teleopMAIN")
public class teleopV1Main extends LinearOpMode {

    //Declaration of classes that wil be used in this teleop
    Intake intake;
    OutTake outTake;
    GAMEPAD GAMEPAD1, GAMEPAD2; //1 chassis
    DriveTrain drive;
    ElapsedTime timerForGlisieraSus;

    boolean flagTimerRestart = false;

    //state variables for the state machine
    STATES reqState = null;
    STATES robotState = STATES.DEFAULT;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        GAMEPAD2 = new GAMEPAD(this.gamepad2, telemetry);
        drive = new DriveTrain(hardwareMap, telemetry);
        timerForGlisieraSus = new ElapsedTime();

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){
            GAMEPAD1.run();
            GAMEPAD2.run();

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

        if (GAMEPAD1.right_bumper.toggle && reqState != null) {


            if(reqState != robotState){
                flagTimerRestart = true;
            } else flagTimerRestart = false;

            if(flagTimerRestart == true){
                timerForGlisieraSus.reset();
            }

            switch (reqState) {
                case LOW:
                    outTake.run(Variables.glisieraPower, Variables.glisieraLow);
                    if(timerForGlisieraSus.seconds() == Variables.timpDupaMiscareaGlisierei){
                        outTake.setBrat(Variables.bratLow);
                        outTake.setClaw(Variables.clawLow);
                        pressure();
                    }
                    robotState = STATES.LOW;
                    break;
                case MID:
                    outTake.run(Variables.glisieraPower, Variables.glisieraMid);
                    if(timerForGlisieraSus.seconds() == Variables.timpDupaMiscareaGlisierei){
                        outTake.setBrat(Variables.bratMid);
                        outTake.setClaw(Variables.clawMid);
                        pressure();
                    }
                    robotState = STATES.MID;
                    break;
                case HIGH:
                    outTake.run(Variables.glisieraPower, Variables.glisieraHigh);
                    if(timerForGlisieraSus.seconds() == Variables.timpDupaMiscareaGlisierei){
                        outTake.setBrat(Variables.bratHigh);
                        outTake.setClaw(Variables.clawHigh);
                        pressure();
                    }
                    robotState = STATES.HIGH;
                    break;
                default:
                    break;
            }
        } else if (!GAMEPAD1.right_bumper.toggle && robotState != STATES.DEFAULT) {
            reqState = robotState;
//            outTake.run(Variables.glisieraPower, Variables.glisieraDown);
//            outTake.setBrat(Variables.bratDefault);
//            outTake.setClaw(Variables.clawDefault);
            outTake.setPressureStanga(Variables.pressureStangaOpen);
            outTake.setPressureDreapta(Variables.pressureDreaptaOpen);
            robotState = STATES.DEFAULT;
        }
    }

    public void pressure(){
        if(GAMEPAD2.left_bumper.toggle){
            if(outTake.getPressureDreapta() == Variables.pressureDreaptaClose){
                outTake.setPressureDreapta(Variables.pressureDreaptaOpen);
                outTake.setPressureStanga(Variables.pressureStangaOpen);
            } else {
                outTake.setPressureDreapta(Variables.pressureDreaptaClose);
                outTake.setPressureStanga(Variables.pressureStangaClose);
            }
        }
    }
}
