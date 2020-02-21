package frc.robot;

public class Autonomous {

    IO io;
    Control control;

    public Autonomous(IO io, Control control) {
        this.io = io;
        this.control = control;
    }

    public void startAuto() {
        while (true) {
            io.initX();
            if (io.xAng > 0) {
                // turn right
                control.drive(0.2, 0);
            } else if (io.xAng < 0) {
                // turn left
                control.drive(-0.2, 0);
            } else {
                break;
            }
        }

        while (true) {
            io.initY();
            if (io.yAng > 3) {
                // move back
                control.drive(0, -0.4);
            } else if (io.yAng < -3) {
                // move forward
                control.drive(0, 0.4);
            } else {
                break;
            }
        }

        control.shooter(true);
    }

}