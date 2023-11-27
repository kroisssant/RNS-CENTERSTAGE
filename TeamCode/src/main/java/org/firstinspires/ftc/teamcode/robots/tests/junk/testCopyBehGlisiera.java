package org.firstinspires.ftc.teamcode.robots.tests.junk;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@Config
@TeleOp
@Disabled
public class testCopyBehGlisiera extends LinearOpMode {
    Intake intake;
    OutTake outTake;

    public static double pow = 0.4;
    public static double supress_pow = 0.1;
    public static int target = 300;
    public static int toleranta = 10;
    public static int supression = 100;

    public static double multiplier_non_encoder = 0.8;
    public static double speed_control = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        outTake = new OutTake(hardwareMap);
        intake = new Intake(hardwareMap); // 300 900 1600


        outTake.setPressureStanga(Variables.pressureStangaClose);
        outTake.setPressureDreapta(Variables.pressureDreaptaClose);

        outTake.setBrat(Variables.bratJos);

        outTake.setClaw(Variables.pivotJos);

        waitForStart();


        while(opModeIsActive() && !isStopRequested()){

            if(outTake.glisieraDreapta.getCurrentPosition()>=target-toleranta && outTake.glisieraDreapta.getCurrentPosition()<=target+toleranta) {
                outTake.glisieraDreapta.setPower(0);
                outTake.glisieraStanga.setPower(0);
            }else if(outTake.glisieraDreapta.getCurrentPosition()>target-supression && outTake.glisieraDreapta.getCurrentPosition()<target-toleranta){
                outTake.glisieraDreapta.setTargetPosition(target);
                outTake.glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outTake.glisieraDreapta.setPower(supress_pow * pow);
                outTake.glisieraStanga.setPower(supress_pow * outTake.glisieraDreapta.getPower() * multiplier_non_encoder * speed_control);
            }else if(outTake.glisieraDreapta.getCurrentPosition()<target-supression && outTake.glisieraDreapta.getCurrentPosition()<target-toleranta){
                outTake.glisieraDreapta.setTargetPosition(target);
                outTake.glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outTake.glisieraDreapta.setPower(pow);
                outTake.glisieraStanga.setPower(outTake.glisieraDreapta.getPower() * multiplier_non_encoder * speed_control);
            }else if(outTake.glisieraDreapta.getCurrentPosition()<target+supression && outTake.glisieraDreapta.getCurrentPosition()>target+toleranta){
                outTake.glisieraDreapta.setTargetPosition(target);
                outTake.glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outTake.glisieraDreapta.setPower(supress_pow * pow);
                outTake.glisieraStanga.setPower(-1 * supress_pow * multiplier_non_encoder * outTake.glisieraDreapta.getPower() * speed_control);
            }else if(outTake.glisieraDreapta.getCurrentPosition()>target+supression && outTake.glisieraDreapta.getCurrentPosition()>target+toleranta){
                outTake.glisieraDreapta.setTargetPosition(target);
                outTake.glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outTake.glisieraDreapta.setPower(pow);
                outTake.glisieraStanga.setPower( -1 * multiplier_non_encoder * outTake.glisieraDreapta.getPower() * speed_control);
            }


            telemetry.addData("glisiera dreapta Pos", outTake.glisieraDreapta.getCurrentPosition());
            telemetry.addData("glisiera stanga Pos", outTake.glisieraStanga.getCurrentPosition());
            telemetry.update();
        }
    }
}

