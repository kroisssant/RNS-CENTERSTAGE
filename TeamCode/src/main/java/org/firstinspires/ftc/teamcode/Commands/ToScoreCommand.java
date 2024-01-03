package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;

public class ToScoreCommand extends SequentialCommandGroup {
    private int setPoint = Constants.GLISIERA_UP;

    private long waitTime = 0;
    private ScoringSubsystem scoringSubsystem;
    private GlisiereSubsystem glisiereSubsystem;

    public ToScoreCommand(int setPoint, ScoringSubsystem scoringSubsystem, GlisiereSubsystem glisiereSubsystem) {
        this.setPoint = setPoint;
        this.scoringSubsystem = scoringSubsystem;
        this.glisiereSubsystem = glisiereSubsystem;

        createCommand();
    }

    public ToScoreCommand(int setPoint, long waitTime, ScoringSubsystem scoringSubsystem, GlisiereSubsystem glisiereSubsystem) {
        this.setPoint = setPoint;
        this.waitTime = waitTime;
        this.scoringSubsystem = scoringSubsystem;
        this.glisiereSubsystem = glisiereSubsystem;

        createCommand();
    }

    public void createCommand() {
        addCommands(
                new InstantCommand(() -> {
                    scoringSubsystem.setPivot(Constants.PIVOT_SUS);
                }),

                new WaitCommand(Constants.WAIT_FOR_PIVOT),

                new InstantCommand(()-> {
                    scoringSubsystem.setBratPos(Constants.BRAT_SUS);
                    glisiereSubsystem.setGlisiereFinalPosition(setPoint);
                }),

                new WaitCommand(waitTime)
        );
    }
}
