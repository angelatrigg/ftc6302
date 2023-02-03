package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class EncoderClass {

    //Import runtime for use in the rest of the program
    private final ElapsedTime runtime = new ElapsedTime();

    public void encoderDrive(double speed,
                             double lfMM, double rfMM, double lrMM, double rrMM, double lftMM, double swvDG, double cwPos,
                             double timeoutS, LinearOpMode opMode, InitSetup initpostsetup) {
        int newlfTarget;
        int newrfTarget;
        int newlrTarget;
        int newrrTarget;
        int newlftTarget;
        int newswvTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newlfTarget = initpostsetup.motor_drive_lf.getCurrentPosition() + (int)(lfMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newrfTarget = initpostsetup.motor_drive_rf.getCurrentPosition() + (int)(rfMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newlrTarget = initpostsetup.motor_drive_lr.getCurrentPosition() + (int)(lrMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newrrTarget = initpostsetup.motor_drive_rr.getCurrentPosition() + (int)(rrMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newlftTarget = initpostsetup.motor_lift.getCurrentPosition() + (int)(lftMM * InitSetup.COUNTS_PER_MM_LIFT);
            newswvTarget = initpostsetup.motor_swivel.getCurrentPosition() + (int)(swvDG * InitSetup.COUNTS_PER_DG_SWIVEL);

            initpostsetup.motor_drive_lf.setTargetPosition(newlfTarget);
            initpostsetup.motor_drive_rf.setTargetPosition(newrfTarget);
            initpostsetup.motor_drive_lr.setTargetPosition(newlrTarget);
            initpostsetup.motor_drive_rr.setTargetPosition(newrrTarget);
            initpostsetup.motor_lift.setTargetPosition(newlftTarget);
            initpostsetup.motor_swivel.setTargetPosition(newswvTarget);

            // Turn On RUN_TO_POSITION
            initpostsetup.motor_drive_lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initpostsetup.motor_drive_rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initpostsetup.motor_drive_lr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initpostsetup.motor_drive_rr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initpostsetup.motor_lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initpostsetup.motor_swivel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            initpostsetup.motor_drive_lf.setPower(Math.abs(speed));
            initpostsetup.motor_drive_rf.setPower(Math.abs(speed));
            initpostsetup.motor_drive_lr.setPower(Math.abs(speed));
            initpostsetup.motor_drive_rr.setPower(Math.abs(speed));
            initpostsetup.motor_lift.setPower(Math.abs(speed));
            initpostsetup.motor_swivel.setPower(Math.abs(speed));
            initpostsetup.claw_servo.setPosition(cwPos);

            // keep looping while we are still active, and there is time left, and motors are not within the target threshold.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (Math.abs(initpostsetup.motor_drive_lf.getCurrentPosition()-initpostsetup.motor_drive_lf.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initpostsetup.motor_drive_rf.getCurrentPosition()-initpostsetup.motor_drive_rf.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initpostsetup.motor_drive_lr.getCurrentPosition()-initpostsetup.motor_drive_lr.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initpostsetup.motor_drive_rr.getCurrentPosition()-initpostsetup.motor_drive_rr.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initpostsetup.motor_lift.getCurrentPosition()-initpostsetup.motor_lift.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initpostsetup.motor_swivel.getCurrentPosition()-initpostsetup.motor_swivel.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE)) {

                // Display it for the driver.
                opMode.telemetry.addData("Running to",  " %7d :%7d", newlfTarget,  newrfTarget, newlrTarget, newrrTarget);
                opMode.telemetry.addData("Currently at",  " at %7d :%7d",
                        initpostsetup.motor_drive_lf.getCurrentPosition(), initpostsetup.motor_drive_rf.getCurrentPosition(), initpostsetup.motor_drive_lr.getCurrentPosition(), initpostsetup.motor_drive_rr.getCurrentPosition(),
                        initpostsetup.motor_lift.getCurrentPosition(), initpostsetup.motor_swivel.getCurrentPosition(), opMode.telemetry.update());
            }

            // Stop all motion;
            initpostsetup.motor_drive_lf.setPower(0);
            initpostsetup.motor_drive_rf.setPower(0);
            initpostsetup.motor_drive_lr.setPower(0);
            initpostsetup.motor_drive_rr.setPower(0);
            initpostsetup.motor_lift.setPower(0);
            initpostsetup.motor_swivel.setPower(0);

            // Turn off RUN_TO_POSITION
            initpostsetup.motor_drive_lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initpostsetup.motor_drive_rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initpostsetup.motor_drive_lr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initpostsetup.motor_drive_rr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initpostsetup.motor_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initpostsetup.motor_swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            opMode.sleep(50);   // optional pause after each move.
        }
    }
}