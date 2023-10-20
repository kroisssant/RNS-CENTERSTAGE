package org.firstinspires.ftc.teamcode.robots.tests.temeLaeti;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class TestPipeline extends OpenCvPipeline {

    Mat blurredImage = new Mat();
    Mat hsvImage = new Mat();
    Mat hsvOutput = new Mat();
    Mat finalOutput = new Mat();

    Point ACTUAL_CENTERPOINT = new Point();
    Point CLOSEST_CENTERPOIT1 = new Point(0,0);
    Point CLOSEST_CENTERPOIT2 = new Point(0,0);

    long ACTUAL_RADIUS;
    long CLOSEST_CIRCLE_RADIUS1 = 0;
    long CLOSEST_CIRCLE_RADIUS2 = 0;

    double[] c;

    public void findCircle(Mat input, Mat output) {
        //Blur
        Imgproc.blur(input, blurredImage, new Size(15, 15));

        //convert frame to hsv (hue, saturation, value)
        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsvImage, new Scalar(45, 45, 65), new Scalar(90, 220, 245), hsvOutput);

        //searching the circle by using Hough
        Imgproc.Canny(hsvOutput, hsvOutput, 10, 20);
        Imgproc.HoughCircles(hsvOutput, output, Imgproc.HOUGH_GRADIENT, 2.3, 50);

        ArrayList<Point> centers = new ArrayList<>();
        ArrayList<Long> radius = new ArrayList<>();

        CLOSEST_CIRCLE_RADIUS1 = 0;
        CLOSEST_CIRCLE_RADIUS2 = 0;

        for(int i=0; i < output.cols(); i++){
            c = output.get(0, i);

            ACTUAL_CENTERPOINT.x = Math.round(c[0]);
            ACTUAL_CENTERPOINT.y = Math.round(c[1]);
            ACTUAL_RADIUS = Math.round(c[2]);

            //araylists of the centerpoints and radiuses of all the circles found
            centers.add(new Point(ACTUAL_CENTERPOINT.x, ACTUAL_CENTERPOINT.y));
            radius.add(ACTUAL_RADIUS);

            //finding the closest 2 based on the radius
            //drawings for testing purposes only
            if(ACTUAL_RADIUS > CLOSEST_CIRCLE_RADIUS1){

                CLOSEST_CIRCLE_RADIUS2 = CLOSEST_CIRCLE_RADIUS1;
                CLOSEST_CENTERPOIT2 = CLOSEST_CENTERPOIT1;
                drawCircleFound(input, new Scalar(0, 250, 0), CLOSEST_CENTERPOIT2, (int) CLOSEST_CIRCLE_RADIUS2);

                CLOSEST_CENTERPOIT1 = ACTUAL_CENTERPOINT;
                CLOSEST_CIRCLE_RADIUS1 = ACTUAL_RADIUS;
                drawCircleFound(input, new Scalar(255,0,0),  CLOSEST_CENTERPOIT1, (int) CLOSEST_CIRCLE_RADIUS1);

            } else if(ACTUAL_RADIUS > CLOSEST_CIRCLE_RADIUS1){

                CLOSEST_CENTERPOIT2 = ACTUAL_CENTERPOINT;
                CLOSEST_CIRCLE_RADIUS2 = ACTUAL_RADIUS;
                drawCircleFound(input, new Scalar(0,250,0),  CLOSEST_CENTERPOIT2, (int) CLOSEST_CIRCLE_RADIUS2);

            } else {

                drawCircleFound(input, new Scalar(0,0,0), ACTUAL_CENTERPOINT, (int) ACTUAL_RADIUS);
            }

        }

    }

    //in order to test the finding
    public void drawCircleFound(Mat input, Scalar scalar, Point point, int radius ){
        Imgproc.circle(input, point, (int) radius, scalar, 10);
    }

    @Override
    public void init(Mat firstFrame){}

    @Override
    public Mat processFrame(Mat input) {
        findCircle(input, finalOutput);
        return input;
    }
}
