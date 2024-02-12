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
                            drive.trajectorySequenceBuilder(new Pose2d(-35, -65, Math.toRadians(90)))
                                    .strafeTo(new Vector2d(-53, -43.8))


                                    .turn(-Math.toRadians(90))
                                    .back(4)
                                    .strafeTo(new Vector2d(-44, -20))
//-43 -36 -28

                                    .strafeTo(new Vector2d(-32, -28.5))


                                    .strafeTo(new Vector2d(0, -45))
                                    .turn(-Math.toRadians(-90))
                                    /*.back(8)
                                    .turn(Math.toRadians(180))
                                    .strafeRight(2.5)
                                    .back(4.5)\

                                     */
                                    .strafeTo(new Vector2d(38.5, -43.8))
                                    .turn(-Math.toRadians(-90))
                                    .back(8)
                                    .strafeTo(new Vector2d(44, -34))
                                    .strafeTo(new Vector2d(32, -40))
                                    /*//Grab cone
                                    .addTemporalMarker(() -> {
                                        ////initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
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
                                        ////initsetup.motor_lift.setPower(1);
                                    })
                                    //1 second after this point, stop lift
                                    .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                        ////initsetup.motor_lift.setPower(0);
                                    })
                                    //Rotate swivel to place cone above junction and drop cone
                                    .addTemporalMarker(() -> {
                                        //encoderClass.encoderSwivel(1, 90, 5.0, this, //initsetup);
                                        ////initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                    })
                                    //Give a second to drop cone and rotate claw.
                                    .waitSeconds(0.5)
//Start lowering lift and rotate swivel to the front for the next cone
                                    .addTemporalMarker(() -> {
                                        ////initsetup.motor_lift.setPower(-1);
                                        //encoderClass.encoderSwivel(1, -90, 5.0, this, //initsetup);
                                    })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        ////initsetup.motor_lift.setPower(0);
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
                                        ////initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        ////initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        ////initsetup.motor_lift.setPower(0);
                                    })
                                    //Move backwards in front of scoring junction
                                    .back(30)
                                    .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                ////initsetup.motor_lift.setPower(-1);
                                //encoderClass.encoderSwivel(1, -90, 5.0, this, //initsetup);
                            })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        ////initsetup.motor_lift.setPower(0);
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
                                        ////initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        ////initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        ////initsetup.motor_lift.setPower(0);
                                    })
                                    //Move backwards in front of scoring junction
                                    .back(30)
                                    .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        ////initsetup.motor_lift.setPower(-1);
                                        //encoderClass.encoderSwivel(1, -90, 5.0, this, //initsetup);
                                    })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        ////initsetup.motor_lift.setPower(0);
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
                                        ////initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        ////initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        ////initsetup.motor_lift.setPower(0);
                                    })
                                    //Move backwards in front of scoring junction
                                    .back(30)
                                    .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                                    .waitSeconds(0.5)
                                    .addTemporalMarker(() -> {
                                        ////initsetup.motor_lift.setPower(-1);
                                        //encoderClass.encoderSwivel(1, -90, 5.0, this, //initsetup);
                                    })
                                    //2 seconds from this point, stop the lift movement
                                    .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                        ////initsetup.motor_lift.setPower(0);
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
                                        ////initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                        ////initsetup.motor_lift.setPower(1);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        ////initsetup.motor_lift.setPower(0);
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
/*
                                    //Start lifting
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    //Stop lifing 0.5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Strafe to the left to start
                                    .strafeTo(new Vector2d(22, -63))
                                    //Smooth transition from strafe to curving toward the first scoreing pole
                                    .splineToConstantHeading(new Vector2d(12, -15), Math.toRadians(90))
                                    //Turn toward high junction
                                    .turn(Math.toRadians(-33))
                                    //Forward to score on high junction
                                    .forward(8)
                                    //Start lifting 2.7 seconds BEFORE this point
                                    .UNSTABLE_addTemporalMarkerOffset(-2.7, () -> {
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    //Set lift to go down
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(-1);
                                    })
                                    //Halve the speed the lift lowers 0.5 seconds AFTER this point and stop the swivel rotation
                                    .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                        //initsetup.motor_lift.setPower(-0.5);
                                        //initsetup.motor_swivel.setPower(0);
                                    })
                                    //Stop the lift 2.75 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(2.75, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Start rotating swivel to reset its position and open the claw
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_swivel.setPower(-0.6);
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                    })
                                    //Pause for half a second for everything to catch up
                                    .waitSeconds(0.5)
                                    //Move backwards away from the junction
                                    .back(8)
                                    //Turn the rest of the way to face the stack of cones
                                    .turn(Math.toRadians(-57))
                                    //Drive in front of cones
                                    .lineTo(new Vector2d(56, -12))

                                    //In front of cones with claw open

                                    //Close the claw on a new cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                    })
                                    //Wait half a second for claw to close
                                    .waitSeconds(0.5)
                                    //Start moving lift up
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    //Start moving lift down at half speed 3 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(-0.5);
                                    })
                                    //Stop lift 6 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(6, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Start rotating swivel to the backwards position 1.5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                                        //initsetup.motor_swivel.setPower(0.1);
                                    })
                                    //Stop swivel 3.5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                                        //initsetup.motor_swivel.setPower(0);
                                    })
                                    //Start roating swivel back to its normal position 5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                                        //initsetup.motor_swivel.setPower(-0.1);
                                    })
                                    //Wait half a second for things to catch up
                                    .waitSeconds(0.5)
                                    //Set reversed direction for a properly shaped curve
                                    .setReversed(true)
                                    //Curve back to the high junction to score
                                    .splineToLinearHeading(new Pose2d(24.5, -6, Math.toRadians(-45)), Math.toRadians(135))
                                    //Open the claw to score the cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                    })
                                    //Wait half a second for claw to open
                                    .waitSeconds(0.5)
                                    //Disable reversed direction
                                    .setReversed(false)
                                    //Curve back to stack of cones
                                    .splineToLinearHeading(new Pose2d(56, -12, Math.toRadians(0)), Math.toRadians(0))

                                    //In front of cones with claw open

                                    //Close the claw on a new cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                    })
                                    //Wait half a second for claw to close
                                    .waitSeconds(0.5)
                                    //Start moving lift up
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    //Start moving lift down at half speed 3 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(-0.5);
                                    })
                                    //Stop lift 6 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(6, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Start rotating swivel to the backwards position 1.5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                                        //initsetup.motor_swivel.setPower(0.1);
                                    })
                                    //Stop swivel 3.5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                                        //initsetup.motor_swivel.setPower(0);
                                    })
                                    //Start roating swivel back to its normal position 5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                                        //initsetup.motor_swivel.setPower(-0.1);
                                    })
                                    //Wait half a second for things to catch up
                                    .waitSeconds(0.5)
                                    .setReversed(true)
                                    //Curve back to the high junction to score
                                    .splineToLinearHeading(new Pose2d(24.5, -6, Math.toRadians(-45)), Math.toRadians(135))
                                    //Open the claw to score the cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                    })
                                    //Wait half a second for claw to open
                                    .waitSeconds(0.5)
                                    //Disable reversed direction
                                    .setReversed(false)
                                    //Curve back to stack of cones
                                    .splineToLinearHeading(new Pose2d(56, -12, Math.toRadians(0)), Math.toRadians(0))

                                    //In front of cones with claw open

                                    //Close the claw on a new cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                                    })
                                    //Wait half a second for claw to close
                                    .waitSeconds(0.5)
                                    //Start moving lift up
                                    .addTemporalMarker(() -> {
                                        //initsetup.motor_lift.setPower(1);
                                    })
                                    //Start moving lift down at half speed 3 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                                        //initsetup.motor_lift.setPower(-0.5);
                                    })
                                    //Stop lift 6 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(6, () -> {
                                        //initsetup.motor_lift.setPower(0);
                                    })
                                    //Start rotating swivel to the backwards position 1.5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                                        //initsetup.motor_swivel.setPower(0.1);
                                    })
                                    //Stop swivel 3.5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                                        //initsetup.motor_swivel.setPower(0);
                                    })
                                    //Start roating swivel back to its normal position 5 seconds AFTER this point
                                    .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                                        //initsetup.motor_swivel.setPower(-0.1);
                                    })
                                    //Wait half a second for things to catch up
                                    .waitSeconds(0.5)
                                    .setReversed(true)
                                    //Curve back to the high junction to score
                                    .splineToLinearHeading(new Pose2d(24.5, -6, Math.toRadians(-45)), Math.toRadians(135))
                                    //Open the claw to score the cone
                                    .addTemporalMarker(() -> {
                                        //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                                    })
                                    //Wait half a second for claw to open
                                    .waitSeconds(0.5)
                                    //Disable reversed direction
                                    .setReversed(false)
                                    //Curve back to stack of cones
                                    .splineToLinearHeading(new Pose2d(37, -12, Math.toRadians(0)), Math.toRadians(0))

 */
                                    .build()


                    );

            meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
        }
    }