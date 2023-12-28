package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private DoubleSupplier fw, str, rot;

    public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier fw, DoubleSupplier str, DoubleSupplier rot) {
        this.driveSubsystem = driveSubsystem;
        this.fw = fw;
        this.str = str;
        this.rot = rot;
    }

    @Override
    public void execute() {
        driveSubsystem.drive(str.getAsDouble(), -fw.getAsDouble(), rot.getAsDouble());
    }
}
