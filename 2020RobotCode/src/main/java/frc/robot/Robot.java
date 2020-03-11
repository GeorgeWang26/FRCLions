package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

    private IO io = new IO();
    private Control control = new Control(io);
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

        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < 1000){
            control.drive(0, 1);
        }
    }

    @Override
    public void autonomousPeriodic() {
        // System.out.println("auto loop");
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

        // System.out.println("up limit (0)" + control.upLimit.get() + "  down limit (1)" + control.downLimit.get() + "  belt limit (2)"   + control.beltLimit.get());        
        // if(control.beltLimit.get() == false){
        //     System.out.println(control.beltLimit.get());
        // }
        io.updateInput();
        if(io.getButton(11)){
            control.shooterSpeed += 10*io.getSlider();
        }else{
            control.shooterSpeed = 0.6;
        }

        // autonomous.complete = false;
        while (io.getButton(1)) {
            // System.out.println("button1 pressed");
            autonomous.lineUp();
            io.updateInput();
        }
        
        if(io.getButton(2)){
            control.shooter(true);
        } else if(io.getButton(12)){
            control.lowPower(true);
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

        System.out.println("num " + io.joystick.getAxisCount() + "   0 " + io.joystick.getRawAxis(0) + "    1 " + io.joystick.getRawAxis(1) + "    2 " + io.joystick.getRawAxis(2) + "    3 " + io.joystick.getRawAxis(3));
    }

}
