/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.apriltags.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name = "Autonomous With Object Detection Right")
public class AutoWithObjectDetectionRight extends LinearOpMode
{
    private DcMotor motor_drive_lf;
    private DcMotor motor_drive_rf;
    private DcMotor motor_drive_lr;
    private DcMotor motor_drive_rr;
    private DcMotor motor_lift;
    private DcMotor motor_swivel;
    private Servo claw_servo;
    static final double SERVO_CLOSED = 1.55;
    static final double SERVO_OPEN = 0.9;

    private ElapsedTime runtime = new ElapsedTime();

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    //int ID_TAG_OF_INTEREST = 18; // Tag ID 18 from the 36h11 family
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;

    AprilTagDetection tagOfInterest = null;

    //REMEMBER TO SET NEW VALUES FOR LIFT AND SWIVEL MOTORS

    //In theory, swivel uses degrees instead of millimeters
    static final double     COUNTS_PER_MOTOR_REV_DRIVE    = 537.7 ;    // Drive motors
    static final double     COUNTS_PER_MOTOR_REV_LIFT    = 537.7 ; //Unnecessary but just in case different motors are used for wheels and the lift / arm
    static final double     COUNTS_PER_MOTOR_REV_SWIVEL    = 1120 ; //Just for another motor type with different ticks per revolution
    // For an example of gear reduction, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // 1 = No External Gearing.
    static final double     LIFT_GEAR_REDUCTION    = 1.0 ;
    static final double     SWIVEL_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_MM_DRIVE   = 95.0 ;     // For figuring circumference
    static final double     WHEEL_DIAMETER_MM_LIFT   = 41.0 ;
    static final double     COUNTS_PER_MM_DRIVE        = (COUNTS_PER_MOTOR_REV_DRIVE * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_MM_DRIVE * 3.1415); //Drive motors
    static final double     COUNTS_PER_MM_LIFT         = (COUNTS_PER_MOTOR_REV_LIFT * LIFT_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_MM_LIFT * 3.1415); //Uses separate gear reduction and wheel diameter for lift / arm
    static final double     COUNTS_PER_DG_SWIVEL       = ((COUNTS_PER_MOTOR_REV_SWIVEL * SWIVEL_GEAR_REDUCTION) /
            360) / (COUNTS_PER_MOTOR_REV_SWIVEL * SWIVEL_GEAR_REDUCTION); //Converts counts per rev into degrees
    static final double     DRIVE_SPEED             = 0.3;
    static final double     UP_LIFT_SPEED           = 0.5;
    static final double     DOWN_LIFT_SPEED         = 0.3;
    static final double     SWIVEL_SPEED            = 0.2;
    static final double     STRAFE_SPEED            = 0.3;
    static final double     TURN_SPEED              = 0.5;
    static final double     SERVO_SPEED             = 0.3;


