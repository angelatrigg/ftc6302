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

@Autonomous(name = "Autonomous With Object Detection Left")
public class AutoWithObjectDetectionLeft extends LinearOpMode
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
        //shorthand definitions: (TbTW=To be tweaked) (
        //Initial lift to be able to grab cone
        encoderClass.encoderDrive(1, 0, 0, 0, 0, 50, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //strafe right
        encoderClass.encoderDrive(0.5, 660, -660, -660, 660, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //move forward
        encoderClass.encoderDrive(0.5, 980, 980, 980, 980, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //turn left and raise arm (TbTW)
        encoderClass.encoderDrive(0.5, 230, 230, 230, 230, 1100, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //move forward
        encoderClass.encoderDrive(0.5, 560, 560, 560, 560, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        //drop cone (open servo)
        encoderClass.encoderDrive(0.5, 0, 0, 0, 0, 0, 0, InitSetup.SERVO_OPEN_AUTO, 5.0, this, initsetup);
        //move backward
        encoderClass.encoderDrive(0.5, -280, -280, -280, -280, 0, 0, InitSetup.SERVO_OPEN_AUTO, 5.0, this, initsetup);

        /* Actually do something useful */
        if(aprilTagsSetup.tagOfInterest == null){
            //default trajectory here if preferred
            //Park
            encoderClass.encoderDrive(0.5, -360, 360, 360, -360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.ONE){
            //Trajectory if tag one is detected
            //Park
            encoderClass.encoderDrive(0.5, -1060, 1060, 1060, -1060, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.TWO){
            //Trajectory if tag two is detected
            //Park
            encoderClass.encoderDrive(0.5, -360, 360, 360, -360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }else{
            //Trajectory if tag three is detected
            //Park
            encoderClass.encoderDrive(0.5, 360, -360, -360, 360, 0, 0, InitSetup.SERVO_CLOSED_AUTO, 5.0, this, initsetup);
        }
    }
}