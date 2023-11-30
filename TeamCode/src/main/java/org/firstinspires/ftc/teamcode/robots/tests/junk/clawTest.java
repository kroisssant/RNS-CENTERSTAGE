package org.firstinspires.ftc.teamcode.robots.tests.junk;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;

@Config
@TeleOp
public class clawTest extends LinearOpMode {
    public static double posClaw = 0.79;
    public static double posBrat = 0.1;
    public static double pressureStanga = 0.35;
    public static double pressureDreapta = 0.46;

    OutTake outTake;
    @Override
    public void runOpMode() throws InterruptedException {
        outTake = new OutTake(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            outTake.claw.setPosition(posClaw);
            outTake.setBrat(posBrat);
            outTake.pressureStanga.setPosition(pressureStanga);
            outTake.pressureDreapta.setPosition(pressureDreapta);
        }
    }
}
