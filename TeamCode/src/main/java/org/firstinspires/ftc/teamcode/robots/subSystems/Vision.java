//package org.firstinspires.ftc.teamcode.robots.subSystems;
//
//import static org.firstinspires.ftc.teamcode.robots.DriveConstants.TFOD_MODEL_ASSET;
//
//import android.util.Size;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.tfod.TfodProcessor;
//
//import java.util.List;
//
//public class Vision {
//
//    private TfodProcessor tfod;
//    private VisionPortal visionPortal;
//
//    private Telemetry telemetry;
//
//    boolean enable = true;
//
//
//    public Vision(Telemetry telemetry, HardwareMap hardwareMap) {
//        this.telemetry = telemetry;
//        // Create the TensorFlow processor by using a builder.
//        tfod = new TfodProcessor.Builder()
//
//                // With the following lines commented out, the default TfodProcessor Builder
//                // will load the default model for the season. To define a custom model to load,
//                // choose one of the following:
//                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
//                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
//                .setModelAssetName(TFOD_MODEL_ASSET)
//                //.setModelFileName(TFOD_MODEL_FILE)
//
//                // The following default settings are available to un-comment and edit as needed to
//                // set parameters for custom models.
//                //.setModelLabels(LABELS)
//                //.setIsModelTensorFlow2(true)
//                //.setIsModelQuantized(true)
//                .setModelInputSize(300)
////                .setModelAspectRatio(16.0 / 9.0)
//
//                .build();
//
//        VisionPortal.Builder builder = new VisionPortal.Builder();
//
//        // set all coefs for vision portal
//        visionPortal = builder.setCamera(hardwareMap.get(WebcamName.class, "inghetata"))
//                .setCameraResolution(new Size(640, 480))
//                .enableCameraMonitoring(true)
//                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
//                .setAutoStopLiveView(false)
//                .addProcessor(tfod)
//                .build();
//        tfod.setMinResultConfidence(0.75f);
//        visionPortal.setProcessorEnabled(tfod, enable);
//
//    }   // end method initTfod()
//
//    /**
//     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
//     */
//    private void telemetryTfod() {
//
//        List<Recognition> currentRecognitions = tfod.getRecognitions();
//        telemetry.addData("# Objects Detected", currentRecognitions.size());
//        // Step through the list of recognitions and display info for each one.
//        for (Recognition recognition : currentRecognitions) {
//            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
//            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
//
//            telemetry.addData(""," ");
//            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
//            telemetry.addData("- Position", "%.0f / %.0f", x, y);
//            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
//        }   // end for() loop
//
//    }   // end method telemetryTfod()
//
//    public List<Recognition> getRecognitions() {
//        return tfod.getRecognitions();
//    }
//    public void disable() {enable = false;}
//    public void enable() {enable = true;}
//}
