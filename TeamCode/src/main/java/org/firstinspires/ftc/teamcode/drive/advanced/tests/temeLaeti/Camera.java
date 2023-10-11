package org.firstinspires.ftc.teamcode.drive.advanced.tests.temeLaeti;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;


public class Camera {

    WebcamName webCam;
    OpenCvCamera camera;
    int cameraMonitorViewId;
    public void initCamera(OpenCvPipeline pipeline, HardwareMap hardwareMap){

        webCam = hardwareMap.get(WebcamName.class, "inghetata");
        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(webCam, cameraMonitorViewId);

        camera.setPipeline(pipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                camera.closeCameraDevice();
                //deocamdata speram ca nu da eroare
            }
        });
    }

}
