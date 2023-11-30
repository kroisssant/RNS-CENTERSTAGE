package org.firstinspires.ftc.teamcode.robots.subSystems;


import com.acmerobotics.dashboard.config.Config;

@Config
public class Variables {
    public static double pressureDreaptaClose = 0.35;
    public static double pressureDreaptaOpen = 0.46;
    public static double pressureStangaClose = 0.26;
    public static double pressureStangaOpen = 0.35;

    public static int glisieraDown = 100;
    public static int glisieraLow = 300;
    public static int glisieraMid = 800;
    public static int glisieraHigh = 1000;
    public static int glisieraMax = 1550; //TODO: DE SCHIMBAT
    public static int numarPerechiGlisiere = 3;
    public static double tickPerPerecheGlisiera = glisieraMax / numarPerechiGlisiere;

    public static double maxVelocity = 450;
    public static double maxAccel = 1200;
    public static double kV = 0.004;
    public static double kA = 0.0001;
    public static double kS = 0;
    public static double kG = 0.1;
    public static double kP = 0.01;
    public static double carriageWeight = 0.05;

    public static double bratDefault = 0;

    public static double bratJos = 0.08;
    public static double bratSus = 0.95;

    public static double clawJos = 0.79;
    public static double clawSus = 0.46;

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
