package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Linear OpMode", group="Linear OpMode")
public class BasicOpMode_Linear extends LinearOpMode {
    HardwareHandler hardwareHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        hardwareHandler = new HardwareHandler(hardwareMap);

        telemetry.addData("Status", "Initialized");

        waitForStart();
        hardwareHandler.setAutoRunModes();
        hardwareHandler.clawLeftGripper.setPosition(0.6);
        hardwareHandler.clawRightGripper.setPosition(0.4);
        hardwareHandler.frontLeftDrive.setPower(1);
        hardwareHandler.frontRightDrive.setPower(1);
        hardwareHandler.backLeftDrive.setPower(1);
        hardwareHandler.backRightDrive.setPower(1);
        hardwareHandler.frontLeftDrive.setTargetPosition(2880);
        hardwareHandler.frontRightDrive.setTargetPosition(2880);
        hardwareHandler.backLeftDrive.setTargetPosition(2880);
        hardwareHandler.backRightDrive.setTargetPosition(2880);
        hardwareHandler.resetPower();

    }
}
