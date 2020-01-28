package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

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

  Joystick joystick;
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


  TalonSRX leftFront;
  TalonSRX leftBack;
  TalonSRX rightFront;
  TalonSRX rightBack;

  CANSparkMax shooterLeft;
  CANSparkMax shooterRight;

  NetworkTable table;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;

  double xAng;
  double yAng;
  double area;


  @Override
  public void robotInit() {
    System.out.println("init");
    joystick = new Joystick(0);

    leftFront = new TalonSRX(0);
    leftBack = new TalonSRX(1);
    rightFront = new TalonSRX(2);
    rightBack = new TalonSRX(3);

    shooterLeft = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    shooterRight = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);

    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
  
    // pid values config here
  }

  // @Override
  // public void robotPeriodic() {
  // }

  @Override
  public void autonomousInit() {
    System.out.println("suto start");
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

  // public void frontTwoSec(){
  //   leftFront.set(ControlMode.PercentOutput, 1);
  //   leftBack.set(ControlMode.PercentOutput, 1);
  //   rightFront.set(ControlMode.PercentOutput, 1);
  //   rightBack.set(ControlMode.PercentOutput, 1);
  //   Timer timer = new Timer();
  //   TimerTask forwardTwoSec = new TimerTask(){
    
  //     @Override
  //     public void run() {
  //       leftFront.set(ControlMode.PercentOutput, 0);
  //       leftBack.set(ControlMode.PercentOutput, 0);
  //       rightFront.set(ControlMode.PercentOutput, 0);
  //       rightBack.set(ControlMode.PercentOutput, 0);
  //       // might be problem here
  //       timer.cancel();
  //     }
  //   };
  //   timer.schedule(forwardTwoSec, 0, 2000);
  // }

}
