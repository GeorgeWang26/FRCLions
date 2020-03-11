package frc.robot;

public class Autonomous {

    private IO io;
    private Control control;
    // public boolean complete = false;

    public Autonomous(IO io, Control control) {
        this.io = io;
        this.control = control;
    }

    // public void autoLineup() {
    // // double Xincrement = 0.125;
    // // double Yincrement = 0.3;
    // // double xRange = 2.5;
    // // double yRange = 8;

    // // while (true) {
    // // io.updateLimelight();
    // // if (io.getLimeX() > xRange) {
    // // // turn right
    // // control.drive(Xincrement, 0);
    // // } else if (io.getLimeX() < -xRange) {
    // // // turn left
    // // control.drive(-Xincrement, 0);
    // // } else {
    // // control.drive(0, 0);
    // // break;
    // // }
    // // }

    // // while (true) {
    // // io.updateLimelight();
    // // if (io.getLimeY() > yRange) {
    // // // move back
    // // control.drive(0, -Yincrement);
    // // } else if (io.getLimeY() < -yRange) {
    // // // move forward
    // // control.drive(0, Yincrement);
    // // } else {
    // // control.drive(0, 0);
    // // break;
    // // }
    // // }

    // // control.shooter(true);

    // while (true) {
    // fakeAuto();
    // }

    // }

    public void lineUp() {
        // if (complete) {
        //     control.shooter(true);
        //     return;
        // }

        double Xincrement = 0.07;
        double Yincrement = 0.2;
        double xRange = 2;
        double yRange = 3;

        boolean xFixed = false;
        boolean yFixed = false;

        io.updateLimelight();
        System.out.println(io.getLimeX() + "  " + io.getLimeY());
        if (io.getLimeX() == Double.MAX_VALUE) {
            return;
        }
        if (io.getLimeX() == 0) {
            control.drive(Xincrement, 0);
            return;
        }

        if (io.getLimeX() > xRange) {
            // turn right
            control.drive(Xincrement, 0);
        } else if (io.getLimeX() < -xRange) {
            // turn left
            control.drive(-Xincrement, 0);
        } else {
            System.out.println("x lined up");
            xFixed = true;
            control.drive(0, 0);
        }

        if (!xFixed) {
            return;
        }
        System.out.println("x done");

        io.updateLimelight();
        if (io.getLimeY() == Double.MAX_VALUE) {
            return;
        }
        if (io.getLimeY() > 2) {
            // move back
            control.drive(0, -Yincrement);
        } else if (io.getLimeY() < -6) {
            // move forward
            control.drive(0, Yincrement);
        } else {
            yFixed = true;
            control.drive(0, 0);
        }

        if (!yFixed) {
            return;
        }
        // complete = true;
        control.shooter(true);
    }

}