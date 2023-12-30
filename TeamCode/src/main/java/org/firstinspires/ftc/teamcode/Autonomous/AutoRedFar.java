package org.firstinspires.ftc.teamcode.Autonomous;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RepeatCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

import java.util.Timer;

@Autonomous
public class AutoRedFar extends CommandOpMode {

    SampleMecanumDrive drive;
    GlisiereSubsystem glisiereSubsystem;
    IntakeSubsystem intakeSubsystem;
    ScoringSubsystem scoringSubsystem;
    private InstantCommand pressureOpen;
    private InstantCommand pressureClose;
    private InstantCommand bratUp;

    private InstantCommand glisieraUp;
    private InstantCommand glisieraDown;

    private SequentialCommandGroup toScoreSequence1;
    private SequentialCommandGroup toScoreSequence2;

    private TrajectorySequence MovCentru;
    private TrajectorySequence MovCentruToStack;
    private TrajectorySequence MovStanga;
    private TrajectorySequence MovStangaToStack;
    private TrajectorySequence MovDreapta;
    private TrajectorySequence MovDreaptaToStack;
    private TrajectorySequence Stack1ToBackbord;

    private SequentialCommandGroup auto;

    @Override
    public void initialize() {
        /*glisiereSubsystem = new GlisiereSubsystem(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        scoringSubsystem = new ScoringSubsystem(hardwareMap);*/

        drive = new SampleMecanumDrive(hardwareMap);

        MovCentru = drive.trajectorySequenceBuilder(new Pose2d(-36, -64, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-36, -14, Math.toRadians(-90)))
                .build();

        MovStanga = drive.trajectorySequenceBuilder(new Pose2d(-36, -64, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-36, -44, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-45, -22, Math.toRadians(-90)))
                .build();

        MovDreapta = drive.trajectorySequenceBuilder(new Pose2d(-36, -64, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-36, -44, Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(-26, -21, Math.toRadians(-90)))
                .build();

        MovCentruToStack = drive.trajectorySequenceBuilder(MovCentru.end())
                .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)))
                .build();

        MovStangaToStack = drive.trajectorySequenceBuilder(MovStanga.end())
                .lineToLinearHeading(new Pose2d(-59, -11.3, Math.toRadians(180)))
                .build();

//        MovDreaptaToStack = drive.trajectorySequenceBuilder(MovDreapta.end())
//                .lineToLinearHeading(new Pose2d(-26, -21, Math.toRadians(-90)))
//                .build();

        Stack1ToBackbord = drive.trajectorySequenceBuilder(MovCentruToStack.end())
                .lineToLinearHeading(new Pose2d(30, -7, Math.toRadians(180)))
                .build();

        pressureOpen = new InstantCommand(() -> {
            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
            scoringSubsystem.pressureToggle = false;
        });

        pressureClose = new InstantCommand(() -> {
            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_INCHIS);
            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_INCHIS);
            scoringSubsystem.pressureToggle = true;
        });

        bratUp = new InstantCommand(() -> {
            scoringSubsystem.setPivot(Constants.PIVOT_SUS);
        });

        glisieraUp = new InstantCommand(() -> {
            glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_UP);
        });

        glisieraDown = new InstantCommand(() -> {
            glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_DOWN);
        });

        /*toScoreSequence1 = new SequentialCommandGroup(
                new InstantCommand(() -> {
                    scoringSubsystem.setPivot(Constants.PIVOT_SUS);
                }),

                new WaitCommand(Constants.WAIT_FOR_PIVOT),

                new InstantCommand(()-> {
                    scoringSubsystem.setBratPos(Constants.BRAT_SUS);
                    glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_UP);
                })
        );

        toScoreSequence2 = new SequentialCommandGroup(
                new InstantCommand(()-> {
                    scoringSubsystem.setBratPos(Constants.BRAT_JOS);
                    scoringSubsystem.setPivot(Constants.PIVOT_JOS);
                }),

                new WaitCommand(Constants.WAIT_FOR_PIVOT_DOWN),

                new InstantCommand(() -> {
                    glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_DOWN);
                })
        );

        register(glisiereSubsystem);*/


        auto = new SequentialCommandGroup(
            new InstantCommand(() -> drive.setPoseEstimate(new Pose2d(-36, -64, Math.toRadians(-90)))),
            new InstantCommand(() -> drive.followTrajectorySequence(MovCentru)),
            new InstantCommand(() -> drive.followTrajectorySequence(MovCentruToStack)),
            new InstantCommand(() -> drive.followTrajectorySequence(Stack1ToBackbord))
        );

        schedule(auto);
    }
}
