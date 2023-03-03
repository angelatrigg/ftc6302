package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "RR Autonomous With Object Detection Right")
public class RoadrunnerAutoODRight extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RoadrunnerSetup rrSetup = new RoadrunnerSetup(hardwareMap);
        InitSetup initsetup = new InitSetup();
        EncoderClass encoderClass = new EncoderClass();
        initsetup.autoSetupLiftSwivelOnly(hardwareMap);

        rrSetup.setPoseEstimate(new Pose2d(35, -65, Math.toRadians(90)));

        TrajectorySequence MainTrajectory = rrSetup.trajectorySequenceBuilder(new Pose2d(35, -65, Math.toRadians(90)))
                //Start lifting
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(1);
                })
                //Stop lifing 0.5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    initsetup.motor_lift.setPower(0);
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
                    initsetup.motor_lift.setPower(1);
                })
                //Set lift to go down
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(-1);
                })
                //Halve the speed the lift lowers 0.5 seconds AFTER this point and stop the swivel rotation
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    initsetup.motor_lift.setPower(-0.5);
                   initsetup.motor_swivel.setPower(0);
                })
                //Stop the lift 2.75 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(2.75, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Start rotating swivel to reset its position and open the claw
                .addTemporalMarker(() -> {
                    initsetup.motor_swivel.setPower(-0.6);
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
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
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                })
                //Wait half a second for claw to close
                .waitSeconds(0.5)
                //Start moving lift up
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(1);
                })
                //Start moving lift down at half speed 3 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                    initsetup.motor_lift.setPower(-0.5);
                })
                //Stop lift 6 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(6, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Start rotating swivel to the backwards position 1.5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                    initsetup.motor_swivel.setPower(0.1);
                })
                //Stop swivel 3.5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                    initsetup.motor_swivel.setPower(0);
                })
                //Start roating swivel back to its normal position 5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                    initsetup.motor_swivel.setPower(-0.1);
                })
                //Wait half a second for things to catch up
                .waitSeconds(0.5)
                //Set reversed direction for a properly shaped curve
                .setReversed(true)
                //Curve back to the high junction to score
                .splineToLinearHeading(new Pose2d(24.5, -6, Math.toRadians(-45)), Math.toRadians(135))
                //Open the claw to score the cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
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
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                })
                //Wait half a second for claw to close
                .waitSeconds(0.5)
                //Start moving lift up
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(1);
                })
                //Start moving lift down at half speed 3 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                    initsetup.motor_lift.setPower(-0.5);
                })
                //Stop lift 6 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(6, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Start rotating swivel to the backwards position 1.5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                    initsetup.motor_swivel.setPower(0.1);
                })
                //Stop swivel 3.5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                    initsetup.motor_swivel.setPower(0);
                })
                //Start roating swivel back to its normal position 5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                    initsetup.motor_swivel.setPower(-0.1);
                })
                //Wait half a second for things to catch up
                .waitSeconds(0.5)
                .setReversed(true)
                //Curve back to the high junction to score
                .splineToLinearHeading(new Pose2d(24.5, -6, Math.toRadians(-45)), Math.toRadians(135))
                //Open the claw to score the cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
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
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                })
                //Wait half a second for claw to close
                .waitSeconds(0.5)
                //Start moving lift up
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(1);
                })
                //Start moving lift down at half speed 3 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                    initsetup.motor_lift.setPower(-0.5);
                })
                //Stop lift 6 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(6, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Start rotating swivel to the backwards position 1.5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                    initsetup.motor_swivel.setPower(0.1);
                })
                //Stop swivel 3.5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                    initsetup.motor_swivel.setPower(0);
                })
                //Start roating swivel back to its normal position 5 seconds AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                    initsetup.motor_swivel.setPower(-0.1);
                })
                //Wait half a second for things to catch up
                .waitSeconds(0.5)
                .setReversed(true)
                //Curve back to the high junction to score
                .splineToLinearHeading(new Pose2d(24.5, -6, Math.toRadians(-45)), Math.toRadians(135))
                //Open the claw to score the cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                })
                //Wait half a second for claw to open
                .waitSeconds(0.5)
                //Disable reversed direction
                .setReversed(false)
                .build();

        Trajectory ParkOne = rrSetup.trajectoryBuilder(MainTrajectory.end(), false)
                //Curve to park in spot 1
                .splineToLinearHeading(new Pose2d(12, -12, Math.toRadians(180)), Math.toRadians(180))
                .build();

        Trajectory ParkTwo = rrSetup.trajectoryBuilder(MainTrajectory.end(), false)
                //Curve to park in spot 2
                .splineToLinearHeading(new Pose2d(37, -12, Math.toRadians(0)), Math.toRadians(0))
                .build();

        Trajectory ParkThree = rrSetup.trajectoryBuilder(MainTrajectory.end(), false)
                //Curve to park in spot 3
                .splineToLinearHeading(new Pose2d(56, -12, Math.toRadians(0)), Math.toRadians(0))
                .build();


        initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
        AprilTagsSetup aprilTagsSetup = new AprilTagsSetup();
        aprilTagsSetup.aprilTagSetup(hardwareMap, this);

        /**
         * APRIL TAGS SETUP HAS BUILT IN WAIT FOR START
         */


        rrSetup.followTrajectorySequence(MainTrajectory);

        if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.ONE){
            //Trajectory if tag one is detected
            //Park
            rrSetup.followTrajectory(ParkOne);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.TWO){
            //Trajectory if tag two is detected
            //Park
            rrSetup.followTrajectory(ParkTwo);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.THREE){
            //Trajectory if tag three is detected
            //Park
            rrSetup.followTrajectory(ParkThree);
        }else{
            //Trajectory if no tag is detected
            //Park
            rrSetup.followTrajectory(ParkTwo);
        }
    }
}
