package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

    private IO io = new IO();
    private Control control = new Control();
    private Autonomous autonomous = new Autonomous(io, control);
    boolean startUp = true;
    double startUpTime = System.currentTimeMillis();

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
        startUp = true;
    }

    @Override
    public void autonomousPeriodic() {
        // System.out.println("auto loop");
        if(startUp){
            startUpTime = System.currentTimeMillis();
            startUp = false;
            return;
        }
        if(System.currentTimeMillis() - startUpTime < 800){
            control.drive(0, -0.3);
            return;
        }
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
         * button 11 -- intake and belt in
         * button 12 -- intake and belt out
         */
        io.updateInput();
        // if(io.getButton(11)){
        //     control.shooterSpeed += 10*io.getSlider();
        // }else{
        //     control.shooterSpeed = 0.6;
        // }

        if(io.getButton(1)){
            autonomous.lineUp();
            return;
        }
        autonomous.complete = false;

        if(io.getButton(2)){
            control.shooter(true);
        } else if(io.getButton(12)){
            // control.lowPower(true);
        }else{
            control.shooter(false);
            // disable the shooter and get for belt
            control.belt(io.getButton(9), false);
        }

        // control.drive(io.getX(), io.getY(), io.getButton(11));

        control.drive(io.getX(), io.getY());
        control.intake(io.getButton(3), false);
        control.pneumatic(io.getButton(5), io.getButton(6));
        control.elevator(io.getButton(7), io.getButton(8));

    }

}
