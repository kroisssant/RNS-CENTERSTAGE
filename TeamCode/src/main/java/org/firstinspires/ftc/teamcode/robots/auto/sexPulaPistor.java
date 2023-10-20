package org.firstinspires.ftc.teamcode.robots.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.StateMachine;
import org.firstinspires.ftc.teamcode.robots.States.Trajectorys;
import org.firstinspires.ftc.teamcode.robots.States.YellowState;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Autonomous
public class sexPulaPistor extends LinearOpMode {
    StateMachine stateMachine;
    SampleMecanumDrive drive;
    Trajectorys trajectorys;
    ElapsedTime timer;
    @Override
    public void runOpMode() throws InterruptedException {
        timer = new ElapsedTime();
        stateMachine = new StateMachine(timer, drive, trajectorys);
        stateMachine.addState(false, new YellowState());
         waitForStart();
         while (opModeIsActive()) {
             stateMachine.periodic();
         }
    }
}
