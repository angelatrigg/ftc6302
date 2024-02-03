package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Reset Arm Position")
public class ResetArm extends LinearOpMode {

    @Override
    public void runOpMode() {

        InitSetup initsetup = new InitSetup();
        initsetup.standardSetup(hardwareMap, this);

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {

                if (gamepad2.a) {
                    initsetup.motor_arm.setPower(1);
                }
                if (gamepad2.b) {
                    initsetup.motor_arm.setPower(-1);
                }
                if (!(gamepad2.a || gamepad1.b)) {
                    initsetup.motor_arm.setPower(0);
                }


                telemetry.addData("Left Rear Pow", initsetup.motor_drive_lr.getPower());
                telemetry.addData("Left Front Pow", initsetup.motor_drive_lf.getPower());
                telemetry.addData("Right Rear Pow", initsetup.motor_drive_rr.getPower());
                telemetry.addData("Right Front Pow", initsetup.motor_drive_rf.getPower());
                telemetry.addData("Left Joystick Y", gamepad1.left_stick_y);
                telemetry.addData("Right Joystick Y", gamepad1.right_stick_y);
                telemetry.addData("Joystick Max Speed", initsetup.JOY_SPEED);
                telemetry.addData("Right Cooldown", initsetup.right_cooldown);
                telemetry.addData("Arm Position: ", initsetup.motor_arm.getCurrentPosition());
                //telemetry.addData("Tilt Servo Pos:", initsetup.claw_tilt_servo.getPosition());
                telemetry.addData("Pan Servo Pos:", initsetup.claw_pan_servo.getPosition());
                //telemetry.addData("Left Servo Pos:", initsetup.claw_left_servo.getPosition());
                //telemetry.addData("Right Servo Pos:", initsetup.claw_right_servo.getPosition());
                telemetry.update();
            }
        }
    }
}