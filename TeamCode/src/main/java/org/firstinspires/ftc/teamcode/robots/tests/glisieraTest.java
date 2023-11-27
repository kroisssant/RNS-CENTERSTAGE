package org.firstinspires.ftc.teamcode.robots.tests;

import com.ThermalEquilibrium.homeostasis.Utils.WPILibMotionProfile;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTake;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@Config
@TeleOp(group = "test", name = "Glisiera TEST")
public class glisieraTest extends LinearOpMode {
    OutTake outtake;
    double[] state;

    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        outtake = new OutTake(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            if(gamepad1.b) {
                outtake.setPosition(Variables.glisieraLow);
            } else if(gamepad1.y) {
                outtake.setPosition(Variables.glisieraHigh);
            }else if(gamepad1.a) {
                outtake.setPosition(Variables.glisieraDown);
            }
            state = outtake.calculatePower();
            outtake.glisieraDreapta.setPower(state[1]);
            outtake.glisieraStanga.setPower(state[0]);
            telemetry.addData("Velocity", state[2]);
            telemetry.addData("Velocity glis", outtake.getVelocity());
            telemetry.update();
        }
    }
}
