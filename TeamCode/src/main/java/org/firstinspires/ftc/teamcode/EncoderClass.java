package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class EncoderClass {

    //Import classes for use in the rest of the program
    private LinearOpMode opMode;
    private InitSetup initsetup;
    private ElapsedTime runtime= new ElapsedTime();

    public void encoderDrive(LinearOpMode mode, double speed,
                             double lfMM, double rfMM, double lrMM, double rrMM, double lftMM, double swvDG, double cwPos,
                             double timeoutS) {
        opMode = mode;
        int newlfTarget;
        int newrfTarget;
        int newlrTarget;
        int newrrTarget;
        int newlftTarget;
        int newswvTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newlfTarget = initsetup.motor_drive_lf.getCurrentPosition() + (int)(lfMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newrfTarget = initsetup.motor_drive_rf.getCurrentPosition() + (int)(rfMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newlrTarget = initsetup.motor_drive_lr.getCurrentPosition() + (int)(lrMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newrrTarget = initsetup.motor_drive_rr.getCurrentPosition() + (int)(rrMM * InitSetup.COUNTS_PER_MM_DRIVE);
            newlftTarget = initsetup.motor_lift.getCurrentPosition() + (int)(lftMM * InitSetup.COUNTS_PER_MM_LIFT);
            newswvTarget = initsetup.motor_swivel.getCurrentPosition() + (int)(swvDG * InitSetup.COUNTS_PER_DG_SWIVEL);

            initsetup.motor_drive_lf.setTargetPosition(newlfTarget);
            initsetup.motor_drive_rf.setTargetPosition(newrfTarget);
            initsetup.motor_drive_lr.setTargetPosition(newlrTarget);
            initsetup.motor_drive_rr.setTargetPosition(newrrTarget);
            initsetup.motor_lift.setTargetPosition(newlftTarget);
            initsetup.motor_swivel.setTargetPosition(newswvTarget);

            // Turn On RUN_TO_POSITION
            initsetup.motor_drive_lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initsetup.motor_drive_rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initsetup.motor_drive_lr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initsetup.motor_drive_rr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initsetup.motor_lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            initsetup.motor_swivel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            initsetup.motor_drive_lf.setPower(Math.abs(speed));
            initsetup.motor_drive_rf.setPower(Math.abs(speed));
            initsetup.motor_drive_lr.setPower(Math.abs(speed));
            initsetup.motor_drive_rr.setPower(Math.abs(speed));
            initsetup.motor_lift.setPower(Math.abs(speed));
            initsetup.motor_swivel.setPower(Math.abs(speed));
            initsetup.claw_servo.setPosition(cwPos);

            // keep looping while we are still active, and there is time left, and motors are not within the target threshold.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (Math.abs(initsetup.motor_drive_lf.getCurrentPosition()-initsetup.motor_drive_lf.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initsetup.motor_drive_rf.getCurrentPosition()-initsetup.motor_drive_rf.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initsetup.motor_drive_lr.getCurrentPosition()-initsetup.motor_drive_lr.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initsetup.motor_drive_rr.getCurrentPosition()-initsetup.motor_drive_rr.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initsetup.motor_lift.getCurrentPosition()-initsetup.motor_lift.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE || Math.abs(initsetup.motor_swivel.getCurrentPosition()-initsetup.motor_swivel.getTargetPosition()) > InitSetup.ENCODER_TOLERANCE)) {

                // Display it for the driver.
                opMode.telemetry.addData("Running to",  " %7d :%7d", newlfTarget,  newrfTarget, newlrTarget, newrrTarget);
                opMode.telemetry.addData("Currently at",  " at %7d :%7d",
                        initsetup.motor_drive_lf.getCurrentPosition(), initsetup.motor_drive_rf.getCurrentPosition(), initsetup.motor_drive_lr.getCurrentPosition(), initsetup.motor_drive_rr.getCurrentPosition(),
                        initsetup.motor_lift.getCurrentPosition(), initsetup.motor_swivel.getCurrentPosition(), opMode.telemetry.update());
            }

            // Stop all motion;
            initsetup.motor_drive_lf.setPower(0);
            initsetup.motor_drive_rf.setPower(0);
            initsetup.motor_drive_lr.setPower(0);
            initsetup.motor_drive_rr.setPower(0);
            initsetup.motor_lift.setPower(0);
            initsetup.motor_swivel.setPower(0);

            // Turn off RUN_TO_POSITION
            initsetup.motor_drive_lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initsetup.motor_drive_rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initsetup.motor_drive_lr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initsetup.motor_drive_rr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initsetup.motor_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            initsetup.motor_swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            opMode.sleep(50);   // optional pause after each move.
        }
    }
}
