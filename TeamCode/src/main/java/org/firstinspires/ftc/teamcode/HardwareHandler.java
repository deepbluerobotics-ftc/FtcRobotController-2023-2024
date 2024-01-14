package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class HardwareHandler {
    public static DcMotor frontLeftDrive = null;
    public static DcMotor backLeftDrive = null;
    public static DcMotor frontRightDrive = null;
    public static DcMotor backRightDrive = null;
    public static DcMotor intake = null;
    public static DcMotor arm = null;

    public static Servo claw = null;
    public static Servo clawLeftGripper = null;
    public static Servo clawRightGripper = null;
    public static Servo launcher = null;

    public static WebcamName camera = null;

    public HardwareMap hardwareMap;

    public HardwareHandler(HardwareMap hm)
    {
        hardwareMap = hm;

        frontLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        intake = hardwareMap.get(DcMotor.class, "intake");
        arm = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");
        clawLeftGripper = hardwareMap.get(Servo.class, "claw_left_gripper");
        clawRightGripper = hardwareMap.get(Servo.class, "claw_right_gripper");
        launcher = hardwareMap.get(Servo.class, "launcher");
        camera = hardwareMap.get(WebcamName.class, "Webcam 1");

        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.FORWARD);
        launcher.setDirection(Servo.Direction.REVERSE);

        resetPower();
        resetRunModes();
    }

    public static void resetPower() {
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        arm.setPower(0);
        intake.setPower(0);
        claw.setPosition(0.38);
        clawLeftGripper.setPosition(0.1);
        clawRightGripper.setPosition(0.8);
        launcher.setPosition(0);
    }

    public static void setPower(double frontLeft, double frontRight, double backLeft, double backRight,
                              double intakePower, double armPower, double clawPosition, double clawLeftGripperPosition,
                              double clawRightGripperPosition, double launcherPosition) {
        backLeftDrive.setPower(backLeft);
        backRightDrive.setPower(backRight);
        frontLeftDrive.setPower(frontLeft);
        frontRightDrive.setPower(frontRight);
        intake.setPower(intakePower);
        arm.setPower(armPower);
        claw.setPosition(clawPosition);
        clawLeftGripper.setPosition(clawLeftGripperPosition);
        clawRightGripper.setPosition(clawRightGripperPosition);
        launcher.setPosition(launcherPosition);
    }

    public static void resetRunModes() {
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void setRunModes() {
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static void resetTargetPositions() {
        backLeftDrive.setTargetPosition(0);
        backRightDrive.setTargetPosition(0);
        frontLeftDrive.setTargetPosition(0);
        frontRightDrive.setTargetPosition(0);
        arm.setTargetPosition(0);
    }

    public static void setAutoRunModes() {
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backLeftDrive.setPower(1);
        backRightDrive.setPower(1);
        frontLeftDrive.setPower(1);
        frontRightDrive.setPower(1);
        arm.setPower(1);
        intake.setPower(0);
    }

    public static void setAutoRunModesSimple() {
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static void setDrivePower(double leftFront, double leftBack, double rightFront, double rightBack) {
        frontLeftDrive.setPower(leftFront);
        frontRightDrive.setPower(rightFront);
        backRightDrive.setPower(rightBack);
        backLeftDrive.setPower(leftBack);
    }

    public static void inchesForward(double distance) {
        int counts = (int) (distance * 1440 / 3.77953);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + counts);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + counts);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + counts);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + counts);
    }

    public static void inchesRotate(double distance) {
        int counts = (int) (distance * 1440 / 3.77953);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + counts);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + counts);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() - counts);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() - counts);
    }

    public static void inchesSideways(double distance) {
        int counts = (int) (distance * 1440 / 3.77953);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + counts);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() - counts);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() - counts);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + counts);
    }
}
