package org.firstinspires.ftc.teamcode.robots.auto;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;


import java.util.ArrayList;
import java.util.List;

@Config
public class HSVPipelineAuto extends OpenCvPipeline {

    Mat blurredImage = new Mat();
    Mat hsvImage = new Mat();
    Mat hsvOutput = new Mat();
    Mat finalMat = new Mat();

    Scalar lowerb = new Scalar(6, 10, 150);         // lower color border for BLUE
    Scalar upperb = new Scalar(84, 50, 220);      // upper color border for BLUE

    MatOfPoint2f areaPoints = new MatOfPoint2f();
    RotatedRect boundingRect = new RotatedRect();

    double THRESHOLD = 0.2;
    double[] c;
    private int color = 1;
    public static int caz = 2; //default

    public HSVPipelineAuto(int Color) {
        color = Color;
    }

    public Mat processFrame(Mat input, int Color) {
        Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);

        if(Color == 1)
            Core.inRange(hsvImage, lowerb, upperb, hsvOutput);   // select only blue pixel
        else if(Color == 2)
            Core.inRange(hsvImage, new Scalar(0, 200, 100), new Scalar(50, 250, 200), hsvOutput);   // select only red pixel

        final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(461,531);
        final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(600, 600);
        final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(800, 800);

        // 1280x720
        Rect left_roi = new Rect(new Point(80,271), new Point(220, 380));
        Rect right_roi = new Rect(new Point(450,188), new Point(650, 320));
        Rect center_roi = new Rect(new Point(1000,129), new Point(1223, 320));

        Mat left = hsvOutput.submat(left_roi);
        Mat right = hsvOutput.submat(right_roi);
        Mat center = hsvOutput.submat(center_roi);

        double leftValue = Core.sumElems(left).val[0]/left_roi.area()/255;
        double rightValue = Core.sumElems(right).val[0]/right_roi.area()/255;
        double centerValue = Core.sumElems(center).val[0]/center_roi.area()/255;

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

        boolean isLeft = leftValue > THRESHOLD;
        boolean isRight = rightValue > THRESHOLD;
        boolean isCenter = centerValue > THRESHOLD;

//        if(isLeft){
//            caz = 3;
//
//            Imgproc.rectangle(
//                    input,
//                    left_roi,
//                    new Scalar(0, 255, 0)
//            );
//        } else if(isRight){
//            caz = 2;
//
//            Imgproc.rectangle(
//                input,
//                right_roi,
//                new Scalar(0, 255, 0)
//            );
//        } else {
//            caz = 1;
//
//            Imgproc.rectangle(
//                input,
//                center_roi,
//                new Scalar(0, 255, 0)
//            );
//        }
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
