package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="turret testben ", group="Linear Opmode")
public class turret1 extends LinearOpMode {

    private DcMotor turret;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // --- Hardware Map ---
        turret = hardwareMap.get(DcMotor.class, "turret");
        DcMotor index = hardwareMap.get(DcMotor.class, "index");
        DcMotor shooter = hardwareMap.get(DcMotor.class, "shooter");

        // --- Motor Directions ---
        turret.setDirection(DcMotor.Direction.FORWARD);   // direct drive
        index.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.FORWARD);

        // --- Run without encoders ---
        DcMotor[] motors = {turret};
        for (DcMotor m : motors) {
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        DcMotor[] motors = {index};
        for (DcMotor m : motors) {
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        DcMotor[] motors = {shooter};
        for (DcMotor m : motors) {
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        waitForStart();

        while (opModeIsActive()) {

            // === Rotation ===
            if (gamepad1.dpad_right) {  // clockwise
                turret.setPower(-0.1);
            } else if (gamepad1.dpad_left) { // counterclockwise
                turret.setPower(0.1);
            }
            else {
                turret.setPower(0);
            }

            if (gamepad1.left_bumper) {  // clockwise
                index.setPower(-1);
            } else if (gamepad1.right_bumper) { // counterclockwise
                index.setPower(1);
            }
            else {
                index.setPower(0);
            }

            if (gamepad1.left_trigger) {  // clockwise
                shooter.setPower(-1);
            } else if (gamepad1.right_trigger) { // counterclockwise
                shooter.setPower(1);
            }
            else {
                shooter.setPower(0);
            }
        }
    }
}