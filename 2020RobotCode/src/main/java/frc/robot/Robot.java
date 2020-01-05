package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {

  // Hardware

  Joystick joystick;

  // motor

  @Override
  public void robotInit() {
    joystick = new Joystick(0);
    // network tables here
    // pid values config here
  }

  public void commonLoop() {
    // the number starts at 1
    boolean buttonNumber = joystick.getRawButton(1);
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
