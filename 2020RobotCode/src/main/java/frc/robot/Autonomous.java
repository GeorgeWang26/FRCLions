package frc.robot;

public class Autonomous {

    private IO io;
    private Control control;

    // public boolean init = true;

    public Autonomous(IO io, Control control) {
        this.io = io;
        this.control = control;
    }

    public void startAuto() {
        // if(init){
        //     control.pneumatic(true, false);
        //     init = false;
        // }
        while (true) {
            io.updateLimelight();
            if (io.getLimeX() > 0) {
                // turn right
                control.drive(0.2, 0);
            } else if (io.getLimeX() < 0) {
                // turn left
                control.drive(-0.2, 0);
            } else {
                break;
            }
        }

        while (true) {
            io.updateLimelight();
            if (io.getLimeY() > 3) {
                // move back
                control.drive(0, -0.4);
            } else if (io.getLimeY() < -3) {
                // move forward
                control.drive(0, 0.4);
            } else {
                break;
            }
        }

        control.shooter(true);
    }

}