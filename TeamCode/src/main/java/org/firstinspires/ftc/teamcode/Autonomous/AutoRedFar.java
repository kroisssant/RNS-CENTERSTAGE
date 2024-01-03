package org.firstinspires.ftc.teamcode.Autonomous;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Commands.RoadRunnerCommand;
import org.firstinspires.ftc.teamcode.Commands.ScoreCommand;
import org.firstinspires.ftc.teamcode.Commands.ToScoreCommand;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;
import org.firstinspires.ftc.teamcode.Utils.CommandOpModeAuto;
import org.firstinspires.ftc.teamcode.Vision.HSVAutoPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

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

    private SequentialCommandGroup toScoreSequence;
    private SequentialCommandGroup scoreSequence;

    private ConditionalCommand autoCommand;

    private Pose2d startPosition = new Pose2d(-40, -64, Math.toRadians(90));

    private TrajectorySequence MovLeftPlace;
    private TrajectorySequence MovCentruPlace;
    private TrajectorySequence MovRightPlace;
    private TrajectorySequence MovLeftMoveToStack;
    private TrajectorySequence MovCentruMoveToStack;
    private TrajectorySequence MovRightMoveToStack;
    private TrajectorySequence StackToBackboard1;
    private TrajectorySequence StackToBackboardLeft;
    private TrajectorySequence StackToBackboardCenter;
    private TrajectorySequence StackToBackboardRight;

    private HSVAutoPipeline pipeline = new HSVAutoPipeline(2);
    private OpenCvCamera camera;

    SequentialCommandGroup autoLeft;
    SequentialCommandGroup autoCenter;
    SequentialCommandGroup autoRight;


    @Override
    public void initialize() {
        //initOpenCV();

        glisiereSubsystem = new GlisiereSubsystem(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        scoringSubsystem = new ScoringSubsystem(hardwareMap);

        scoringSubsystem.pressureClose();
        intakeSubsystem.dropdownUp();

        scoringSubsystem.setBratPos(0.25);
        scoringSubsystem.setPivot(Constants.PIVOT_SUS - 0.2);

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setVision(false);

        MovCentruPlace = drive.trajectorySequenceBuilder(startPosition)
                .lineToLinearHeading(new Pose2d(-36, -4, Math.toRadians(90)))
                .build();

        MovLeftPlace = drive.trajectorySequenceBuilder(startPosition)
                .lineToLinearHeading(new Pose2d(-45, -22, Math.toRadians(90)))
                .build();

        MovRightPlace = drive.trajectorySequenceBuilder(startPosition)
                .lineToLinearHeading(new Pose2d(-36, -33, Math.toRadians(180)))
                .build();

        MovLeftMoveToStack = drive.trajectorySequenceBuilder(MovLeftPlace.end())
                .splineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)), Math.toRadians(180))
                .build();

        MovCentruMoveToStack = drive.trajectorySequenceBuilder(MovCentruPlace.end())
                .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)))
                .build();

        MovRightMoveToStack = drive.trajectorySequenceBuilder(MovRightPlace.end())
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)), Math.toRadians(180))
                .build();

        StackToBackboard1 = drive.trajectorySequenceBuilder(MovCentruMoveToStack.end())
                .lineToLinearHeading(new Pose2d(30, -7, Math.toRadians(180)))
                .build();

        StackToBackboardLeft = drive.trajectorySequenceBuilder(StackToBackboard1.end())
                .lineToLinearHeading(new Pose2d(50, -30, Math.toRadians(180)))
                .build();

        StackToBackboardCenter = drive.trajectorySequenceBuilder(StackToBackboard1.end())
                .lineToLinearHeading(new Pose2d(50, -35, Math.toRadians(180)))
                .build();

        StackToBackboardRight = drive.trajectorySequenceBuilder(StackToBackboard1.end())
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

        register(glisiereSubsystem);

        //caz left
        autoLeft = new SequentialCommandGroup(
                new InstantCommand(() -> drive.setPoseEstimate(startPosition)),
                new ParallelCommandGroup(
                        new RoadRunnerCommand(drive, MovLeftPlace),
                        new ToScoreCommand(100, Constants.PIVOT_JOS, 250, scoringSubsystem, glisiereSubsystem)
                ),
                new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
                            scoringSubsystem.pressureToggle = false;
                        }),
                        new WaitCommand(500),
                        new ScoreCommand(Constants.GLISIERA_DOWN, scoringSubsystem, glisiereSubsystem)
                ),
                new ParallelCommandGroup(
                        new InstantCommand(() -> intakeSubsystem.dropdownDown()),
                        new InstantCommand(() -> intakeSubsystem.runFwd()),
                        new RoadRunnerCommand(drive, MovLeftMoveToStack)
                ),
                new SequentialCommandGroup(
                        new WaitCommand(500),
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_INCHIS);
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_INCHIS);
                            scoringSubsystem.pressureToggle = true;
                        }),
                        new WaitCommand(500),
                        new InstantCommand(() -> intakeSubsystem.runRvs())
                ),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new WaitCommand(200),
                                new InstantCommand(() -> intakeSubsystem.end()),
                                new InstantCommand(() -> intakeSubsystem.dropdownUp())
                        ),
                        new RoadRunnerCommand(drive, StackToBackboard1)
                ),
                new ParallelCommandGroup(
                        new ToScoreCommand(1000, Constants.PIVOT_SUS, 1500, scoringSubsystem, glisiereSubsystem),
                        new RoadRunnerCommand(drive, StackToBackboardLeft)
                ),
                new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
                            scoringSubsystem.pressureToggle = false;
                        }),
                        new WaitCommand(250),
                        new ScoreCommand(Constants.GLISIERA_DOWN, scoringSubsystem, glisiereSubsystem)
                )
        );

        //caz center
         autoCenter = new SequentialCommandGroup(
                new InstantCommand(() -> drive.setPoseEstimate(startPosition)),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new WaitCommand(250),

                                new InstantCommand(() -> {
                                    scoringSubsystem.setPivot(Constants.PIVOT_SUS);
                                }),

//                new WaitCommand(Constants.WAIT_FOR_PIVOT),

                                new InstantCommand(()-> {
                                    scoringSubsystem.setBratPos(1);
                                    glisiereSubsystem.setGlisiereFinalPosition(100);
                                }),

                                new WaitCommand(100)
                                )  ,
                        new RoadRunnerCommand(drive, MovCentruPlace)
                ),
                new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
