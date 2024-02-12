package org.firstinspires.ftc.teamcode;

public class hardwareMapChecker {
    boolean error = false;

    public boolean hardwareMapChecker (InitSetup initsetup) {
        if (initsetup.motor_drive_lf == null) {error = true;}
        if (initsetup.motor_drive_rf ==null) {error = true;}
        if (initsetup.motor_drive_lr ==null) {error = true;}
        if (initsetup.motor_drive_rr ==null) {error = true;}
        if (initsetup.motor_arm ==null) {error = true;}
        if (initsetup.launch_servo ==null) {error = true;}
        if (initsetup.claw_arm_servo ==null) {error = true;}
        if (initsetup.claw_pan_servo ==null) {error = true;}
        if (initsetup.claw_tilt_servo ==null) {error = true;}
        if (initsetup.claw_left_servo ==null) {error = true;}
        if (initsetup.claw_right_servo ==null) {error = true;}

        return error;
    }
}
