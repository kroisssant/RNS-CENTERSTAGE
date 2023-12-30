package org.firstinspires.ftc.teamcode.robots.tests.junk;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;
@Disabled
@TeleOp(group = "test", name = "mechansismTest")
public class fullMechanismTest extends LinearOpMode {
    IntakeOld intake;
    OutTakeOld outtake;

    GAMEPAD GAMEPAD1;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = new IntakeOld(hardwareMap);
        outtake = new OutTakeOld(hardwareMap);
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            GAMEPAD1.run();
            if(GAMEPAD1.right_bumper.value) {
                outtake.setZeroPowerBeh(DcMotor.ZeroPowerBehavior.FLOAT);
                outtake.setPressureDreapta(Variables.pressureDreaptaClose);
                outtake.setPressureStanga(Variables.pressureStangaClose);
                outtake.setBrat(Variables.bratDefault);
//                outtake.setClaw(Variables.clawDefault);

//                intake.setDropdown(Variables.dropdownUp);
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
