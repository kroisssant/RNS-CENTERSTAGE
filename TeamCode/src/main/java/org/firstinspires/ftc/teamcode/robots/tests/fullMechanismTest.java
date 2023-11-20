package org.firstinspires.ftc.teamcode.robots.tests;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp(group = "test", name = "mechansismTest")
public class fullMechanismTest extends LinearOpMode {
    Intake intake;
    OutTake outtake;

    GAMEPAD GAMEPAD1;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap);
        outtake = new OutTake(hardwareMap);
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            GAMEPAD1.run();
            if(GAMEPAD1.right_bumper.value) {
                outtake.setZeroPowerBeh(DcMotor.ZeroPowerBehavior.FLOAT);
                outtake.setClawDreapta(Variables.pressureDreaptaClose);
                outtake.setClawStanga(Variables.pressureStangaClose);
                outtake.setBrat(Variables.bratDefault);
                outtake.setClaw(Variables.clawDefault);

                intake.setDropdown(Variables.dropdownUp);
            }
            if(GAMEPAD1.left_bumper.value != false) {
                intake.setPower(-1);
            } else if(GAMEPAD1.right_bumper.value != false) {
                intake.setPower(1);
            } else if(GAMEPAD1.left_trigger > 0.3) {
                intake.setPower(-Variables.intakePower);
            } else if(GAMEPAD1.right_trigger > 0.3) {
                intake.setPower(Variables.intakePower);
            } else {
                intake.setPower(Variables.intakeNopower);
            }
            telemetry.addData("posGlisiera",outtake.getPosition());
            telemetry.update();
        }
    }
}
