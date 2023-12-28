package org.firstinspires.ftc.teamcode.TeleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.GlisiereSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ScoringSubsystem;

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
    private InstantCommand bratDown;
    private InstantCommand pivotUp;
    private InstantCommand pivotDown;
    private InstantCommand intakeOn;
    private InstantCommand intakeOff;
    private InstantCommand glisieraUp;
    private InstantCommand glisieraDown;



    @Override
    public void initialize() {
        driveSubsystem = new DriveSubsystem(hardwareMap);
        glisiereSubsystem = new GlisiereSubsystem(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        scoringSubsystem = new ScoringSubsystem(hardwareMap);

        driver1 = new GamepadEx(gamepad1);

        driveCommand = new DriveCommand(driveSubsystem, driver1::getLeftY, driver1::getLeftX, driver1::getRightX);

        pressureOpen = new InstantCommand(() -> {
            scoringSubsystem.setPressurePos(Constants.PRESSURE_DESCHIS);
        });

        pressureClose = new InstantCommand(() -> {
            scoringSubsystem.setPressurePos(Constants.PRESSURE_INCHIS);
        });

        bratUp = new InstantCommand(() -> {
            scoringSubsystem.setBratPos(Constants.BRAT_SUS);
        });

        bratDown = new InstantCommand(() -> {
            scoringSubsystem.setBratPos(Constants.BRAT_JOS);
        });

        pivotUp = new InstantCommand(() -> {
            scoringSubsystem.setPivot(Constants.PIVOT_SUS);
        });

        pivotDown = new InstantCommand(() -> {
            scoringSubsystem.setPivot(Constants.PIVOT_JOS);
        });

        intakeOn = new InstantCommand(() -> {
            intakeSubsystem.setIntakePower(Constants.INTAKE_POWER);
        });

        intakeOff = new InstantCommand(() -> {
            intakeSubsystem.setIntakePower(0);
        });

        glisieraUp = new InstantCommand(() -> {
            glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_UP);
        });

        glisieraDown = new InstantCommand(() -> {
            glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_DOWN);
        });

        new GamepadButton(driver1, GamepadKeys.Button.A).whenPressed(pressureOpen);
        new GamepadButton(driver1, GamepadKeys.Button.B).whenPressed(pressureClose);
        new GamepadButton(driver1, GamepadKeys.Button.X).whenPressed(pivotUp);
        new GamepadButton(driver1, GamepadKeys.Button.Y).whenPressed(pivotDown);
        new GamepadButton(driver1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(bratDown);
        new GamepadButton(driver1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(bratUp);
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_RIGHT).whenPressed(intakeOn);
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_LEFT).whenPressed(intakeOff);
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP).whenPressed(glisieraUp);
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN).whenPressed(glisieraDown);

        driveSubsystem.setDefaultCommand(driveCommand);
        register(glisiereSubsystem);
    }
}
