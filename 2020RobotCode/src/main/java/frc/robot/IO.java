package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;

public class IO {

    Joystick joystick = new Joystick(0);
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    CameraServer camServer = CameraServer.getInstance();

    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    // NetworkTableEntry ta = table.getEntry("ta");

    double x;
    double y;

    boolean button1;
    boolean button2;
    boolean button3;
    boolean button4;
    boolean button5;
    boolean button6;
    boolean button7;
    boolean button8;
    boolean button10;
    boolean button11;
    boolean button12;

    double xAng;
    double yAng;

    public void initCamera() {
        camServer.startAutomaticCapture(0);
    }

    public void initialSettings() {
        button1 = joystick.getRawButton(1);
        button2 = joystick.getRawButton(2);
        button3 = joystick.getRawButton(3);
        button4 = joystick.getRawButton(4);
        button5 = joystick.getRawButton(5);
        button6 = joystick.getRawButton(6);
        button7 = joystick.getRawButton(7);
        button8 = joystick.getRawButton(8);
        button10 = joystick.getRawButton(10);
        button11 = joystick.getRawButton(11);
        button12 = joystick.getRawButton(12);

        x = joystick.getX() * 0.3;
        y = -joystick.getY() * 0.8;

        initX();
        initY();
    }

    public void initX() {
        xAng = tx.getDouble(0.0);
    }

    public void initY() {
        yAng = ty.getDouble(0.0);
    }

}