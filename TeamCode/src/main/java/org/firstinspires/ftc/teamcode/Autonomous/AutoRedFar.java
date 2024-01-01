package org.firstinspires.ftc.teamcode.Autonomous;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;
import org.firstinspires.ftc.teamcode.Utils.CommandOpModeAuto;
import org.firstinspires.ftc.teamcode.Vision.HSVAutoPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.Timer;

@Autonomous
public class AutoRedFar extends CommandOpModeAuto {
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

    private ConditionalCommand autoCommand;

    private Pose2d startPosition = new Pose2d(-36, -64, Math.toRadians(-90));

    private TrajectorySequence MovLeftPlace;
    private TrajectorySequence MovCentruPlace;
    private TrajectorySequence MovRightPlace;
    private TrajectorySequence MovLeftMoveToStack1;
    private TrajectorySequence MovCentruMoveToStack1;
    private TrajectorySequence MovRightMoveToStack1;
    private TrajectorySequence MovLeftMoveToStack2;
    private TrajectorySequence MovCentruMoveToStack2;
    private TrajectorySequence MovRightMoveToStack2;
    private TrajectorySequence StackToBackboard1;
    private TrajectorySequence StackToBackboardLeft;
    private TrajectorySequence StackToBackboardCenter;
    private TrajectorySequence StackToBackboardRight;

    private HSVAutoPipeline pipeline = new HSVAutoPipeline(2);
    private OpenCvCamera camera;


    @Override
    public void initialize() {
        initOpenCV();

        glisiereSubsystem = new GlisiereSubsystem(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        scoringSubsystem = new ScoringSubsystem(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);

        MovCentruPlace = drive.trajectorySequenceBuilder(startPosition)
                .lineToLinearHeading(new Pose2d(-36, -24, Math.toRadians(-90)))
                .build();

        MovLeftPlace = drive.trajectorySequenceBuilder(startPosition)
                .lineToLinearHeading(new Pose2d(-45, -22, Math.toRadians(-90)))
                .build();

        MovRightPlace = drive.trajectorySequenceBuilder(startPosition)
                .lineToLinearHeading(new Pose2d(-36, -33, Math.toRadians(-90)))
                .turn(Math.toRadians(90))
                .lineToLinearHeading(new Pose2d(-24, -33, Math.toRadians(0)))
                .build();

        MovLeftMoveToStack1 = drive.trajectorySequenceBuilder(MovLeftPlace.end())
                .lineToLinearHeading(new Pose2d(-45, -14, Math.toRadians(-90)))
                .build();

        MovCentruMoveToStack1 = drive.trajectorySequenceBuilder(MovCentruPlace.end())
                .lineToLinearHeading(new Pose2d(-36, -14, Math.toRadians(-90)))
                .build();

        MovRightMoveToStack1 = drive.trajectorySequenceBuilder(MovRightPlace.end())
                .lineToLinearHeading(new Pose2d(-36, -33, Math.toRadians(0)))
                .build();

        MovLeftMoveToStack2 = drive.trajectorySequenceBuilder(MovLeftMoveToStack1.end())
                .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)))
                .build();

        MovCentruMoveToStack2 = drive.trajectorySequenceBuilder(MovCentruMoveToStack1.end())
                .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)))
                .build();

        MovRightMoveToStack2 = drive.trajectorySequenceBuilder(MovRightMoveToStack1.end())
                .lineToLinearHeading(new Pose2d(-45, -14, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)))
                .build();

        StackToBackboard1 = drive.trajectorySequenceBuilder(MovCentruMoveToStack2.end())
                .lineToLinearHeading(new Pose2d(30, -7, Math.toRadians(180)))
                .build();

        StackToBackboardLeft = drive.trajectorySequenceBuilder(MovCentruMoveToStack2.end())
                .lineToLinearHeading(new Pose2d(50, -30, Math.toRadians(180)))
                .build();

        StackToBackboardCenter = drive.trajectorySequenceBuilder(MovCentruMoveToStack2.end())
                .lineToLinearHeading(new Pose2d(50, -35, Math.toRadians(180)))
                .build();

        StackToBackboardRight = drive.trajectorySequenceBuilder(MovCentruMoveToStack2.end())
                .lineToLinearHeading(new Pose2d(50, -40, Math.toRadians(180)))
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

        toScoreSequence1 = new SequentialCommandGroup(
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

        register(glisiereSubsystem);

        //caz left
        SequentialCommandGroup autoLeft = new SequentialCommandGroup(
                new InstantCommand(() -> drive.setPoseEstimate(startPosition)),
                new InstantCommand(() -> drive.followTrajectorySequence(MovLeftPlace)),
                pressureOpen,
                new InstantCommand(() -> drive.followTrajectorySequence(MovLeftMoveToStack1)),
                //ceva chestie sa lasam intakeul jos???????
                new InstantCommand(() -> drive.followTrajectorySequence(MovLeftMoveToStack2)),
                new InstantCommand(() -> intakeSubsystem.runFwd()),
                new InstantCommand(() -> drive.followTrajectorySequence(StackToBackboard1)),
                new ParallelCommandGroup(
                        toScoreSequence1,
                        new InstantCommand(() -> drive.followTrajectorySequence(StackToBackboardLeft))
                ),
                toScoreSequence2
        );

        //caz center
        SequentialCommandGroup autoCenter = new SequentialCommandGroup(
                new InstantCommand(() -> drive.setPoseEstimate(startPosition)),
                new InstantCommand(() -> drive.followTrajectorySequence(MovCentruPlace)),
                pressureOpen,
                new InstantCommand(() -> drive.followTrajectorySequence(MovCentruMoveToStack1)),
                //ceva chestie sa lasam intakeul jos???????
                new InstantCommand(() -> drive.followTrajectorySequence(MovCentruMoveToStack2)),
                new InstantCommand(() -> intakeSubsystem.runFwd()),
                new InstantCommand(() -> drive.followTrajectorySequence(StackToBackboard1)),
                new ParallelCommandGroup(
                        toScoreSequence1,
                        new InstantCommand(() -> drive.followTrajectorySequence(StackToBackboardCenter))
                ),
                toScoreSequence2
        );

        //caz right
        SequentialCommandGroup autoRight = new SequentialCommandGroup(
                new InstantCommand(() -> drive.setPoseEstimate(startPosition)),
                new InstantCommand(() -> drive.followTrajectorySequence(MovRightPlace)),
                pressureOpen,
                new InstantCommand(() -> drive.followTrajectorySequence(MovRightMoveToStack1)),
                //ceva chestie sa lasam intakeul jos???????
                new InstantCommand(() -> drive.followTrajectorySequence(MovRightMoveToStack2)),
                new InstantCommand(() -> intakeSubsystem.runFwd()),
                new InstantCommand(() -> drive.followTrajectorySequence(StackToBackboard1)),
                new ParallelCommandGroup(
                        toScoreSequence1,
                        new InstantCommand(() -> drive.followTrajectorySequence(StackToBackboardRight))
                ),
                toScoreSequence2
        );

        autoCommand = new ConditionalCommand(
                autoLeft,
                new ConditionalCommand(
                        autoCenter,
                        autoRight,
                        () -> pipeline.getCaz() == 1
                ),
                () -> pipeline.getCaz() == 2
        );
    }

    @Override
    public void runOnce() {
        autoCommand.schedule();

        new Thread(() -> camera.closeCameraDevice());
    }

    private void initOpenCV() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280,720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {}
        });
    }
}
