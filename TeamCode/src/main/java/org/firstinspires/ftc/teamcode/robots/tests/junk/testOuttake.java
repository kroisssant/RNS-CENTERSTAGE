package org.firstinspires.ftc.teamcode.robots.tests.junk;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robots.subSystems.OutTakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp
@Disabled
public class testOuttake extends LinearOpMode {
    OutTakeOld outtake;
    @Override
    public void runOpMode() throws InterruptedException {
        outtake = new OutTakeOld(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            outtake.setBrat(Variables.bratJos);
            outtake.setClaw(Variables.pivotJos);
            outtake.setPressureDreapta(Variables.pressureDreaptaOpen);
            outtake.setPressureStanga(Variables.pressureStangaOpen);
        }
    }
}
