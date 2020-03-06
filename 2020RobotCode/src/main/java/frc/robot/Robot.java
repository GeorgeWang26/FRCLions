package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private Control control = new Control();
  private IO io = new IO();
  private Autonomous autonomous = new Autonomous(io, control);

  @Override
  public void robotInit() {
    io.initCamera();
    // System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nauto pneumatic out");
    // control.pneumatic(true, false);
    // System.out.println("done auto pneumatic");
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    // autonomous.init = true;
    control.pneumatic(true, false);
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
