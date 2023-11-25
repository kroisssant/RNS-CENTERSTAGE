//package org.firstinspires.ftc.teamcode.robots;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvPipeline;
//
//public class UltimateGoalPipeline extends OpenCvPipeline
//{
//    Telemetry telemetry;
//
//    public UltimateGoalPipeline(Telemetry telemetry){
//        this.telemetry = telemetry;
//
//    }
//
//    public enum UltimateGoalRings
//    {
//        FOUR,
//        ONE,
//        ZERO
//    }
//
//
//    static final Scalar RED = new Scalar(255, 0, 0);
//
//    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(461,531);
//    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(600, 600);
//    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(800, 800);
//
//    static final int REGION_WIDTH = 120;
//    static final int REGION_HEIGHT = 80;
//    static final int FOUR_RING_THRESHOLD = 105; // 93    124 for ZERO
//    static final int ONE_RING_THRESHOLD = 115; //104
//
//
//    Point region1_pointA = new Point(
//            REGION1_TOPLEFT_ANCHOR_POINT.x,
//            REGION1_TOPLEFT_ANCHOR_POINT.y);
//    Point region1_pointB = new Point(
//            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
//            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
//
//    Point region2_pointA = new Point(
//            REGION1_TOPLEFT_ANCHOR_POINT.x,
//            REGION1_TOPLEFT_ANCHOR_POINT.y);
//    Point  = new Point(
//            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
//            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
//
//
//    Mat region1_Cb;
//    Mat YCrCb = new Mat();
//    Mat Cb = new Mat();
//    int avg;
//
//    private volatile UltimateGoalRings position = UltimateGoalRings.ZERO;
//
//    void inputToCb(Mat input)
//    {
//        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb );
//        Core.extractChannel(YCrCb, Cb, 2);
//    }
//
//    @Override
//    public void init(Mat firstFrame)
//    {
//        inputToCb(firstFrame);
//        region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
//    }
//
//    @Override
//    public Mat processFrame(Mat input)
//    {
//        inputToCb(input);
//
//        avg = (int) Core.mean(region1_Cb).val[0];
//        telemetry.addData("avg: ",avg);
//        Imgproc.rectangle(
//                input, // Buffer to draw on
//                region1_pointA, // First point which defines the rectangle
//                region1_pointB, // Second point which defines the rectangle
//                RED, // The color the rectangle is drawn in
//                2); // Thickness of the rectangle lines
//
//
//        if(avg <= FOUR_RING_THRESHOLD)
//        {
//            position = UltimateGoalRings.FOUR;
//
//        }
//        else if(avg <= ONE_RING_THRESHOLD)
//        {
//            position = UltimateGoalRings.ONE;
//
//        }
//        else
//        {
//            position = UltimateGoalRings.ZERO;
//        }
//
//        return input;
//    }
//
//    public UltimateGoalRings getAnalysis()
//    {
//        return position;
//    }
//}