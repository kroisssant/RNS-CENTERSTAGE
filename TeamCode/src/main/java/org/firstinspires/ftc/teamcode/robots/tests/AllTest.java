package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTakeOld;

@Config
@TeleOp(group = "test", name = "AllTest")
public class AllTest extends LinearOpMode {
    OutTakeOld outtake;
    IntakeOld intake;
    GAMEPAD gp1;

    public static double dropdown = 0;
    public static double dropdownPower = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        outtake = new OutTakeOld(hardwareMap);
        intake = new IntakeOld(hardwareMap);
        gp1 = new GAMEPAD(this.gamepad1, telemetry);

        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            gp1.run();

            intake.setPower(dropdownPower);
            intake.setDropdown(dropdown);


        }
    }
}
