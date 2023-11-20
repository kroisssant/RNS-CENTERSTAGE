package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Libs.VoltageSpikeDector;

@TeleOp
@Disabled
public class voltageSpikeDectorTest extends LinearOpMode {
    DcMotorEx motor;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        motor = hardwareMap.get(DcMotorEx.class, "motorDeTest");
        VoltageSpikeDector spikeDector= new VoltageSpikeDector(this.telemetry, 1, 1000);
        waitForStart();
        while(opModeIsActive()) {
            motor.setPower(0.5);

            telemetry.addData("Spike", spikeDector.detect(motor.getCurrent(CurrentUnit.AMPS)));
            spikeDector.printTelemetry();

            telemetry.update();
        }
    }
}