    @Override
    public void runOpMode()
    {
        motor_drive_lf = hardwareMap.get(DcMotor.class, "motor_drive_lf");
        motor_drive_rf = hardwareMap.get(DcMotor.class, "motor_drive_rf");
        motor_drive_lr = hardwareMap.get(DcMotor.class, "motor_drive_lr");
        motor_drive_rr = hardwareMap.get(DcMotor.class, "motor_drive_rr");
        motor_lift = hardwareMap.get(DcMotor.class, "motor_lift");
        motor_swivel = hardwareMap.get(DcMotor.class, "motor_swivel");
        claw_servo = hardwareMap.get(Servo.class, "claw_servo");

        // Reverse one of the drive motors.
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        //motor_drive_rf.setDirection(DcMotorSimple.Direction.REVERSE);
        //motor_drive_rr.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_drive_lr.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_drive_lf.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_lift.setDirection(DcMotorSimple.Direction.REVERSE);

        motor_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_swivel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor_drive_lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_drive_rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_drive_lr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_drive_rr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_swivel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor_drive_lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_drive_rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_drive_lr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_drive_rr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280,720, OpenCvCameraRotation.SIDEWAYS_RIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == ONE || tag.id == TWO || tag.id == THREE)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        /* Actually do something useful */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        /* Actually do something useful */
        if(tagOfInterest == null){
            //default trajectory here if preferred
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 50, 0, SERVO_OPEN, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 300, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 670, 670, 670, 670, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(STRAFE_SPEED, -380, 380, 380, -380, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 430, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 170, 170, 170, 170, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_OPEN, 1.5);
            encoderDrive(DRIVE_SPEED, -170, -170, -170, -170, 0, 0, SERVO_OPEN, 5.0);
            encoderDrive(STRAFE_SPEED, 320, -320, -320, 320, 0, 0, SERVO_OPEN, 5.0);

        }else if(tagOfInterest.id == ONE){
            //Trajectory if tag one is detected
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 50, 0, SERVO_OPEN, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 300, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 670, 670, 670, 670, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(STRAFE_SPEED, -380, 380, 380, -380, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 430, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 170, 170, 170, 170, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_OPEN, 1.5);
            encoderDrive(DRIVE_SPEED, -170, -170, -170, -170, 0, 0, SERVO_OPEN, 5.0);
            encoderDrive(STRAFE_SPEED, -320, 320, 320, -320, 0, 0, SERVO_OPEN, 5.0);
        }else if(tagOfInterest.id == TWO){
            //Trajectory if tag two is detected
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 50, 0, SERVO_OPEN, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 300, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 670, 670, 670, 670, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(STRAFE_SPEED, -380, 380, 380, -380, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 430, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 170, 170, 170, 170, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_OPEN, 1.5);
            encoderDrive(DRIVE_SPEED, -170, -170, -170, -170, 0, 0, SERVO_OPEN, 5.0);
            encoderDrive(STRAFE_SPEED, 320, -320, -320, 320, 0, 0, SERVO_OPEN, 5.0);
        }else{
            //Trajectory if tag three is detected
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 50, 0, SERVO_OPEN, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 300, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 670, 670, 670, 670, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(STRAFE_SPEED, -380, 380, 380, -380, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 430, 0, SERVO_CLOSED, 5.0);
            encoderDrive(DRIVE_SPEED, 170, 170, 170, 170, 0, 0, SERVO_CLOSED, 5.0);
            encoderDrive(0, 0, 0, 0, 0, 0, 0, SERVO_OPEN, 1.5);
            encoderDrive(DRIVE_SPEED, -170, -170, -170, -170, 0, 0, SERVO_OPEN, 5.0);
            encoderDrive(STRAFE_SPEED, 950, -950, -950, 950, 0, 0, SERVO_OPEN, 5.0);
        }
        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
        //while (opModeIsActive()) {sleep(20);}
    }

    public void encoderDrive(double speed,
                             double lfMM, double rfMM, double lrMM, double rrMM, double lftMM, double swvDG, double cwPos,
                             double timeoutS) {
        int newlfTarget;
        int newrfTarget;
        int newlrTarget;
        int newrrTarget;
        int newlftTarget;
        int newswvTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newlfTarget = motor_drive_lf.getCurrentPosition() + (int)(lfMM * COUNTS_PER_MM_DRIVE);
            newrfTarget = motor_drive_rf.getCurrentPosition() + (int)(rfMM * COUNTS_PER_MM_DRIVE);
            newlrTarget = motor_drive_lr.getCurrentPosition() + (int)(lrMM * COUNTS_PER_MM_DRIVE);
            newrrTarget = motor_drive_rr.getCurrentPosition() + (int)(rrMM * COUNTS_PER_MM_DRIVE);
            newlftTarget = motor_lift.getCurrentPosition() + (int)(lftMM * COUNTS_PER_MM_LIFT);
            newswvTarget = motor_swivel.getCurrentPosition() + (int)(swvDG * COUNTS_PER_DG_SWIVEL);

            motor_drive_lf.setTargetPosition(newlfTarget);
            motor_drive_rf.setTargetPosition(newrfTarget);
            motor_drive_lr.setTargetPosition(newlrTarget);
            motor_drive_rr.setTargetPosition(newrrTarget);
            motor_lift.setTargetPosition(newlftTarget);
            motor_swivel.setTargetPosition(newswvTarget);

            // Turn On RUN_TO_POSITION
            motor_drive_lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_drive_rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_drive_lr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_drive_rr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_swivel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motor_drive_lf.setPower(Math.abs(speed));
            motor_drive_rf.setPower(Math.abs(speed));
            motor_drive_lr.setPower(Math.abs(speed));
            motor_drive_rr.setPower(Math.abs(speed));
            motor_lift.setPower(Math.abs(speed));
            motor_swivel.setPower(Math.abs(speed));
            claw_servo.setPosition(cwPos);

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motor_drive_lf.isBusy() || motor_drive_rf.isBusy() || motor_drive_lr.isBusy() || motor_drive_rr.isBusy() || motor_lift.isBusy() || motor_swivel.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newlfTarget,  newrfTarget, newlrTarget, newrrTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        motor_drive_lf.getCurrentPosition(), motor_drive_rf.getCurrentPosition(), motor_drive_lr.getCurrentPosition(), motor_drive_rr.getCurrentPosition(),
                        motor_lift.getCurrentPosition(), motor_swivel.getCurrentPosition(), telemetry.update());
            }

            // Stop all motion;
            motor_drive_lf.setPower(0);
            motor_drive_rf.setPower(0);
            motor_drive_lr.setPower(0);
            motor_drive_rr.setPower(0);
            motor_lift.setPower(0);
            motor_swivel.setPower(0);

            // Turn off RUN_TO_POSITION
            motor_drive_lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_drive_rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_drive_lr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_drive_rr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}