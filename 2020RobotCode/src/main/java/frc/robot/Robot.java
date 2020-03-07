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
        System.out.println("auto loop");
        autonomous.lineUp();
    }

    @Override
    public void teleopPeriodic() {
        /**
         * x & y -- driving
         * 
         * button 1 -- autonomous line up
         *             belt drive and shooter will be activated once lined up
         * 
         * button 2 -- activate shooter as well as the belt drive
         * button 9 -- belt drive only
         * 
         * button 3 -- intake roll ball in
         * button 4 -- intake roll ball out
         * 
         * button 5 -- pneumatic push out 
         * button 6 -- pneumatic pull back
         * 
         * button 7 -- elevator go up 
         * button 8 -- elevator go down
         * 
         */

        io.updateInput();

        while (io.getButton(1)) {
            System.out.println("button1 pressed");
            autonomous.lineUp();
            io.updateInput();
        }
        
        if(io.getButton(2) || io.getButton(9)){
            control.belt(true);
        } else {
            control.belt(false);
        }

        control.shooter(io.getButton(2));

        control.drive(io.getX(), io.getY());
        control.intake(io.getButton(3), io.getButton(4));
        control.pneumatic(io.getButton(5), io.getButton(6));
        control.elevator(io.getButton(7), io.getButton(8));
    }

}
