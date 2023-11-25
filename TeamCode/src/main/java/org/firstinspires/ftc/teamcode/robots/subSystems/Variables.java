package org.firstinspires.ftc.teamcode.robots.subSystems;


import com.acmerobotics.dashboard.config.Config;

@Config
public class Variables {
    public static double pressureDreaptaClose = 0.05;
    public static double pressureDreaptaOpen = 0.13;
    public static double pressureStangaClose = 0.05;
    public static double pressureStangaOpen = 0.18;

    public static int glisieraDown = 100;
    public static int glisieraLow = 300;
    public static int glisieraMid = 800;
    public static int glisieraHigh = 1000;

    public static double bratDefault = 0;

    public static double bratJos = 0.1;
    public static double bratSus = 0.9;

    public static double pivotJos = 0.93;
    public static double pivotSus = 0.55;

    public static double dropdownPower = 0;

    public static double intakePower = 0.4;
    public static double intakeNopower = 0;


    public static double pow = 1;
    public static double supress_pow = 0.3;
    public static int target = 300;
    public static int toleranta = 10;
    public static int supression = 100;

    public static double powManual = 0.4;
    public static double supress_powManual = 0.1;
    public static int tolerantaManual = 10;
    public static int supressionManual = 0;

    public static double multiplier_non_encoder = 0.8;
    public static double speed_control = 1;


}
