package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.wolfpackmachina.bettersensors.Drivers.ColorSensorV3;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class SensorTest extends LinearOpMode {

    ColorSensorV3 colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.get(ColorSensorV3.class, "colorSensor");

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("green", colorSensor.green());

            telemetry.addData("distance", colorSensor.getDistance(DistanceUnit.CM));

            telemetry.update();
        }
    }
}
