package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Control {

    private TalonFX leftFront = new TalonFX(1);
    private TalonFX leftBack = new TalonFX(2);
    private TalonFX rightFront = new TalonFX(3);
    private TalonFX rightBack = new TalonFX(4);

    // private CANSparkMax elevator = new CANSparkMax(1, MotorType.kBrushless);
    private CANSparkMax elevator = new CANSparkMax(1, MotorType.kBrushless);


    //private CANSparkMax shooterTop = new CANSparkMax(2, MotorType.kBrushed);
    private TalonSRX shooterTop = new TalonSRX(5);
    private CANSparkMax shooterBot = new CANSparkMax(2, MotorType.kBrushed);

    // private CANSparkMax intake = new CANSparkMax(4, MotorType.kBrushless);
    //private CANSparkMax intake = new CANSparkMax(4, MotorType.kBrushed);
    private CANSparkMax intake = new CANSparkMax(4, MotorType.kBrushed);
    
    private CANSparkMax beltDrive = new CANSparkMax(3, MotorType.kBrushed);
    // private CANSparkMax beltSpark = new CANSparkMax(1, MotorType.kBrushed);
    // private CANSparkMax beltSpark2 = new CANSparkMax(4, MotorType.kBrushed);


    // try to get rid of the line below
    // private Compressor compress = new Compressor(0);
    private DoubleSolenoid intakeDouble = new DoubleSolenoid(0, 1);

    public void drive(double x, double y) {
        double leftTotal = x + y;
        double rightTotal = x - y;

        leftFront.set(ControlMode.PercentOutput, leftTotal);
        leftBack.set(ControlMode.PercentOutput, leftTotal);
        rightFront.set(ControlMode.PercentOutput, rightTotal);
        rightBack.set(ControlMode.PercentOutput, rightTotal);
    }

    public void intake(boolean roll, boolean rollOut) {
        if (roll) {
            intake.set(-0.35);
        } else if (rollOut) {
            intake.set(0.35);
        } else {
            intake.set(0);
        }
    }

    public void shooter(boolean shoot) {
        if (shoot) {
            shooterTop.set(ControlMode.PercentOutput, 0.9);
            shooterBot.set(-1);
        } else {
            shooterTop.set(ControlMode.PercentOutput, 0);
            shooterBot.set(0);
        }
    }
    
    public void pneumatic(boolean out, boolean in) {
        if (out) {
            // shoot out
            intakeDouble.set(Value.kForward);
        } else if (in) {
            // take it back in
            intakeDouble.set(Value.kReverse);
        } else {
            intakeDouble.set(Value.kOff);
        }
    }

    public void elevator(boolean up, boolean down) {
        if (up) {
            elevator.set(-0.75);
        } else if (down) {
            elevator.set(0.75);
        } else {
            elevator.set(0);
        }
    }

    public void belt(boolean move) {
        if (move) {
            beltDrive.set(0.35);
        } else {
            beltDrive.set(0);
        }
    }
}