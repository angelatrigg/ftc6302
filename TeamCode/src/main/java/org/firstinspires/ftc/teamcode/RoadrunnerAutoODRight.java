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

        TrajectorySequence InitialTrajectory = rrSetup.trajectorySequenceBuilder(new Pose2d(35, -65, Math.toRadians(90)))
               /* //Grab cone
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
                    initsetup.motor_lift.setPower(1);
                })
                //1 second after this point, stop lift
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Rotate swivel to place cone above junction and drop cone
                .addTemporalMarker(() -> {
                    //encoderClass.encoderSwivel(1, 90, 5.0, this, initsetup);
                    //initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                })
                //Give a second to drop cone and rotate claw.
                .waitSeconds(0.5)

                */
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                    initsetup.motor_lift.setPower(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                .strafeTo(new Vector2d(22, -63))
                .splineToConstantHeading(new Vector2d(12, -15), Math.toRadians(90))
                .turn(Math.toRadians(-36))
                .forward(7)
                .UNSTABLE_addTemporalMarkerOffset(-2.7, () -> {
                    initsetup.motor_lift.setPower(1);
                })
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(-1);
                    //initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                })
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    initsetup.motor_lift.setPower(0);
                    //encoderClass.encoderLift(.5, 210, 5.0, this, initsetup);
                })
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                    //initsetup.motor_lift.setPower(0);
                })
                .waitSeconds(0.5)
                .back(7)
                .turn(Math.toRadians(-54))
                .lineTo(new Vector2d(50, -12))
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                    initsetup.motor_lift.setPower(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                    initsetup.motor_swivel.setPower(0.75);
                })
                .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                    initsetup.motor_swivel.setPower(0);
                })
                .waitSeconds(0.5)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(27, -4, Math.toRadians(-45)), Math.toRadians(135))
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                })
                .waitSeconds(0.5)
                .setReversed(false)
                .build();
        TrajectorySequence RepeatingTrajectory = rrSetup.trajectorySequenceBuilder(InitialTrajectory.end())
                //Start lowering lift and rotate swivel to the front for the next cone
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(-1);
                    //encoderClass.encoderSwivel(1, -90, 5.0, this, initsetup);
                })
                //2 seconds from this point, stop the lift movement
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    initsetup.motor_lift.setPower(0);
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
                    initsetup.motor_lift.setPower(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(3, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Move backwards in front of scoring junction
                .back(30)
                .splineToConstantHeading(new Vector2d(23.5, -6), Math.toRadians(90))
                .waitSeconds(0.5)
                .build();

        /*Trajectory ParkOne = rrSetup.trajectoryBuilder(RepeatingTrajectory.end(), false)
                //Strafe away from junction
                .strafeRight(6)
                //Move forward to park in one
                .forward(10)
                .build();

        Trajectory ParkTwo = rrSetup.trajectoryBuilder(RepeatingTrajectory.end(), false)
                //Strafe away from junction
                .strafeRight(6)
                //Move backwards to park in two
                .back(10)
                .build();

        Trajectory ParkThree = rrSetup.trajectoryBuilder(RepeatingTrajectory.end(), false)
                //Strafe away from junction
                .strafeRight(6)
                //Move backwards to park in three
                .back(37)
                .build();

         */
        initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
        AprilTagsSetup aprilTagsSetup = new AprilTagsSetup();
        aprilTagsSetup.aprilTagSetup(hardwareMap, this);

        /**
         * APRIL TAGS SETUP HAS BUILT IN WAIT FOR START
         */


        rrSetup.followTrajectorySequence(InitialTrajectory);
        //rrSetup.followTrajectorySequence(RepeatingTrajectory);
        //rrSetup.followTrajectorySequence(RepeatingTrajectory);
        //rrSetup.followTrajectorySequence(RepeatingTrajectory);
        //rrSetup.followTrajectorySequence(RepeatingTrajectory);
/*
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
*/
    }
}
