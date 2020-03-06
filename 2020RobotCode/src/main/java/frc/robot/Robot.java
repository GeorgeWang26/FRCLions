package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private Control control = new Control();
  private IO io = new IO();
  private Autonomous autonomous = new Autonomous(io, control);

  @Override
  public void robotInit() {
    io.initCamera();

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    // pneumatic reach out
    control.pneumatic(true, false);
  }

  @Override
  public void autonomousPeriodic() {
    autonomous.autoLineup();
  }

  @Override
  public void teleopPeriodic() {
    io.updateInput();

    while(io.getButton(1)){
      autonomous.fakeAuto();
    }

    control.shooter(io.getButton(2));
    control.drive(io.getX(), io.getY());
    control.intake(io.getButton(3), io.getButton(4));
    control.pneumatic(io.getButton(5), io.getButton(6));
    control.elevator(io.getButton(7), io.getButton(8));
  }

}
