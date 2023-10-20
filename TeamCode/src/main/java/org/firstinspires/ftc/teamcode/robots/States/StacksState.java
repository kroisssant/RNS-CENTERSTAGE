package org.firstinspires.ftc.teamcode.robots.States;

import org.firstinspires.ftc.teamcode.Libs.STATE;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class StacksState implements STATE {

    SampleMecanumDrive drive;
    int loopIndexer = 0;
    long startTime = 0;
    long endTime = 0;
    long allocatedTime = 0;
    Trajectorys trajectorys;
    String name = "Stacks State";
    boolean finnished;

    public void init(SampleMecanumDrive drive, Trajectorys trajectorys) {
        this.drive = drive;
        this.trajectorys = trajectorys;
    };
    public int periodic(int loopIndexer) {
        drive.followTrajectory(trajectorys.toStack1);
        // After it arives start to loop thru the PIXELS
        // And after every pixel was shouted increase the loopIndexer

        return loopIndexer;
    }
    public boolean isFinnished() {return finnished;}
}
