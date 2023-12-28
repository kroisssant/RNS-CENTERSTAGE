package org.firstinspires.ftc.teamcode.TeleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
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

    Motor glisieraStanga, glisieraDreapta;

    DriveSubsystem driveSubsystem;
    GlisiereSubsystem glisiereSubsystem;
    IntakeSubsystem intakeSubsystem;
    ScoringSubsystem scoringSubsystem;
    DriveCommand driveCommand;
    GamepadEx driver1;

    private InstantCommand pressureOpen;
    private InstantCommand pressureClose;
    private InstantCommand bratUpAndPivot;

    private InstantCommand glisieraUp;
    private InstantCommand glisieraDown;

    @Override
    public void initialize() {
        glisieraDreapta = new Motor(hardwareMap, HardwareConstants.ID_GLISIERA_DREAPTA);
        glisieraStanga = new Motor(hardwareMap, HardwareConstants.ID_GLISIERA_STANGA);

        driveSubsystem = new DriveSubsystem(hardwareMap);
        glisiereSubsystem = new GlisiereSubsystem(hardwareMap, telemetry, glisieraDreapta, glisieraStanga);
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

        bratUpAndPivot = new InstantCommand(() -> {
            scoringSubsystem.setPivot(Constants.PIVOT_SUS);
            sleep(500);
            scoringSubsystem.setBratPos(Constants.BRAT_SUS);
        });

        glisieraUp = new InstantCommand(() -> {
            glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_UP);
        });

        glisieraDown = new InstantCommand(() -> {
            glisiereSubsystem.setGlisiereFinalPosition(Constants.GLISIERA_DOWN);
        });

        driveSubsystem.setDefaultCommand(driveCommand);
        register(glisiereSubsystem);

        driver1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(bratUpAndPivot);

        driver1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new ConditionalCommand(pressureOpen,
                        pressureClose,
                        () -> { return scoringSubsystem.pressureToggle;}
                ));

        driver1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whileHeld(new InstantCommand(intakeSubsystem::runFwd))
                .whenReleased(new InstantCommand(intakeSubsystem::end));

        driver1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whileHeld(new InstantCommand(intakeSubsystem::runRvs))
                .whenReleased(new InstantCommand(intakeSubsystem::end));

        new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP).whenPressed(glisieraUp);
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN).whenPressed(glisieraDown);


    }
}
