package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.RepeatCommand;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class RoadRunnerCommand extends CommandBase {
    SampleMecanumDrive drive;
    TrajectorySequence trajectorySequence;

    public RoadRunnerCommand(SampleMecanumDrive drive, TrajectorySequence trajectorySequence) {
        this.drive = drive;
        this.trajectorySequence = trajectorySequence;
    }

    @Override
    public void initialize() {
        drive.followTrajectorySequenceAsync(trajectorySequence);
    }
    @Override
    public void execute() {
        if(drive.isBusy()) {
            drive.update();
        }
    }

    @Override
    public boolean isFinished() {
        return !drive.isBusy();
    }
}
