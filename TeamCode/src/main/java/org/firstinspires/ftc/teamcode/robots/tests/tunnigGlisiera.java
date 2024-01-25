package org.firstinspires.ftc.teamcode.robots.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.subSystems.IntakeSubsystem;

public class  tunnigGlisiera extends LinearOpMode {
    IntakeSubsystem intake;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new IntakeSubsystem(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            if(gamepad1.a) {
                intake.setIntakePos(300);
            }
            if(gamepad1.b) {
                intake.setIntakePower(0);
            }
            intake.glisieraIntake.getCurrentPosition();
            intake.updateControlLoop();
        }
    }
}
