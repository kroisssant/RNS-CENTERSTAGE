package org.firstinspires.ftc.teamcode.Libs;

public class Convertors {
    public int rpmToIpm(int rpm, int wheelDiameterInInch) {
        return Math.toIntExact((long)(rpm * wheelDiameterInInch * Math.PI));
    }
    public double centimetersToInch(int meters) {
        return(meters * 0.3937);
    }
}
