package frc.robot;

public class Autonomous {

    private IO io;
    private Control control;

    public Autonomous(IO io, Control control) {
        this.io = io;
        this.control = control;
    }

    // public void autoLineup() {
    //     // double Xincrement = 0.125;
    //     // double Yincrement = 0.3;
    //     // double xRange = 2.5;
    //     // double yRange = 8;

    //     // while (true) {
    //     // io.updateLimelight();
    //     // if (io.getLimeX() > xRange) {
    //     // // turn right
    //     // control.drive(Xincrement, 0);
    //     // } else if (io.getLimeX() < -xRange) {
    //     // // turn left
    //     // control.drive(-Xincrement, 0);
    //     // } else {
    //     // control.drive(0, 0);
    //     // break;
    //     // }
    //     // }

    //     // while (true) {
    //     // io.updateLimelight();
    //     // if (io.getLimeY() > yRange) {
    //     // // move back
    //     // control.drive(0, -Yincrement);
    //     // } else if (io.getLimeY() < -yRange) {
    //     // // move forward
    //     // control.drive(0, Yincrement);
    //     // } else {
    //     // control.drive(0, 0);
    //     // break;
    //     // }
    //     // }

    //     // control.shooter(true);

    //     while (true) {
    //         fakeAuto();
    //     }

    // }

    public void lineUp() {
        double Xincrement = 0.125;
        double Yincrement = 0.3;
        double xRange = 2.5;
        double yRange = 8;

        boolean xFixed = false;
        boolean yFixed = false;

        io.updateLimelight();
        if (io.getLimeX() > xRange) {
            // turn right
            control.drive(Xincrement, 0);
        } else if (io.getLimeX() < -xRange) {
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
        if (io.getLimeY() > yRange) {
            // move back
            control.drive(0, -Yincrement);
        } else if (io.getLimeY() < -yRange) {
            // move forward
            control.drive(0, Yincrement);
        } else {
            yFixed = true;
            control.drive(0, 0);
        }

        if (!yFixed) {
            return;
        }

        control.shooter(true);
        control.belt(true);
    }

}