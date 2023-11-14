package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;

@TeleOp(group = "test", name = "intakeTest")
public class intakeTest extends LinearOpMode {
    Intake intake;
    GAMEPAD gp1;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap);
        gp1 = new GAMEPAD(this.gamepad1, telemetry);
        waitForStart();
        while(opModeIsActive() && isStopRequested()) {
            gp1.run();
            if(gp1.left_bumper.value != false) {
                intake.setPower(-1);
            } else if(gp1.right_bumper.value != false) {
                intake.setPower(1);
            } else if(gp1.left_trigger > 0.3) {
                intake.setPower(-0.5);
            } else if(gp1.right_trigger > 0.3) {
                intake.setPower(0.5);
            } else {
                intake.setPower(0);
            }
        }
    }
}
