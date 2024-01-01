package com.example.meepmeep;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class Meepmeep {

    TrajectorySequence movStanga;
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(480);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                //MOV STANGA
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.49)
                .setDimensions(13.38, 14.76)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-36, -64, Math.toRadians(-90)))
                                .lineToLinearHeading(new Pose2d(-36, -33, Math.toRadians(-90)))
                                .turn(Math.toRadians(90))
                                .lineToLinearHeading(new Pose2d(-24, -33, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(-36, -33, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(-45, -14, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(30, -7, Math.toRadians(180)))

                                .build()
                );


        //MOV CENTRU
        // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
        /*.setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.49)
        .setDimensions(13.38, 14.76)
        .followTrajectorySequence(drive ->
                drive.trajectorySequenceBuilder(new Pose2d(-36, -64, Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(-36, -24, Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(-36, -14, Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(-60, -12, Math.toRadians(180)))
                        .lineToLinearHeading(new Pose2d(30, -7, Math.toRadians(180)))
                        .lineToLinearHeading(new Pose2d(50, -35, Math.toRadians(180)))

                        .build()
        );*/


        /*MOV DREAPTA
        // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
        .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.49)
        .setDimensions(13.38, 14.76)
        .followTrajectorySequence(drive ->
                drive.trajectorySequenceBuilder(new Pose2d(-36, -64, Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(-36, -14, Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(-35.5, -7, Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(-59, -11.3, Math.toRadians(180)))

                        .build()
        );*/


        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}