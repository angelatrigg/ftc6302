package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "DriveOp")
public class DriveOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        InitSetup initsetup = new InitSetup();
        initsetup.standardSetup(hardwareMap);

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.
                if (-gamepad1.left_stick_y <= initsetup.JOY_SPEED && -gamepad1.left_stick_y >= -initsetup.JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    initsetup.motor_drive_lf.setPower(-gamepad1.left_stick_y);
                    initsetup.motor_drive_lr.setPower(-gamepad1.left_stick_y);
                } else if (-gamepad1.left_stick_y > initsetup.JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    initsetup.motor_drive_lf.setPower(initsetup.JOY_SPEED);
                    initsetup.motor_drive_lr.setPower(initsetup.JOY_SPEED);
                } else if (-gamepad1.left_stick_y < -initsetup.JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    initsetup.motor_drive_lf.setPower(-initsetup.JOY_SPEED);
                    initsetup.motor_drive_lr.setPower(-initsetup.JOY_SPEED);
                }
                if (-gamepad1.right_stick_y <= initsetup.JOY_SPEED && -gamepad1.right_stick_y >= -initsetup.JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    initsetup.motor_drive_rf.setPower(-gamepad1.right_stick_y);
                    initsetup.motor_drive_rr.setPower(-gamepad1.right_stick_y);
                } else if (-gamepad1.right_stick_y > initsetup.JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    initsetup.motor_drive_rf.setPower(initsetup.JOY_SPEED);
                    initsetup.motor_drive_rr.setPower(initsetup.JOY_SPEED);
                } else if (-gamepad1.right_stick_y < -initsetup.JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    initsetup.motor_drive_rf.setPower(-initsetup.JOY_SPEED);
                    initsetup.motor_drive_rr.setPower(-initsetup.JOY_SPEED);
                }
                if (gamepad1.left_bumper) {
                    initsetup.motor_drive_rf.setPower(initsetup.JOY_SPEED);
                    initsetup.motor_drive_lr.setPower(initsetup.JOY_SPEED);
                    initsetup.motor_drive_lf.setPower(-initsetup.JOY_SPEED);
                    initsetup.motor_drive_rr.setPower(-initsetup.JOY_SPEED);
                }
                if (gamepad1.right_bumper) {
                    initsetup.motor_drive_rf.setPower(-initsetup.JOY_SPEED);
                    initsetup.motor_drive_lr.setPower(-initsetup.JOY_SPEED);
                    initsetup.motor_drive_lf.setPower(initsetup.JOY_SPEED);
                    initsetup.motor_drive_rr.setPower(initsetup.JOY_SPEED);
                }
                if (gamepad1.dpad_right) {
                    initsetup.motor_drive_rf.setPower(-0.5);
                    initsetup.motor_drive_lr.setPower(-0.5);
                    initsetup.motor_drive_lf.setPower(0.5);
                    initsetup.motor_drive_rr.setPower(0.5);
                }
                if (gamepad1.dpad_left) {
                    initsetup.motor_drive_rf.setPower(0.5);
                    initsetup.motor_drive_lr.setPower(0.5);
                    initsetup.motor_drive_lf.setPower(-0.5);
                    initsetup.motor_drive_rr.setPower(-0.5);
                }
                if (gamepad1.dpad_up) {
                    initsetup.motor_drive_lf.setPower(0.5);
                    initsetup.motor_drive_lr.setPower(0.5);
                    initsetup.motor_drive_rf.setPower(0.5);
                    initsetup.motor_drive_rr.setPower(0.5);
                }
                if (gamepad1.dpad_down) {
                    initsetup.motor_drive_lf.setPower(-0.5);
                    initsetup.motor_drive_lr.setPower(-0.5);
                    initsetup.motor_drive_rf.setPower(-0.5);
                    initsetup.motor_drive_rr.setPower(-0.5);
                }
                if (gamepad1.a) {
                    initsetup.JOY_SPEED = 0.5;
                }
                if (gamepad1.b) {
                    initsetup.JOY_SPEED = 0.8;
                }
                if (gamepad1.x) {
                    initsetup.JOY_SPEED = 0.3;
                }
                if (gamepad1.y) {
                    initsetup.JOY_SPEED = 1;
                }
                if (gamepad2.a) {
                    initsetup.motor_lift.setPower(1);
                }
                if (gamepad2.b) {
                    initsetup.motor_lift.setPower(-0.8);
                }
                if (gamepad2.left_bumper) {
                    initsetup.motor_swivel.setPower(0.5);
                }
                if (gamepad2.right_bumper) {
                    initsetup.motor_swivel.setPower(-0.5);
                }
                if (gamepad2.x) {
                        initsetup.SERVO_POS = InitSetup.SERVO_CLOSED;
                }
                if (gamepad2.y) {
                    initsetup.SERVO_POS = InitSetup.SERVO_OPEN;
                }
                if (!gamepad2.b && !gamepad2.a && !gamepad2.x && !gamepad2.y && !gamepad2.right_bumper && !gamepad2.left_bumper) {
                    initsetup.motor_lift.setPower(0);
                    initsetup.motor_swivel.setPower(0);
                }
                initsetup.claw_servo.setPosition(initsetup.SERVO_POS);
                telemetry.addData("Left Rear Pow", initsetup.motor_drive_lr.getPower());
                telemetry.addData("Left Front Pow", initsetup.motor_drive_lf.getPower());
                telemetry.addData("Right Rear Pow", initsetup.motor_drive_rr.getPower());
                telemetry.addData("Right Front Pow", initsetup.motor_drive_rf.getPower());
                telemetry.addData("Left Joystick Y", gamepad1.left_stick_y);
                telemetry.addData("Right Joystick Y", gamepad1.right_stick_y);
                telemetry.addData("Joystick Max Speed", initsetup.JOY_SPEED);
                telemetry.update();
            }
        }
    }
}