package frc.robot;

public class Autonomous {

    private IO io;
    private Control control;
    boolean complete = false;
    boolean xFixed = false;
    boolean yFixed = false;
    boolean autoComplete;
    
    double Xincrement = 0.062;
    double Yincrement = 0.1;

    public Autonomous(IO io, Control control) {
        this.io = io;
        this.control = control;
    }
    
    public void auto() {
        if (autoComplete) {
            control.shooter(true);
            return;
        }

        io.updateLimelight();
        if (io.getLimeX() == Double.MAX_VALUE) {
            return;
        }
        if (io.getLimeX() == 0) {
            control.drive(0.1, 0);
            return;
        }

        if (io.getLimeX() > 0.5) {
            // turn right
            control.drive(Xincrement, 0);
        } else if (io.getLimeX() < -0.5) {
            // turn left
            control.drive(-Xincrement, 0);
        } else {
            xFixed = true;
            control.drive(0, 0);
        }

        if (!xFixed) {
            return;
        }

        io.updateLimelight();
        if (io.getLimeY() == Double.MAX_VALUE) {
            return;
        }
        if (io.getLimeY() > 2) {
            // move back
            control.drive(0, -Yincrement);
        } else if (io.getLimeY() < -2) {
            // move forward
            control.drive(0, Yincrement);
        } else {
            yFixed = true;
            control.drive(0, 0);
        }

        if (!yFixed) {
            return;
        }

        xFixed = false;
        yFixed = false;
        autoComplete = true;
        control.shooter(true);
    }







    public void lineUp() {
        if (complete) {
            control.shooter(true);
            return;
        }

        io.updateLimelight();
        if (io.getLimeX() == Double.MAX_VALUE) {
            return;
        }
        if (io.getLimeX() == 0) {
            control.drive(0.1, 0);
            return;
        }

        if (io.getLimeX() > 0.5) {
            // turn right
            control.drive(Xincrement, 0);
        } else if (io.getLimeX() < -0.5) {
            // turn left
            control.drive(-Xincrement, 0);
        } else {
            xFixed = true;
            control.drive(0, 0);
        }

        if (!xFixed) {
            return;
        }

        io.updateLimelight();
        if (io.getLimeY() == Double.MAX_VALUE) {
            return;
        }
        if (io.getLimeY() > 2) {
            // move back
            control.drive(0, -Yincrement);
        } else if (io.getLimeY() < -2) {
            // move forward
            control.drive(0, Yincrement);
        } else {
            yFixed = true;
            control.drive(0, 0);
        }

        if (!yFixed) {
            return;
        }

        xFixed = false;
        yFixed = false;
        complete = true;
        control.shooter(true);
    }

}