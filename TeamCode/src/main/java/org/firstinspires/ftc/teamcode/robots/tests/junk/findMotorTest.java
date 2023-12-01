package org.firstinspires.ftc.teamcode.robots.tests.junk;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
@TeleOp
public class findMotorTest extends LinearOpMode {
    DcMotorEx motor1, motor2, motor3, motor4;
    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        motor1 = hardwareMap.get(DcMotorEx.class, "leftRear");
        motor2 = hardwareMap.get(DcMotorEx.class, "leftFront");
        motor3 = hardwareMap.get(DcMotorEx.class, "rightRear"); //reverse
        motor4 = hardwareMap.get(DcMotorEx.class, "rightFront"); //reverse

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor4.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            telemetry.addData("motor1", motor1.getCurrentPosition()); //lr
            telemetry.addData("motor2", motor2.getCurrentPosition()); //lf -- glisiera dr exp 2 pid
            telemetry.addData("motor3", motor3.getCurrentPosition()); //rr  --intake dreapta exp 3
            telemetry.addData("motor4", motor4.getCurrentPosition()); //fr -- intake st ctrl 3

            telemetry.update();
        }
    }
}
