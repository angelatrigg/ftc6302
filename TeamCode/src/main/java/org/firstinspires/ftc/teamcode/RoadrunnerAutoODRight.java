package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.apriltags.AprilTagsSetup;
import org.firstinspires.ftc.teamcode.functions.EncoderClass;
import org.firstinspires.ftc.teamcode.functions.InitSetup;
import org.firstinspires.ftc.teamcode.roadrunner.RoadrunnerSetup;
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
                //Lift slightly to clear arm
                .addTemporalMarker(() -> {
                    encoderClass.encoderLift(200, this, initsetup);
                    initsetup.motor_lift.setPower(1);
                })
                //Strafe to the left to start
                .strafeTo(new Vector2d(22, -63))
                //Smooth transition from strafe to curving toward the first scoreing pole
                .splineToConstantHeading(new Vector2d(12, -15), Math.toRadians(90))
                //.turn(Math.toRadians(-90))
                //Turn toward high junction
                .turn(Math.toRadians(-37))
                //Forward to score on high junction
                .forward(7.3)
                //Start lifting 2.7 seconds BEFORE this point
                .UNSTABLE_addTemporalMarkerOffset(-2.7, () -> {
                    encoderClass.encoderLift(1000, this, initsetup);
                    initsetup.motor_lift.setPower(1);
                })
                //Set lift to go down
                .addTemporalMarker(() -> {
                    encoderClass.encoderLift(150, this, initsetup);
                    initsetup.motor_lift.setPower(0.5);
                })
                //Start rotating swivel to reset its position, set the zero location for the swivel motor, and open the claw
                .addTemporalMarker(() -> {
                    initsetup.motor_swivel.setPower(-0.2);
                    //encoderClass.encoderSwivel(0, this, initsetup);
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                })
                //Pause for half a second for everything to catch up
                .waitSeconds(0.5)
                //Move backwards away from the junction
                .back(7.3)
                //Turn the rest of the way to face the stack of cones
                .turn(Math.toRadians(-53))
                //Drive in front of cones
                .lineTo(new Vector2d(56.5, -11.5))

                //In front of cones with claw open

                //Close the claw on a new cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                })
                //Wait half a second for claw to close
                .waitSeconds(1)
                //Start moving lift up
                .addTemporalMarker(() -> {
                    encoderClass.encoderLift(1000, this, initsetup);
                    initsetup.motor_lift.setPower(1);
                })
                .waitSeconds(1)
                //Start rotating swivel to the backwards position 1 second AFTER this point
                .addTemporalMarker(() -> {
                    //encoderClass.encoderSwivel(45, this, initsetup);
                    initsetup.motor_swivel.setPower(0.3);
                })
                //Wait half a second for things to catch up
                .waitSeconds(0.5)
                //Set reversed direction for a properly shaped curve
                .setReversed(true)
                //Curve back to the high junction to score
                .splineToLinearHeading(new Pose2d(25, -6, Math.toRadians(-41)), Math.toRadians(135))
                //Start moving lift down
                .addTemporalMarker(() -> {
                    encoderClass.encoderLift(100, this, initsetup);
                    initsetup.motor_lift.setPower(0.5);
                })
                //Open the claw to score the cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                })
                //Start roating swivel back to its normal position half a second AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    initsetup.motor_swivel.setPower(-0.40);
                })
                //Wait a second for claw to open
                .waitSeconds(0.5)
                //Disable reversed direction
                .setReversed(false)
                //Curve back to stack of cones
                /*.splineToLinearHeading(new Pose2d(56.5, -11.5, Math.toRadians(5)), Math.toRadians(0))

                //In front of cones with claw open

                .waitSeconds(0.5)
                //Close the claw on a new cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
                })
                //Wait half a second for claw to close
                .waitSeconds(0.5)
                //Start moving lift up
                .addTemporalMarker(() -> {
                    encoderClass.encoderLift(1000, this, initsetup);
                    initsetup.motor_lift.setPower(1);
                })
                //Start rotating swivel to the backwards position 1 second AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    //encoderClass.encoderSwivel(45, this, initsetup);
                    initsetup.motor_swivel.setPower(0.2);
                })
                //Wait half a second for things to catch up
                .waitSeconds(0.5)
                //Set reversed direction for a properly shaped curve
                .setReversed(true)
                //Curve back to the high junction to score
                .splineToLinearHeading(new Pose2d(24.5, -6, Math.toRadians(-46)), Math.toRadians(135))
                //Start moving lift down
                .addTemporalMarker(() -> {
                    encoderClass.encoderLift(150, this, initsetup);
                    initsetup.motor_lift.setPower(0.5);
                })
                //Open the claw to score the cone
                .addTemporalMarker(() -> {
                    initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
                })
                //Start roating swivel back to its normal position half a second AFTER this point
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    initsetup.motor_swivel.setPower(-0.40);
                })
                //Wait a second for claw to open
                .waitSeconds(0.5)
                //Disable reversed direction
                .setReversed(false)
                 */
                .build();

        Trajectory ParkOne = rrSetup.trajectoryBuilder(MainTrajectory.end(), false)
                //Curve to park in spot 1
                .splineToLinearHeading(new Pose2d(10, -12, Math.toRadians(180)), Math.toRadians(180))
                //.lineTo(new Vector2d(10, -12))
                .build();

        Trajectory ParkTwo = rrSetup.trajectoryBuilder(MainTrajectory.end(), false)
                //Curve to park in spot 2
                .splineToLinearHeading(new Pose2d(35, -12, Math.toRadians(0)), Math.toRadians(0))
                //.lineTo(new Vector2d(34, -12))
                .build();

        Trajectory ParkThree = rrSetup.trajectoryBuilder(MainTrajectory.end(), false)
                //Curve to park in spot 3
                .splineToLinearHeading(new Pose2d(54, -12, Math.toRadians(0)), Math.toRadians(0))
                //.lineTo(new Vector2d(54, -12))
                .build();


        initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
        AprilTagsSetup aprilTagsSetup = new AprilTagsSetup();
        aprilTagsSetup.aprilTagSetup(hardwareMap, this);

        /**
         * APRIL TAGS SETUP HAS BUILT IN WAIT FOR START
         */


        rrSetup.followTrajectorySequence(MainTrajectory);

        if(aprilTagsSetup.tagOfInterest == null){
            //Trajectory if tag three is detected
            //Park
            rrSetup.followTrajectory(ParkTwo);
        }
        else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.ONE){
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
