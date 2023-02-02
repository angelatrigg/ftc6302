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

@Autonomous(name = "Autonomous With Object Detection Left")
public class AutoWithObjectDetectionLeft extends LinearOpMode
{

    private InitSetup initsetup;
    private EncoderClass encoderClass;
    private AprilTagsSetup aprilTagsSetup;

    @Override
    public void runOpMode()
    {

        initsetup = new InitSetup();
        aprilTagsSetup = new AprilTagsSetup();
        encoderClass = new EncoderClass();
        initsetup.autoSetup(hardwareMap, this);
        aprilTagsSetup.aprilTagSetup(hardwareMap, this);

        /**
         * Auto Steps Start
         */

        //Initial lift to be able to grab cone
        encoderClass.encoderDrive(this, 1, 0, 0, 0, 0, 50, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        //Drive forward to move signal
        encoderClass.encoderDrive(this, 0.55, 1500, 1500, 1500, 1500, 300, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        sleep(250);
        //Back up to free signal
        encoderClass.encoderDrive(this, 0.5, -250, -250, -250, -250, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        //Strafe to the right and lift for initial score
        encoderClass.encoderDrive(this, 0.5, 350, -350, -350, 350, 800, 0, InitSetup.SERVO_CLOSED_AUTO, 3.5);
        //Forward for initial score
        encoderClass.encoderDrive(this, 0.4, 180, 180, 180, 180, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0);
        //Reset swivel position
        initsetup.motor_swivel.setPower(-1);
        sleep(100);
        initsetup.motor_swivel.setPower(0);
        //Slightly lower lift for more accurate scoring
        encoderClass.encoderDrive(this, 0.6, 0, 0, 0, 0, -100, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0);
        //Drop cone
        initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
        //Back up after initial score
        encoderClass.encoderDrive(this, 0.4, -180, -180, -180, -180, 0, 0, InitSetup.SERVO_OPEN_AUTO, 2.0);
        //Turn towards cones and drop lift part of the way (2)
        encoderClass.encoderDrive(this, 0.5, -460, 460, -460, 460, -440, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        //Reset swivel position
        initsetup.motor_swivel.setPower(-1);
        sleep(100);
        initsetup.motor_swivel.setPower(0);
        //Drive to cones and finish lowering arm (2)
        encoderClass.encoderDrive(this, 0.6, 1000, 1000, 1000, 1000, -300, 0, InitSetup.SERVO_OPEN_AUTO, 5.0);
        //Close claw (2)
        initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
        sleep(100);
        //Lift cone (2)
        encoderClass.encoderDrive(this, 1, 0, 0, 0, 0, 300, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0);
        //Back up to score (2)
        encoderClass.encoderDrive(this, 0.5, -980, -980, -980, -980, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        //Turn towards junction and raise lift (2)
        encoderClass.encoderDrive(this, 0.5, 480, -480, 480, -480, 950, 0, InitSetup.SERVO_CLOSED_AUTO, 2.5);
        //Forward to score (2)
        encoderClass.encoderDrive(this, 0.4, 140, 140, 140, 140, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 2.5);
        initsetup.motor_swivel.setPower(-1);
        sleep(100);
        initsetup.motor_swivel.setPower(0);
        encoderClass.encoderDrive(this, 0.6, 0, 0, 0, 0, -100, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0);
        //Drop cone (2)
        initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
        //Back up from score (2)
        encoderClass.encoderDrive(this, 0.4, -180, -180, -180, -180, 0, 0, InitSetup.SERVO_OPEN_AUTO, 5.0);


        /* Actually do something useful */
        if(aprilTagsSetup.tagOfInterest == null){
            //default trajectory here if preferred
            //Park
            encoderClass.encoderDrive(this, 0.5, -360, 360, 360, -360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.ONE){
            //Trajectory if tag one is detected
            //Park
            encoderClass.encoderDrive(this, 0.5, -1060, 1060, 1060, -1060, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.TWO){
            //Trajectory if tag two is detected
            //Park
            encoderClass.encoderDrive(this, 0.5, -360, 360, 360, -360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        }else{
            //Trajectory if tag three is detected
            //Park
            encoderClass.encoderDrive(this, 0.5, 360, -360, -360, 360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0);
        }
    }
}