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

@Config
@Disabled
@TeleOp
public class testCopyBehGeneral extends LinearOpMode {
    DcMotorEx motor1, motor2;
    public static double pow = 0.7;
    public static double supress_pow = 0.3;
    public static int target =2000;
    public static int toleranta = 10;
    public static int supression = 300;
    public static double multiplier_non_encoder = 0.8;
    public static double speed_control = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "motor2");
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
//            if(gamepad1.a) {
//            if(motor1.getCurrentPosition()==0 || motor2.getCurrentPosition()==0)
//                speed_control=1;
//            else
//            speed_control = 1/(motor2.getCurrentPosition()/ motor1.getCurrentPosition());
            if(motor1.getCurrentPosition()>=target-toleranta && motor1.getCurrentPosition()<=target+toleranta) {
                motor1.setPower(0);
                motor2.setPower(0);
            }else if(motor1.getCurrentPosition()>target-supression && motor1.getCurrentPosition()<target-toleranta){
                motor1.setTargetPosition(target);
                motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor1.setPower(supress_pow * pow);
                motor2.setPower(supress_pow * motor1.getPower() * multiplier_non_encoder * speed_control);
            }else if(motor1.getCurrentPosition()<target-supression && motor1.getCurrentPosition()<target-toleranta){
                motor1.setTargetPosition(target);
                motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor1.setPower(pow);
                motor2.setPower(motor1.getPower() * multiplier_non_encoder * speed_control);
            }else if(motor1.getCurrentPosition()<target+supression && motor1.getCurrentPosition()>target+toleranta){
                motor1.setTargetPosition(target);
                motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor1.setPower(supress_pow * pow);
                motor2.setPower(-1 * supress_pow * multiplier_non_encoder * motor1.getPower() * speed_control);
            }else if(motor1.getCurrentPosition()>target+supression && motor1.getCurrentPosition()>target+toleranta){
                motor1.setTargetPosition(target);
                motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor1.setPower(pow);
                motor2.setPower( -1 * multiplier_non_encoder * motor1.getPower() * speed_control);
            }
//            }
//            if(gamepad1.b) {
//                motor1.setPower(0);
//                motor2.setPower(0);
//            }
            telemetry.addData("motor1 pow", motor1.getPower());
            telemetry.addData("motor2 pow", motor2.getPower());
//            telemetry.addData("motor1 amps", motor1.getCurrent(CurrentUnit.AMPS));
//            telemetry.addData("motor2 amps", motor2.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("current pos 1", motor1.getCurrentPosition());
            telemetry.addData("current pos 2", motor2.getCurrentPosition());
            telemetry.addData("delta speed", speed_control);
            telemetry.update();

        }
    }
}
