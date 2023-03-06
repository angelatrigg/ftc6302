package org.firstinspires.ftc.teamcode.apriltags;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.apriltags.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class AprilTagsSetup {


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

    public void aprilTagSetup(HardwareMap hardwareMap, LinearOpMode opMode) {

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

        opMode.telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!opMode.isStarted() && !opMode.isStopRequested())
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
                    opMode.telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest, opMode);
                }
                else
                {
                    opMode.telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        opMode.telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        opMode.telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest, opMode);
                    }
                }

            }
            else
            {
                opMode.telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    opMode.telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    opMode.telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest, opMode);
                }

            }

            opMode.telemetry.update();
            opMode.sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the opMode.telemetry */
        if(tagOfInterest != null)
        {
            opMode.telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest, opMode);
            opMode.telemetry.update();
        }
        else
        {
            opMode.telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            opMode.telemetry.update();
        }

        /* Actually do something useful */
        if(tagOfInterest != null)
        {
            opMode.telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest, opMode);
            opMode.telemetry.update();
        }
        else
        {
            opMode.telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            opMode.telemetry.update();
        }

    }

    //It suppresses some warning or something don't ask me why
    @SuppressLint("DefaultLocale")

    void tagToTelemetry(AprilTagDetection detection, LinearOpMode opMode)
    {
        opMode.telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        opMode.telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        opMode.telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        opMode.telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        opMode.telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        opMode.telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        opMode.telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}
