package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;

public class ScoreCommand extends SequentialCommandGroup {
    private int setPoint = Constants.GLISIERA_DOWN;
    private ScoringSubsystem scoringSubsystem;
    private GlisiereSubsystem glisiereSubsystem;

    public ScoreCommand(int setPoint, ScoringSubsystem scoringSubsystem, GlisiereSubsystem glisiereSubsystem) {
        this.setPoint = setPoint;
        this.scoringSubsystem = scoringSubsystem;
        this.glisiereSubsystem = glisiereSubsystem;

        createCommand();
    }

    public void createCommand() {
        addCommands(
                new InstantCommand(()-> {
                    scoringSubsystem.setBratPos(Constants.BRAT_JOS);
                }),
                new WaitCommand(Constants.WAIT_FOR_BRAT_JOS),

                new InstantCommand(() -> {
                    scoringSubsystem.setPivot(Constants.PIVOT_JOS);
                }),

                new WaitCommand(Constants.WAIT_FOR_PIVOT_DOWN),

                new InstantCommand(() -> {
                    glisiereSubsystem.setGlisiereFinalPosition(setPoint);
                })
        );
    }
}
