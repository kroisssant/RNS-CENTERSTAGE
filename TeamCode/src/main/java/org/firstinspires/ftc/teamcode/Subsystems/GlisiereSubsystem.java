package org.firstinspires.ftc.teamcode.Subsystems;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.FeedforwardEx;
import com.ThermalEquilibrium.homeostasis.Parameters.FeedforwardCoefficientsEx;
import com.ThermalEquilibrium.homeostasis.Utils.Timer;
import com.ThermalEquilibrium.homeostasis.Utils.WPILibMotionProfile;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import org.firstinspires.ftc.teamcode.Utils.RNSMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.HardwareConstants;

@Disabled

public class GlisiereSubsystem extends SubsystemBase {
    public RNSMotor glisieraStanga, glisieraDreapta;
    private Telemetry telemetry;
    private WPILibMotionProfile motionProfile;
    private Timer timer = new Timer();
    private double lastVelocity = 0;
    private double lastTime = 0;

    private int setPoint = 0;

    private PIDController leftPIDController = new PIDController(
            Constants.GLISIERA_STANGA_KP, Constants.GLISIERA_STANGA_KI, Constants.GLISIERA_STANGA_KD
    );
    private PIDController rightPIDController = new PIDController(
            Constants.GLISIERA_DREAPTA_KP, Constants.GLISIERA_DREAPTA_KI, Constants.GLISIERA_DREAPTA_KD
    );

    private FeedforwardEx feedForwardControl = new FeedforwardEx(new FeedforwardCoefficientsEx(
            Constants.GLISIERE_KV, Constants.GLISIERE_KA, Constants.GLISIERE_KS, Constants.GLISIERE_KG, 0
    ));

    public GlisiereSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        glisieraDreapta = new RNSMotor(hardwareMap, HardwareConstants.ID_GLISIERA_DREAPTA);
        glisieraStanga = new RNSMotor(hardwareMap, HardwareConstants.ID_GLISIERA_STANGA);

        glisieraDreapta.setInverted(true);

        glisieraStanga.setZeroPowerBehavior(RNSMotor.ZeroPowerBehavior.BRAKE);
        glisieraDreapta.setZeroPowerBehavior(RNSMotor.ZeroPowerBehavior.BRAKE);

        this.telemetry = telemetry;

        setGlisiereFinalPosition(0);
    }

    @Override
    public void periodic(){
        updateControlLoop();
    }

    public WPILibMotionProfile getMotionProfile(double goalPosition, double goalSpeed){
        feedForwardControl = new FeedforwardEx(new FeedforwardCoefficientsEx(
                Constants.GLISIERE_KV, Constants.GLISIERE_KA, Constants.GLISIERE_KS, 0, 0
        ));

        WPILibMotionProfile.Constraints constraints = new WPILibMotionProfile.Constraints(Constants.GLISIERA_MAX_VELOCITY, Constants.GLISIERA_MAX_ACCELERATION);
        WPILibMotionProfile.State initialState = new WPILibMotionProfile.State(getAveragePosition(), getAverageVelocity());
        WPILibMotionProfile.State finalState = new WPILibMotionProfile.State(goalPosition, goalSpeed);

        return new WPILibMotionProfile(constraints, finalState, initialState);
    }

    public double getAverageVelocity(){
        double leftVelocity = glisieraStanga.getCorrectedVelocity();
        double rightVelocity = glisieraDreapta.getCorrectedVelocity();

        return (leftVelocity+rightVelocity)/2;
    }

    public double getAveragePosition(){
        double leftVelocity = glisieraStanga.getCurrentPosition();
        double rightVelocity = glisieraDreapta.getCurrentPosition();

        return (leftVelocity+rightVelocity)/2;
    }

    public void setGlisiereFinalPosition(int position){
        timer = new Timer();
        motionProfile = getMotionProfile(position, 0);
        setPoint = position;
    }

    public void updateControlLoop(){
        WPILibMotionProfile.State state = motionProfile.calculate(timer.currentTime());

        double leftPower = leftPIDController.calculate(glisieraStanga.getCurrentPosition(), setPoint) + Constants.GLISIERE_KG;

        double rightPower = rightPIDController.calculate(glisieraDreapta.getCurrentPosition(), setPoint) + Constants.GLISIERE_KG;

        glisieraStanga.set(leftPower);
        glisieraDreapta.set(rightPower);

        graphTelemetry(state);
    }

    public boolean glisieraIsAtPlace(){
        return setPoint - Constants.PID_POS_TOLERANCE <= getAveragePosition() && getAveragePosition() <= setPoint + Constants.PID_POS_TOLERANCE;
    }
    public void graphTelemetry(WPILibMotionProfile.State state) {
        telemetry.addData("Viteza glisiera", getAveragePosition());
        telemetry.addData("Viteza Motion Profile", setPoint);
        telemetry.addData("Velocity error", state.velocity - getAverageVelocity());
        telemetry.update();
    }
}
