package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Autonomous")
public class Auto extends LinearOpMode {

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        InitSetup initsetup = new InitSetup();
        EncoderClass encoderClass = new EncoderClass();
       initsetup.autoSetup(hardwareMap);


        waitForStart();

        /**
         * NOTES FOR LATER *
            Possibly add easier way to determine strafe distance
         */

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.

    }
}