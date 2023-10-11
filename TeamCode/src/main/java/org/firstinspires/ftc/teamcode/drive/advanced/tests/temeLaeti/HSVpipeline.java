package org.firstinspires.ftc.teamcode.drive.advanced.tests.temeLaeti;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class HSVpipeline extends OpenCvPipeline {

    Mat blurredImage = new Mat();
    Mat hsvImage = new Mat();
    Mat hsvOutput = new Mat();
    Mat finalOutput = new Mat();

    final Point TARGET_CENTERPOINT = new Point(320,240);
    Point ACTUAL_CENTERPOINT = new Point();

    final long TARGET_RADIUS = 100;
    long ACTUAL_RADIUS;
    double[] c;
    boolean foundCircle = false;

    public void findCircle(Mat input, Mat output) {
        //Blur
        Imgproc.blur(input, blurredImage, new Size(10, 10));

        //convert frame to hsv (hue, saturation, value)
        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsvImage, new Scalar(45, 45, 65), new Scalar(90, 220, 245), hsvOutput);

        //searching the circle by using Hough
        Imgproc.Canny(hsvOutput, hsvOutput, 10, 20);
        Imgproc.HoughCircles(hsvOutput, output, Imgproc.HOUGH_GRADIENT, 2.3, 50);


        if(output == null){
            foundCircle = false;
        } else {
            foundCircle = true;
        }

        for(int i=0; i<output.cols(); i++){
            c = output.get(0, i);
            ACTUAL_CENTERPOINT.x = Math.round(c[0]);
            ACTUAL_CENTERPOINT.y = Math.round(c[1]);
            ACTUAL_RADIUS = Math.round(c[2]);
        }
    }

    //in order to test the finding
    public void drawCircleFound(Mat input){
        Imgproc.circle(input, ACTUAL_CENTERPOINT, (int) ACTUAL_RADIUS, new Scalar(238,130,238), 20);
        Imgproc.drawMarker(input, ACTUAL_CENTERPOINT, new Scalar(238,130,238));
    }

    @Override
    public void init(Mat firstFrame){}

    @Override
    public Mat processFrame(Mat input) {
        findCircle(input, finalOutput);
        drawCircleFound(input);
        return input;
    }
}
