package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class BasicOpMode_Iterative extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
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
    //private int armPosition = 0;
    private double armPower = 0;
    private double clawPosition = 0.5;
    private double clawLeftGripperPosition = 0.8;
    private double clawRightGripperPosition = 0.4;
    private double launcherPosition = 0;

    private double maxArmPos = 3600;
    private double minArmPos = 0;


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
        telemetry.addData("Status", "Initialized");

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

        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        //Intake logic
        if (gamepad1.b) {
            //intakeRotating = !intakeRotating;
            //intakeState = true;

            intakePower = 1;
        }
        if (gamepad1.a /*&& intakeState*/) {
            intakePower = 0;

            /*intakeState = false;
        if (intakeRotating)
            intakePower = 1;
        else
            intakePower = 0;*/
        }
        if (gamepad1.x) {
            intakePower = -1;
        }


        // Claw left gripper logic
        if (gamepad1.left_bumper && !clawLeftGripperState) {
            clawLeftGripperClosed = !clawLeftGripperClosed;
            clawLeftGripperState = true;
        }
        else if (!gamepad1.left_bumper && clawLeftGripperState)
            clawLeftGripperState = false;
        if (clawLeftGripperClosed)
            clawLeftGripperPosition = 0.6;
        else
            clawLeftGripperPosition = 0.8;

        // Claw right gripper logic
        if (gamepad1.right_bumper && !clawRightGripperState) {
            clawRightGripperClosed = !clawRightGripperClosed;
            clawRightGripperState = true;
        }
        else if (!gamepad1.right_bumper && clawRightGripperState)
            clawRightGripperState = false;
        if (clawRightGripperClosed)
            clawRightGripperPosition = 0.4;
        else
            clawRightGripperPosition = 0.2;

        // Arm logic
        if ((gamepad1.right_trigger > 0.2 && arm.getCurrentPosition() < maxArmPos) || (gamepad1.left_trigger > 0.2 && arm.getCurrentPosition() > minArmPos))
            armPower = gamepad1.right_trigger - gamepad1.left_trigger;
        else
            armPower = 0;



        /*if (gamepad1.x) {
            armPosition = 432;
        }
        else if (gamepad1.y) {
            armPosition = 2880;
        }
        else if (gamepad1.a) {
            armPosition = 0;
        }*/

        // Claw logic
        if (gamepad1.dpad_up && !gamepad1.dpad_down && clawPosition < 1)
            clawPosition += 0.01;
        if (gamepad1.dpad_down && !gamepad1.dpad_up && clawPosition > 0)
            clawPosition -= 0.01;

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
