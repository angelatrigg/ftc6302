package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "DriveOp")
public class DriveOp extends LinearOpMode {

    private DcMotor motor_drive_lf;
    private DcMotor motor_drive_lr;
    private DcMotor motor_drive_rf;
    private DcMotor motor_drive_rr;
    private DcMotor motor_lift;
    private DcMotor motor_swivel;

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

        // Reverse one of the drive motors.
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        motor_drive_lf.setDirection(DcMotorSimple.Direction.REVERSE);
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        motor_drive_lr.setDirection(DcMotorSimple.Direction.REVERSE);
        //Below breaks drive
        //motor_drive_lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor_drive_lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor_drive_rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor_drive_rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.
                motor_drive_lf.setPower(-gamepad1.left_stick_y);
                motor_drive_rf.setPower(-gamepad1.right_stick_y);
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.
                motor_drive_lr.setPower(-gamepad1.left_stick_y);
                motor_drive_rr.setPower(-gamepad1.right_stick_y);
                if (gamepad1.left_bumper || gamepad1.dpad_left) {
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
                } else if (gamepad1.right_bumper || gamepad1.dpad_right) {
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
                } else if (gamepad1.dpad_up) {
                    motor_drive_lf.setPower(1);
                    motor_drive_lr.setPower(1);
                    motor_drive_rf.setPower(1);
                    motor_drive_rr.setPower(1);
                } else if (gamepad1.dpad_down) {
                    motor_drive_lf.setPower(-1);
                    motor_drive_lr.setPower(-1);
                    motor_drive_rf.setPower(-1);
                    motor_drive_rr.setPower(-1);
                } else if (gamepad2.a) {
                    motor_lift.setPower(-0.8);
                } else if (gamepad2.b) {
                    motor_lift.setPower(0.3);
                } else if (gamepad2.left_bumper) {
                    motor_swivel.setPower(-0.5);
                } else if (gamepad2.right_bumper) {
                    motor_swivel.setPower(0.5);
                } else {
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_rf.setPower(0);
                    motor_drive_lr.setPower(0);
                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.
                    motor_drive_lf.setPower(0);
                    motor_drive_rr.setPower(0);
                    motor_lift.setPower(0);
                    motor_swivel.setPower(0);
                }
                telemetry.addData("Left Pow", motor_drive_lr.getPower());
                telemetry.addData("Right Pow", motor_drive_lf.getPower());
                telemetry.update();
            }
        }
    }
}