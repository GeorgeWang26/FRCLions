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

    private TalonFX leftFront = new TalonFX(1);
    private TalonFX leftBack = new TalonFX(2);
    private TalonFX rightFront = new TalonFX(3);
    private TalonFX rightBack = new TalonFX(4);

    private CANSparkMax intake = new CANSparkMax(4, MotorType.kBrushless);

    private CANSparkMax beltDrive = new CANSparkMax(2, MotorType.kBrushed);
    private TalonSRX shooterTop = new TalonSRX(5);
    private CANSparkMax shooterBot = new CANSparkMax(3, MotorType.kBrushed);
    
    DigitalInput upLimit = new DigitalInput(1);
    DigitalInput downLimit = new DigitalInput(0);
    private CANSparkMax elevator = new CANSparkMax(1, MotorType.kBrushless);

    private DoubleSolenoid intakeDoubleRight = new DoubleSolenoid(6, 7);
    private DoubleSolenoid intakeDoubleLeft = new DoubleSolenoid(1, 0);


    public void drive(double x, double y) {
        double leftTotal = x + y;
        double rightTotal = x - y;
        // System.out.println(x + " " + y);

        leftFront.set(ControlMode.PercentOutput, leftTotal);
        leftBack.set(ControlMode.PercentOutput, leftTotal);
        rightFront.set(ControlMode.PercentOutput, rightTotal);
        rightBack.set(ControlMode.PercentOutput, rightTotal);
    }

    public void intake(boolean roll, boolean rollOut) {
        if (roll) {
            intake.set(-0.5);
        } else if (rollOut) {
            intake.set(0.5);
        } else {
            intake.set(0);
        }
    }

    public void shooter(boolean shoot) {
        if (shoot) {
            shooterTop.set(ControlMode.PercentOutput,-0.65);
            shooterBot.set(0.65);
            beltDrive.set(-0.5);
        } else {
            shooterTop.set(ControlMode.PercentOutput, 0);
            shooterBot.set(0);
            beltDrive.set(0);
        }
    }
    
    public void pneumatic(boolean out, boolean in) {
        if (out) {
            // shoot out
            System.out.println("shoot out");
            intakeDoubleLeft.set(Value.kForward);
            intakeDoubleRight.set(Value.kForward);
        } else if (in) {
            // take it back in
            System.out.println("back in");
            intakeDoubleLeft.set(Value.kReverse);
            intakeDoubleRight.set(Value.kReverse);
        } else {
            intakeDoubleLeft.set(Value.kOff);
            intakeDoubleRight.set(Value.kOff);
        }
    }

    public void elevator(boolean up, boolean down) {

        // System.out.println("uoLimit " + upLimit.get() + "   downLimit " + downLimit.get());
        if (up) {
            if(!upLimit.get()){
                System.out.println("up break");
                elevator.set(0);
            }else{
                //System.out.println("up");
                elevator.set(-0.75);
            }
        } else if (down) {
            if(!downLimit.get()){
                System.out.println("down break");
                elevator.set(0);
            }else{
                //System.out.println("down");
                elevator.set(0.75);
            }
        } else {
            // System.out.println("rest");
            elevator.set(0); 
        }
    }
    
}