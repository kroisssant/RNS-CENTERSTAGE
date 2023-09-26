package org.firstinspires.ftc.teamcode.Libs;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class VoltageSpikeDector {
    public double alpha; // Smoothing factor, adjust as needed
    public double currentTreshhold; // Voltage threshold for spike detection (slightly above 12V)
    Telemetry telemetry;
    double ema;
    double current;
    public VoltageSpikeDector (Telemetry telemetry, double currentTreshhold, double alpha){
        this.telemetry = telemetry;
        this.currentTreshhold = currentTreshhold;
        this.alpha = alpha;

    }
    public boolean detect(double current) {
        // Simulating voltage readings (replace this with actual sensor readings)

        // Initialize EMA with the initial voltage reading
        this.current = current;
        ema = current;

        // Continuously monitor the voltage
        while (true) {
            // Read the voltage (replace with actual reading)

            // Update the EMA with the new voltage reading
            ema = alpha * current + (1 - alpha) * ema;

            // Check if EMA voltage exceeds the threshold
            if (ema > ema+currentTreshhold) {
                System.out.println("Current spike detected! EMA Amps: " + ema + "Amps");
                return true;
                // You can take appropriate action here, like stopping the motor or logging the event.
            }
            return false;
        }
    }

    public void printTelemetry() {
        telemetry.addData("Current", this.current);
        telemetry.addData("EMA", ema);
        telemetry.addLine();
        telemetry.update();
    }
}
