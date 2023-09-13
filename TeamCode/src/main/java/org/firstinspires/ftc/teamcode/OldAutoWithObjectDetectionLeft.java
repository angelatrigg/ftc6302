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

@Autonomous(name = "Autonomous With Object Detection Left (Old)")
public class OldAutoWithObjectDetectionLeft extends LinearOpMode
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


        /* Actually do something useful */
        if(aprilTagsSetup.tagOfInterest == null){
            //default trajectory here if preferred
            //Park
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.ONE){
            //Trajectory if tag one is detected
            //Park
        }else if(aprilTagsSetup.tagOfInterest.id == aprilTagsSetup.TWO){
            //Trajectory if tag two is detected
            //Park
        }else{
            //Trajectory if tag three is detected
            //Park
        }
    }
}