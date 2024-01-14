package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoRedLeft", group="Linear OpMode")
public class AutoRedLeft extends LinearOpMode {
    private ElapsedTime runtime;
    private HardwareHandler handler;

    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor intake = null;
    private DcMotor arm = null;
    private Servo claw = null;
    private Servo clawLeftGripper = null;
    private Servo clawRightGripper = null;
    private Servo launcher = null;

    private double leftClawClosed = 0.1;
    private double leftClawOpen = 0.4;
    private double rightClawClosed = 0.8;
    private double rightClawOpen = 0.55;

    @Override
    public void runOpMode() throws InterruptedException {
        runtime = new ElapsedTime();
        handler = new HardwareHandler(hardwareMap);

        frontLeftDrive = handler.frontLeftDrive;
        backLeftDrive = handler.backLeftDrive;
        frontRightDrive = handler.frontRightDrive;
        backRightDrive = handler.backRightDrive;
        intake = handler.intake;
        arm = handler.arm;
        claw = handler.claw;
        clawLeftGripper = handler.clawLeftGripper;
        clawRightGripper = handler.clawRightGripper;
        launcher = handler.launcher;

        handler.resetTargetPositions();
        handler.setAutoRunModes();

        telemetry.addData("Status", "Initialized");

        waitForStart();
        runtime.reset();

        handler.setAutoRunModesSimple();
        clawLeftGripper.setPosition(leftClawClosed);
        clawRightGripper.setPosition(rightClawClosed);
        arm.setTargetPosition(144);
        while ((arm.isBusy())) {
            arm.setPower(0);
            frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        handler.setAutoRunModesSimple();
        handler.setDrivePower(1,1, 1, 1);
        handler.inchesSideways(50);
        while ((frontLeftDrive.isBusy() || frontRightDrive.isBusy() || backRightDrive.isBusy() || backLeftDrive.isBusy())) {
            handler.setDrivePower(0, 0, 0, 0);
            frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        handler.setAutoRunModesSimple();
        handler.setDrivePower(1, 1, 1, 1);
        handler.inchesForward(-98);
        while ((frontLeftDrive.isBusy() || frontRightDrive.isBusy() || backRightDrive.isBusy() || backLeftDrive.isBusy())) {
            handler.setDrivePower(0, 0, 0, 0);
            frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
}
