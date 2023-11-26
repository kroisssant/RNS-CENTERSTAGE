package org.firstinspires.ftc.teamcode.robots.tests;

import android.graphics.LinearGradient;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.robots.auto.HSVPipelineAuto;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class TestCamera extends LinearOpMode {
    HSVPipelineAuto  pipeline;
    OpenCvCamera camera;
    @Override
    public void runOpMode() throws InterruptedException {
        pipeline = new HSVPipelineAuto(2, this.telemetry);
        initOpenCV();
        FtcDashboard.getInstance().startCameraStream(camera, 0);
        while (opModeInInit() && !isStopRequested()) {
            telemetry.addData("caz", pipeline.getCaz());
            telemetry.update();
        }
        waitForStart();
    }





    private void initOpenCV() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280,720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {}
        });
    }
}
