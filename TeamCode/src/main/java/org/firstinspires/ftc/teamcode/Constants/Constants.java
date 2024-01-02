package org.firstinspires.ftc.teamcode.Constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {
    public static final double APRIL_TAG_MAX_VEL = 50; // in / s
    public static final double APRIL_TAG_MAX_ACC = 50; // in / s
    public static double PRESSURE_DREAPTA_DESCHIS = 0.38;
    public static double PRESSURE_DREAPTA_INCHIS = 0.33;

    public static double PRESSURE_STANGA_INCHIS = 0;
    public static double PRESSURE_STANGA_DESCHIS = 0.05;

    public static double PIVOT_JOS = 0;
    public static double PIVOT_SUS = 1;

    public static double BRAT_SUS = 0.9;
    public static double BRAT_JOS = 0.07;

    public static final int GLISIERA_MAX_VELOCITY = 0;
    public static final int GLISIERA_MAX_ACCELERATION = 0;
    public static final double GLISIERA_SPOOL_RADIUS = 0;

    public static double GLISIERE_KV = 0.0005;
    public static double GLISIERE_KS = 0;
    public static double GLISIERE_KA = 0;
    public static double GLISIERE_KG = 0.03;

    public static double GLISIERA_STANGA_KP = 0.005;
    public static double GLISIERA_DREAPTA_KP = 0.005;

    public static double GLISIERA_STANGA_KD = 0;
    public static double GLISIERA_DREAPTA_KD = 0;

    public static double GLISIERA_STANGA_KI = 0;
    public static double GLISIERA_DREAPTA_KI = 0;

    public static double INTAKE_POWER = 0.6;

    public static int GLISIERA_UP = 2000;
    public static  int GLISIERA_DOWN = 0;

    public static int PID_POS_TOLERANCE = 5;

    public static int WAIT_FOR_PIVOT = 500;
    public static int WAIT_FOR_PIVOT_DOWN = 1000;

    public static double NOMINAL_VOLTAGE = 13;

    public static double DROPDOWN_JOS = 0;
    public static double DROPDOWN_SUS = 0.3;

}
