package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
@Autonomous(name = "RR Autonomous With Object Detection Left")
public class RoadrunnerAutoODLeft extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RoadrunnerSetup rrSetup = new RoadrunnerSetup(hardwareMap);
        InitSetup initsetup = new InitSetup();
        EncoderClass encoderClass = new EncoderClass();
        initsetup.deadwheelSetup(hardwareMap);

        Pose2d startPose = (new Pose2d(-35, -65, Math.toRadians(90)));
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
                .strafeTo(new Vector2d(-53, -43.8))
                .build();
        TrajectorySequence SecondTrajectory = rrSetup.trajectorySequenceBuilder(InitialTrajectory.end())
                //Check left spike from right spike
                .strafeRight(4)
                .turn(-Math.toRadians(90))
                .strafeTo(new Vector2d(-53, -20))
                .strafeTo(new Vector2d(-44, -20))
                //.strafeTo(new Vector2d(18, -38))
                .build();
        TrajectorySequence ThirdTrajectory = rrSetup.trajectorySequenceBuilder(SecondTrajectory.end())
                //Check center spike from left spike
                .strafeTo(new Vector2d(-32, -28.5))
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
                .back(8)
                .turn(Math.toRadians(180))
                .strafeRight(1)
                .build();
        TrajectorySequence PostDetectionTrajectoryTwo = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .back(8)
                .turn(Math.toRadians(180))
                .strafeRight(1.5)
                .build();
        TrajectorySequence PostDetectionTrajectoryThree = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .back(8)
                .turn(Math.toRadians(180))
                .strafeLeft(5.5)
                .back(4.5)
                .build();

        if (detectionEnd == InitialTrajectory.end()) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryOne);
        }
        if (detectionEnd == SecondTrajectory.end()) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryTwo);
        }
        if (detectionEnd == ThirdTrajectory.end()) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryThree);
        }

        //Drop pixel on designated spike mark
        initsetup.claw_tilt_servo.setPosition(InitSetup.SERVO_TILT_DOWN);
        rrSetup.followTrajectorySequence(WaitTraj);
        initsetup.claw_right_servo.setPosition(InitSetup.SERVO_RIGHT_OPEN);
        rrSetup.followTrajectorySequence(WaitTraj);
        initsetup.claw_tilt_servo.setPosition(InitSetup.SERVO_TILT_UP);
        initsetup.claw_arm_servo.setPosition(InitSetup.SERVO_ARM_UP);

        TrajectorySequence BackDropOne = rrSetup.trajectorySequenceBuilder(WaitTraj.end())
                .forward(5)
                .turn(-Math.toRadians(90))
                .strafeTo(new Vector2d(-53, -43))
                .build();
        TrajectorySequence BackDropTwo = rrSetup.trajectorySequenceBuilder(WaitTraj.end())
                .forward(5)
                .strafeTo(new Vector2d(-53, -36))
                .build();
        TrajectorySequence BackDropThree = rrSetup.trajectorySequenceBuilder(WaitTraj.end())
                .forward(5)
                .strafeTo(new Vector2d(-53, -28))
                .build();

        if (detectionEnd == InitialTrajectory.end()) {
            rrSetup.followTrajectorySequence(BackDropOne);
        }
        if (detectionEnd == SecondTrajectory.end()) {
            rrSetup.followTrajectorySequence(BackDropTwo);
        }
        if (detectionEnd == ThirdTrajectory.end()) {
            rrSetup.followTrajectorySequence(BackDropThree);
        }
        initsetup.claw_left_servo.setPosition(InitSetup.SERVO_LEFT_OPEN);
        rrSetup.followTrajectorySequence(WaitTraj);
        while(initsetup.claw_arm_servo.getPosition() > InitSetup.SERVO_ARM_DOWN) {
            initsetup.claw_arm_servo.setPosition((initsetup.claw_arm_servo.getPosition() - 0.005));
        }

    }
}
