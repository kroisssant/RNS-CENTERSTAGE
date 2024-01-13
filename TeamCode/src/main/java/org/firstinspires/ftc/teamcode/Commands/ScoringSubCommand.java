package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;

import java.util.function.Supplier;

public class ScoringSubCommand extends CommandBase {
    private ScoringSubsystem scoringSubsystem;
    private Supplier<Boolean> isMoving;

    public ScoringSubCommand(ScoringSubsystem scoringSubsystem, Supplier<Boolean> isMoving) {
        this.scoringSubsystem = scoringSubsystem;
        this.isMoving = isMoving;
    }

    @Override
    public void execute() {
        boolean move = isMoving.get();
        if(Math.abs(scoringSubsystem.getBratPos() - Constants.BRAT_JOS) < 0.06) {
            if(move) {
                scoringSubsystem.setBratPos(Constants.BRAT_JOS + 0.05);
            }
            else {
                scoringSubsystem.setBratPos(Constants.BRAT_JOS);
            }
        }
    }
}
