package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "DriveOp")
public class DriveOp extends LinearOpMode {

    private DcMotor motor_drive_lf;
    private DcMotor motor_drive_lr;
    private DcMotor motor_drive_rf;
    private DcMotor motor_drive_rr;
    private DcMotor motor_lift;
    private DcMotor motor_swivel;
    private Servo claw_servo;
    double JOY_SPEED = 0.8;
    static final double SERVO_SPEED = 0.1;
    static final double SERVO_MAX = 1.55;
    static final double SERVO_MIN = 0.85;
    double SERVO_POS = SERVO_MAX;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        motor_drive_lf = hardwareMap.get(DcMotor.class, "motor_drive_lf");
        motor_drive_lr = hardwareMap.get(DcMotor.class, "motor_drive_lr");
        motor_drive_rf = hardwareMap.get(DcMotor.class, "motor_drive_rf");
        motor_drive_rr = hardwareMap.get(DcMotor.class, "motor_drive_rr");
        motor_lift = hardwareMap.get(DcMotor.class, "motor_lift");
        motor_swivel = hardwareMap.get(DcMotor.class, "motor_swivel");
        claw_servo = hardwareMap.get(Servo.class, "claw_servo");

        // Reverse one of the drive motors.
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        //motor_drive_lf.setDirection(DcMotorSimple.Direction.REVERSE);
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        motor_drive_lr.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_drive_lf.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_lift.setDirection(DcMotorSimple.Direction.REVERSE);
        //Below breaks drive
        //motor_drive_lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor_drive_lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor_drive_rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor_drive_rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_swivel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.
                if (-gamepad1.left_stick_y <= JOY_SPEED && -gamepad1.left_stick_y >= -JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    motor_drive_lf.setPower(-gamepad1.left_stick_y);
                    //motor_drive_rf.setPower(-gamepad1.right_stick_y);
                    motor_drive_lr.setPower(-gamepad1.left_stick_y);
                    //motor_drive_rr.setPower(-gamepad1.right_stick_y);
                } else if (-gamepad1.left_stick_y > JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    motor_drive_lf.setPower(JOY_SPEED);
                    motor_drive_lr.setPower(JOY_SPEED);
                } else if (-gamepad1.left_stick_y < -JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    motor_drive_lf.setPower(-JOY_SPEED);
                    motor_drive_lr.setPower(-JOY_SPEED);
                }

                if (-gamepad1.right_stick_y <= JOY_SPEED && -gamepad1.right_stick_y >= -JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    motor_drive_rf.setPower(-gamepad1.right_stick_y);
                    motor_drive_rr.setPower(-gamepad1.right_stick_y);
                } else if (-gamepad1.right_stick_y > JOY_SPEED && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    motor_drive_rf.setPower(JOY_SPEED);
                    motor_drive_rr.setPower(JOY_SPEED);
                } else if (-gamepad1.right_stick_y < -.80 && !gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
                    motor_drive_rf.setPower(-JOY_SPEED);
                    motor_drive_rr.setPower(-JOY_SPEED);
                }
                if (gamepad1.left_bumper) {
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_rf.setPower(1);
                    motor_drive_lr.setPower(1);
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_lf.setPower(-1);
                    motor_drive_rr.setPower(-1);
                } else if (gamepad1.right_bumper) {
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_rf.setPower(-1);
                    motor_drive_lr.setPower(-1);
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_lf.setPower(1);
                    motor_drive_rr.setPower(1);
                } else if (gamepad1.dpad_right) {
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_rf.setPower(-0.5);
                    motor_drive_lr.setPower(-0.5);
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_lf.setPower(0.5);
                    motor_drive_rr.setPower(0.5);
                } else if (gamepad1.dpad_left) {
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_rf.setPower(0.5);
                    motor_drive_lr.setPower(0.5);
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_lf.setPower(-0.5);
                    motor_drive_rr.setPower(-0.5);
                } else if (gamepad1.dpad_up) {
                    motor_drive_lf.setPower(0.5);
                    motor_drive_lr.setPower(0.5);
                    motor_drive_rf.setPower(0.5);
                    motor_drive_rr.setPower(0.5);
                } else if (gamepad1.dpad_down) {
                    motor_drive_lf.setPower(-0.5);
                    motor_drive_lr.setPower(-0.5);
                    motor_drive_rf.setPower(-0.5);
                    motor_drive_rr.setPower(-0.5);
                } else if (gamepad1.a) {
                    JOY_SPEED = 0.8;
                } else if (gamepad1.b) {
                    JOY_SPEED = 0.3;
                } else if (gamepad2.a) {
                    motor_lift.setPower(0.65);
                } else if (gamepad2.b) {
                    motor_lift.setPower(-0.5);
                } else if (gamepad2.left_bumper) {
                    motor_swivel.setPower(0.3);
                } else if (gamepad2.right_bumper) {
                    motor_swivel.setPower(-0.3);
                } else if (gamepad2.x) {
                        SERVO_POS = SERVO_MAX;
                } else if (gamepad2.y) {
                        SERVO_POS = SERVO_MIN;
                } else {
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    //motor_drive_rf.setPower(0);
                    //motor_drive_lr.setPower(0);
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    //motor_drive_lf.setPower(0);
                    //motor_drive_rr.setPower(0);
                    motor_lift.setPower(0);
                    motor_swivel.setPower(0);
                }
                claw_servo.setPosition(SERVO_POS);
                telemetry.addData("Left Rear Pow", motor_drive_lr.getPower());
                telemetry.addData("Left Front Pow", motor_drive_lf.getPower());
                telemetry.addData("Right Rear Pow", motor_drive_rr.getPower());
                telemetry.addData("Right Front Pow", motor_drive_rf.getPower());
                telemetry.addData("Left Joystick Y", gamepad1.left_stick_y);
                telemetry.addData("Right Joystick Y", gamepad1.right_stick_y);
                telemetry.update();
            }
        }
    }
}