package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class InitSetup {

    //Variables to define motors
    public DcMotor motor_drive_lf;
    public DcMotor motor_drive_rf;
    public DcMotor motor_drive_lr;
    public DcMotor motor_drive_rr;
    public DcMotor motor_lift;
    public DcMotor motor_swivel;
    public Servo claw_servo;

    //Variable for joystick speed
    public double JOY_SPEED;

    //Variable for servo position
    public double SERVO_POS;

    //Import classes for use in the rest of the program
    private LinearOpMode opMode;
    private ElapsedTime runtime= new ElapsedTime();
    //private HardwareMap hardwareMap;

    //Servo variables
    public static final double SERVO_CLOSED_AUTO = 1.55;
    public static final double SERVO_OPEN_AUTO = 0.75;
    public static final double SERVO_CLOSED = 1.55;
    public static final double SERVO_OPEN = 0.85;

    //Number of ticks for each motor type
    public static final double     COUNTS_PER_MOTOR_REV_DRIVE    = 537.7 ;
    public static final double     COUNTS_PER_MOTOR_REV_LIFT    = 537.7 ;
    public static final double     COUNTS_PER_MOTOR_REV_SWIVEL    = 1120 ;

    //Gear reductions (if any, just leave 1 if direct mounted)
    public static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    public static final double     LIFT_GEAR_REDUCTION    = 1.0 ;
    public static final double     SWIVEL_GEAR_REDUCTION    = 1.0 ;

    //Wheel diameters for driving by millimeters
    public static final double     WHEEL_DIAMETER_MM_DRIVE   = 95.0 ;     // For figuring circumference
    public static final double     WHEEL_DIAMETER_MM_LIFT   = 41.0 ;

    //Formulas for driving by millimeters and degrees
    public static final double     COUNTS_PER_MM_DRIVE        = (COUNTS_PER_MOTOR_REV_DRIVE * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_MM_DRIVE * 3.1415); //Drive motors
    public static final double     COUNTS_PER_MM_LIFT         = (COUNTS_PER_MOTOR_REV_LIFT * LIFT_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_MM_LIFT * 3.1415); //Uses separate gear reduction and wheel diameter for lift / arm
    public static final double     COUNTS_PER_DG_SWIVEL       = ((COUNTS_PER_MOTOR_REV_SWIVEL * SWIVEL_GEAR_REDUCTION) /
            360) / (COUNTS_PER_MOTOR_REV_SWIVEL * SWIVEL_GEAR_REDUCTION); //Converts counts per rev into degrees

    //Tolerance of encoders to reduce hangs trying to get the perfect position
    public static final double     ENCODER_TOLERANCE       = 7;

    public void standardSetup(HardwareMap hardwareMap, LinearOpMode opMode) {

        //Set initial joystick speed limit
        JOY_SPEED = 0.8;

        //Set initial servo position
        SERVO_POS = SERVO_CLOSED;

        //Define motors via hardwareMap
        motor_drive_lf = hardwareMap.get(DcMotor.class, "motor_drive_lf");
        motor_drive_rf = hardwareMap.get(DcMotor.class, "motor_drive_rf");
        motor_drive_lr = hardwareMap.get(DcMotor.class, "motor_drive_lr");
        motor_drive_rr = hardwareMap.get(DcMotor.class, "motor_drive_rr");
        motor_lift = hardwareMap.get(DcMotor.class, "motor_lift");
        motor_swivel = hardwareMap.get(DcMotor.class, "motor_swivel");
        claw_servo = hardwareMap.get(Servo.class, "claw_servo");

        //Reverse directions of motors
        motor_drive_lr.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_drive_lf.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_lift.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set brake behavior on motors
        motor_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_swivel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void autoSetup(HardwareMap hardwareMap, LinearOpMode opMode) {

        //Set initial joystick speed limit
        JOY_SPEED = 0.8;

        //Set initial servo position
        SERVO_POS = SERVO_CLOSED;

        //Define motors via hardwareMap
        motor_drive_lf = hardwareMap.get(DcMotor.class, "motor_drive_lf");
        motor_drive_rf = hardwareMap.get(DcMotor.class, "motor_drive_rf");
        motor_drive_lr = hardwareMap.get(DcMotor.class, "motor_drive_lr");
        motor_drive_rr = hardwareMap.get(DcMotor.class, "motor_drive_rr");
        motor_lift = hardwareMap.get(DcMotor.class, "motor_lift");
        motor_swivel = hardwareMap.get(DcMotor.class, "motor_swivel");
        claw_servo = hardwareMap.get(Servo.class, "claw_servo");

        //Reverse directions of motors
        motor_drive_lr.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_drive_lf.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_lift.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set brake behavior on motors
        motor_drive_lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_drive_rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_drive_lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_drive_rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_swivel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Reset encoder values
        motor_drive_lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_drive_rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_drive_lr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_drive_rr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_swivel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Enable encoders
        motor_drive_lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_drive_rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_drive_lr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_drive_rr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
