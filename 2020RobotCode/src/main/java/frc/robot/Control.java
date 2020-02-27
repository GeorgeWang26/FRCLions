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
    private CANSparkMax elevator = new CANSparkMax(6, MotorType.kBrushed);


    private CANSparkMax shooterLeft = new CANSparkMax(2, MotorType.kBrushed);
    private CANSparkMax shooterRight = new CANSparkMax(3, MotorType.kBrushed);

    // private CANSparkMax intake = new CANSparkMax(4, MotorType.kBrushless);
    private CANSparkMax intake = new CANSparkMax(4, MotorType.kBrushless);

    private TalonSRX belt = new TalonSRX(5);
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

    public void intake(boolean roll){
        if(roll){
            intake.set(-1);
        }else{
            intake.set(0);
        }
    }

    public void shooter(boolean shoot) {
        if (shoot) {
            shooterLeft.set(0.75);
            shooterRight.set(-0.75);
        } else {
            shooterLeft.set(0);
            shooterRight.set(0);
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

    public void belt(boolean move, boolean reverse){
        if(move){
            belt.set(ControlMode.PercentOutput, 1);
            elevator.set(-1);
            intake.set(-0.35);
        }
        else if(reverse){
            intake.set(0.35);
        }else{
            belt.set(ControlMode.PercentOutput, 0);
            elevator.set(0);
            intake.set(0);
        }
    }
}