package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name="AprilTag WebCam Example")
public class AprilTagWebcamExample extends LinearOpMode {

    private AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    private DcMotor frontLeft, backLeft, frontRight, backRight;
    private DcMotor Intake, flyWheelLeft, flyWheelRight, turret;
    private Servo flyWheelServo;

    @Override
    public void runOpMode() throws InterruptedException {

        // ---- Hardware Map ----
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        Intake = hardwareMap.get(DcMotor.class, "Intake");
        flyWheelLeft = hardwareMap.get(DcMotor.class, "flyWheelLeft");
        turret = hardwareMap.get(DcMotor.class, "turret");

        flyWheelServo = hardwareMap.get(Servo.class, "flyWheelServo");

        // ---- Init Vision ----
        aprilTagWebcam.init(hardwareMap, telemetry);

        telemetry.addLine("Initialization complete");
        telemetry.update();

        waitForStart();

        // ---- Main Control Loop ----
        while (opModeIsActive()) {

            aprilTagWebcam.update();

            AprilTagDetection id20 = aprilTagWebcam.getTagBySpecificId(20);
            aprilTagWebcam.displayDetectionTelemetry(id20);

            AprilTagDetection id24 = aprilTagWebcam.getTagBySpecificId(24);
            aprilTagWebcam.displayDetectionTelemetry(id24);

            // Auto-align to tag ID 20
            AprilTagDetection tag = id20;

            if (tag != null) {
                double error = tag.ftcPose.bearing;  // degrees left/right
                double kP = 0.02;
                double turretPower = error * kP;

                turretPower = Math.max(-0.5, Math.min(0.5, turretPower));

                turret.setPower(turretPower);

                telemetry.addData("Bearing", error);
                telemetry.addData("Turret Power", turretPower);
            } else {
                turret.setPower(0);
            }

            telemetry.update();
        }

        aprilTagWebcam.stop();
    }
}
