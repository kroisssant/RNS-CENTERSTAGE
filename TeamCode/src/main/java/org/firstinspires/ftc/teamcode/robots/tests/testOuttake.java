package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp
public class testOuttake extends LinearOpMode {
    OutTake outtake;
    @Override
    public void runOpMode() throws InterruptedException {
        outtake = new OutTake(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
        }
    }
}
