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
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Autonomous With Object Detection Right")
public class OldAutoWithObjectDetectionRight extends LinearOpMode
{

    @Override
    public void runOpMode()
    {

        InitSetup initsetup = new InitSetup();
        AprilTagsSetup aprilTagsSetup = new AprilTagsSetup();
        EncoderClass encoderClass = new EncoderClass();
        initsetup.autoSetup(hardwareMap);
        aprilTagsSetup.aprilTagSetup(hardwareMap, this);

        /*
                                     |
        START OF AUTONOMOUS STEPS    |
                                     V
         */

        //Initial lift to be able to grab cone
        encoderClass.encoderDrive(1, 0, 0, 0, 0, 50, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //Drive forward to move signal
        encoderClass.encoderDrive(0.55, 1500, 1500, 1500, 1500, 300, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //Back up to free signal
        encoderClass.encoderDrive(0.5, -250, -250, -250, -250, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //Strafe to the left and lift for initial score
        encoderClass.encoderDrive(0.5, -350, 350, 350, -350, 800, 0, InitSetup.SERVO_CLOSED_AUTO, 3.5, this, initsetup);
        //Forward for initial score
        encoderClass.encoderDrive(0.4, 180, 180, 180, 180, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0, this, initsetup);
        //Reset swivel position
        initsetup.motor_swivel.setPower(-1);
        sleep(100);
        initsetup.motor_swivel.setPower(0);
        //Slightly lower lift for more accurate scoring
        encoderClass.encoderDrive(0.6, 0, 0, 0, 0, -100, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0, this, initsetup);
        //Drop cone
        initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
        //Back up after initial score
        encoderClass.encoderDrive(0.4, -180, -180, -180, -180, 0, 0, InitSetup.SERVO_OPEN_AUTO, 2.0, this, initsetup);
        //Turn towards cones and drop lift part of the way (2)
        encoderClass.encoderDrive(0.5, 460, -460, 460, -460, -440, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //Reset swivel position
        initsetup.motor_swivel.setPower(-1);
        sleep(100);
        initsetup.motor_swivel.setPower(0);
        //Drive to cones and finish lowering arm (2)
        encoderClass.encoderDrive(0.6, 1000, 1000, 1000, 1000, -300, 0, InitSetup.SERVO_OPEN_AUTO, 5.0, this, initsetup);
        //Close claw (2)
        initsetup.claw_servo.setPosition(InitSetup.SERVO_CLOSED_AUTO);
        sleep(100);
        //Lift cone (2)
        encoderClass.encoderDrive(1, 0, 0, 0, 0, 300, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0, this, initsetup);
        //Back up to score (2)
        encoderClass.encoderDrive(0.5, -980, -980, -980, -980, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //Turn towards junction and raise lift (2)
        encoderClass.encoderDrive(0.5, -470, 470, -470, 470, 950, 0, InitSetup.SERVO_CLOSED_AUTO, 2.5, this, initsetup);
        //Forward to score (2)
        encoderClass.encoderDrive(0.4, 140, 140, 140, 140, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 2.5, this, initsetup);
        initsetup.motor_swivel.setPower(-1);
        sleep(100);
        initsetup.motor_swivel.setPower(0);
        encoderClass.encoderDrive(0.6, 0, 0, 0, 0, -100, 0, InitSetup.SERVO_CLOSED_AUTO, 2.0, this, initsetup);
        //Drop cone (2)
        initsetup.claw_servo.setPosition(InitSetup.SERVO_OPEN_AUTO);
        //Back up from score (2)
        encoderClass.encoderDrive(0.4, -180, -180, -180, -180, 0, 0, InitSetup.SERVO_OPEN_AUTO, 5.0, this, initsetup);

        /* Actually do something useful */
        if(aprilTagsSetup.tagOfInterest == null){
            //default trajectory here if preferred
            //Park
            encoderClass.encoderDrive(0.5, 360, -360, -360, 360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.ONE){
            //Trajectory if tag one is detected
            //Park
            encoderClass.encoderDrive(0.5, -360, 360, 360, -360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.TWO){
            //Trajectory if tag two is detected
            //Park
            encoderClass.encoderDrive(0.5, 360, -360, -360, 360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }else{
            //Trajectory if tag three is detected
            //Park
            encoderClass.encoderDrive(0.5, 1060, -1060, -1060, 1060, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }
    }
}