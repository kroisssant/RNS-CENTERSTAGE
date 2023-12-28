package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;

@Config
@TeleOp
public class test extends LinearOpMode {

    Servo pressureDreapta, pressureStanga, bratStanga, bratDreapta, pivot;

    DcMotorEx glisieraDreapta, glisieraStanga, intake;

    public static double pressureDreaptaV = 0;
    public static double pressureStangaV = 0;


    public static double brat = 0.11;

    public static double pivotV = 0;

    public static double intakepow = 0.4;
    Gamepad gamepad;

    @Override
    public void runOpMode() throws InterruptedException {

        glisieraDreapta = hardwareMap.get(DcMotorEx.class, "glisieraDreapta");
        glisieraStanga = hardwareMap.get(DcMotorEx.class, "glisieraStanga");
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        pressureDreapta = hardwareMap.get(Servo.class, "pressureDreapta");
        pressureStanga = hardwareMap.get(Servo.class, "pressureStanga");
        bratDreapta = hardwareMap.get(Servo.class, "bratDreapta");
        bratStanga = hardwareMap.get(Servo.class, "bratStanga");
        pivot = hardwareMap.get(Servo.class, "pivot");

        pressureDreapta.setDirection(Servo.Direction.REVERSE);
        bratDreapta.setDirection(Servo.Direction.REVERSE);

        glisieraStanga.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()&& !isStopRequested()){
            if(gamepad.a) {
                pressureStanga.setPosition(pressureStangaV);
            }

            if(gamepad.b){
                pressureDreapta.setPosition(pressureDreaptaV);
            }

            if(gamepad.x){
                bratDreapta.setPosition(brat);
            }

            if(gamepad.y){
                bratStanga.setPosition(pivotV);
            }

            if(gamepad.right_trigger > 0.2){
                intake.setPower(intakepow);
            } else if(gamepad.left_trigger > 0.2){
                intake.setPower(-intakepow);
            }

            telemetry.addData("dreapta", glisieraDreapta.getCurrentPosition());
            telemetry.addData("stanga", glisieraStanga.getCurrentPosition());
            telemetry.update();
        }
    }
}
