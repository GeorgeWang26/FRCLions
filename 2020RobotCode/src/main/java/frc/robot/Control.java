package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Control {

    TalonFX leftFront = new TalonFX(1);
    TalonFX leftBack = new TalonFX(2);
    TalonFX rightFront = new TalonFX(3);
    TalonFX rightBack = new TalonFX(4);

    CANSparkMax elevator = new CANSparkMax(1, MotorType.kBrushless);

    CANSparkMax shooterLeft = new CANSparkMax(2, MotorType.kBrushed);
    CANSparkMax shooterRight = new CANSparkMax(3, MotorType.kBrushed);

    Compressor compress = new Compressor(0);
    DoubleSolenoid intakeDouble = new DoubleSolenoid(0, 1);

    public void drive(double x, double y) {
        double leftTotal = x + y;
        double rightTotal = x - y;

        leftFront.set(ControlMode.PercentOutput, leftTotal);
        leftBack.set(ControlMode.PercentOutput, leftTotal);
        rightFront.set(ControlMode.PercentOutput, rightTotal);
        rightBack.set(ControlMode.PercentOutput, rightTotal);
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
            elevator.set(1);
        } else if (down) {
            elevator.set(-1);
        } else {
            elevator.set(0);
        }
    }

}