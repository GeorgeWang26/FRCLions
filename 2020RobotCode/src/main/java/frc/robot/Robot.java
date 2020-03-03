package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  Control control = new Control();
  IO io = new IO();
  Autonomous autonomous = new Autonomous(io, control);

  @Override
  public void robotInit() {
    io.initCamera();
    control.pneumatic(true, false);
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
    control.intake(io.getButton(11), io.getButton(12));
    control.shooter(io.getButton(1));
    control.pneumatic(io.getButton(3), io.getButton(4));
    control.elevator(io.getButton(8), io.getButton(7));
  }

}
