package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.robots.subSystems.OutTakeOld;
import org.firstinspires.ftc.teamcode.robots.subSystems.Variables;

@TeleOp(name = "sequanceTest")
public class sequance extends LinearOpMode {
    OutTakeOld outTake;
    ElapsedTime timer;
    STATES glisieraState = STATES.DONE;
    @Override
    public void runOpMode() throws InterruptedException {
        outTake = new OutTakeOld(hardwareMap);
        timer = new ElapsedTime();
        GAMEPAD GAMEPAD = new GAMEPAD(this.gamepad1, telemetry);
        waitForStart();
        timer.startTime();
        while(opModeIsActive() && !isStopRequested()) {
            if(GAMEPAD.x.toggle) {
                timer.reset();
                glisieraState = STATES.WAITING;
            }
            if(glisieraState ==  STATES.WAITING && timer.milliseconds() > 2000) {
                outTake.setBrat(Variables.pivotSus);
                outTake.setClaw(Variables.pivotSus);
                glisieraState = STATES.DONE;
            }
        }
    }
    enum STATES {
        DONE,
        WAITING
    }
}
