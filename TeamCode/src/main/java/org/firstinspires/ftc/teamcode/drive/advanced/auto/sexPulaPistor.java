package org.firstinspires.ftc.teamcode.drive.advanced.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.State;

import org.firstinspires.ftc.teamcode.Libs.StateMachine;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.advanced.States.Trajectorys;
import org.firstinspires.ftc.teamcode.drive.advanced.States.YellowState;

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
