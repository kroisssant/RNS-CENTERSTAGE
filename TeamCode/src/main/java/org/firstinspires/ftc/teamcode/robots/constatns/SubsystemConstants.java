package org.firstinspires.ftc.teamcode.robots.constatns;

import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.config.Config;

@Config
public class SubsystemConstants {
    public static int MAX_VEL_GLISIERA = 0;
    public static int MAX_ACCEL_GLISIERA = 0;
    public static double kV = 0;
    public static double kA = 0;
    public static double kP = 0;
    public static double kG = 0;
    public static double tickPerPerecheGlisiera = 0;
    public static double carriageWeight = 0;

    public static double kPIntake = 0;
    public static double kDIntake = 0;
    public static double kIIntake = 0;
    public static PIDCoefficients glisieraIntakePIDCoef = new PIDCoefficients(kPIntake, kDIntake, kIIntake);

}
