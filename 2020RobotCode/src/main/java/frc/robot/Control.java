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


    private double shooterTime = System.currentTimeMillis();
    private double beltTime = System.currentTimeMillis();
    private boolean shooterOn = false;


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

        leftFront.set(ControlMode.PercentOutput, leftTotal);
        leftBack.set(ControlMode.PercentOutput, leftTotal);
        rightFront.set(ControlMode.PercentOutput, rightTotal);
        rightBack.set(ControlMode.PercentOutput, rightTotal);
    }

    boolean beltToggle = false;

    public void intake(boolean rollIn, boolean rollOut){
        if (rollIn) {
            intake.set(-0.5);
            if (!beltLimit.get()){
                // intake.set(0);
                if(beltToggle == false){
                    beltTime = System.currentTimeMillis();
                    beltToggle = true;
                }

                if (System.currentTimeMillis() - beltTime < 2500){
                    beltDrive.set(0.90);
                    System.out.println(System.currentTimeMillis() - beltTime);
                }else{
                    beltDrive.set(0);
                }

            }else{
                beltToggle = false;
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
        } else if(down){
            beltDrive.set(-0.6);
        } else {
            beltDrive.set(0);
        }
    }

    public void shooter(boolean shoot) {
        if (shoot) {

            if(!shooterOn){
                // start up run once only 
                shooterOn = true;
                shooterTime = System.currentTimeMillis();
                beltDrive.set(-0.6);
                shooterBot.set(ControlMode.PercentOutput, 0.50);
                return;

            }else if ((System.currentTimeMillis() - shooterTime) < 800){
                // 800 ms
                beltDrive.set(-0.6);

                if(System.currentTimeMillis() - shooterTime < 300){
                    shooterBot.set(ControlMode.PercentOutput, 0.50);

                }else{
                    // shooter forward 500ms
                    // both -0.55
                    shooterTop.set(ControlMode.PercentOutput, -0.55);
                    shooterBot.set(ControlMode.PercentOutput, -0.55);
                }
                return;
            }            
            
            // after 800 ms
            shooterTop.set(ControlMode.PercentOutput, -0.55);
            shooterBot.set(ControlMode.PercentOutput, -0.55);
            beltDrive.set(0.75);

        } else {
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
            beltDrive.set(0.75);
        }else{
            // this will be done under shooter() in Robot.java
            // shooterTop.set(ControlMode.PercentOutput, 0);
            // shooterBot.set(ControlMode.PercentOutput, 0);
        }
    }

}