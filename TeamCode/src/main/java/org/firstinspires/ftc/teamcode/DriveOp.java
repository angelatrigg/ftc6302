package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "DriveOp")
public class DriveOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        InitSetup initsetup = new InitSetup();
        initsetup.standardSetup(hardwareMap, this);

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.

                //Joystick movement controls
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

                //Strafe controls
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

                //Driving speed limit controls
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

                //Arm controls
                if (gamepad2.a) {
                    initsetup.motor_arm.setPower(0.5);
                }
                if (gamepad2.b) {
                    initsetup.motor_arm.setPower(-0.5);
                }
                if (!(gamepad2.a || gamepad1.b)) {
                    initsetup.motor_arm.setPower(0);
                }

                //Pixel claw controls
                if (gamepad2.left_bumper && initsetup.SERVO_LEFT_STATE && initsetup.left_cooldown == 0) {
                    initsetup.claw_left_servo.setPosition(InitSetup.SERVO_LEFT_OPEN);
                    initsetup.SERVO_LEFT_STATE = false;
                    initsetup.left_cooldown = 10;
                } else if (gamepad2.left_bumper && !initsetup.SERVO_LEFT_STATE && initsetup.left_cooldown == 0) {
                    initsetup.claw_left_servo.setPosition(InitSetup.SERVO_LEFT_CLOSED);
                    initsetup.SERVO_LEFT_STATE = true;
                    initsetup.left_cooldown = 10;
                }
                if (gamepad2.right_bumper && initsetup.SERVO_RIGHT_STATE && initsetup.right_cooldown == 0) {
                    initsetup.claw_right_servo.setPosition(InitSetup.SERVO_RIGHT_OPEN);
                    initsetup.SERVO_RIGHT_STATE = false;
                    initsetup.right_cooldown = 10;
                } else if (gamepad2.right_bumper && !initsetup.SERVO_RIGHT_STATE && initsetup.right_cooldown == 0) {
                    initsetup.claw_right_servo.setPosition(InitSetup.SERVO_RIGHT_CLOSED);
                    initsetup.SERVO_RIGHT_STATE = true;
                    initsetup.right_cooldown = 10;
                }

                //Claw arm controls
                if (gamepad2.x) {
                    initsetup.claw_arm_servo.setPosition(InitSetup.SERVO_LAUNCHER_CLOSED);
                }
                if (gamepad2.y) {
                    initsetup.claw_arm_servo.setPosition(InitSetup.SERVO_LAUNCHER_OPEN);
                }

                //Claw gimbal controls
                if (gamepad2.dpad_up){
                    initsetup.SERVO_TILT_POS = (initsetup.SERVO_TILT_POS - 0.005);
                    initsetup.claw_tilt_servo.setPosition(initsetup.SERVO_TILT_POS);
                }
                if (gamepad2.dpad_left) {
                    initsetup.SERVO_PAN_POS = (initsetup.SERVO_PAN_POS + 0.005);
                    initsetup.claw_pan_servo.setPosition(initsetup.SERVO_PAN_POS);
                }
                if (gamepad2.dpad_right) {
                    initsetup.SERVO_PAN_POS = (initsetup.SERVO_PAN_POS - 0.005);
                    initsetup.claw_pan_servo.setPosition(initsetup.SERVO_PAN_POS);
                }
                if (gamepad2.dpad_down) {
                    initsetup.SERVO_TILT_POS = (initsetup.SERVO_TILT_POS + 0.005);
                    initsetup.claw_tilt_servo.setPosition(initsetup.SERVO_TILT_POS);
                }


                //Cooldowns
                if (initsetup.left_cooldown > 0 && !gamepad2.left_bumper) {initsetup.left_cooldown = initsetup.left_cooldown-1;}
                if (initsetup.right_cooldown > 0 && !gamepad2.right_bumper) {initsetup.right_cooldown = initsetup.right_cooldown-1;}


                telemetry.addData("Left Rear Pow", initsetup.motor_drive_lr.getPower());
                telemetry.addData("Left Front Pow", initsetup.motor_drive_lf.getPower());
                telemetry.addData("Right Rear Pow", initsetup.motor_drive_rr.getPower());
                telemetry.addData("Right Front Pow", initsetup.motor_drive_rf.getPower());
                telemetry.addData("Left Joystick Y", gamepad1.left_stick_y);
                telemetry.addData("Right Joystick Y", gamepad1.right_stick_y);
                telemetry.addData("Joystick Max Speed", initsetup.JOY_SPEED);
                telemetry.addData("Right Cooldown", initsetup.right_cooldown);
                /*telemetry.addData("Tilt Servo Pos:", initsetup.claw_tilt_servo.getPosition());
                telemetry.addData("Pan Servo Pos:", initsetup.claw_pan_servo.getPosition());
                telemetry.addData("Left Servo Pos:", initsetup.claw_left_servo.getPosition());
                telemetry.addData("Right Servo Pos:", initsetup.claw_right_servo.getPosition());*/
                telemetry.update();
            }
        }
    }
}