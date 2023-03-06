package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "RR Autonomous With Object Detection Left")
public class RoadrunnerAutoODLeft extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RoadrunnerSetup rrSetup = new RoadrunnerSetup(hardwareMap);
        InitSetup initsetup = new InitSetup();
        EncoderClass encoderClass = new EncoderClass();
        initsetup.autoSetupLiftSwivelOnly(hardwareMap);

        rrSetup.setPoseEstimate(new Pose2d(-35, -65, Math.toRadians(90)));

        TrajectorySequence InitialTrajectory = rrSetup.trajectorySequenceBuilder(new Pose2d(-35, -65, Math.toRadians(90)))
                //Grab cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                })
                //Initial strafe to the right
                .strafeTo(new Vector2d(-20, -58))
                //Continuous movement to the row with the cones and scoring pole
                .splineToConstantHeading(new Vector2d(-12, -15), Math.toRadians(90))
                //Turn to the right 90 degrees
                .turn(Math.toRadians(-90))
                //Move over to score
                .lineTo(new Vector2d(-23.5, -6))
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
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                })
                //Give a second to drop cone and rotate claw.
                .waitSeconds(1)
                //Strafe away from junction
                .strafeRight(6)
                //Start lowering lift and rotate swivel to the back for the next cone
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(-1);
                    //encoderClass.encoderSwivel(1, 90, 5.0, this, initsetup);
                })
                //2 seconds from this point, stop the lift movement
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Move backwards to grab a new cone
                .back(37)
                //Close claw around cone and raise lift to free it
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                    initsetup.motor_lift.setPower(1);
                })
                //Wait a second to grab cone and lift
                .waitSeconds(1)
                //2 seconds from this point, stop lift movement
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Move forward in front of scoring junction
                .forward(37)
                //Strafe left to score
                .strafeLeft(6)
                //Rotate swivel to score over junction
                .addTemporalMarker(() -> {
                    //encoderClass.encoderSwivel(1, -90, 5.0, this, initsetup);
                })
                //Wait a second to drop cone
                .waitSeconds(1)
                .build();
        TrajectorySequence RepeatingTrajectory = rrSetup.trajectorySequenceBuilder(InitialTrajectory.end())
                //Strafe away from junction
                .strafeRight(6)
                //Start lowering lift and rotate swivel to the back for the next cone
                .addTemporalMarker(() -> {
                    initsetup.motor_lift.setPower(-1);
                    //encoderClass.encoderSwivel(1, 90, 5.0, this, initsetup);
                })
                //2 seconds from this point, stop the lift movement
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Move backwards to grab a new cone
                .back(37)
                //Close claw around cone and raise lift to free it
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                    initsetup.motor_lift.setPower(1);
                })
                //Wait a second to grab cone and lift
                .waitSeconds(1)
                //2 sconds from this point, stop lift movement
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    initsetup.motor_lift.setPower(0);
                })
                //Move forward in front of scoring junction
                .forward(37)
                //Strafe left to score
                .strafeLeft(6)
                //Rotate swivel to score over junction
                .addTemporalMarker(() -> {
                    //encoderClass.encoderSwivel(1, -90, 5.0, this, initsetup);
                })
                //Wait a second to drop cone
                .waitSeconds(1)
                .build();

        Trajectory ParkOne = rrSetup.trajectoryBuilder(RepeatingTrajectory.end(), false)
                //Strafe away from junction
                .strafeRight(6)
                //Move backwards to park in one
                .back(37)
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
                //Move forward to park in three
                .forward(10)
                .build();

        AprilTagsSetup aprilTagsSetup = new AprilTagsSetup();
        aprilTagsSetup.aprilTagSetup(hardwareMap, this);

        /**
         * APRIL TAGS SETUP HAS BUILT IN WAIT FOR START
         */

        rrSetup.followTrajectorySequence(InitialTrajectory);
        rrSetup.followTrajectorySequence(RepeatingTrajectory);
        rrSetup.followTrajectorySequence(RepeatingTrajectory);

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
