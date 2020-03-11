package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;

public class IO {

    Joystick joystick = new Joystick(0);
    private Joystick box = new Joystick(1);
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private CameraServer frontCam = CameraServer.getInstance();
    private CameraServer beltCam = CameraServer.getInstance();

    private NetworkTableEntry tx = table.getEntry("tx");
    private NetworkTableEntry ty = table.getEntry("ty");

    private double x, y;
    private double slider;
    private boolean[] buttons = new boolean[13];
    private boolean[] boxButtons = new boolean[9];
    private double xAng, yAng;

    public void initCamera() {
        frontCam.startAutomaticCapture(0);
        beltCam.startAutomaticCapture(1);
    }

    public void updateInput() {
        for (int i = 1; i < 13; i++) {
            buttons[i] = joystick.getRawButton(i);
        }
        for(int i = 1; i < 9; i++){
            boxButtons[i] = box.getRawButton(i);
        }
        x = joystick.getX();
        y = -joystick.getY();
        slider = joystick.getRawAxis(3);

        System.out.println("\n\n" + x + " " + y);

        if(x < 0.1 && x > -0.1){
        x = 0;
        }
        if(y < 0.1 && y > -0.1){
        y = 0;
        }

        x *= 0.3;
        y *= 0.8;

        updateLimelight();
    }

    public void updateLimelight() {
        xAng = tx.getDouble(Double.MAX_VALUE);
        yAng = ty.getDouble(Double.MAX_VALUE);
    }

    public boolean getButton(int buttonNum) {
        return buttons[buttonNum];
    }

    public boolean getBoxButton(int buttonNum){
        return boxButtons[buttonNum];
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSlider(){
        return slider;
    }

    public double getLimeX() {
        return xAng;
    }

    public double getLimeY() {
        return yAng;
    }

}