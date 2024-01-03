package org.firstinspires.ftc.teamcode.TeleOp;

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
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;

import java.util.Timer;

@TeleOp
public class TeleOpMain extends CommandOpMode {

    DriveSubsystem driveSubsystem;
    GlisiereSubsystem glisiereSubsystem;
    IntakeSubsystem intakeSubsystem;
    ScoringSubsystem scoringSubsystem;
    DriveCommand driveCommand;
    GamepadEx driver1;

    private InstantCommand pressureOpen;
    private InstantCommand pressureClose;
    private InstantCommand bratUp;

    private InstantCommand glisieraUp;
    private InstantCommand glisieraDown;

    private SequentialCommandGroup toScoreSequence1;
    private SequentialCommandGroup toScoreSequence2;

    @Override
    public void initialize() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        glisiereSubsystem = new GlisiereSubsystem(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        scoringSubsystem = new ScoringSubsystem(hardwareMap);

        driver1 = new GamepadEx(gamepad1);

        driveCommand = new DriveCommand(driveSubsystem, driver1::getLeftY, driver1::getLeftX, driver1::getRightX);

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

        driveSubsystem.setDefaultCommand(driveCommand);
        register(glisiereSubsystem);

        driver1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(bratUp);


        driver1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(
                        new ConditionalCommand(
                                pressureOpen,
                                pressureClose,
                                () -> scoringSubsystem.pressureToggle
                        )
                );

        driver1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(
                        new ConditionalCommand(
                                new InstantCommand(()->intakeSubsystem.dropdownUp()),
                                new InstantCommand(()->intakeSubsystem.dropdownDown()),
                                intakeSubsystem::isDropDownDown
                        )
                );

        driver1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whileHeld(new InstantCommand(intakeSubsystem::runFwd))
                .whenReleased(new InstantCommand(intakeSubsystem::end));

        driver1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whileHeld(new InstantCommand(intakeSubsystem::runRvs))
                .whenReleased(new InstantCommand(intakeSubsystem::end));

        new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP).whenPressed(toScoreSequence1);
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN).whenPressed(toScoreSequence2);
    }
}