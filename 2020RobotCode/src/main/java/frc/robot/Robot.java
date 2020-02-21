package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  Control control = new Control();
  IO io = new IO();
  Autonomous autonomous = new Autonomous(io, control);

  @Override
  public void robotInit() {
    io.initCamera();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    autonomous.startAuto();
  }

  @Override
  public void teleopPeriodic() {
    io.initialSettings();
    control.drive(io.x, io.y);
    control.shooter(io.button1);
    control.pneumatic(io.button3, io.button4);
    control.elevator(io.button7, io.button8);
  }

}
