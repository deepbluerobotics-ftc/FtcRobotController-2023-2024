package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Tele", group="Iterative OpMode")
public class Tele extends OpMode {
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

    private double frontLeftPower = 0;
    private double frontRightPower = 0;
    private double backLeftPower = 0;
    private double backRightPower = 0;
    private double intakePower = 0;
    private double armPower = 0;
    private double clawPosition = 0.38;
    private double clawLeftGripperPosition = 0.1;
    private double clawRightGripperPosition = 0.8;
    private double launcherPosition = 0;

    private double leftClawClosed = 0.1;
    private double leftClawOpen = 0.4;
    private double rightClawClosed = 0.8;
    private double rightClawOpen = 0.55;

    private double max = 0;
    private double axial = 0;
    private double lateral = 0;
    private double yaw = 0;

    private boolean intakeRotating = false;
    private boolean clawLeftGripperClosed = false;
    private boolean clawRightGripperClosed = false;
    private boolean armUp = false;

    private boolean intakeState = false;
    private boolean clawLeftGripperState = false;
    private boolean clawRightGripperState = false;
    private boolean armState = false;

    @Override
    public void init() {
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

        handler.setRunModes();

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        axial = -gamepad1.left_stick_y;
        lateral = gamepad1.left_stick_x;
        yaw = gamepad1.right_stick_x;

        frontLeftPower = axial + lateral + yaw;
        frontRightPower = axial - lateral - yaw;
        backLeftPower = axial - lateral + yaw;
        backRightPower = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 0.7) {
            frontLeftPower   /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        //Intake logic
        if (gamepad1.b) {

            intakePower = 0.7;
        }
        if (gamepad1.a) {
            intakePower = 0;
        }
        if (gamepad1.x) {
            intakePower = -0.7;
        }

        // Claw left gripper logic
        if (gamepad1.left_bumper && !clawLeftGripperState) {
            clawLeftGripperClosed = !clawLeftGripperClosed;
            clawLeftGripperState = true;
        }
        else if (!gamepad1.left_bumper && clawLeftGripperState)
            clawLeftGripperState = false;
        if (clawLeftGripperClosed)
            clawLeftGripperPosition = leftClawClosed;
        else
            clawLeftGripperPosition = leftClawOpen;

        // Claw right gripper logic
        if (gamepad1.right_bumper && !clawRightGripperState) {
            clawRightGripperClosed = !clawRightGripperClosed;
            clawRightGripperState = true;
        }
        else if (!gamepad1.right_bumper && clawRightGripperState)
            clawRightGripperState = false;
        if (clawRightGripperClosed)
            clawRightGripperPosition = rightClawClosed;
        else
            clawRightGripperPosition = rightClawOpen;

        // Arm logic
        armPower = gamepad1.right_trigger - gamepad1.left_trigger;

        // Claw logic
        if (gamepad1.dpad_up && !gamepad1.dpad_down && clawPosition < 1)
            clawPosition += 0.005;
        if (gamepad1.dpad_down && !gamepad1.dpad_up && clawPosition > 0)
            clawPosition -= 0.005;

        //Launcher logic
        if (gamepad1.y) {
            launcherPosition = 1;
        }

        //Set powers and positions
        handler.setPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower, intakePower, armPower,
                                      clawPosition, clawLeftGripperPosition, clawRightGripperPosition, launcherPosition);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Front Left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        telemetry.addData("Arm/Intake", "%4.2f, %4.2f", armPower, intakePower);
        telemetry.addData("Claw  Rotation/Left/Right", "%4.2f, %4.2f, %4.2f", clawPosition, clawLeftGripperPosition, clawRightGripperPosition);
        telemetry.update();
    }
}
