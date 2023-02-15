package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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


        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        //encoderDrive(DRIVE_SPEED,  500,  500, 500, 500, 0, 0, 0, 5.0);
        //encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 50, 0, 0, 5.0);
        //encoderDrive(DOWN_LIFT_SPEED, 0, 0, 0, 0, -25, 0, 0, 5.0);
        encoderClass.encoderDrive(0.5, 0, 0, 0, 0, 0, 45, 0, 5.0, this, initsetup);
        encoderClass.encoderDrive(0.5, 0, 0, 0, 0, 0, -45, 0, 5.0, this, initsetup);
        //encoderDrive(SERVO_SPEED, 0, 0, 0, 0, 0, 0, 50, 5.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.

    }
}