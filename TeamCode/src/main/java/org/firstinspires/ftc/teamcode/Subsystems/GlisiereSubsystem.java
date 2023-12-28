package org.firstinspires.ftc.teamcode.Subsystems;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.FeedforwardEx;
import com.ThermalEquilibrium.homeostasis.Parameters.FeedforwardCoefficientsEx;
import com.ThermalEquilibrium.homeostasis.Utils.Timer;
import com.ThermalEquilibrium.homeostasis.Utils.WPILibMotionProfile;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;

@Disabled

public class GlisiereSubsystem extends SubsystemBase {
    public Motor glisieraStanga, glisieraDreapta;
    private Telemetry telemetry;
    private WPILibMotionProfile motionProfile; // = getMotionProfile(0,0);
    private Timer timer;
    private double lastVelocity = 0;
    private double lastTime = 0;

    private PIDController leftPIDController = new PIDController(
            Constants.GLISIERA_STANGA_KP, Constants.GLISIERA_STANGA_KI, Constants.GLISIERA_STANGA_KD
    );
    private PIDController rightPIDController = new PIDController(
            Constants.GLISIERA_DREAPTA_KP, Constants.GLISIERA_DREAPTA_KI, Constants.GLISIERA_DREAPTA_KD
    );

    private FeedforwardEx feedForwardControl = new FeedforwardEx(new FeedforwardCoefficientsEx(
        Constants.GLISIERE_KV, Constants.GLISIERE_KA, Constants.GLISIERE_KS, Constants.GLISIERE_KG, 0
    ));

    public GlisiereSubsystem(HardwareMap hardwareMap, Telemetry telemetry, Motor glisieraDreapta, Motor glisieraStanga) {
//        glisieraDreapta = new Motor(hardwareMap, HardwareConstants.ID_GLISIERA_DREAPTA);
//        glisieraStanga = new Motor(hardwareMap, HardwareConstants.ID_GLISIERA_STANGA);
        this.glisieraStanga = glisieraStanga;
        this.glisieraDreapta = glisieraDreapta;

        this.glisieraDreapta.setInverted(true);

        this.glisieraStanga.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        this.glisieraDreapta.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        this.telemetry = telemetry;

        this.motionProfile = getMotionProfile(0,0);

        setGlisiereFinalPosition(0);
    }

    @Override
    public void periodic(){
        updateControlLoop();
    }

    public WPILibMotionProfile getMotionProfile(double goalPosition, double goalSpeed){
        WPILibMotionProfile.Constraints constraints = new WPILibMotionProfile.Constraints(Constants.GLISIERA_MAX_VELOCITY, Constants.GLISIERA_MAX_ACCELERATION);
        WPILibMotionProfile.State initialState = new WPILibMotionProfile.State(glisieraStanga.getCurrentPosition(), getAverageVelocity());
        WPILibMotionProfile.State finalState = new WPILibMotionProfile.State(goalPosition, goalSpeed);

        return new WPILibMotionProfile(constraints,initialState,finalState);
    }

    public double getAverageVelocity(){
        double leftVelocity = glisieraStanga.getCorrectedVelocity();
        double rightVelocity = glisieraDreapta.getCorrectedVelocity();

        return (leftVelocity+rightVelocity)/2;
    }

    public void setGlisiereFinalPosition(int position){
        timer = new Timer();
        motionProfile = getMotionProfile(position, 0);
    }

    public void updateControlLoop(){
        WPILibMotionProfile.State state = motionProfile.calculate(timer.currentTime());

        double leftPower = feedForwardControl.calculate(
                state.position,
                state.velocity,
                (state.velocity - lastVelocity)/(timer.currentTime() - lastTime)
        ) + leftPIDController.calculate(glisieraStanga.getCurrentPosition(), state.position);

        double rightPower = feedForwardControl.calculate(
                state.position,
                state.velocity,
                (state.velocity - lastVelocity)/(timer.currentTime() - lastTime)
        ) + rightPIDController.calculate(glisieraDreapta.getCurrentPosition(), state.position);

        lastVelocity = state.velocity;
        lastTime = timer.currentTime();

        glisieraStanga.set(leftPower);
        glisieraDreapta.set(rightPower);

        graphTelemetry(state);
    }

    public void graphTelemetry(WPILibMotionProfile.State state) {
        telemetry.addData("Viteza glisiera", getAverageVelocity());
        telemetry.addData("Viteza Motion Profile", state.velocity);
        telemetry.addData("Velocity error", state.velocity - getAverageVelocity());
        telemetry.update();
    }
}
