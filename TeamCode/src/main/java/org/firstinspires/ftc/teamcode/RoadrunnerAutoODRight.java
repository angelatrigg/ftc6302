package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import java.util.Vector;

@Autonomous(name = "RR Autonomous With Object Detection Right")
public class RoadrunnerAutoODRight extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RoadrunnerSetup rrSetup = new RoadrunnerSetup(hardwareMap);
        InitSetup initsetup = new InitSetup();
        EncoderClass encoderClass = new EncoderClass();
        initsetup.deadwheelSetup(hardwareMap);

        Pose2d startPose = (new Pose2d(35, -65, Math.toRadians(90)));
        Pose2d spikePlaceEnd;
        rrSetup.setPoseEstimate(startPose);

        //Close claw
        initsetup.claw_left_servo.setPosition(InitSetup.SERVO_LEFT_CLOSED);
        initsetup.claw_right_servo.setPosition(InitSetup.SERVO_RIGHT_CLOSED);

        TrajectorySequence WaitTraj = rrSetup.trajectorySequenceBuilder(startPose)
                //Check right spike
                .waitSeconds(1)
                .build();
        TrajectorySequence ForwardTraj = rrSetup.trajectorySequenceBuilder(startPose)
                //Check right spike
                .forward(8)
                .build();
        TrajectorySequence InitialTrajectory = rrSetup.trajectorySequenceBuilder(startPose)
                //Check right spike
                .strafeTo(new Vector2d(37, -44.5))
                .build();
        TrajectorySequence SecondTrajectory = rrSetup.trajectorySequenceBuilder(InitialTrajectory.end())
                //Check left spike from right spike
                .turn(-Math.toRadians(-90))
                .back(8)
                .strafeTo(new Vector2d(43, -34))
                //.strafeTo(new Vector2d(18, -38))
                .build();
        TrajectorySequence ThirdTrajectory = rrSetup.trajectorySequenceBuilder(SecondTrajectory.end())
                //Check center spike from left spike
                .strafeTo(new Vector2d(33, -43))
                .build();

        waitForStart();

        rrSetup.followTrajectorySequence(InitialTrajectory);
        Pose2d detectionEnd = InitialTrajectory.end();

        if (((DistanceSensor) initsetup.colorSensor).getDistance(DistanceUnit.CM) > 4.6) {
            rrSetup.followTrajectorySequence(SecondTrajectory);
            detectionEnd = SecondTrajectory.end();

        }
        if (((DistanceSensor) initsetup.colorSensor).getDistance(DistanceUnit.CM) > 4.6) {
            rrSetup.followTrajectorySequence(ThirdTrajectory);
            detectionEnd = ThirdTrajectory.end();
        }

        TrajectorySequence PostDetectionTrajectoryOne = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .back(6)
                .turn(Math.toRadians(180))
                .strafeLeft(2)
                .build();
        TrajectorySequence PostDetectionTrajectoryTwo = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .back(6)
                .turn(Math.toRadians(180))
                .strafeLeft(2.5)
                .build();
        TrajectorySequence PostDetectionTrajectoryThree = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .back(6)
                .turn(Math.toRadians(180))
                .strafeRight(2.5)
                .back(4.5)
                .build();

        if (detectionEnd == InitialTrajectory.end()) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryOne);
            spikePlaceEnd = PostDetectionTrajectoryOne.end();
        }
        if (detectionEnd == SecondTrajectory.end()) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryTwo);
            spikePlaceEnd = PostDetectionTrajectoryTwo.end();
        }
        if (detectionEnd == ThirdTrajectory.end()) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryThree);
            spikePlaceEnd = PostDetectionTrajectoryThree.end();
        }

        //Drop pixel on designated spike mark
        initsetup.claw_tilt_servo.setPosition(InitSetup.SERVO_TILT_DOWN);
        rrSetup.followTrajectorySequence(WaitTraj);
        initsetup.claw_right_servo.setPosition(InitSetup.SERVO_RIGHT_OPEN);
        rrSetup.followTrajectorySequence(WaitTraj);
       //rrSetup.followTrajectorySequence(ForwardTraj);



        /*TrajectorySequence PostSpikePlaceTrajectoryOne = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .forward(4)
                .splineToLinearHeading(new Pose2d(58, -35), 0)
                .build();
        TrajectorySequence PostSpikePlaceTrajectoryTwo = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .forward(4)
                .splineToLinearHeading(new Pose2d(58, -35), 0)
                .build();
        TrajectorySequence PostSpikePlaceTrajectoryThree = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .forward(4)
                .splineToLinearHeading(new Pose2d(58, -35), 0)
                .build();
*/


        /*AprilTagsSetup aprilTagsSetup = new AprilTagsSetup();
        aprilTagsSetup.aprilTagSetup(hardwareMap, this);

        /**
         * APRIL TAGS SETUP HAS BUILT IN WAIT FOR START
         */


/*
        if(aprilTagsSetup.tagOfInterest == null){
            //Trajectory if tag three is detected
            //Park
        }
        else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.ONE){
            //Trajectory if tag one is detected
            //Park
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.TWO){
            //Trajectory if tag two is detected
            //Park
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.THREE){
            //Trajectory if tag three is detected
            //Park
        }else{
            //Trajectory if no tag is detected
            //Park
        }*/
    }
}
