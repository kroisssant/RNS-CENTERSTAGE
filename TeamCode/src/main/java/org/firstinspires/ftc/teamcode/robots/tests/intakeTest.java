package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@Config
@TeleOp(group = "test", name = "intakeTest")
public class intakeTest extends LinearOpMode {
    OutTake outtake;
    Intake intake;
    GAMEPAD gp1;
    public static double pos;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        outtake = new OutTake(hardwareMap);
        intake = new Intake(hardwareMap);
        gp1 = new GAMEPAD(this.gamepad1, telemetry);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            gp1.run();
            if(gp1.left_bumper.value != false) {
                intake.setPower(-1);
            } else if(gp1.right_bumper.value != false) {
                intake.setPower(1);
            } else if(gp1.left_trigger > 0.3) {
                intake.setPower(-Variables.intakePower);
            } else if(gp1.right_trigger > 0.3) {
                intake.setPower(Variables.intakePower);
            } else {
                intake.setPower(Variables.intakeNopower);
            }
            intake.setDropdown(pos);
            outtake.setClawDreapta(Variables.pressureDreaptaOpen);
            outtake.setClawStanga(Variables.pressureStangaOpen);
        }
    }
}
