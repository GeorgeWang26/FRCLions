package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import java.util.Timer;
import java.util.TimerTask;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {

  // joystick, motor, network table

  // center of the shooting area is 249cm above carpet
  final int hCenter = 249;
  // the limelight is mounted 30 cm above carpet
  // this value needs to be changed 
  final int hLimelight = 30;
  final double HIGHT = hCenter - hLimelight;
  // 40 or 70, init at 40
  int limelightAngle = 40;

  Joystick joystick = new Joystick(0);
  double x;
  double y;

  boolean button1;
  boolean button2;
  boolean button3;
  boolean button4;
  boolean button5;
  boolean button6;
  boolean button7;
  boolean button8;
  boolean button10;
  boolean button11;
  boolean button12;

  TalonSRX leftFront = new TalonSRX(0);
  TalonSRX leftBack = new TalonSRX(1);
  TalonSRX rightFront = new TalonSRX(2);
  TalonSRX rightBack = new TalonSRX(3);

  CANSparkMax shooterLeft = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
  CANSparkMax shooterRight = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);

  // pneumatics 
  Compressor compress = new Compressor(0);
  // Solenoid intake = new Solenoid(0);
  // 1 forward 2 reverse
  DoubleSolenoid intakeDouble = new DoubleSolenoid(0,1);

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");

  double xAng;
  double yAng;
  double area;



  @Override
  public void robotInit() {
    System.out.println("init");

    // c.setClosedLoopControl(true);
    // c.setClosedLoopControl(false);
    
    boolean enabled = compress.enabled();
    boolean pressureSwitch = compress.getPressureSwitchValue();
    double current = compress.getCompressorCurrent();

    System.out.println("enable " + enabled + " pressureSwitch " + pressureSwitch + " current " + current);
    // pid values config here
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    System.out.println("auto init");
    // frontTwoSec();

  }

  public void driveLoop() {
    x = joystick.getX();
    y = -joystick.getY();

    button1 = joystick.getRawButton(1);
    button2 = joystick.getRawButton(2);
    button3 = joystick.getRawButton(3);
    button4 = joystick.getRawButton(4);
    button5 = joystick.getRawButton(5);
    button6 = joystick.getRawButton(6);
    button7 = joystick.getRawButton(7);
    button8 = joystick.getRawButton(8);
    button10 = joystick.getRawButton(10);
    button11 = joystick.getRawButton(11);
    button12 = joystick.getRawButton(12);


    xAng = tx.getDouble(0.0);
    yAng = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

    // System.out.println("x angle: " + xAng + "  y angle: " + ty + "  area: " + area);
    double leftTotal = x + y;
    double rightTotal = -x-y;

    System.out.println("left   x " + x + "     y " + y + "    total " + leftTotal);
    leftFront.set(ControlMode.PercentOutput, x, DemandType.ArbitraryFeedForward, y);
    leftBack.set(ControlMode.PercentOutput, x, DemandType.ArbitraryFeedForward, y);
    System.out.println("right  x " + -x + "    y " + -y + "     total " + rightTotal);
    rightFront.set(ControlMode.PercentOutput, -x, DemandType.ArbitraryFeedForward, -y);
    rightBack.set(ControlMode.PercentOutput, -x, DemandType.ArbitraryFeedForward, -y);

    // leftFront.set(ControlMode.PercentOutput, leftTotal);
    // leftBack.set(ControlMode.PercentOutput, leftTotal);
    // rightFront.set(ControlMode.PercentOutput, rightTotal);
    // rightBack.set(ControlMode.PercentOutput, rightTotal);

    if(button1) {
      System.out.println("pressed to shoot");
      shooterLeft.set(1);
      shooterRight.set(-1);
    }else{
      System.out.println("stop shooting");
      shooterLeft.set(0);
      shooterRight.set(0);
    }

    // if(button2) {
    //   intake.set(true);
    // }else{
    //   intake.set(false);
    // }

    if(button3) {
      intakeDouble.set(kForward);
    }else if(button4) {
      intakeDouble.set(kReverse);
    }else {
      intakeDouble.set(kOff);
    }

  }

  public void autoLoop() {
    // autonomous loop
  }

  @Override
  public void autonomousPeriodic() {
    System.out.println("auto");
    autoLoop();
    System.out.println("auto ends");
  }

  @Override
  public void teleopPeriodic() {
    driveLoop();
  }

  public double getDirectDistance() {
    double d = HIGHT/Math.sin(limelightAngle + yAng);
    System.out.println(d);
    return d;
  }

  public double getHorizontalDistance() {
    double d = HIGHT/Math.tan(limelightAngle + yAng);
    System.out.println(d);
    return d;
  }

}
