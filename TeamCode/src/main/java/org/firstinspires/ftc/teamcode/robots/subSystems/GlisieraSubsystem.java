package org.firstinspires.ftc.teamcode.robots.subSystems;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.FeedforwardController;
import com.ThermalEquilibrium.homeostasis.Utils.WPILibMotionProfile;
import com.arcrobotics.ftclib.controller.PController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.constatns.SubsystemConstants;

public class GlisieraSubsystem {
    double lastVelocity = 0, lastTime = 0;
    public DcMotorEx glisieraRight;
    WPILibMotionProfile motionProfile;
    ElapsedTime timer;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    FeedforwardController glisieraFeedforward;
    double power;
    PController glisieraP;

    public GlisieraSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        glisieraRight = hardwareMap.get(DcMotorEx.class, "glisieraRight");
        glisieraRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glisieraRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        glisieraRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void setPosition(int ticks) {
//        glisieraFeedforward = new FeedforwardController() {
//            @Override
//            public double calculate(double x, double v, double a) {
//                return SubsystemConstants.kV * v + SubsystemConstants.kA * a;
//            }
//        };
//        WPILibMotionProfile.Constraints constraints =
//                new WPILibMotionProfile.Constraints(SubsystemConstants.MAX_VEL_GLISIERA, SubsystemConstants.MAX_ACCEL_GLISIERA);
//        WPILibMotionProfile.State goal =
//                new WPILibMotionProfile.State(ticks, 0);
//        WPILibMotionProfile.State initial =
//                new WPILibMotionProfile.State(glisieraRight.getCurrentPosition(), glisieraRight.getVelocity());
//        motionProfile =
//                new WPILibMotionProfile(constraints, goal, initial);
//        glisieraP = new PController(SubsystemConstants.kP);
//        timer.reset();
//        lastVelocity = 0;
//        lastTime = 0;
        glisieraRight.setTargetPosition(ticks);
        glisieraRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        glisieraRight.setPower(1);

    }
    public double[] calculatePower() {
        WPILibMotionProfile.State state = motionProfile.calculate(timer.milliseconds());
        power = glisieraFeedforward.calculate(state.position, state.velocity, state.velocity - lastVelocity) / (timer.milliseconds() - lastTime)
                + glisieraP.calculate(glisieraRight.getCurrentPosition(), state.position)
                + getPassivePower(glisieraRight.getCurrentPosition());
        lastVelocity = glisieraRight.getVelocity();
        lastTime = timer.milliseconds();
        return new double[]{power, glisieraRight.getCurrentPosition()};
    }
    public double getPassivePower(int ticks) {
        return SubsystemConstants.kG * (Math.ceil(ticks / SubsystemConstants.tickPerPerecheGlisiera)) + SubsystemConstants.carriageWeight;
    }
    public void updateControlLoop() {
        glisieraRight.setPower(calculatePower()[0]);
    }

}
