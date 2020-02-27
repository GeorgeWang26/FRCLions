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
    io.updateInput();
    control.drive(io.getX(), io.getY());
    control.intake(io.getButton(11));
    control.belt(io.getButton(5), io.getButton(6));
    control.shooter(io.getButton(1));
    control.pneumatic(io.getButton(3), io.getButton(4));
    control.elevator(io.getButton(7), io.getButton(8));
  }

}