//                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
                            scoringSubsystem.pressureToggle = false;
                        }),
                        new WaitCommand(500),
                        new ScoreCommand(Constants.GLISIERA_DOWN, scoringSubsystem, glisiereSubsystem)
                ),
                new ParallelCommandGroup(
                        new InstantCommand(() -> intakeSubsystem.dropdownDown()),
                        new InstantCommand(() -> intakeSubsystem.runFwd()),
                        new RoadRunnerCommand(drive, MovCentruMoveToStack)
                ),
                new SequentialCommandGroup(
                        new WaitCommand(500),
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_INCHIS);
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_INCHIS);
                            scoringSubsystem.pressureToggle = true;
                        }),
                        new WaitCommand(500),
                        new InstantCommand(() -> intakeSubsystem.runRvs())
                ),
                new ParallelCommandGroup(
                        new RoadRunnerCommand(drive, StackToBackboard1),
                        new SequentialCommandGroup(
                                new WaitCommand(200),
                                new InstantCommand(() -> intakeSubsystem.end()),
                                new InstantCommand(() -> intakeSubsystem.dropdownUp())
                        )
                ),
                new ParallelCommandGroup(
                        new ToScoreCommand(1000,Constants.PIVOT_SUS,  1500, scoringSubsystem, glisiereSubsystem),
                        new RoadRunnerCommand(drive, StackToBackboardCenter)
                ),
                new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
                            scoringSubsystem.pressureToggle = false;
                        }),
                        new WaitCommand(250),
                        new ScoreCommand(Constants.GLISIERA_DOWN, scoringSubsystem, glisiereSubsystem)
                )
        );

        //caz right
         autoRight = new SequentialCommandGroup(
                new InstantCommand(() -> drive.setPoseEstimate(startPosition)),
                new RoadRunnerCommand(drive, MovRightPlace),
                new SequentialCommandGroup(
                        new ToScoreCommand(100, Constants.PIVOT_JOS, 250, scoringSubsystem, glisiereSubsystem),
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
                            scoringSubsystem.pressureToggle = false;
                        }),
                        new WaitCommand(500),
                        new ScoreCommand(Constants.GLISIERA_DOWN, scoringSubsystem, glisiereSubsystem)
                ),
                new ParallelCommandGroup(
                        new InstantCommand(() -> intakeSubsystem.dropdownDown()),
                        new InstantCommand(() -> intakeSubsystem.runFwd()),
                        new RoadRunnerCommand(drive, MovRightMoveToStack)
                ),
                new SequentialCommandGroup(
                        new WaitCommand(500),
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_INCHIS);
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_INCHIS);
                            scoringSubsystem.pressureToggle = true;
                        }),
                        new WaitCommand(500),
                        new InstantCommand(() -> intakeSubsystem.runRvs())
                ),
                new ParallelCommandGroup(
                        new RoadRunnerCommand(drive, StackToBackboard1),
                        new SequentialCommandGroup(
                                new WaitCommand(500),
                                new InstantCommand(() -> intakeSubsystem.end()),
                                new InstantCommand(() -> intakeSubsystem.dropdownUp())
                        )
                ),
                new ParallelCommandGroup(
                        new ToScoreCommand(Constants.GLISIERA_UP, scoringSubsystem, glisiereSubsystem),
                        new RoadRunnerCommand(drive, StackToBackboardRight)
                ),
                new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            scoringSubsystem.setPressureDreaptaPos(Constants.PRESSURE_DREAPTA_DESCHIS);
                            scoringSubsystem.setPressureStangaPos(Constants.PRESSURE_STANGA_DESCHIS);
                            scoringSubsystem.pressureToggle = false;
                        }),
                        new WaitCommand(250),
                        new ScoreCommand(Constants.GLISIERA_DOWN, scoringSubsystem, glisiereSubsystem)
                )
        );

        //1 == center, 2 == left, 3 == right default
        /*autoCommand = new ConditionalCommand(
                autoLeft,
                new ConditionalCommand(
                        autoRight,
                        autoCenter,
                        () -> pipeline.getCaz() == 3
                ),
                () -> pipeline.getCaz() == 2
        );*/
    }

    @Override
    public void runOnce() {
        autoCenter.schedule();

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
