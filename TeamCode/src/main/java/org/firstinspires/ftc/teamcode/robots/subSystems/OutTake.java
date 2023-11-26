package org.firstinspires.ftc.teamcode.robots.subSystems;

import static org.firstinspires.ftc.teamcode.robots.subSystems.Variables.kG;
import static org.firstinspires.ftc.teamcode.robots.subSystems.Variables.toleranta;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.FeedbackController;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.FeedforwardController;
import com.ThermalEquilibrium.homeostasis.Utils.Timer;
import com.ThermalEquilibrium.homeostasis.Utils.WPILibMotionProfile;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OutTake {
    public Servo claw, pressureStanga, pressureDreapta;
    public DcMotorEx glisieraDreapta, glisieraStanga;
    public Servo bratStanga, bratDreapta;
    public WPILibMotionProfile motionProfile = new WPILibMotionProfile(new WPILibMotionProfile.Constraints(0, 0), new WPILibMotionProfile.State(0, 0));
    public FeedforwardController motorFeedForward = new FeedforwardController() {
        @Override
        public double calculate(double x, double v, double a) {
            return Variables.kV * v + Variables.kA * a;
        }
    };
    public PIDController glisieraPIDStanga = new PIDController(Variables.kP, 0, 0);
    public PIDController glisieraPIDDreapta = new PIDController(Variables.kP, 0, 0);;
    public Timer timer = new Timer();
    public double startTime = 0;

    public double lastTime = 0;
    public double lastVelocity = 0;

    public OutTake (HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");

        pressureDreapta = hardwareMap.get(Servo.class, "pressureDreapta");
        pressureStanga = hardwareMap.get(Servo.class, "pressureStanga");
        pressureStanga.setDirection(Servo.Direction.REVERSE);

        bratStanga = hardwareMap.get(Servo.class, "bratStanga");
        bratDreapta = hardwareMap.get(Servo.class, "bratDreapta");
        bratStanga.setDirection(Servo.Direction.REVERSE);

        glisieraDreapta = hardwareMap.get(DcMotorEx.class, "glisieraDreapta");
        glisieraStanga = hardwareMap.get(DcMotorEx.class, "glisieraStanga");

        glisieraDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glisieraStanga.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        glisieraDreapta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setZeroPowerBeh(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setZeroPowerBeh(DcMotorEx.ZeroPowerBehavior beh) {
        glisieraDreapta.setZeroPowerBehavior(beh);
        glisieraStanga.setZeroPowerBehavior(beh);
    }

    public void run(double power, int target) {
        glisieraDreapta.setTargetPosition(target);
        glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        glisieraDreapta.setPower(power);
        glisieraStanga.setPower(glisieraDreapta.getPower());
    }
    public void stopGlisiera() {
        glisieraStanga.setPower(0);
        glisieraDreapta.setPower(0);
    }
    public void setPressureDreapta(double pos) {
        pressureDreapta.setPosition(pos);
    }
    public void setPressureStanga(double pos) {
        pressureStanga.setPosition(pos);
    }
    public double getPressureDreapta(){return (double) pressureDreapta.getPosition();};
    public void setClaw(double pos) {
        claw.setPosition(pos);
    }

    public void setBrat(double pos) {
        bratDreapta.setPosition(pos);
        bratStanga.setPosition(pos);
    }

    public void glisieraPos(int target, int supress, int toleranta){
        if(glisieraDreapta.getCurrentPosition()>=target-toleranta && glisieraDreapta.getCurrentPosition()<=target+toleranta) {
            glisieraDreapta.setPower(0);
            glisieraStanga.setPower(0);
        }else if(glisieraDreapta.getCurrentPosition()>target-supress && glisieraDreapta.getCurrentPosition()<target-toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.supress_pow * Variables.pow);
            glisieraStanga.setPower(Variables.supress_pow * glisieraDreapta.getPower() * Variables.multiplier_non_encoder * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()<target-supress && glisieraDreapta.getCurrentPosition()<target-toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.pow);
            glisieraStanga.setPower(glisieraDreapta.getPower() * Variables.multiplier_non_encoder * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()<target+supress && glisieraDreapta.getCurrentPosition()>target+toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.supress_pow * Variables.pow);
            glisieraStanga.setPower(-1 * Variables.supress_pow * Variables.multiplier_non_encoder * glisieraDreapta.getPower() * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()>target+supress && glisieraDreapta.getCurrentPosition()>target+toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.pow);
            glisieraStanga.setPower( -1 * Variables.multiplier_non_encoder * glisieraDreapta.getPower() * Variables.speed_control);
        }
    }
    public void glisieraPos(int target){
        if(glisieraDreapta.getCurrentPosition()>=target-toleranta && glisieraDreapta.getCurrentPosition()<=target+toleranta) {
            glisieraDreapta.setPower(0);
            glisieraStanga.setPower(0);
        }else if(glisieraDreapta.getCurrentPosition()>target-Variables.supression && glisieraDreapta.getCurrentPosition()<target-toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.supress_pow * Variables.pow);
            glisieraStanga.setPower(Variables.supress_pow * glisieraDreapta.getPower() * Variables.multiplier_non_encoder * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()<target-Variables.supression && glisieraDreapta.getCurrentPosition()<target-toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.pow);
            glisieraStanga.setPower(glisieraDreapta.getPower() * Variables.multiplier_non_encoder * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()<target+Variables.supression && glisieraDreapta.getCurrentPosition()>target+toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.supress_pow * Variables.pow);
            glisieraStanga.setPower(-1 * Variables.supress_pow * Variables.multiplier_non_encoder * glisieraDreapta.getPower() * Variables.speed_control);
        }else if(glisieraDreapta.getCurrentPosition()>target+Variables.supression && glisieraDreapta.getCurrentPosition()>target+toleranta){
            glisieraDreapta.setTargetPosition(target);
            glisieraDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glisieraDreapta.setPower(Variables.pow);
            glisieraStanga.setPower( -1 * Variables.multiplier_non_encoder * glisieraDreapta.getPower() * Variables.speed_control);
        }
    }

    public void setPosition(int ticks) {
        motorFeedForward = new FeedforwardController() {
            @Override
            public double calculate(double x, double v, double a) {
                return Variables.kV * v + Variables.kA * a;
            }
        };
        glisieraPIDStanga = new PIDController(Variables.kP, 0, 0);
        glisieraPIDDreapta = new PIDController(Variables.kP, 0, 0);;

        WPILibMotionProfile.Constraints constraints = new WPILibMotionProfile.Constraints(Variables.maxVelocity, Variables.maxAccel);
        WPILibMotionProfile.State initial = new WPILibMotionProfile.State(getPosition(), getVelocity());
        WPILibMotionProfile.State goal = new WPILibMotionProfile.State(ticks, 0);
        motionProfile = new WPILibMotionProfile(constraints, goal, initial);

        startTime = timer.currentTime();
        lastTime = 0;
        lastVelocity = 0;
    }

    public double[] calculatePower() {
        WPILibMotionProfile.State state = motionProfile.calculate(timer.currentTime() - startTime);

        double leftMotorPower = motorFeedForward.calculate(state.position, state.velocity, (state.velocity - lastVelocity) / (timer.currentTime() - lastTime))
                + glisieraPIDStanga.calculate(glisieraStanga.getCurrentPosition(), state.position)
                + getPassivePower(glisieraStanga.getCurrentPosition());

        double rightMotorPower = motorFeedForward.calculate(state.position, state.velocity, (state.velocity - lastVelocity) / (timer.currentTime() - lastTime))
                + glisieraPIDDreapta.calculate(glisieraDreapta.getCurrentPosition(), state.position)
                + getPassivePower(glisieraDreapta.getCurrentPosition());

        lastTime = timer.currentTime();
        lastVelocity = state.velocity;

        return new double[]{leftMotorPower, rightMotorPower, state.position};
    }

    public double getPassivePower(int ticks) {
        return Variables.kG * (Math.ceil(ticks / Variables.tickPerPerecheGlisiera)) + Variables.carriageWeight;
    }
    public void glisieraTelemetry(Telemetry telemetry1){
        telemetry1.addData("glisiera dreapta Pos", glisieraDreapta.getCurrentPosition());
        telemetry1.addData("glisiera stanga Pos", glisieraStanga.getCurrentPosition());
        telemetry1.update();
    }

    public int getPosition() {
        return (glisieraDreapta.getCurrentPosition() + glisieraStanga.getCurrentPosition()) / 2;
    }
    public double getVelocity(){
        return ( glisieraDreapta.getVelocity() + glisieraStanga.getVelocity() ) / 2;
    }
}
