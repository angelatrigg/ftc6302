package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.QuinticSpline;
import com.acmerobotics.roadrunner.path.heading.SplineInterpolator;
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
                            drive.trajectorySequenceBuilder(new Pose2d(35, -65, Math.toRadians(90)))
                                    /*//Grab cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                    })
                                    //Initial strafe to the left
                                    .strafeTo(new Vector2d(22, -63))
                                    //Continuous movement to the row with the cones and scoring pole
                                    .splineToConstantHeading(new Vector2d(12, -15), Math.toRadians(90))
                                    //.splineToConstantHeading(new Vector2d(23.5, -10), Math.toRadians(90))
                                    //Turn to the right 90 degrees
                                    .turn(Math.toRadians(-90))
                                    //Move over to score
                                    .strafeTo(new Vector2d(23.5, -6))
                                    //3 seconds before this point, start raising lift
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    //1 second after this point, stop lift
                                    .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Rotate swivel to place cone above junction and drop cone
                                    .addTemporalMarker(() -> {
                                        //encoderClass.encoderSwivel(1, 90, 5.0, this, initsetup);
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                    })
                                    //Give a second to drop cone and rotate claw.
                                    .waitSeconds(0.5)
//Start lowering lift and rotate swivel to the front for the next cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(-1);
                                        //encoderClass.encoderSwivel(1, -90, 5.0, this, initsetup);
                                    })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .setReversed(true)
                                    .strafeRight(1)
                                    //Move forward to park in one
                                    //.forward(37)

                                    .splineToConstantHeading(new Vector2d(30, -12), Math.toRadians(0))
                                    .lineTo(new Vector2d(60, -12))
                                    .setReversed(false)
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Move backwards in front of scoring junction
                                    .back(30)
                                    .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                //initsetup.motor_lift.setPower(-1);
                                //encoderClass.encoderSwivel(1, -90, 5.0, this, initsetup);
                            })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .setReversed(true)
                                    .strafeRight(1)
                                    //Move forward to park in one
                                    //.forward(37)

                                    .splineToConstantHeading(new Vector2d(30, -12), Math.toRadians(0))
                                    .lineTo(new Vector2d(60, -12))
                                    .setReversed(false)
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Move backwards in front of scoring junction
                                    .back(30)
                                    .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(-1);
                                        //encoderClass.encoderSwivel(1, -90, 5.0, this, initsetup);
                                    })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .setReversed(true)
                                    .strafeRight(1)
                                    //Move forward to park in one
                                    //.forward(37)

                                    .splineToConstantHeading(new Vector2d(30, -12), Math.toRadians(0))
                                    .lineTo(new Vector2d(60, -12))
                                    .setReversed(false)
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Move backwards in front of scoring junction
                                    .back(30)
                                    .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(-1);
                                        //encoderClass.encoderSwivel(1, -90, 5.0, this, initsetup);
                                    })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .setReversed(true)
                                    .strafeRight(1)
                                    //Move forward to park in one
                                    //.forward(37)

                                    .splineToConstantHeading(new Vector2d(30, -12), Math.toRadians(0))
                                    .lineTo(new Vector2d(60, -12))
                                    .setReversed(false)
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Move backwards in front of scoring junction
                                    .back(30)
                                    .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                                    .waitSeconds(0.5)
                                    .setReversed(true)
                                    .strafeRight(1)
                                    //Move forward to park in one
                                    //.forward(37)

                                    .splineToConstantHeading(new Vector2d(30, -12), Math.toRadians(0))
                                    .lineTo(new Vector2d(60, -12))

                                     */
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .strafeTo(new Vector2d(22, -63))
                                    .splineToConstantHeading(new Vector2d(12, -15), Math.toRadians(90))
                                    .turn(Math.toRadians(-36))
                                    .forward(7)
                                    .UNSTABLE_addTemporalMarkerOffset(-3, () -> {
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(-1);
                                        ////initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .back(7)
                                    .turn(Math.toRadians(-54))
                                    .lineTo(new Vector2d(56, -12))
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                                        //initsetup.motor_swivel.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                                        //initsetup.motor_swivel.setPower(0);
                                    })
                                    .waitSeconds(0.5)
                                    .setReversed(true)
                                    .splineToLinearHeading(new Pose2d(30, -7, Math.toRadians(-45)), Math.toRadians(135))
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                    })
                                    .waitSeconds(0.5)
                                    .setReversed(false)
                                    .build()
                    );

            meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
        }
    }