package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {
        public static void main(String[] args) {
            MeepMeep meepMeep = new MeepMeep(800);

            RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(32, 32, Math.toRadians(136.6), Math.toRadians(136.6), 13.24)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))
                                    .strafeTo(new Vector2d(-20, -58))
                                    //.lineToLinearHeading(new Pose2d(-12, -10, Math.toRadians(0)))
                                    //.splineToSplineHeading(new Pose2d(-23.5, -6, Math.toRadians(0)), Math.toRadians(140))
                                    .splineToSplineHeading(new Pose2d(-12, -22, Math.toRadians(0)), Math.toRadians(90))
                                    //.splineToConstantHeading(new Vector2d(-12, -15), Math.toRadians(90))
                                    //.turn(Math.toRadians(90))
                                    .splineToConstantHeading(new Vector2d(-23.5, -6), Math.toRadians(90))

                                    //.splineToConstantHeading(new Vector2d(-68, -12), Math.toRadians(-200))
                                    //.splineToConstantHeading(new Vector2d(-68, -12), Math.toRadians(90))

                                    //.splineToConstantHeading(new Vector2d(-68, -12), Math.toRadians(-90))
                                    //.splineToSplineHeading(new Pose2d(-68, -12, Math.toRadians(0)), Math.toRadians(-200))
                                    //.lineToLinearHeading(new Pose2d(-68, -12, Math.toRadians(0)))
                                    //.strafeTo(new Vector2d(-68, -12))
                                    // .lineToLinearHeading(new Pose2d(-23.5, -6, Math.toRadians(90)))
                                    //.strafeTo(new Vector2d(-23.5, -6))
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {

                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {

                                    })
                                    .waitSeconds(1)
                                    .strafeRight(6)
                                    //.splineToConstantHeading(new Vector2d(-33.5, -12), Math.toRadians(90))
                                    .back(40)
                                    .waitSeconds(1)
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {

                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {

                                    })
                                    .forward(30)
                                    .splineToConstantHeading(new Vector2d(-23.5, -6), Math.toRadians(90))
                                    //.strafeLeft(5)
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {

                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {

                                    })
                                    .waitSeconds(1)
                                    .strafeRight(6)
                                    .back(40)
                                    .waitSeconds(1)
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {

                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {

                                    })
                                    .forward(30)
                                    //.strafeLeft(5)
                                    .splineToConstantHeading(new Vector2d(-23.5, -6), Math.toRadians(90))
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {

                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {

                                    })
                                    .waitSeconds(1)
                                    .strafeRight(6)
                                    .back(40)
                                    .waitSeconds(1)
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {

                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {

                                    })
                                    .forward(30)
                                    //.strafeLeft(5)
                                    .splineToConstantHeading(new Vector2d(-23.5, -6), Math.toRadians(90))
                                    .build()
                    );

            meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
        }
    }