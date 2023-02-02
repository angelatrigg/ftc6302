package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous")
public class Auto extends LinearOpMode {

    private InitSetup initsetup;
    private EncoderClass encoderClass;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
       initsetup = new InitSetup();
       encoderClass = new EncoderClass();
       initsetup.autoSetup(hardwareMap, this);


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
        encoderClass.encoderDrive(this, 0.5, 0, 0, 0, 0, 0, 45, 0, 5.0);
        encoderClass.encoderDrive(this, 0.5, 0, 0, 0, 0, 0, -45, 0, 5.0);
        //encoderDrive(SERVO_SPEED, 0, 0, 0, 0, 0, 0, 50, 5.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.

    }
}