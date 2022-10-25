package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {

    private DcMotor motor_drive_lf;
    private DcMotor motor_drive_rf;
    private DcMotor motor_drive_lr;
    private DcMotor motor_drive_rr;
    private DcMotor motor_lift;
    private DcMotor motor_swivel;

    private ElapsedTime runtime = new ElapsedTime();

    //REMEMBER TO SET NEW VALUES FOR LIFT AND SWIVEL MOTORS

    //In theory, swivel uses degrees instead of millimeters
    static final double     COUNTS_PER_MOTOR_REV_DRIVE    = 537.7 ;    // Drive motors
    static final double     COUNTS_PER_MOTOR_REV_LIFT    = 537.7 ; //Unnecessary but just in case different motors are used for wheels and the lift / arm
    static final double     COUNTS_PER_MOTOR_REV_SWIVEL    = 537.7 ; //Just for another motor type with different ticks per revolution
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
    static final double     DRIVE_SPEED             = 0.6;
    static final double     UP_LIFT_SPEED           = 0.5;
    static final double     DOWN_LIFT_SPEED         = 0.3;
    static final double     SWIVEL_SPEED            = 0.3;
    static final double     STRAFE_SPEED            = 0.6;
    static final double     TURN_SPEED              = 0.5;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        motor_drive_lf = hardwareMap.get(DcMotor.class, "motor_drive_lf");
        motor_drive_rf = hardwareMap.get(DcMotor.class, "motor_drive_rf");
        motor_drive_lr = hardwareMap.get(DcMotor.class, "motor_drive_lr");
        motor_drive_rr = hardwareMap.get(DcMotor.class, "motor_drive_rr");
        motor_lift = hardwareMap.get(DcMotor.class, "motor_lift");
        motor_swivel = hardwareMap.get(DcMotor.class, "motor_swivel");

        // Reverse one of the drive motors.
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        motor_drive_rf.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_drive_rr.setDirection(DcMotorSimple.Direction.REVERSE);

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

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at",  "%7d :%7d",
                motor_drive_lf.getCurrentPosition(),
                motor_drive_rf.getCurrentPosition(),
                motor_drive_lr.getCurrentPosition(),
                motor_drive_rr.getCurrentPosition(),
                motor_lift.getCurrentPosition(),
                motor_swivel.getCurrentPosition());

        telemetry.update();

        waitForStart();

        /**
         * NOTES FOR LATER *
            Possibly add easier way to determine strafe distance
         */

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  500,  500, 500, 500, 0, 0, 5.0);
        encoderDrive(UP_LIFT_SPEED, 0, 0, 0, 0, 500, 0, 5.0);
        encoderDrive(DOWN_LIFT_SPEED, 0, 0, 0, 0, -500, 0, 5.0);
        encoderDrive(SWIVEL_SPEED, 0, 0, 0, 0, 0, 45, 5.0);
        encoderDrive(SWIVEL_SPEED, 0, 0, 0, 0, 0, -45, 5.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.

    }

        /*
         *  Method to perform a relative move, based on encoder counts.
         *  Encoders are not reset as the move is based on the current position.
         *  Move will stop if any of three conditions occur:
         *  1) Move gets to the desired position
         *  2) Move runs out of time
         *  3) Driver stops the opmode running.
         */
        public void encoderDrive(double speed,
        double lfMM, double rfMM, double lrMM, double rrMM, double lftMM, double swvDG,
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

                // keep looping while we are still active, and there is time left, and both motors are running.
                // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
                // its target position, the motion will stop.  This is "safer" in the event that the robot will
                // always end the motion as soon as possible.
                // However, if you require that BOTH motors have finished their moves before the robot continues
                // onto the next step, use (isBusy() || isBusy()) in the loop test.
                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (motor_drive_lf.isBusy() && motor_drive_rf.isBusy() && motor_drive_lr.isBusy() && motor_drive_rr.isBusy() && motor_lift.isBusy() && motor_swivel.isBusy())) {

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
}