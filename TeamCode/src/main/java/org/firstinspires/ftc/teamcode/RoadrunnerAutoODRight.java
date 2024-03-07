package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Autonomous Right Side Without Backdrop")
public class RoadrunnerAutoODRight extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RoadrunnerSetup rrSetup = new RoadrunnerSetup(hardwareMap);
        InitSetup initsetup = new InitSetup();
        EncoderClass encoderClass = new EncoderClass();
        initsetup.deadwheelSetup(hardwareMap);

        Pose2d startPose = (new Pose2d(35, -65, Math.toRadians(90)));
        Pose2d spikePlaceEnd;
        boolean detectionFallback = false;
        Pose2d finalPos;
        rrSetup.setPoseEstimate(startPose);

        //Close claw
        initsetup.claw_left_servo.setPosition(InitSetup.SERVO_LEFT_CLOSED);
        initsetup.claw_right_servo.setPosition(InitSetup.SERVO_RIGHT_CLOSED);

        TrajectorySequence InitialTrajectory = rrSetup.trajectorySequenceBuilder(startPose)
                //Check right spike
                .strafeTo(new Vector2d(37.5, -45.5))
                .build();
        TrajectorySequence SecondTrajectory = rrSetup.trajectorySequenceBuilder(InitialTrajectory.end())
                //Check left spike from right spike
                /*.turn(-Math.toRadians(-90))
                .back(8)
                .strafeTo(new Vector2d(43, -34))
                 */
                .lineToSplineHeading(new Pose2d(44, -34, Math.toRadians(180)))
                //.strafeTo(new Vector2d(18, -38))
                .build();
     /*   TrajectorySequence ThirdTrajectory = rrSetup.trajectorySequenceBuilder(SecondTrajectory.end())
                //Check center spike from left spike
                .strafeTo(new Vector2d(33, -43))
                .build();

      */

        waitForStart();

        rrSetup.followTrajectorySequence(InitialTrajectory);
        Pose2d detectionEnd = InitialTrajectory.end();

        if ((initsetup.sensorDistance.getDistance(DistanceUnit.CM) >= 10)) {
            rrSetup.followTrajectorySequence(SecondTrajectory);
            detectionEnd = SecondTrajectory.end();

        }
        if ((initsetup.sensorDistance.getDistance(DistanceUnit.CM) >= 10)) {
            //rrSetup.followTrajectorySequence(ThirdTrajectory);
            detectionFallback = true;
        }

        TrajectorySequence PostDetectionTrajectoryOne = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .back(7)
                .turn(Math.toRadians(180))
                .strafeLeft(2)
                .build();
        TrajectorySequence PostDetectionTrajectoryTwo = rrSetup.trajectorySequenceBuilder(detectionEnd)
                .back(6)
                .turn(Math.toRadians(180))
                //.forward(1)
                //.strafeLeft(2)
                .build();
        TrajectorySequence PostDetectionTrajectoryThree = rrSetup.trajectorySequenceBuilder(detectionEnd)
                /*.back(6)
                .turn(Math.toRadians(180))
                .strafeRight(2.5)
                .back(4.5)
                 */
                .strafeTo(new Vector2d(44, -43))
                .turn(Math.toRadians(180))
                .strafeTo(new Vector2d(35.5, -45))

                .build();

        if (detectionEnd == InitialTrajectory.end() && !detectionFallback) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryOne);
            spikePlaceEnd = PostDetectionTrajectoryOne.end();
        }
        if (detectionEnd == SecondTrajectory.end() && !detectionFallback) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryTwo);
            spikePlaceEnd = PostDetectionTrajectoryTwo.end();
        }
        if (detectionFallback) {
            rrSetup.followTrajectorySequence(PostDetectionTrajectoryThree);
            spikePlaceEnd = PostDetectionTrajectoryThree.end();
        }

        //Drop pixel on designated spike mark
        initsetup.claw_tilt_servo.setPosition(InitSetup.SERVO_TILT_DOWN);
        sleep(500);
        initsetup.claw_right_servo.setPosition(InitSetup.SERVO_RIGHT_OPEN);
        sleep(500);
    }
}
