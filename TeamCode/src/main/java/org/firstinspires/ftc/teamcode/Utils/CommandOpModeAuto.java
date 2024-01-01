package org.firstinspires.ftc.teamcode.Utils;

import com.arcrobotics.ftclib.command.CommandOpMode;

public abstract class CommandOpModeAuto extends CommandOpMode {
    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        if(!isStopRequested() && opModeIsActive()) {
            runOnce();
        }

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            run();
        }

        reset();
    }

    public abstract void runOnce();
}
