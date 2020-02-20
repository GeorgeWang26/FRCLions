package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.cameraserver.CameraServer;

import java.util.Timer;
import java.util.TimerTask;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;





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

  // code above not needed

  Joystick joystick = new Joystick(0);
  XboxController xbox = new XboxController(1);
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

  TalonFX leftFront = new TalonFX(1);
  TalonFX leftBack = new TalonFX(2);
  TalonFX rightFront = new TalonFX(3);
  TalonFX rightBack = new TalonFX(4);

  CANSparkMax elevator = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);

  CANSparkMax shooterLeft = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushed);
  CANSparkMax shooterRight = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushed);

  // pneumatics 
  Compressor compress = new Compressor(0);
  DoubleSolenoid intakeDouble = new DoubleSolenoid(0,1);

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");

  double xAng;
  double yAng;
  double area;

  CameraServer camServer = CameraServer.getInstance();

  @Override
  public void robotInit() {
    System.out.println("init");
    camServer.startAutomaticCapture(0);
  }

  @Override
  public void robotPeriodic() {
    // not needed right now
  }

  @Override
  public void autonomousInit() {
    System.out.println("auto init");

  }

  public void driveLoop() {
    initialSettings();
    drive(x, y);
    shooter(button1);
    pneumatic(button3, button4);
    elevator(button7, button8);
  }

  public void autoLoop() {
    // autonomous loop
    xAng = tx.getDouble(0.0);
    double turnSpeed = 0.2;
    double forwardSpeed = 0.4;
    while(true) {
      if(xAng > 0) {
        //turn right
        leftFront.set(ControlMode.PercentOutput, turnSpeed);
        leftBack.set(ControlMode.PercentOutput, turnSpeed);
        rightFront.set(ControlMode.PercentOutput, turnSpeed);
        rightBack.set(ControlMode.PercentOutput, turnSpeed);
      } else if (xAng < 0) {
        //turn left
        leftFront.set(ControlMode.PercentOutput, -turnSpeed);
        leftBack.set(ControlMode.PercentOutput, -turnSpeed);
        rightFront.set(ControlMode.PercentOutput, -turnSpeed);
        rightBack.set(ControlMode.PercentOutput, -turnSpeed);
      }else{
        break;
      }
      xAng = tx.getDouble(0.0);
    }

      yAng = ty.getDouble(0.0);
    while(true) {
      if(yAng > 3) {
        //move back
        leftFront.set(ControlMode.PercentOutput, -forwardSpeed);
        leftBack.set(ControlMode.PercentOutput, -forwardSpeed);
        rightFront.set(ControlMode.PercentOutput, forwardSpeed);
        rightBack.set(ControlMode.PercentOutput, forwardSpeed);
      } else if (yAng < -3) {
        //move forward
        leftFront.set(ControlMode.PercentOutput, forwardSpeed);
        leftBack.set(ControlMode.PercentOutput, forwardSpeed);
        rightFront.set(ControlMode.PercentOutput, -forwardSpeed);
        rightBack.set(ControlMode.PercentOutput, -forwardSpeed);
      }else{
        break;
      }
      yAng = ty.getDouble(0.0);
    }
    
    shooterLeft.set(-1);
    shooterRight.set(1);
  }

  public void drive(double x, double y){
    double leftTotal = x + y;
    double rightTotal = x - y;

    leftFront.set(ControlMode.PercentOutput, leftTotal);
    leftBack.set(ControlMode.PercentOutput, leftTotal);
    rightFront.set(ControlMode.PercentOutput, rightTotal);
    rightBack.set(ControlMode.PercentOutput, rightTotal);
  }

  public void shooter(boolean shoot){
    if(shoot) {
      shooterLeft.set(0.75);
      shooterRight.set(-0.75);
    }else{
      shooterLeft.set(0);
      shooterRight.set(0);
    }
  }

  public void pneumatic(boolean out, boolean in){
     if(out) {
      // shoot out
      intakeDouble.set(Value.kForward);
    }else if(in) {
      // take it back in
      intakeDouble.set(Value.kReverse);
    }else {
      intakeDouble.set(Value.kOff);
    }
  }

  public void elevator(boolean up, boolean down){
    if(up){
      elevator.set(1);
    }else if(down){
      elevator.set(-1);
    }else{
      elevator.set(0);
    }
  }

  public void initialSettings(){ 
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

    x = joystick.getX()*0.3;
    y = -joystick.getY()*0.8;

    xAng = tx.getDouble(0.0);
    yAng = ty.getDouble(0.0);
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
