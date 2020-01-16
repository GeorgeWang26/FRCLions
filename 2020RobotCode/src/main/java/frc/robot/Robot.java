package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {

  // joystick, motor, network table

  Joystick joystick;
  TalonSRX leftFront;
  TalonSRX leftBack;
  TalonSRX rightFront;
  TalonSRX rightBack;
  double x;
  double y;

  @Override
  public void robotInit() {
    joystick = new Joystick(0);

    leftFront = new TalonSRX(0);
    leftBack = new TalonSRX(1);
    rightFront = new TalonSRX(2);
    rightBack = new TalonSRX(3);

    // network tables here
    // pid values config here
  }

  public void commonLoop() {
    // the number starts at 1
    x = joystick.getX();
    y = -joystick.getY();

    leftFront.set(ControlMode.PercentOutput, 0, DemandType.ArbitraryFeedForward, 1);
  }

  public void autoLoop() {
    // autonomous loop
  }

  @Override
  public void autonomousPeriodic() {
    autoLoop();
  }

  @Override
  public void teleopPeriodic() {
    commonLoop();
  }

}
