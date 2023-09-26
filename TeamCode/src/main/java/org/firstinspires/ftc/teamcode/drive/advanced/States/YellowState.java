package org.firstinspires.ftc.teamcode.drive.advanced.States;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.STATE;
import org.firstinspires.ftc.teamcode.Libs.StateMachine;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class YellowState implements STATE {
    SampleMecanumDrive drive;
    int loopIndexer = 0;
    long startTime = 0;
    long endTime = 0;
    long allocatedTime = 0;
    Trajectorys trajectorys;
    String name = "Yellow State";
    boolean finnished;

    public void init(SampleMecanumDrive drive, Trajectorys trajectorys) {
        this.drive = drive;
        this.trajectorys = trajectorys;
    };
    public int periodic(int loopIndexer) {
        drive.followTrajectory(trajectorys.toAlliceYellow);
        // activate intake
        // transfer
        //if thing in
        drive.followTrajectory(trajectorys.toBackDrop1);
        // drop on back drop
        finnished = true;
        return loopIndexer;};
    public boolean isFinnished() {return finnished;}
}
