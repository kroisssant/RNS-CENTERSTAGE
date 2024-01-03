package org.firstinspires.ftc.teamcode.Vision;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class HSVAutoPipeline extends OpenCvPipeline {

    Mat blurredImage = new Mat();
    Mat hsvImage = new Mat();
    Mat hsvOutput = new Mat();
    Mat hsvOutput1 = new Mat();
    Mat finalMat = new Mat();

    Scalar lowerb = new Scalar(80, 50, 50);         // lower color border for BLUE
    Scalar upperb = new Scalar(150, 255, 225);      // upper color border for BLUE

    MatOfPoint2f areaPoints = new MatOfPoint2f();
    RotatedRect boundingRect = new RotatedRect();
    Telemetry telemetry;
    public static double THRESHOLD = 0.2;
    double[] c;
    private int color = 1;
    public static int caz = 1;

    public HSVAutoPipeline(int Color) {
        color = Color;
    }
    public HSVAutoPipeline(int Color, Telemetry telemetry) {
        this.telemetry = telemetry;
        color = Color;
    }

    public Mat processFrame(Mat input, int Color) {
        Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);

        if(Color == 1)
            Core.inRange(hsvImage, lowerb, upperb, hsvOutput);   // select only blue pixel
        else if(Color == 2){
            Core.inRange(hsvImage, new Scalar(0, 50, 100), new Scalar(9, 255, 255), hsvOutput);   // select only red pixel\
            Core.inRange(hsvImage, new Scalar(169, 50, 100), new Scalar(179, 255, 255), hsvOutput1);
            Core.add(hsvOutput1, hsvOutput, hsvOutput); ;

        }

        Imgproc.GaussianBlur(hsvOutput, hsvOutput, new Size(11, 11), 1);

        final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(461,531);
        final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(600, 600);
        final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(800, 800);

        // 1280x720
        Rect left_roi = new Rect(new Point(150,271), new Point(320, 380));
        Rect center_roi = new Rect(new Point(450,240), new Point(950, 350));
        Rect right_roi = new Rect(new Point(1000,250), new Point(1223, 520));

        Mat left = hsvOutput.submat(left_roi);
        Mat right = hsvOutput.submat(right_roi);
        Mat center = hsvOutput.submat(center_roi);

        double leftValue = Core.sumElems(left).val[0]/left_roi.area()/255;
        double rightValue = Core.sumElems(right).val[0]/right_roi.area()/255;
        double centerValue = Core.sumElems(center).val[0]/center_roi.area()/255;

        System.out.println(" 1 " + leftValue);
        System.out.println(" 2 " + rightValue);
        System.out.println(" 3 " + centerValue);

        Imgproc.rectangle(
                input,
                left_roi,
                new Scalar(255, 0, 0)
        );

        Imgproc.rectangle(
                input,
                right_roi,
                new Scalar(255, 0, 0)
        );

        Imgproc.rectangle(
                input,
                center_roi,
                new Scalar(255, 0, 0)
        );

        boolean isCenter = centerValue > THRESHOLD;
        boolean isLeft = leftValue > THRESHOLD;
        boolean isRight = rightValue > THRESHOLD;


        System.out.println("isRight" + isRight);
        System.out.println("isLeft" + isLeft);
        System.out.println("isCenter" + isCenter);

        if(isLeft){
            caz = 2;

            Imgproc.rectangle(
                    input,
                    left_roi,
                    new Scalar(0, 255, 0)
            );
        } else if(isRight){
            caz = 3;

            Imgproc.rectangle(
                    input,
                    right_roi,
                    new Scalar(0, 255, 0)
            );
        } else if(isCenter){
            caz = 1;

            Imgproc.rectangle(
                    input,
                    center_roi,
                    new Scalar(0, 255, 0)
            );
        }
        else {
            caz = 1;
        }

        left.release();
        right.release();
        center.release();

        return input;
    }

    @Override
    public void init (Mat firstFrame){

    }

    @Override
    public Mat processFrame (Mat input){
        return processFrame(input, color);
    }

    public int getCaz() {
        return caz;
    }
}