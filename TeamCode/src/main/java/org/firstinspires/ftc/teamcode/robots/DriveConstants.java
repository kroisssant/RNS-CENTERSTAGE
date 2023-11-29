package org.firstinspires.ftc.teamcode.robots;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/*
 * Constants shared between multiple drive types.
 *
 * TODO: Tune or adjust the following constants to fit your robot. Note that the non-final
 * fields may also be edited through the dashboard (connect to the robot's WiFi network and
 * navigate to https://192.168.49.1:8080/dash). Make sure to save the values here after you
 * adjust them in the dashboard; **config variable changes don't persist between app restarts**.
 *
 * These are not the only parameters; some are located in the localizer classes, drive base classes,
 * and op modes themselves.
 */
@Config
public class DriveConstants {

    /*
     * These are motor constants that should be listed online for your motors.
     */
    public static final double TICKS_PER_REV = 384.5;
    public static final double MAX_RPM = 435;

    /*
     * Set RUN_USING_ENCODER to true to enable built-in hub velocity control using drive encoders.
     * Set this flag to false if drive encoders are not present and an alternative localization
     * method is in use (e.g., tracking wheels).
     *
     * If using the built-in motor velocity PID, update MOTOR_VELO_PID with the tuned coefficients
     * from DriveVelocityPIDTuner.
     */
    public static final boolean RUN_USING_ENCODER = true;
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(25 , 0, 9.55, 13);

    //  THIS ARE FOR FTCLIB RAMSETE CONTROLLER
    public static double B = 2.0;
    public static double ZETA = 0.7;
    /*
     * These are physical constants that can be determined from your robot (including the track
     * width; it will be tune empirically later although a rough estimate is important). Users are
     * free to chose whichever linear distance unit they would like so long as it is consistently
     * used. The default values were selected with inches in mind. Road runner uses radians for
     * angular distances although most angular parameters are wrapped in Math.toRadians() for
     * convenience. Make sure to exclude any gear ratio included in MOTOR_CONFIG from GEAR_RATIO.
     */
    public static double WHEEL_RADIUS = 1.88; // in
    public static double WHEEL_DIAMETER = WHEEL_RADIUS * 2;
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 12.7; // in

    // FTCLIB
    public static double DISTANCE_PER_PULSE = WHEEL_DIAMETER * Math.PI / TICKS_PER_REV;

    /*
     * These are the feedforward parameters used to model the drive motor behavior. If you are using
     * the built-in velocity PID, *these values are fine as is*. However, if you do not have drive
     * motor encoders or have elected not to use them for velocity control, these values should be
     * empirically tuned.
     */
    public static double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0;

    // Drivetrain.class constants
    public static double kPxy = 0;
    public static double kPHeading = 0;

    /*
     * These values are used to generate the trajectories for you robot. To ensure proper operation,
     * the constraints should never exceed ~80% of the robot's actual capabilities. While Road
     * Runner is designed to enable faster autonomous motion, it is a good idea for testing to start
     * small and gradually increase them later after everything is working. All distance units are
     * inches.
     */

    // MAX_VEL AND MAX_ACCEL ARE USED AS MAX_VELOCITY AND MAX_ACCELEATION IN FTCLIB
    public static double MAX_VEL =50;
    public static double MAX_ACCEL = 60;
    public static double MAX_ANG_VEL = Math.toRadians(60);
    public static double MAX_ANG_ACCEL = Math.toRadians(60);

    public static TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(MAX_VEL, MAX_ACCEL);

    public static PIDCoefficients xPIDCoef = new PIDCoefficients(0, 0, 0);
    public static PIDCoefficients yPIDCoef = new PIDCoefficients(0, 0, 0);
    public static PIDCoefficients thetaPIDCoef = new PIDCoefficients(0, 0, 0);

    public static PIDController xPID = new PIDController(xPIDCoef.p, xPIDCoef.d, xPIDCoef.i);
    public static PIDController yPID = new PIDController(yPIDCoef.p, yPIDCoef.d, yPIDCoef.i);
    public static ProfiledPIDController thetaPID= new ProfiledPIDController(thetaPIDCoef.p, thetaPIDCoef.d,
            thetaPIDCoef.i, constraints);

    public static Translation2d m_frontLeftLocation =
            new Translation2d(0.1981, 0.1981);
    public static Translation2d m_frontRightLocation =
            new Translation2d(0.1981, -0.1981);
    public static Translation2d m_backLeftLocation =
            new Translation2d(-0.1981, 0.1981);
    public static Translation2d m_backRightLocation =
            new Translation2d(-0.1981, -0.1981);

    /*
     * Adjust the orientations here to match your robot. See the FTC SDK documentation for details.
     */
    public static RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIR =
            RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIR =
            RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

    // Vision.class constatns

    public static final String TFOD_MODEL_ASSET = "pixie FPNLITE.tflite";
    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;
    }

    public static double getMotorVelocityF(double ticksPerSecond) {
        // see https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit#heading=h.61g9ixenznbx
        return 32767 / ticksPerSecond;
    }
}
