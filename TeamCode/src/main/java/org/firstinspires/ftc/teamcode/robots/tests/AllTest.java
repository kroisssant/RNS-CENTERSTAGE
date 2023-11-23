package org.firstinspires.ftc.teamcode.robots.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.subSystems.Intake;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@Config
@TeleOp(group = "test", name = "AllTest")
public class AllTest extends LinearOpMode {
    OutTake outtake;
    Intake intake;
    GAMEPAD gp1;

    public static double dropdown = 0;
    public static double dropdownPower = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        outtake = new OutTake(hardwareMap);
        intake = new Intake(hardwareMap);
        gp1 = new GAMEPAD(this.gamepad1, telemetry);

        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            gp1.run();

            intake.setPower(dropdownPower);
            intake.setDropdown(dropdown);


        }
    }
}
