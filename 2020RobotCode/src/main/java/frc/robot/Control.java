package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Control {

    // Driver motors
    private TalonFX leftFront = new TalonFX(1);
    private TalonFX leftBack = new TalonFX(2);
    private TalonFX rightFront = new TalonFX(3);
    private TalonFX rightBack = new TalonFX(4);

    // Neo 550 intake BRUSHLESS!
    private CANSparkMax intake = new CANSparkMax(4, MotorType.kBrushless);

    // Redline 775 Pro BBRUSHED
    private CANSparkMax beltDrive = new CANSparkMax(2, MotorType.kBrushed);
    // Mini Cim Motors BRUSHED
    private TalonSRX shooterTop = new TalonSRX(5);
    private TalonSRX shooterBot = new TalonSRX(6);

    // Electrical Switch limits
    public DigitalInput upLimit = new DigitalInput(0);
    public DigitalInput downLimit = new DigitalInput(1);
    public DigitalInput beltLimit = new DigitalInput(2);

    // Neo Motor BRUSHLESS
    private CANSparkMax elevator = new CANSparkMax(1, MotorType.kBrushless);

    private DoubleSolenoid intakeDoubleRight = new DoubleSolenoid(6, 7);
    private DoubleSolenoid intakeDoubleLeft = new DoubleSolenoid(0, 1);

    // LEDS
    // private CANSparkMax LED = new CANSparkMax(8, MotorType.kBrushed);

    private double shooterTime = System.currentTimeMillis();
    private double beltTime = System.currentTimeMillis();
    private boolean shooterOn = false;

    public double shooterSpeed = 0.6;

    private IO io;

    public Control(IO io){
        this.io = io;
    }

    // public void drive(double x, double y, boolean nitroSpeed) {
    //     double leftTotal = x + y;
    //     double rightTotal = x - y;
    //     // System.out.println(x + " " + y);

    //     if (nitroSpeed) {
    //         leftTotal *= 2;
    //         rightTotal *= 2;
    //     }

    //     leftFront.set(ControlMode.PercentOutput, leftTotal);
    //     leftBack.set(ControlMode.PercentOutput, leftTotal);
    //     rightFront.set(ControlMode.PercentOutput, rightTotal);
    //     rightBack.set(ControlMode.PercentOutput, rightTotal);
    // }

    public void drive(double x, double y) {
        double leftTotal = x + y;
        double rightTotal = x - y;
        // System.out.println(x + " " + y);

        leftFront.set(ControlMode.PercentOutput, leftTotal);
        leftBack.set(ControlMode.PercentOutput, leftTotal);
        rightFront.set(ControlMode.PercentOutput, rightTotal);
        rightBack.set(ControlMode.PercentOutput, rightTotal);
    }

    public void intake(boolean rollIn, boolean rollOut){
        if (rollIn) {
            intake.set(-0.5);
            if (!beltLimit.get()){
                beltTime = System.currentTimeMillis();

                while (System.currentTimeMillis() - beltTime < 2000){
                    io.updateInput();
                    drive(io.getX(), io.getY());
                    beltDrive.set(0.65);
                }

                beltDrive.set(0);
            }
        } else if (rollOut) {
            intake.set(0.2);
        } else {
            intake.set(0);
        }
    }

    public void belt(boolean up, boolean down) {
        if (up) {
            beltDrive.set(0.75);
            // System.out.println("up");
        } else if(down){
            // System.out.println("down");
            beltDrive.set(-0.6);
        } else {
            beltDrive.set(0);
        }
    }

    public void shooter(boolean shoot) {
        if (shoot) {
            // LED.set(-0.87);

            // reverse here
            if(!shooterOn){
                shooterTime = System.currentTimeMillis();
                shooterOn = true;
                belt(false, true);
                shooterBot.set(ControlMode.PercentOutput, 0.50);
                // System.out.println("move down\n\n");
            }else if ((System.currentTimeMillis() - shooterTime) < 800){
                belt(false, true);
                // System.out.println(System.currentTimeMillis() - shooterTime);
                shooterBot.set(ControlMode.PercentOutput, 0.50);
                return;
            }

            // shoot 
            belt(true, false);
            shooterTop.set(ControlMode.PercentOutput, -shooterSpeed);
            shooterBot.set(ControlMode.PercentOutput, -shooterSpeed);
            // should not need intake to go for autonomous 15s
            intake.set(-0.5);
        } else {
            // LED.set(-0.35);
            shooterOn = false;
            shooterTop.set(ControlMode.PercentOutput, 0);
            shooterBot.set(ControlMode.PercentOutput, 0);
        }
    }

    public void pneumatic(boolean out, boolean in) {
        if (out) {
            // shoot out
            // System.out.println("shoot out");
            intakeDoubleLeft.set(Value.kForward);
            intakeDoubleRight.set(Value.kForward);
        } else if (in) {
            // take it back in
            // System.out.println("back in");
            intakeDoubleLeft.set(Value.kReverse);
            intakeDoubleRight.set(Value.kReverse);
        } else {
            intakeDoubleLeft.set(Value.kOff);
            intakeDoubleRight.set(Value.kOff);
        }
    }

    public void elevator(boolean up, boolean down) {

        // System.out.println("upLimit " + upLimit.get() + " downLimit " +
        // downLimit.get());
        if (up) {
            if (!upLimit.get()) {
                // System.out.println("up break");
                elevator.set(0);
            } else {
                // System.out.println("up");
                elevator.set(-0.75);
            }
        } else if (down) {
            if (!downLimit.get()) {
                // System.out.println("down break");
                elevator.set(0);
            } else {
                // System.out.println("down");
                elevator.set(0.75);
            }
        } else {
            // System.out.println("rest");
            elevator.set(0);
        }

    }

    public void lowPower(boolean spitOut){
        if(spitOut){
            shooterTop.set(ControlMode.PercentOutput, -0.2);
            shooterBot.set(ControlMode.PercentOutput, -0.2);
            belt(true, false);
        }else{
            // this will be done under shooter() in Robot.java
            // shooterTop.set(ControlMode.PercentOutput, 0);
            // shooterBot.set(ControlMode.PercentOutput, 0);
        }
    }

}